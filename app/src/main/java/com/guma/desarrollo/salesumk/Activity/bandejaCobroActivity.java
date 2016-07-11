package com.guma.desarrollo.salesumk.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.guma.desarrollo.salesumk.Adapters.SpecialAdapter;
import com.guma.desarrollo.salesumk.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class bandejaCobroActivity extends AppCompatActivity {

    SpecialAdapter adapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandeja_cobro);
        setTitle("COBROS REALIZADOS");
        lv = (ListView) findViewById(R.id.listview_bandeja_cobro);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        loadData();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        String titulo = String.valueOf(item.getTitle());

        if (id == 16908332){
            finish();
        }
        switch (titulo){
            case "add":
                Intent MenuIntent = new Intent(bandejaCobroActivity.this,FrmCobro_Activity.class);
                bandejaCobroActivity.this.startActivity(MenuIntent);
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
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        String[] datos ={"RECIBO","RECIBO"};
        for (int c=0;c<datos.length;c++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("RECIBO", datos[c]);
            map.put("FECHA", datos[c]);
            map.put("MONTO", datos[c]);
            fillMaps.add(map);
        }
        adapter = new SpecialAdapter(this, fillMaps, R.layout.grid_item_bandeja_cobro, from, to);
        lv.setAdapter(adapter);
    }

}
