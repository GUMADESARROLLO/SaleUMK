package com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Adapters.AnimeAdapter;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Adapters.SpecialAdapter;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.DataBase.DataBaseHelper;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Lib.Anime;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Lib.Variables;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InfoClientesActivity extends AppCompatActivity {

    /*private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;*/

    ListView lv;
    ListView lv_mora;
    ListView lv_factura;
    SpecialAdapter adapter;
    Variables myVa;
    DataBaseHelper myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_clientes);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("INFORMACION DEL CLIENTE");

        myDB = new DataBaseHelper(InfoClientesActivity.this);

        lv = (ListView) findViewById(R.id.listview_info_cliente);
        lv_mora = (ListView) findViewById(R.id.listview_mora);
        lv_factura = (ListView) findViewById(R.id.list_item_facturas);

        loadData_Cliente();
        loadData_Cliente_mora();
        loadData_Cliente_Factura();

        /*List<Anime> items = new ArrayList<>();
        items.add(new Anime("Bedegas", 230));
        items.add(new Anime("Lotes", 456));
        items.add(new Anime("Vencimiento", 342));


        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);
        adapter = new AnimeAdapter(items);
        recycler.setAdapter(adapter);*/


    }

    private void loadData_Cliente() {
        String[] from = new String[] { "Item"};
        int[] to = new int[] { R.id.item_infoCliente};
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        String[] datos = new String[0];


        Cursor res = myDB.InfoCliente(myVa.getInv_Cliente());
        if (res.getCount()==0){
            datos = new String[]{"NOMBRE:", "DIRECCION:", "TELEFONO:", "SALDO:", "LIMITE CREDITO:", "DISPONIBLE", "MOROSO:"};

        }else{
            if (res.moveToFirst()) {
                do {
                    datos = new String[]{
                            "NOMBRE: " + res.getString(1),
                            "DIRECCION: "+ res.getString(2),
                            "TELEFONO: "+ res.getString(3),
                            "SALDO: "+ res.getString(6),
                            "LIMITE CREDITO: "+ res.getString(5),
                            "DISPONIBLE: "+ res.getString(7),
                            "MOROSO: "+ res.getString(4)};
                } while(res.moveToNext());
            }

        }


        for (int c=0;c<datos.length;c++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("Item", datos[c]);
            fillMaps.add(map);
        }
        adapter = new SpecialAdapter(this, fillMaps, R.layout.grid_item_info_cliente, from, to);
        lv.setAdapter(adapter);
    }
    private void loadData_Cliente_mora() {
        String[] from = new String[] { "Item"};
        int[] to = new int[] { R.id.item_infoCliente_mora};
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();

        String[] datos = new String[0];


        Cursor res = myDB.InfoCliente(myVa.getInv_Cliente());
        if (res.getCount()==0){
            datos = new String[]{"NoVencidos:","Dias30:","Dias60:","Dias90:","Dias120:","Mas120"};

        }else{
            if (res.moveToFirst()) {
                do {
                    datos = new String[]{
                            "NoVencidos: " + res.getString(9),
                            "Dias30: "+ res.getString(10),
                            "Dias60: "+ res.getString(11),
                            "Dias90: "+ res.getString(12),
                            "Dias120: "+ res.getString(13),
                            "Mas120: "+ res.getString(14)};
                } while(res.moveToNext());
            }
        }

        for (int c=0;c<datos.length;c++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("Item", datos[c]);
            fillMaps.add(map);
        }
        adapter = new SpecialAdapter(this, fillMaps, R.layout.grid_item_info_cliente_mora, from, to);
        lv_mora.setAdapter(adapter);
    }
    private void loadData_Cliente_Factura() {

        String[] from = new String[] {"Factura","Monto","Saldo"};
        int[] to = new int[] { R.id.Item_Factura,R.id.Item_Monto,R.id.Item_Saldo};
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();


        Cursor res = myDB.InfoClienteFactura(myVa.getInv_Cliente());
        if (res.getCount()==0){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("Factura", "");
            map.put("Monto", "");
            map.put("Saldo", "");
            fillMaps.add(map);

        }else{
            if (res.moveToFirst()) {
                do {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("Factura", res.getString(0));
                    map.put("Monto", res.getString(4));
                    map.put("Saldo", res.getString(3));
                    fillMaps.add(map);
                } while(res.moveToNext());
            }
        }


        adapter = new SpecialAdapter(this, fillMaps, R.layout.grid_item_infocl_factura, from, to);
        lv_factura.setAdapter(adapter);

    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == 16908332){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
