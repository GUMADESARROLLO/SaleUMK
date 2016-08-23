package com.guma.desarrollo.salesumk.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.guma.desarrollo.salesumk.Adapters.SpecialAdapter;
import com.guma.desarrollo.salesumk.DataBase.DataBaseHelper;
import com.guma.desarrollo.salesumk.Lib.Variables;
import com.guma.desarrollo.salesumk.R;

import java.util.HashMap;

public class BandejaPedido extends AppCompatActivity {
    Variables vrb;
    DataBaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bandeja_pedido);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(vrb.getCliente_Name_recibo_factura());

        myDB = new DataBaseHelper(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    // TODO: 23/08/2016 queda pendiente construir el listado de los pedidos realizados 
    private void loadData() {
        String[] from = new String[] { "RECIBO","FECHA","MONTO"};
        int[] to = new int[] { R.id.Item_Bandeja_recibo,R.id.Item_bandeja_fecha,R.id.Item_bandeja_monto};
        String[] datos = myDB.GetCobros(vrb.getCliente_recibo_factura());
        for (int c=0;c<datos.length;c++){
            String[] valores = datos[c].split(",");
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("RECIBO", valores[0]);
            map.put("FECHA", valores[1]);
            map.put("MONTO", valores[2]);
            vrb.getFillMapsBandejaCobro().add(map);
        }
        //vrb.adapter = new SpecialAdapter(this, vrb.getFillMapsBandejaCobro(), R.layout.grid_item_bandeja_cobro, from, to);
        //lv.setAdapter(adapter);

        vrb.setAdapterBandejaCobro(new SpecialAdapter(this, vrb.getFillMapsBandejaCobro(), R.layout.grid_item_bandeja_cobro, from, to));
        vrb.getLv_list_facturaCobroBandejaCobro().setAdapter(vrb.getAdapterBandejaCobro());





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
