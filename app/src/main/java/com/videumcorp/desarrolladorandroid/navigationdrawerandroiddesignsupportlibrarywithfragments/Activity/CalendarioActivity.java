package com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Adapters.SpecialAdapter;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.DataBase.DataBaseHelper;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CalendarioActivity extends AppCompatActivity {

    SpecialAdapter adapter2;
    ListView lv;
    DataBaseHelper myDB;
    TextView D1;
    TextView D2;
    TextView D3;
    TextView D4;
    TextView D5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        setTitle("AGENDA DE TRABAJO");
        //((calendarioActivity) this).getSupportActionBar().setTitle("AGENDA DE TRABAJO");
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        D1 = (TextView) findViewById(R.id.d1);
        D2 = (TextView) findViewById(R.id.d2);
        D3 = (TextView) findViewById(R.id.d3);
        D4 = (TextView) findViewById(R.id.d4);
        D5 = (TextView) findViewById(R.id.d5);

        D1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CalendarioActivity.this, "LLamar1", Toast.LENGTH_SHORT).show();
            }
        });
        D2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CalendarioActivity.this, "LLamar2", Toast.LENGTH_SHORT).show();
            }
        });
        D3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CalendarioActivity.this, "LLamar3", Toast.LENGTH_SHORT).show();
            }
        });
        D4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CalendarioActivity.this, "LLamar4", Toast.LENGTH_SHORT).show();
            }
        });
        D5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CalendarioActivity.this, "LLamar5", Toast.LENGTH_SHORT).show();
            }
        });




        lv = (ListView) findViewById(R.id.listview);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        myDB = new DataBaseHelper(this);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        loadData();
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == 16908332){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void loadData(){
        ArrayList<String> datos = new ArrayList<String>();
        String[] from = new String[] {"rowid", "D1", "D2","D3","D4","D5"};
        int[] to = new int[] { R.id.d1, R.id.d2, R.id.d3,R.id.d4,R.id.d5};

        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();

        Cursor res =  myDB.GetData("Articulo");
        if (res.moveToFirst()) {

            do {
                HashMap<String, String> map = new HashMap<String, String>();
                datos.add(res.getString(1)+"\n["+res.getString(0)+"]");
                map.put("rowid", res.getString(0));
                map.put("D1", res.getString(0));
                map.put("D2",res.getString(0));
                map.put("D3",res.getString(0));
                map.put("D4",res.getString(0));
                map.put("D5",res.getString(0));
                fillMaps.add(map);

            } while(res.moveToNext());


        }
        res.close();



        // fill in the grid_item layout
        adapter2 = new SpecialAdapter(this, fillMaps, R.layout.grid_item_calendario, from, to);
        lv.setAdapter(adapter2);
    }

}
