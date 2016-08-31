package com.guma.desarrollo.salesumk.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.guma.desarrollo.salesumk.Adapters.SpecialAdapter;
import com.guma.desarrollo.salesumk.DataBase.DataBaseHelper;
import com.guma.desarrollo.salesumk.Lib.Funciones;
import com.guma.desarrollo.salesumk.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewTicketActivity extends AppCompatActivity {

    Intent iPedido;
    TextView CodPedido,NombreVendedor,NombreCL,CountListArticulo,TotalPagar;
    DataBaseHelper myDB;
    String IdPedido;
    ListView lv;
    SpecialAdapter adapter;
    Funciones vrF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ticket);
        setTitle("RESUMEN");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        iPedido = getIntent();


        IdPedido        = iPedido.getStringExtra("COD");
        myDB            = new DataBaseHelper(this);
        iPedido         = getIntent();
        CodPedido       = (TextView) findViewById(R.id.IdPedido);
        NombreVendedor  = (TextView) findViewById(R.id.NombreVendedor);
        NombreCL        = (TextView) findViewById(R.id.NombreCliente);

        CodPedido.setText(IdPedido);

        lv = (ListView) findViewById(R.id.ListView1);
        CountListArticulo = (TextView) findViewById(R.id.txtCountArti);
        TotalPagar= (TextView) findViewById(R.id.Total);

        verPedido();




    }

    private void verPedido() {
        String[] from = new String[] { "Articulo","Descr","Cantidad","Precio","Valor"};
        int[] to = new int[] { R.id.Item_articulo,R.id.Item_descr,R.id.Item_cant,R.id.Item_precio,R.id.Item_valor};
        float SubT = 0,TotalLinea=0;
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        adapter = new SpecialAdapter(this,  fillMaps, R.layout.grid_item_ticket, from, to);

        Cursor res = myDB.getPedido(IdPedido);
        if (res.getCount()!=0){
            if (res.moveToFirst()) {
                do {
                    NombreVendedor.setText(res.getString(3));
                    NombreCL.setText(res.getString(4));
                } while(res.moveToNext());
            }
        }
        Cursor rDetalle = myDB.getPDetalle(IdPedido);
        if (rDetalle.getCount()!=0){
            if (rDetalle.moveToFirst()) {
                do {
                    TotalLinea = Float.parseFloat(rDetalle.getString(3).replace(",","")) * Float.parseFloat(rDetalle.getString(4).replace(",",""));
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("Articulo", rDetalle.getString(1));
                    map.put("Descr", rDetalle.getString(2));
                    map.put("Cantidad", rDetalle.getString(3));
                    map.put("Precio", rDetalle.getString(4));
                    map.put("Valor", vrF.number_format(TotalLinea,2));
                    fillMaps.add(map);
                    SubT += TotalLinea;
                } while(rDetalle.moveToNext());
            }
        }

        TotalPagar.setText("C$ " + vrF.number_format(SubT,2));
        CountListArticulo.setText(fillMaps.size()+" Articulos");
        lv.setAdapter(adapter);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == 16908332){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
