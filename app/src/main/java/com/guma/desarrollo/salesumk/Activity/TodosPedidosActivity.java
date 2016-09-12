package com.guma.desarrollo.salesumk.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.guma.desarrollo.salesumk.Adapters.SpecialAdapter;
import com.guma.desarrollo.salesumk.DataBase.DataBaseHelper;
import com.guma.desarrollo.salesumk.Lib.ClssURL;
import com.guma.desarrollo.salesumk.Lib.Funciones;
import com.guma.desarrollo.salesumk.Lib.Servidor;
import com.guma.desarrollo.salesumk.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TodosPedidosActivity extends AppCompatActivity {
    List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
    DataBaseHelper myDB;
    FloatingActionButton fab;
    SpecialAdapter adapter;
    ListView lv;
    Funciones vrF;
    Servidor srv;
    ClssURL Url;
    ProgressDialog pdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos_pedidos);
        setTitle("TODOS LOS PEDIDOS");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        vrF = new Funciones();
        srv = new Servidor();
        myDB = new DataBaseHelper(this);
        lv = (ListView) findViewById(R.id.listview_bandeja_pedido);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Push(TodosPedidosActivity.this);
            }
        });
        loadData();
    }
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == 16908332){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void loadData() {
        String[] from = new String[] { "COD","CCLIENTE","FECHA","MONTO","ESTADO"};
        int[] to = new int[] { R.id.Item_PBandeja_Cod,R.id.Item_PBandeja_Cliente,R.id.Item_PBandeja_fecha,R.id.Item_PBandeja_Monto,R.id.Item_PBandeja_Estado};
        String[] datos = myDB.GetPedido();

        for (int c=0;c<datos.length;c++){
            String[] valores = datos[c].split(",");
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("COD", valores[0]);
            map.put("CCLIENTE", valores[1]);
            map.put("FECHA", valores[2]);
            map.put("MONTO", "C$ " + vrF.number_format(Float.parseFloat(valores[3]),2));
            map.put("ESTADO", valores[4]);
            fillMaps.add(map);
        }
        adapter = new SpecialAdapter(this, fillMaps, R.layout.grid_item_bandeja_pedido, from, to);
        lv.setAdapter(adapter);
    }
    private void Push(final Context context){
        final String[] dtsPedidos = srv.PushPedidos(context);
        final AsyncHttpClient Cnx = new AsyncHttpClient();
        final RequestParams paramentros = new RequestParams();
        paramentros.put("P",dtsPedidos[0]);
        paramentros.put("D",dtsPedidos[1]);
        pdialog = ProgressDialog.show(context, "","Procesando, ya casi terminamos...", true);
        Cnx.post(Url.getURL_Pedido(), paramentros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
                if (statusCode==200){

                    ArrayList<String> updateSQL = updatePedidos(new String(responseBody));
                    String[] items = updateSQL.get(0).split(",");
                    SQLiteDatabase db = myDB.getWritableDatabase();
                    if (Integer.parseInt(items[0])!=0){
                        db.execSQL(items[1]);
                    }
                    db.close();

                    myDB.UpdateRecord("Pedido","Estado","IdPedido",1,dtsPedidos[2]);


                    /*SQLiteDatabase db = myDB.getWritableDatabase();
                    db.execSQL("UPDATE Pedido SET Estado =0");
                    db.close();*/
                    vrF.msg(TodosPedidosActivity.this,"La informacion fue Ingresada correctamente");
                    pdialog.dismiss();
                }else{
                    pdialog.dismiss();
                    Toast.makeText(context, "No hay respuesta del servidor", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {
                pdialog.dismiss();
                Toast.makeText(context, "Problemas de Cobertura de datos", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public ArrayList<String> updatePedidos(String response){
        ArrayList<String> listado = new ArrayList<String>();
        try{
            JSONArray jsonArray = new JSONArray(response);
            String texto;

            for (int i=0; i<jsonArray.length(); i++){
                texto = jsonArray.getJSONObject(i).getInt("count")+ "," +
                        jsonArray.getJSONObject(i).getString("SQL");
                listado.add(texto);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return listado;

    }

}
