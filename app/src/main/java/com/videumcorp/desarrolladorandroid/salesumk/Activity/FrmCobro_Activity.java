package com.videumcorp.desarrolladorandroid.salesumk.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.videumcorp.desarrolladorandroid.salesumk.Adapters.SpecialAdapter;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FrmCobro_Activity extends AppCompatActivity {

    SpecialAdapter adapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_cobro_);
        setTitle("RECIBO OFICIAL DE COBRO");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        lv = (ListView) findViewById(R.id.listview_DRecibo);
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

        if (id == 16908332){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void loadData() {
        String[] from = new String[] { "FacturaNo","vfactura","ValorNC","Retencion","Descuento","VRecibido","Saldo"};
        int[] to = new int[] { R.id.Item_recibo,R.id.Item_vFactura,R.id.Item_VNC,R.id.Item_Retencion,R.id.Item_Descuento,R.id.Item_VRecibo,R.id.Item_Saldo};
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();

        String[] datos ={"77473","77742"};
        for (int c=0;c<datos.length;c++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("FacturaNo", datos[c]);
            map.put("vfactura", datos[c]);
            map.put("ValorNC", datos[c]);
            map.put("Retencion", datos[c]);
            map.put("Descuento", datos[c]);
            map.put("VRecibido", datos[c]);
            map.put("Saldo", datos[c]);
            fillMaps.add(map);
        }
        adapter = new SpecialAdapter(this, fillMaps, R.layout.grid_item_recibo, from, to);
        lv.setAdapter(adapter);
    }

}
