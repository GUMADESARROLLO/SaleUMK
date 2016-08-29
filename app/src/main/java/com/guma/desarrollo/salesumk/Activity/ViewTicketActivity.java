package com.guma.desarrollo.salesumk.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.guma.desarrollo.salesumk.DataBase.DataBaseHelper;
import com.guma.desarrollo.salesumk.R;

import java.util.HashMap;

public class ViewTicketActivity extends AppCompatActivity {

    Intent iPedido;
    TextView CodPedido,NombreVendedor,NombreCL;
    DataBaseHelper myDB;
    String IdPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ticket);
        setTitle("RESUMEN");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        IdPedido        = iPedido.getStringExtra("COD");
        myDB            = new DataBaseHelper(this);
        iPedido         = getIntent();
        CodPedido       = (TextView) findViewById(R.id.IdPedido);
        NombreVendedor  = (TextView) findViewById(R.id.NombreVendedor);
        NombreCL        = (TextView) findViewById(R.id.NombreCliente);

        CodPedido.setText(IdPedido);

        verPedido();




    }

    private void verPedido() {
        Cursor res = myDB.getPedido(IdPedido);
        if (res.getCount()!=0){
            if (res.moveToFirst()) {
                do {
                    NombreVendedor.setText(res.getString(3));
                    NombreCL.setText(res.getString(4));
                } while(res.moveToNext());
            }
        }
        Cursor rDetalle = myDB.getPedido(IdPedido);
        if (rDetalle.getCount()!=0){
            if (rDetalle.moveToFirst()) {
                do {

                } while(rDetalle.moveToNext());
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == 16908332){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
