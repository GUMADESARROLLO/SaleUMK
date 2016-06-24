package com.videumcorp.desarrolladorandroid.salesumk.Activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.videumcorp.desarrolladorandroid.salesumk.Adapters.SpecialAdapter;
import com.videumcorp.desarrolladorandroid.salesumk.DataBase.DataBaseHelper;
import com.videumcorp.desarrolladorandroid.salesumk.Lib.Variables;
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

    Variables myVa;
    DataBaseHelper myDB;

    String Bodegas;
    String Reglas;
    String txtTitulo;
    TextView titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        ((DetallesActivity) this).getSupportActionBar().setTitle("INFORMACION ARTICULO");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        myDB = new DataBaseHelper(DetallesActivity.this);

        lv_bodegas = (ListView) findViewById(R.id.listview_Bodegas);
        lv_reglas= (ListView) findViewById(R.id.listview_reglas);
        lv_lote= (ListView) findViewById(R.id.listview_lotes);
        lv_liq= (ListView) findViewById(R.id.listview_liq);

        titulo = (TextView) findViewById(R.id.TituloArticulo);







        Cursor res = myDB.GetInfoArt(myVa.getInv_Articulo());
        if (res.getCount()==0){
            Bodegas = "";
            Reglas = "";
        }else{
            if (res.moveToFirst()) {
                do {
                    txtTitulo = res.getString(1)+ "\n" + res.getString(0);
                    Bodegas = res.getString(6);
                    Reglas = res.getString(7);
                } while(res.moveToNext());
            }

        }
        titulo.setText(txtTitulo);
        loadData_bodegas(Bodegas);
        loadData_reglas(Reglas);
        loadData_lote();
        loadData_liq();

/*
        items.add(new Anime("Bedegas", 230));
        items.add(new Anime("Lotes", 456));
        items.add(new Anime("Vencimiento", 342));
        List<Anime> items = new ArrayList<>();


        recycler = (RecyclerView) findViewById(R.id.reciclador);
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

        Cursor res = myDB.GetLiq(myVa.getInv_Articulo());
        if (res.getCount()==0){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("lote", "");
            map.put("vencimiento","");
            map.put("cantidad", "");
            map.put("dias","");
            fillMaps.add(map);
        }else{
            if (res.moveToFirst()) {
                do {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("lote", res.getString(5));
                    map.put("vencimiento",res.getString(4));
                    map.put("cantidad", res.getString(3));
                    map.put("dias",res.getString(2));
                    fillMaps.add(map);
                } while(res.moveToNext());
            }

        }
        adapter2 = new SpecialAdapter(this, fillMaps, R.layout.grid_item_articulo_liq, from, to);
        lv_liq.setAdapter(adapter2);

    }
    private void loadData_lote(){
        String[] from = new String[] { "lote", "fecha","cantidad","bodega"};
        int[] to = new int[] { R.id.item_LOTE_VENCE, R.id.item_FECHA_lote_VENCE, R.id.item_cant_lote, R.id.item_BODEGA_lote};
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();

        Cursor res = myDB.GetLotesArt(myVa.getInv_Articulo());
        if (res.getCount()==0){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("lote", "");
            map.put("fecha","");
            map.put("cantidad", "");
            map.put("bodega","");
            fillMaps.add(map);
        }else{
            if (res.moveToFirst()) {
                do {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("lote", res.getString(0));
                    map.put("fecha",res.getString(2));
                    map.put("cantidad", res.getString(3));
                    map.put("bodega",res.getString(4));
                    fillMaps.add(map);
                } while(res.moveToNext());
            }

        }

        adapter2 = new SpecialAdapter(this, fillMaps, R.layout.grid_item_articulo_lotes, from, to);
        lv_lote.setAdapter(adapter2);

    }
    private void loadData_bodegas(String Bodegas){


        String[] from = new String[] { "Bodega", "Cantidad"};
        int[] to = new int[] { R.id.item_Bodegas, R.id.item_cant_Bodegas};
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();


        String[] bdg = Bodegas.toString().split("xxx");
        for(int b=0;b < bdg.length;b++){
            String[] item = bdg[b].toString().split("_");
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("Bodega", item[0].toString()+ " " + item[1].toString());
            map.put("Cantidad",item[2].toString());
            fillMaps.add(map);
        }


        adapter2 = new SpecialAdapter(this, fillMaps, R.layout.grid_item_articulo_bodega, from, to);
        lv_bodegas.setAdapter(adapter2);

    }
    private void loadData_reglas(String Reglas){
        String[] from = new String[] { "Reglas"};
        int[] to = new int[] { R.id.item_reglas};
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();


        String[] Rules = Reglas.toString().split("xxx");
        for(int b=0;b < Rules.length;b++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("Reglas",Rules[b].toString());
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
