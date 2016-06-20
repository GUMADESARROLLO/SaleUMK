package com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ListView;

import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Adapters.AnimeAdapter;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Adapters.SpecialAdapter;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Lib.Anime;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetallesActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    ListView lv_bodegas;
    ListView lv_reglas;
    ListView lv_lote;
    ListView lv_liq;
    SpecialAdapter adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        ((DetallesActivity) this).getSupportActionBar().setTitle("Informacion Articulo");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        List<Anime> items = new ArrayList<>();

        lv_bodegas = (ListView) findViewById(R.id.listview_Bodegas);
        lv_reglas= (ListView) findViewById(R.id.listview_reglas);
        lv_lote= (ListView) findViewById(R.id.listview_lotes);
        lv_liq= (ListView) findViewById(R.id.listview_liq);

        loadData_bodegas();
        loadData_reglas();
        loadData_lote();
        loadData_liq();

        //items.add(new Anime("Bedegas", 230));
        //items.add(new Anime("Lotes", 456));
        //items.add(new Anime("Vencimiento", 342));


/*        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);
        adapter = new AnimeAdapter(items);
        recycler.setAdapter(adapter);*/
    }
    private void loadData_liq(){
        String[] from = new String[] { "lote", "vencimiento","cantidad","dias"};
        int[] to = new int[] { R.id.item_LOTE_VENCE, R.id.item_vencimiento, R.id.item_cant_vence, R.id.item_DIAS_LIQ};
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();

        for (int i= 0;i<2;i++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("lote", "[000]");
            map.put("vencimiento","[000]");
            map.put("cantidad", "[000]");
            map.put("dias","[000]");
            fillMaps.add(map);
        }
        adapter2 = new SpecialAdapter(this, fillMaps, R.layout.grid_item_articulo_liq, from, to);
        lv_liq.setAdapter(adapter2);

    }
    private void loadData_lote(){
        String[] from = new String[] { "lote", "fecha","cantidad","bodega"};
        int[] to = new int[] { R.id.item_LOTE_VENCE, R.id.item_FECHA_lote_VENCE, R.id.item_cant_lote, R.id.item_BODEGA_lote};
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();

        for (int i= 0;i<2;i++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("lote", "[000]");
            map.put("fecha","[000]");
            map.put("cantidad", "[000]");
            map.put("bodega","[000]");
            fillMaps.add(map);
        }
        adapter2 = new SpecialAdapter(this, fillMaps, R.layout.grid_item_articulo_lotes, from, to);
        lv_lote.setAdapter(adapter2);

    }
    private void loadData_bodegas(){
        String[] from = new String[] { "Bodega", "Cantidad"};
        int[] to = new int[] { R.id.item_Bodegas, R.id.item_cant_Bodegas};
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();

        for (int i= 0;i<2;i++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("Bodega", "[000]");
            map.put("Cantidad","[000]");
            fillMaps.add(map);
        }
        adapter2 = new SpecialAdapter(this, fillMaps, R.layout.grid_item_articulo_bodega, from, to);
        lv_bodegas.setAdapter(adapter2);

    }
    private void loadData_reglas(){
        String[] from = new String[] { "Reglas"};
        int[] to = new int[] { R.id.item_reglas};
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();

        for (int i= 0;i<2;i++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("Reglas","[000]");
            fillMaps.add(map);
        }
        adapter2 = new SpecialAdapter(this, fillMaps, R.layout.grid_item_articulo_reglas, from, to);
        lv_reglas.setAdapter(adapter2);

    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == 16908332){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
