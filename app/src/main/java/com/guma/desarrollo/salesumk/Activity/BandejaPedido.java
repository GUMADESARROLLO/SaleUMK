package com.guma.desarrollo.salesumk.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.guma.desarrollo.salesumk.Adapters.SpecialAdapter;
import com.guma.desarrollo.salesumk.DataBase.DataBaseHelper;
import com.guma.desarrollo.salesumk.Lib.Funciones;
import com.guma.desarrollo.salesumk.Lib.Variables;
import com.guma.desarrollo.salesumk.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BandejaPedido extends AppCompatActivity {
    Variables vrb;
    Funciones vrF;
    DataBaseHelper myDB;
    SpecialAdapter adapter;
    ListView lv;
    List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bandeja_pedido);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(vrb.getCliente_Name_recibo_factura());

        myDB = new DataBaseHelper(this);
        lv = (ListView) findViewById(R.id.listview_bandeja_pedido);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(BandejaPedido.this);
                final CharSequence[] items = {"VER", "ELIMINAR"};
                final String COD = ClearString(String.valueOf(lv.getItemAtPosition(position)),"COD=");
                builder.setTitle(COD);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("ELIMINAR")){
                            builder.setMessage("Desea Eliminar este Registro")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                        }
                                    })
                                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                        }
                                    }).create().show();
                        }else{
                            startActivity(new Intent(BandejaPedido.this, ViewTicketActivity.class).putExtra("COD",COD));
                        }



                    }
                }).create().show();

                return false;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        loadData();
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
    private String ClearString(String cadena,String Star){
        Log.d("STRING",cadena);
        String[] Posiciones = cadena.split(",");
        cadena = Posiciones[1];
        int c1 = cadena.indexOf(Star)+Star.length();
        cadena = cadena.substring(c1);
        return cadena;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        String titulo = String.valueOf(item.getTitle());

        if (id == 16908332){
            finish();
        }
        switch (titulo){
            case "add":
                Intent MenuIntent = new Intent(BandejaPedido.this,CrearSaleActivity.class);
                BandejaPedido.this.startActivity(MenuIntent);
                break;


        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cobro,menu);
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fillMaps.clear();
        loadData();
    }
}
