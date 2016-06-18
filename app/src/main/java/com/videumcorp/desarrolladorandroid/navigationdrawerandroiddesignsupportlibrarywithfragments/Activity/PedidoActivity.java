package com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.DataBase.DataBaseHelper;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.R;

import java.util.ArrayList;

public class PedidoActivity extends AppCompatActivity {
    DataBaseHelper myDB;

    ListView lv;

    SearchView sv;

    ArrayAdapter<String> adapter;
    TextView txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnAdd = (Button) findViewById(R.id.button_Add_Art);

        ((PedidoActivity) this).getSupportActionBar().setTitle("");


        myDB = new DataBaseHelper(PedidoActivity.this);
        lv = (ListView) findViewById(R.id.ListView1);
        sv = (SearchView) findViewById(R.id.searchView1);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });






        loadData();
    }

    private void loadData(){
        ArrayList<String> datos = new ArrayList<String>();

        Cursor res =  myDB.GetData("Articulo");
        if (res.moveToFirst()) {
            do {
                datos.add(res.getString(1));

            } while(res.moveToNext());
        }


        res.close();

        adapter = new ArrayAdapter<String>(PedidoActivity.this, android.R.layout.simple_list_item_1,datos);

        lv.setAdapter(adapter);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                adapter.getFilter().filter(text);
                return false;
            }
        });

    }

}
