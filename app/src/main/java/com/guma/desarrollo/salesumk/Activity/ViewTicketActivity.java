package com.guma.desarrollo.salesumk.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.guma.desarrollo.salesumk.R;

public class ViewTicketActivity extends AppCompatActivity {
    Intent iPedido;
    TextView CodPedido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ticket);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("RESUMEN");
        iPedido = getIntent();
        CodPedido = (TextView) findViewById(R.id.IdPedido);
        CodPedido.setText(iPedido.getStringExtra("COD"));




    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == 16908332){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
