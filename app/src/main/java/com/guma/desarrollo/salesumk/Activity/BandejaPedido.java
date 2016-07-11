package com.guma.desarrollo.salesumk.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.guma.desarrollo.salesumk.R;

public class BandejaPedido extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bandeja_pedido);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("FARMACIA FARMA VALUE/ RUC J310000170967");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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

}
