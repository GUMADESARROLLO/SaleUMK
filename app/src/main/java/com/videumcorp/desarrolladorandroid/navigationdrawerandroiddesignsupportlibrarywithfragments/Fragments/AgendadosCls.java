package com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Fragments;


import android.app.Dialog;
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
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Activity.CrearSaleActivity;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Activity.FrmCobro_Activity;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Activity.InfoClientesActivity;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Activity.ObservacionActivity;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Adapters.SpecialAdapter;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Lib.ClssURL;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.DataBase.DataBaseHelper;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Activity.MainActivity;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.R;


import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by marangelo.php on 04/06/2016.
 */
public class AgendadosCls extends Fragment  {

    DataBaseHelper myDB;
    ListView lv;
    SearchView sv;
    ArrayAdapter<String> adapter;
    SpecialAdapter adapter2;
    TextView txt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_cls_agendados, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("AGENDA DE, ");

        myDB = new DataBaseHelper(getActivity());
        //lv = (ListView) view.findViewById(R.id.ListView1);
        sv = (SearchView) view.findViewById(R.id.searchView1);
        txt =(TextView) view.findViewById(R.id.tvLastUpdate);

        lv = (ListView) view.findViewById(R.id.listview);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearList();
                CallInve();
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
                String selectedFromList = String.valueOf(lv.getItemAtPosition(position));
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                ListView modeListView = new ListView(getActivity());
                 String Cod = ClearString(selectedFromList);
                String[] modes = new String[] { "PEDIDO", "COBRO","OBSERVACION" };
                ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1, modes);
                modeListView.setAdapter(modeAdapter);
                builder.setView(modeListView);
                final Dialog dialog = builder.create();
                dialog.show();
                Toast.makeText(getActivity(), Cod, Toast.LENGTH_SHORT).show();
                modeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        switch (position){
                            case 0:
                                Intent Mint = new Intent(getActivity(),CrearSaleActivity.class);
                                getActivity().startActivity(Mint);
                                break;
                            case 1:
                                Intent Mint2 = new Intent(getActivity(),FrmCobro_Activity.class);
                                getActivity().startActivity(Mint2);
                                break;
                            case 2:
                                Intent Mint3 = new Intent(getActivity(),ObservacionActivity.class);
                                getActivity().startActivity(Mint3);
                                break;
                        }


                        dialog.dismiss();
                    }
                });
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
    private void loadData(){
        ArrayList<String> datos = new ArrayList<String>();
        String Date="";

        String[] from = new String[] {"rowid", "art"};
        int[] to = new int[] { R.id.item1, R.id.item2};

        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();



        Cursor res =  myDB.GetData("CLIENTES");
        if (res.moveToFirst()) {

            do {
                HashMap<String, String> map = new HashMap<String, String>();
                datos.add(res.getString(1)+"\n["+res.getString(0)+"]");
                map.put("rowid", "["+res.getString(0)+"]");
                map.put("art", res.getString(1));
                fillMaps.add(map);
                Date = res.getString(8);
            } while(res.moveToNext());


        }


        txt.setText("Ultima Actualización: " + Date.toString());

        res.close();



        // fill in the grid_item layout
        adapter2 = new SpecialAdapter(getActivity(), fillMaps, R.layout.grid_item_rutas, from, to);
        lv.setAdapter(adapter2);
    }
    private void ClearList(){
        String[] datos = {"Actualizando...."};
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,datos);
        lv.setAdapter(adapter);
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
                        jsonArray.getJSONObject(i).getString("DISPO");
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

        paramentros.put("C","F17");

        Cnx.post(ClssURL.getURL_mtlc(), paramentros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode,org.apache.http.Header[] headers, byte[] responseBody) {

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
                                items[13].toString());
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
