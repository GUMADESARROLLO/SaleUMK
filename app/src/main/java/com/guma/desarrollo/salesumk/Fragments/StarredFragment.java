package com.guma.desarrollo.salesumk.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.guma.desarrollo.salesumk.Activity.InfoClientesActivity;
import com.guma.desarrollo.salesumk.Adapters.SpecialAdapter;
import com.guma.desarrollo.salesumk.Lib.ClssURL;
import com.guma.desarrollo.salesumk.DataBase.DataBaseHelper;
import com.guma.desarrollo.salesumk.Activity.MainActivity;
import com.guma.desarrollo.salesumk.Lib.Variables;
import com.guma.desarrollo.salesumk.R;

import org.apache.http.Header;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class StarredFragment extends Fragment {

    DataBaseHelper myDB;
    Variables myVar;
    ListView lv;
    SearchView sv;
    ArrayAdapter<String> adapter;
    SpecialAdapter adapter2;
    TextView txt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_starred, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("MASTER DE CLIENTES");

        myDB = new DataBaseHelper(getActivity());
        lv = (ListView) view.findViewById(R.id.listview);
        sv = (SearchView) view.findViewById(R.id.searchView1);
        txt =(TextView) view.findViewById(R.id.tvLastUpdate);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearList();
                CallInve();
                loadData();
            }
        });
        loadData();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                adapter2.getFilter().filter(text);
                return false;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFromList = String.valueOf(lv.getItemAtPosition(position));
                //String codigo = selectedFromList.substring(selectedFromList.indexOf("[")+1, selectedFromList.length()-1);
                String Cod = ClearString(selectedFromList);
                myVar.setInv_Cliente(Cod);
                //Toast.makeText(getActivity(), Cod, Toast.LENGTH_SHORT).show();
                Intent mint = new Intent(getActivity(),InfoClientesActivity.class);
                getActivity().startActivity(mint);

            }
        });



        return view;
    }
    private String ClearString(String cadena){
        int c1 = cadena.indexOf("[");
        int c2 = cadena.indexOf("]");
        cadena = cadena.substring(c1+1,c2);
        return cadena;
    }

    private void ClearList(){
        String[] datos = {"Actualizando...."};
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,datos);
        lv.setAdapter(adapter);
    }
    private void loadData(){
        ArrayList<String> datos = new ArrayList<String>();
        String Date="";

        String[] from = new String[] {"rowid", "namecliente", "telefono"};
        int[] to = new int[] { R.id.item1, R.id.item2, R.id.item3};

        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();

        Cursor res =  myDB.GetData("CLIENTES");
        if (res.moveToFirst()) {

            do {
                HashMap<String, String> map = new HashMap<String, String>();
                datos.add(res.getString(1)+"\n["+res.getString(0)+"]");
                map.put("rowid", "["+res.getString(0)+"]");
                map.put("namecliente", res.getString(1));
                map.put("telefono",res.getString(3));
                fillMaps.add(map);
                Date = res.getString(8);
            } while(res.moveToNext());


        }


        txt.setText("Ultima Actualización: " + Date.toString());

        res.close();

        adapter2 = new SpecialAdapter(getActivity(), fillMaps, R.layout.grid_item, from, to);
        lv.setAdapter(adapter2);
    }
    public void Error404(String TipoError){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(TipoError)
                .setNegativeButton("OK",null)
                .create()
                .show();
    }
    public ArrayList<String> obtDatosUS(String response){
        ArrayList<String> listado = new ArrayList<String>();
        try{
            JSONArray jsonArray = new JSONArray(response);
            String texto;

            for (int i=0; i<jsonArray.length(); i++){
                texto = jsonArray.getJSONObject(i).getString("CLIENTE")+ "," +
                        jsonArray.getJSONObject(i).getString("NOMBRE")+ "," +
                        jsonArray.getJSONObject(i).getString("DIRECCION")+ "," +
                        jsonArray.getJSONObject(i).getString("TELEFONO1")+ "," +
                        jsonArray.getJSONObject(i).getString("MOROSO")+ "," +
                        jsonArray.getJSONObject(i).getString("LIMITE_CREDITO")+ "," +
                        jsonArray.getJSONObject(i).getString("SALDO")+ "," +
                        jsonArray.getJSONObject(i).getString("DISPO")+ "," +
                        jsonArray.getJSONObject(i).getString("NoVencidos")+ "," +
                        jsonArray.getJSONObject(i).getString("Dias30")+ "," +
                        jsonArray.getJSONObject(i).getString("Dias60")+ "," +
                        jsonArray.getJSONObject(i).getString("Dias90")+ "," +
                        jsonArray.getJSONObject(i).getString("Dias120")+ "," +
                        jsonArray.getJSONObject(i).getString("Mas120");
                listado.add(texto);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return listado;

    }
    public ArrayList<String> obtDatosFacturas(String response){
        ArrayList<String> listado = new ArrayList<String>();
        try{
            JSONArray jsonArray = new JSONArray(response);
            String texto;

            for (int i=0; i<jsonArray.length(); i++){
                texto = jsonArray.getJSONObject(i).getString("FACTURA")+ "," +
                        jsonArray.getJSONObject(i).getString("CLIENTE")+ "," +
                        jsonArray.getJSONObject(i).getString("VENDEDOR")+ "," +
                        jsonArray.getJSONObject(i).getString("MONTO")+ "," +
                        jsonArray.getJSONObject(i).getString("SALDO");
                listado.add(texto);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return listado;

    }
    public void CallInve(){
        AsyncHttpClient Cnx = new AsyncHttpClient();
        RequestParams paramentros = new RequestParams();
        Intent intent = getActivity().getIntent();
        String Vendedor = intent.getStringExtra("Vendedor");
        paramentros.put("C",myVar.getIdVendedor());

        Cnx.post(ClssURL.getURL_mtlc(), paramentros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                boolean Inserted=false;
                if (statusCode==200){

                    ArrayList<String> MeEncontro = obtDatosUS(new String(responseBody));
                    SQLiteDatabase db = myDB.getWritableDatabase();
                    db.execSQL("DELETE FROM CLIENTES");


                    for (int n=0; n<MeEncontro.size();n++){
                        String[] items = MeEncontro.get(n).toString().split(",");
                        Inserted = myDB.insertDataCliente(
                                items[0].toString(),
                                items[1].toString(),
                                items[2].toString(),
                                items[3].toString(),
                                items[4].toString(),
                                items[5].toString(),
                                items[6].toString(),
                                items[7].toString(),
                                items[8].toString(),
                                items[9].toString(),
                                items[10].toString(),
                                items[11].toString(),
                                items[12].toString(),
                                items[13].toString()

                        );
                    }

                    if (Inserted == true){
                    }else{
                        Error404("Error de Actualizacion de datos");
                    }

                }else{
                    Error404("Sin Cobertura de datos.");
                }


            }
            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {
                Error404("Sin Cobertura de datos.");
            }
        });


        // TODO: 30/07/2016 QUEDA PENDIENTE AGREGAR LA FECHA DE VENCIMIENTO DE LA FACTURA
        Cnx.post(ClssURL.getURL_Factura(), paramentros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                boolean Inserted=false;
                if (statusCode==200){

                    ArrayList<String> MeEncontro = obtDatosFacturas(new String(responseBody));
                    SQLiteDatabase db = myDB.getWritableDatabase();
                    db.execSQL("DELETE FROM FACTURAS");
                    for (int n=0; n<MeEncontro.size();n++){
                        String[] items = MeEncontro.get(n).toString().split(",");
                        Inserted = myDB.insertFacturas(
                                items[0].toString(),
                                items[1].toString(),
                                items[2].toString(),
                                items[3].toString(),
                                items[4].toString()

                        );
                    }

                    if (Inserted == true){
                        loadData();
                        Toast.makeText(getActivity(), "Actualización completada",Toast.LENGTH_SHORT).show();

                    }else{
                        Error404("Error de Actualizacion de datos");
                    }

                }else{
                    Error404("Sin Cobertura de datos.");
                }


            }
            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {
                Error404("Sin Cobertura de datos.");
            }
        });
    }


}
