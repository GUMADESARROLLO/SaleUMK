package com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Lib.ClssURL;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.DataBase.DataBaseHelper;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Activity.MainActivity;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.R;


import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by marangelo.php on 04/06/2016.
 */
public class Liq12Fragment extends Fragment {

    DataBaseHelper myDB;

    ListView lv;
    SearchView sv;

    ArrayAdapter<String> adapter;
    TextView txt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_liq12, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("LIQ12 [UMK]");

        myDB = new DataBaseHelper(getActivity());
        lv = (ListView) view.findViewById(R.id.ListView1);
        sv = (SearchView) view.findViewById(R.id.searchView1);
        txt =(TextView) view.findViewById(R.id.tvLastUpdate);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearList();
                CallInve();
            }
        });
        loadData();
        return view;
    }
    private void ClearList(){
        String[] datos = {"Actualizando...."};
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,datos);
        lv.setAdapter(adapter);
    }
    private void loadData(){
        ArrayList<String> datos = new ArrayList<String>();
        Cursor res =  myDB.GetData("LIQ12");
        String Date="";
        if (res.moveToFirst()) {
            do {
                datos.add(res.getString(1));
                Date = res.getString(6);
            } while(res.moveToNext());
        }
        txt.setText("Ultima Actualización: " + Date.toString());
        res.close();
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,datos);
        lv.setAdapter(adapter);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                adapter.getFilter().filter(text);
                return false;
            }
        });

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
                texto = jsonArray.getJSONObject(i).getString("ARTICULO")+ "," +
                        jsonArray.getJSONObject(i).getString("DESCRIPCION")+ "," +
                        jsonArray.getJSONObject(i).getString("DIAS_VENCIMIENTO")+ "," +
                        jsonArray.getJSONObject(i).getString("CANT_DISPONIBLE")+ "," +
                        jsonArray.getJSONObject(i).getString("fecha_vencimientoR")+ "," +
                        jsonArray.getJSONObject(i).getString("LOTE");
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
        //paramentros.put("U",User);
        //paramentros.put("P",Passw);

        Cnx.post(ClssURL.getURL_liq12(), paramentros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {

                boolean Inserted=false;
                if (statusCode==200){

                    ArrayList<String> MeEncontro = obtDatosUS(new String(responseBody));
                    SQLiteDatabase db = myDB.getWritableDatabase();
                    db.execSQL("DELETE FROM LIQ12");


                    for (int n=0; n<MeEncontro.size();n++){
                        String[] items = MeEncontro.get(n).toString().split(",");
                        Inserted = myDB.insertDataLIQ12(
                                items[0].toString(),
                                items[1].toString(),
                                items[2].toString(),
                                items[3].toString(),
                                items[4].toString(),
                                items[5].toString()
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

