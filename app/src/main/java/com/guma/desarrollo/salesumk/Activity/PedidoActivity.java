package com.guma.desarrollo.salesumk.Activity;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.guma.desarrollo.salesumk.Adapters.MyAlertDialogFragment;
import com.guma.desarrollo.salesumk.Adapters.SpecialAdapter;
import com.guma.desarrollo.salesumk.DataBase.DataBaseHelper;
import com.guma.desarrollo.salesumk.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PedidoActivity extends AppCompatActivity {
    DataBaseHelper myDB;

    ListView lv;

    SearchView sv;

    SpecialAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("");
        myDB = new DataBaseHelper(PedidoActivity.this);
        lv = (ListView) findViewById(R.id.ListView1);

        sv = (SearchView) findViewById(R.id.searchView1);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //{PRECIO=C$ 70.0000, CANTI=0.00  - CJA, DESCR=Abre Boca Transparente Pequeño Unidad, ARTI=[40951219]}
                String[] Cod = ClearString(String.valueOf(lv.getItemAtPosition(position)));
                FragmentManager fm = getSupportFragmentManager();
                MyAlertDialogFragment alertDialog = new MyAlertDialogFragment();
                alertDialog.setArticulo(Cod);
                alertDialog.show(fm, "dialog_addcls");
            }
        });
        loadData();
    }


    private String[] ClearString(String cadena){
        int Pstr1 = cadena.indexOf("[")+1;
        int Pstr2 = cadena.indexOf("]");

        String Privot1 = "DESCR=",Privot2 = "PRECIO=C$ ";

        int Pstr3 = cadena.indexOf(Privot1)+(Privot1).length();
        int Pstr4 = cadena.indexOf("ARTI");

        int Pstr5 = cadena.indexOf(Privot2)+(Privot2).length();
        int Pstr6 = cadena.indexOf("CANTI");

        String[] Str = new String[3];

        Str[0] = cadena.substring(Pstr1,Pstr2);
        Str[1] = cadena.substring(Pstr3,Pstr4).replace(",","");
        Str[2] = cadena.substring(Pstr5,Pstr6).replace(",","");

        return Str;
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


            String[] from = new String[] {"ARTI", "DESCR", "CANTI","PRECIO"};
            int[] to = new int[] { R.id.item1, R.id.item2, R.id.item3, R.id.item4};

            List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();

            Cursor res =  myDB.GetData("Articulo");
            if (res.moveToFirst()) {

                do {
                    HashMap<String, String> map = new HashMap<String, String>();
                    datos.add(res.getString(1)+"\n["+res.getString(0)+"]");
                    map.put("ARTI", "["+res.getString(0)+"]");
                    map.put("DESCR", res.getString(1));
                    map.put("CANTI",res.getString(2).replace("["," - ").replace("]",""));
                    map.put("PRECIO",res.getString(3));
                    fillMaps.add(map);

                } while(res.moveToNext());
            }
            res.close();

            adapter = new SpecialAdapter(PedidoActivity.this, fillMaps, R.layout.grid_item_add_articulo, from, to);
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
