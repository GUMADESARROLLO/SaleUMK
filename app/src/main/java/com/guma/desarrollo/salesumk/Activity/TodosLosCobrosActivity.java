package com.guma.desarrollo.salesumk.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TodosLosCobrosActivity extends AppCompatActivity {
    List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
    DataBaseHelper myDB;
    SpecialAdapter adapter;
    ListView lv;
    Servidor srv;
    ClssURL Url;
    ProgressDialog pdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos_los_cobros);
        setTitle("TODOS LOS COBROS");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        myDB = new DataBaseHelper(this);
        srv = new Servidor();
        lv = (ListView) findViewById(R.id.listview_bandeja_cobro);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Push(TodosLosCobrosActivity.this);
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
        String[] from = new String[] { "RECIBO","FECHA","MONTO"};
        int[] to = new int[] { R.id.Item_Bandeja_recibo,R.id.Item_bandeja_fecha,R.id.Item_bandeja_monto};
        String[] datos = myDB.getAllcobros();
        for (int c=0;c<datos.length;c++){
            String[] valores = datos[c].split(",");
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("RECIBO", valores[0]);
            map.put("FECHA", valores[1]);
            map.put("MONTO", valores[2]);
            fillMaps.add(map);
        }
        adapter = new SpecialAdapter(this, fillMaps, R.layout.grid_item_bandeja_cobro, from, to);
        lv.setAdapter(adapter);
    }
    private void Push(final Context context){
        String[] dtsCobro = srv.PushCobros(context);
        final AsyncHttpClient Cnx = new AsyncHttpClient();
        final RequestParams paramentros = new RequestParams();
        paramentros.put("R",dtsCobro[0]);
        paramentros.put("DR",dtsCobro[1]);
        pdialog = ProgressDialog.show(context, "","Procesando, ya casi terminamos...", true);
        Cnx.post(Url.getURL_AsynRecibo(), paramentros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
                if (statusCode==200){
                    Toast.makeText(context, "Correcto", Toast.LENGTH_SHORT).show();
                    //myDB.Update(finalLogReg);
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

}
