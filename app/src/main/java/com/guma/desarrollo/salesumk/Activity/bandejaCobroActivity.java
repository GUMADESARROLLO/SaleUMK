package com.guma.desarrollo.salesumk.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.guma.desarrollo.salesumk.Adapters.SpecialAdapter;
import com.guma.desarrollo.salesumk.DataBase.DataBaseHelper;
import com.guma.desarrollo.salesumk.Lib.ClssURL;
import com.guma.desarrollo.salesumk.Lib.Funciones;
import com.guma.desarrollo.salesumk.Lib.Variables;
import com.guma.desarrollo.salesumk.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class bandejaCobroActivity extends AppCompatActivity {

    Variables vrb;
    DataBaseHelper myDB;
    Funciones vrf;
    private  String SqlSyncInsert;
    private  String SqlSyncInsertDetalles;

    ProgressDialog pdialog;

    List<HashMap<String, String>> fillMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandeja_cobro);

        //ASGINACION DE TITULOS AL ACTIVITY
        setTitle("COBROS REALIZADOS");
        vrb.getFillMapsBandejaCobro().clear();

        //INICIALIZADOR DEL LISTADO DE
        vrb.setLv_list_facturaCobroBandejaCobro((ListView) findViewById(R.id.listview_bandeja_cobro));

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }



        myDB = new DataBaseHelper(bandejaCobroActivity.this);
        TextView txtClienteCobro = (TextView) findViewById(R.id.txtLblClienteCobro);

        //CODIGO DE CLIENTE PARA BUSCAR LOS RECIBOS
        txtClienteCobro.setText(vrb.getCliente_Name_recibo_factura());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ENCABEZADOS DE LOS RECIBOS
                Cursor res = myDB.GetAllRecibo();
                if (res.getCount()!=0){
                    SqlSyncInsert = "INSERT INTO recibo (IdRecibo, IdCliente, Vendedor, Fecha, MRecibo, TC, TM, Recibimos, LCantidad, Concepto, Efectivo, CHK, NumCHK, Banco) VALUES";
                    if (res.moveToFirst()){
                        do {
                            SqlSyncInsert +=
                                    "("+
                                    "'"+res.getString(0)+"',"+
                                            "'"+res.getString(1)+"',"+
                                            "'"+res.getString(2)+"',"+
                                            "'"+vrf.DateFormat(res.getString(3))+"',"+
                                            "'"+res.getString(4)+"',"+
                                            "'"+res.getString(5)+"',"+
                                            "'"+res.getString(6)+"',"+
                                            "'"+res.getString(7)+"',"+
                                            "'"+res.getString(8)+"',"+
                                            "'"+res.getString(9)+"',"+
                                            "'"+res.getString(10)+"',"+
                                            "'"+res.getString(11)+"',"+
                                            "'"+res.getString(12)+"',"+
                                            "'"+res.getString(13)+"'"
                                            +"),";
                        } while(res.moveToNext());
                        SqlSyncInsert = SqlSyncInsert.substring(0,SqlSyncInsert.length()-1);
                    }
                }

                //DETALLES DE LOS RECIBOS
                Cursor ResDetalle = myDB.GetAllRDetalle();
                if (ResDetalle.getCount()!=0){
                    SqlSyncInsertDetalles = "INSERT INTO rdetalle (IdRecibo, NFactura, FValor, ValorNC, Retencion, Descuento, VRecibo, Saldo) VALUES";
                    if (ResDetalle.moveToFirst()){
                        do {
                            SqlSyncInsertDetalles +=
                                    "("+
                                            "'"+ResDetalle.getString(0)+"',"+
                                            "'"+ResDetalle.getString(1)+"',"+
                                            "'"+ResDetalle.getString(2)+"',"+
                                            "'"+ResDetalle.getString(3)+"',"+
                                            "'"+ResDetalle.getString(4)+"',"+
                                            "'"+ResDetalle.getString(5)+"',"+
                                            "'"+ResDetalle.getString(6)+"',"+
                                            "'"+ResDetalle.getString(7)+"'"
                                            +"),";
                        } while(ResDetalle.moveToNext());
                        SqlSyncInsertDetalles = SqlSyncInsertDetalles.substring(0,SqlSyncInsertDetalles.length()-1);
                    }
                }
                Push(SqlSyncInsert,SqlSyncInsertDetalles);
            }
        });

        vrb.getLv_list_facturaCobroBandejaCobro().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                vrb.setIdViewRecibo(ClearString(String.valueOf(vrb.getLv_list_facturaCobroBandejaCobro().getItemAtPosition(position))));



                final AlertDialog.Builder builder = new AlertDialog.Builder(bandejaCobroActivity.this);
                final CharSequence[] items = {"REVISAR", "ELIMINAR"};
                builder.setTitle( vrb.getIdViewRecibo());
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("ELIMINAR")){
                            builder.setMessage("Desea Eliminar este Registro")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            if (myDB.DeleteRecibo(vrb.getIdViewRecibo()) == true){
                                                Toast.makeText(bandejaCobroActivity.this, "Se Elimino Correctamente el Recibo", Toast.LENGTH_LONG).show();
                                                vrb.getFillMapsBandejaCobro().remove(position);
                                                vrb.getAdapterBandejaCobro().notifyDataSetChanged();

                                            }else{
                                                Toast.makeText(bandejaCobroActivity.this, "No se pudo Eliminar el recibo", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    })
                                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                        }
                                    }).create().show();
                        }else{
                            Intent MenuIntent = new Intent(bandejaCobroActivity.this,ViewFrmCobro.class);
                            bandejaCobroActivity.this.startActivity(MenuIntent);
                        }



                    }
                }).create().show();

                /**/

            }
        });
        loadData();
    }

    private String ClearString(String cadena){
        String Star="RECIBO=";
        int c1 = cadena.indexOf(Star)+Star.length();
        int c2 = cadena.indexOf("}");
        cadena = cadena.substring(c1,c2);
        return cadena;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        String titulo = String.valueOf(item.getTitle());

        if (id == 16908332){
            vrb.getFillMapsBandejaCobro().clear();
            finish();
        }
        switch (titulo){
            case "add":
                Intent MenuIntent = new Intent(bandejaCobroActivity.this,FrmCobro_Activity.class);
                bandejaCobroActivity.this.startActivity(MenuIntent);
                finish();
                break;


        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cobro,menu);
        return true;
    }
    private void loadData() {
        String[] from = new String[] { "RECIBO","FECHA","MONTO"};
        int[] to = new int[] { R.id.Item_Bandeja_recibo,R.id.Item_bandeja_fecha,R.id.Item_bandeja_monto};
        String[] datos = myDB.GetCobros(vrb.getCliente_recibo_factura());
        for (int c=0;c<datos.length;c++){
            String[] valores = datos[c].split(",");
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("RECIBO", valores[0]);
            map.put("FECHA", valores[1]);
            map.put("MONTO", valores[2]);
            vrb.getFillMapsBandejaCobro().add(map);
        }
        //vrb.adapter = new SpecialAdapter(this, vrb.getFillMapsBandejaCobro(), R.layout.grid_item_bandeja_cobro, from, to);
        //lv.setAdapter(adapter);

        vrb.setAdapterBandejaCobro(new SpecialAdapter(this, vrb.getFillMapsBandejaCobro(), R.layout.grid_item_bandeja_cobro, from, to));
        vrb.getLv_list_facturaCobroBandejaCobro().setAdapter(vrb.getAdapterBandejaCobro());





    }
    private void Push(String SqlPush,String SqlPushDetalle){
        AsyncHttpClient Cnx = new AsyncHttpClient();
        RequestParams PushDataRecibo = new RequestParams();
        RequestParams PushDataRDetalle = new RequestParams();
        PushDataRecibo.put("D",SqlPush);
        PushDataRDetalle.put("D",SqlPushDetalle);

        pdialog = ProgressDialog.show(bandejaCobroActivity.this, "","Procesando. Porfavor Espere...", true);
        Cnx.post(ClssURL.getURL_doom(), PushDataRecibo, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200){
                    SQLiteDatabase db = myDB.getWritableDatabase();
                    db.execSQL("DELETE FROM Recibo");
                }else{
                    Error("Problemas de Conexion al Servidor de Recibos");
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Error("Problemas de Conexion al Servidor Recibos");
            }
        });

        Cnx.post(ClssURL.getURL_doom(), PushDataRDetalle, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200){

                    vrb.getFillMapsBandejaCobro().clear();
                    vrb.getAdapterBandejaCobro().notifyDataSetChanged();
                    pdialog.dismiss();
                    Error("La Informacion Fue Ingresada Al Servidor");

                    SQLiteDatabase db = myDB.getWritableDatabase();
                    db.execSQL("DELETE FROM RDetalle");
                }else{
                    Error("Problemas de Conexion al Servidor de detalle");
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Error("Problemas de Conexion al Servidor de detalle");
            }
        });





    }
    public void Error(String TipoError){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(bandejaCobroActivity.this);
        builder.setMessage(TipoError)
                .setNegativeButton("OK",null)
                .create()
                .show();
    }


}

