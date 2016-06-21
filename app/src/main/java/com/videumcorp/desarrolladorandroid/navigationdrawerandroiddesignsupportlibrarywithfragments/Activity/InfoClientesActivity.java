package com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Activity;

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
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Lib.Anime;
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
    SpecialAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_clientes);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        lv = (ListView) findViewById(R.id.listview_info_cliente);
        lv_mora = (ListView) findViewById(R.id.listview_mora);
        loadData_Cliente();
        loadData_Cliente_mora();

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

            String[] datos ={"NOMBRE:","DIRECCION:","TELEFONO:","SALDO:","LIMITE CREDITO:","DISPONIBLE","MOROSO:"};
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

        String[] datos ={"NoVencidos:","Dias30:","Dias60:","Dias90:","Dias120:","Mas120"};
        for (int c=0;c<datos.length;c++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("Item", datos[c]);
            fillMaps.add(map);
        }
        adapter = new SpecialAdapter(this, fillMaps, R.layout.grid_item_info_cliente_mora, from, to);
        lv_mora.setAdapter(adapter);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == 16908332){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
