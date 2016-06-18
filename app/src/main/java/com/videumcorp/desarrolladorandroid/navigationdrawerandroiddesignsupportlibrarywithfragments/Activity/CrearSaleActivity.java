package com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;


import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.R;



public class CrearSaleActivity extends AppCompatActivity {
    ListView lv;
    ArrayAdapter<String> adapter;
    private SearchManager searchManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_sale);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        lv = (ListView) findViewById(R.id.ListView1);
        String[] datos ={"ARTICULO 1","ARTICULO 2","ARTICULO 3","ARTICULO 4","ARTICULO 5","ARTICULO 6","ARTICULO 7"};
        adapter = new ArrayAdapter<String>(CrearSaleActivity.this, android.R.layout.simple_list_item_1,datos);
        lv.setAdapter(adapter);

        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        //((CrearSaleActivity) this).getSupportActionBar().setTitle("");
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
