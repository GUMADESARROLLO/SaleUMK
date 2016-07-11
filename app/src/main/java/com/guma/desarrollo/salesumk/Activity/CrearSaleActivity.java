package com.guma.desarrollo.salesumk.Activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


import com.guma.desarrollo.salesumk.Adapters.SpecialAdapter;
import com.guma.desarrollo.salesumk.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CrearSaleActivity extends AppCompatActivity {
    //ListView lv;
    //ArrayAdapter<String> adapter;
    private SearchManager searchManager;

    SpecialAdapter adapter;
    ListView lv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_sale);
        setTitle("FARMACIA FARMA VALUE/ RUC J310000170967");
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        lv = (ListView) findViewById(R.id.listview_sale);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


       /*
        lv = (ListView) findViewById(R.id.ListView1);
        String[] datos ={"Anfotericina B 50mg Liofilizado para Sol. Iny. Vial 1/Caja Refrigerado (Naprod)","Esomeprazol 40mg PPSI 5 ml/Vial 1/Combipack (Naprod)","Anastrozol 1 Mg Tab Recubierta 28/Caja (Naprod)","Dacarbazina 200 Mg PPSI I.V FAM 1/Caja Refrigerado(Naprod)","Hidroxiurea 500 Mg Capsula 50/Caja (Naprod)","Mercaptopurina 50 mg Tabletas 100/Caja (Naprod)"};
        adapter = new ArrayAdapter<String>(CrearSaleActivity.this, android.R.layout.simple_list_item_1,datos);
        lv.setAdapter(adapter);
        */

        //searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        //((CrearSaleActivity) this).getSupportActionBar().setTitle("");
        loadData();
    }

    private void loadData() {
        String[] from = new String[] { "Articulo","Descr","Cantidad","Precio","Valor"};
        int[] to = new int[] { R.id.Item_articulo,R.id.Item_descr,R.id.Item_cant,R.id.Item_precio,R.id.Item_valor};
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        String[] datos ={"77473","77742"};
        for (int c=0;c<datos.length;c++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("Articulo", datos[c]);
            map.put("Descr", datos[c]);
            map.put("Cantidad", datos[c]);
            map.put("Precio", datos[c]);
            map.put("Valor", datos[c]);
            fillMaps.add(map);
        }
        adapter = new SpecialAdapter(this, fillMaps, R.layout.grid_item_sale, from, to);
        lv.setAdapter(adapter);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        String titulo = String.valueOf(item.getTitle());

        if (id == 16908332){
            finish();
        }
        switch (titulo){
            case "add":
                Intent MenuIntent = new Intent(CrearSaleActivity.this,PedidoActivity.class);
                CrearSaleActivity.this.startActivity(MenuIntent);
            break;
            case "send":
                Intent itns = new Intent(CrearSaleActivity.this,TicketActivity.class);
                startActivity(itns);
                break;
            case "Cancelar":
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pedido,menu);
        return true;
    }

}
