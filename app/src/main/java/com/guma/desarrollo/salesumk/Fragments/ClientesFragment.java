package com.guma.desarrollo.salesumk.Fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.guma.desarrollo.salesumk.Activity.BandejaPedido;
import com.guma.desarrollo.salesumk.Activity.CrearSaleActivity;
import com.guma.desarrollo.salesumk.Activity.InfoClientesActivity;
import com.guma.desarrollo.salesumk.Activity.MainActivity;
import com.guma.desarrollo.salesumk.Activity.ObservacionActivity;
import com.guma.desarrollo.salesumk.Activity.bandejaCobroActivity;
import com.guma.desarrollo.salesumk.Adapters.SpecialAdapter;
import com.guma.desarrollo.salesumk.DataBase.DataBaseHelper;
import com.guma.desarrollo.salesumk.Lib.ClssURL;
import com.guma.desarrollo.salesumk.Lib.Variables;
import com.guma.desarrollo.salesumk.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ClientesFragment extends Fragment {

    DataBaseHelper myDB;
    Variables myVar;
    ListView lv;
    SearchView sv;

    SpecialAdapter adapter2;
    TextView txt;

    ProgressDialog pdialog;
    List<HashMap<String, String>> fillMaps;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_clientes, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("MASTER DE CLIENTES");

        myDB = new DataBaseHelper(getActivity());
        lv = (ListView) view.findViewById(R.id.listview);
        sv = (SearchView) view.findViewById(R.id.searchView1);
        txt =(TextView) view.findViewById(R.id.tvLastUpdate);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallInve();
                loadData();
            }
        });


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

                final String Cod = ClearString(String.valueOf(lv.getItemAtPosition(position)));
                final String NombreCod = getNameString(String.valueOf(lv.getItemAtPosition(position)),"namecliente=");
                Toast.makeText(getActivity(), NombreCod, Toast.LENGTH_SHORT).show();
                final CharSequence[]items = { "PEDIDO", "COBRO"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


                if (Cod.equals("0")){
                    Toast.makeText(getActivity(), "Tiene que Sincronizar el Dispositivo", Toast.LENGTH_SHORT).show();
                }else{

                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, final int item) {
                            //vrb.setCliente_Name_recibo_factura(childText.getText().toString());
                            myVar.setCliente_recibo_factura(Cod);
                            myVar.setCliente_Name_recibo_factura(NombreCod);

                            switch (item){
                                case 0:
                                    startActivity(new Intent(getActivity(),BandejaPedido.class));
                                    break;
                                case 1:
                                    startActivity(new Intent(getActivity(),bandejaCobroActivity.class));
                                    break;
                            }

                        }
                    }).create().show();

                    /*myVar.setInv_Cliente(Cod);
                    Intent mint = new Intent(getActivity(),InfoClientesActivity.class);
                    getActivity().startActivity(mint);*/
                }



            }
        });
        loadData();



        return view;
    }
    private String ClearString(String cadena){
        int c1 = cadena.indexOf("[");
        int c2 = cadena.indexOf("]");
        cadena = cadena.substring(c1+1,c2);
        return cadena;
    }
    private String getNameString(String cadena,String Star){
        String[] Posiciones = cadena.split(",");
        cadena = Posiciones[0];
        int c1 = cadena.indexOf(Star)+Star.length();
        cadena = cadena.substring(c1);
        return cadena;
    }

    private void loadData(){
        ArrayList<String> datos = new ArrayList<String>();
        String Date="";

        String[] from = new String[] {"rowid", "namecliente", "telefono"};
        int[] to = new int[] { R.id.item1, R.id.item2, R.id.item3};

        fillMaps = new ArrayList<HashMap<String, String>>();

        Cursor res =  myDB.GetData("CLIENTES");
        if (res.getCount()!=0){
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
        }else{
            HashMap<String, String> map = new HashMap<String, String>();
            datos.add("");
            map.put("rowid", "[0]");
            map.put("namecliente", "SIN DATOS");
            map.put("telefono","");
            fillMaps.add(map);
            Date = "00/00/0000";

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
                        jsonArray.getJSONObject(i).getString("SALDO")+ "," +
                        jsonArray.getJSONObject(i).getString("FECHA_VENCE");
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
        paramentros.put("C",myVar.getIdVendedor());

        pdialog = ProgressDialog.show(getActivity(), "","Procesando. Porfavor Espere...", true);

        Cnx.post(ClssURL.getURL_mtlc(), paramentros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                boolean Inserted=false;
                if (statusCode==200){

                    ArrayList<String> MeEncontro = obtDatosUS(new String(responseBody));
                    SQLiteDatabase db = myDB.getWritableDatabase();
                    db.execSQL("DELETE FROM CLIENTES");


                    for (int n=0; n<MeEncontro.size();n++){
                        String[] items = MeEncontro.get(n).split(",");
                        Inserted = myDB.insertDataCliente(
                                items[0],
                                items[1],
                                items[2],
                                items[3],
                                items[4],
                                items[5],
                                items[6],
                                items[7],
                                items[8],
                                items[9],
                                items[10],
                                items[11],
                                items[12],
                                items[13]

                        );
                    }

                    if (Inserted){
                    }else{
                        adapter2.notifyDataSetChanged();
                        pdialog.dismiss();
                        Error404("Error de Actualizacion de datos");
                    }

                }else{
                    adapter2.notifyDataSetChanged();
                    pdialog.dismiss();
                    Error404("Sin Cobertura de datos.");
                }


            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                adapter2.notifyDataSetChanged();
                pdialog.dismiss();
                Error404("Sin Cobertura de datos.");
            }
        });


        Cnx.post(ClssURL.getURL_Factura(), paramentros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                boolean Inserted=false;
                if (statusCode==200){

                    ArrayList<String> MeEncontro = obtDatosFacturas(new String(responseBody));
                    SQLiteDatabase db = myDB.getWritableDatabase();
                    db.execSQL("DELETE FROM FACTURAS");
                    for (int n=0; n<MeEncontro.size();n++){
                        String[] items = MeEncontro.get(n).split(",");
                        Inserted = myDB.insertFacturas(items[0],items[1],items[2],items[3],items[4],items[5]);
                    }

                    if (Inserted){
                        fillMaps.clear();
                        adapter2.notifyDataSetChanged();
                        pdialog.dismiss();
                        loadData();
                        Toast.makeText(getActivity(), "Actualización completada",Toast.LENGTH_SHORT).show();

                    }else{
                        adapter2.notifyDataSetChanged();
                        pdialog.dismiss();
                        Error404("Error de Actualizacion de datos");
                    }

                }else{
                    adapter2.notifyDataSetChanged();
                    pdialog.dismiss();
                    Error404("Sin Cobertura de datos.");
                }


            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                adapter2.notifyDataSetChanged();
                pdialog.dismiss();
                Error404("Sin Cobertura de datos.");
            }
        });
    }


}
