package com.guma.desarrollo.salesumk.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.guma.desarrollo.salesumk.Adapters.SpecialAdapter;
import com.guma.desarrollo.salesumk.DataBase.DataBaseHelper;
import com.guma.desarrollo.salesumk.Lib.Variables;
import com.guma.desarrollo.salesumk.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class bandejaCobroActivity extends AppCompatActivity {

    SpecialAdapter adapter;
    ListView lv;
    Variables vrb;
    DataBaseHelper myDB;
    private  String SqlSyncInsert ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandeja_cobro);
        setTitle("COBROS REALIZADOS");
        lv = (ListView) findViewById(R.id.listview_bandeja_cobro);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        myDB = new DataBaseHelper(bandejaCobroActivity.this);
        TextView txtClienteCobro = (TextView) findViewById(R.id.txtLblClienteCobro);
        txtClienteCobro.setText(vrb.getCliente_Name_recibo_factura());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDB.GetAllRecibo();
                if (res.getCount()!=0){
                    SqlSyncInsert = "INSERT INTO recibo (IdRecibo, IdCliente, Vendedor, Fecha, MRecibo, TC, TM, Recibimos, LCantidad, Concepto, Efectivo, CHK, NumCHK, Banco) VALUES";
                    if (res.moveToFirst()){
                        do {
                            SqlSyncInsert +=
                                    "("+
                                    "'"+res.getString(0)+"',"+
                                            "'"+res.getString(1)+"',"+
                                            "'"+res.getString(2)+"',"+
                                            "'"+res.getString(3)+"',"+
                                            "'"+res.getString(4)+"',"+
                                            "'"+res.getString(5)+"',"+
                                            "b '"+res.getString(6)+"',"+
                                            "'"+res.getString(7)+"',"+
                                            "'"+res.getString(8)+"',"+
                                            "'"+res.getString(9)+"',"+
                                            "b '"+res.getString(10)+"',"+
                                            "b '"+res.getString(11)+"',"+
                                            "'"+res.getString(12)+"',"+
                                            "'"+res.getString(13)+"'"
                                            +"),";
                        } while(res.moveToNext());
                        SqlSyncInsert = SqlSyncInsert.substring(0,SqlSyncInsert.length()-1);
                    }
                }

                // TODO: 28/07/2016 verificar los tipos de datos booleanos en las base de datos mysql 
                Log.d("SUPERMEGAULTRAQUERRY",SqlSyncInsert);

                Toast.makeText(bandejaCobroActivity.this, SqlSyncInsert, Toast.LENGTH_LONG).show();

                //SQLITE
                //INSERT INTO "main"."Recibo"
                //VALUES ('F17-3622', '00042', 'ESPERANZA CASTILLO', '28/6/2016', 1080, '', 0, 'COMISARIATO DE LA POLICIA NACIONAL - RUC J0130000001725', 'UN MIL OCHENTA NETOS.', 'concetorio gogia', 1, 0, '', '');

                //MYSQL
                //INSERT INTO recibo (IdRecibo, IdCliente, Vendedor, Fecha, MRecibo, TC, TM, Recibimos, LCantidad, Concepto, Efectivo, CHK, NumCHK, Banco)
                // VALUES
                // ('F17-3622', '00042', 'ESPERANZA CASTILLO', '0000-00-00', 1080, NULL, b'0', 'COMISARIATO DE LA POLICIA NACIONAL - RUC J0130000001725', 'UN MIL OCHENTA NETOS.', 'concetorio gogia', b'1', b'0', NULL, NULL),
                // ('F17-3626', '00042', 'ESPERANZA CASTILLO', '0000-00-00', 1080, NULL, b'0', 'COMISARIATO DE LA POLICIA NACIONAL - RUC J0130000001725', 'UN MIL OCHENTA NETOS.', 'concetorio gogia', b'1', b'0', NULL, NULL);



            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                vrb.setIdViewRecibo(ClearString(String.valueOf(lv.getItemAtPosition(position))));


                final AlertDialog.Builder builder = new AlertDialog.Builder(bandejaCobroActivity.this);
                final CharSequence[] items = {"REVISAR", "ELIMINAR"};
                builder.setTitle( vrb.getIdViewRecibo());
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("ELIMINAR")){
                            builder.setMessage("Desea Eliminar este Registro")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            if (myDB.DeleteRecibo(vrb.getIdViewRecibo()) == true){
                                                Toast.makeText(bandejaCobroActivity.this, "Se Elimino Correctamente el Recibo", Toast.LENGTH_LONG).show();
                                            }else{
                                                Toast.makeText(bandejaCobroActivity.this, "No se pudo Eliminar el recibo", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    })
                                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // User cancelled the dialog
                                        }
                                    }).create().show();
                        }else{
                            Intent MenuIntent = new Intent(bandejaCobroActivity.this,ViewFrmCobro.class);
                            bandejaCobroActivity.this.startActivity(MenuIntent);
                        }



                    }
                }).create().show();

                /**/

            }
        });
        loadData();
    }
    private String ClearString(String cadena){
        String Star="RECIBO=";
        int c1 = cadena.indexOf(Star)+Star.length();
        int c2 = cadena.indexOf("}");
        cadena = cadena.substring(c1,c2);
        return cadena;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        String titulo = String.valueOf(item.getTitle());

        if (id == 16908332){
            finish();
        }
        switch (titulo){
            case "add":
                Intent MenuIntent = new Intent(bandejaCobroActivity.this,FrmCobro_Activity.class);
                bandejaCobroActivity.this.startActivity(MenuIntent);
                break;


        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cobro,menu);
        return true;
    }

    private void loadData() {
        String[] from = new String[] { "RECIBO","FECHA","MONTO"};
        int[] to = new int[] { R.id.Item_Bandeja_recibo,R.id.Item_bandeja_fecha,R.id.Item_bandeja_monto};
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();


        String[] datos = myDB.GetCobros(vrb.getCliente_recibo_factura());
        //String[] datos ={"t1,t2,t3"};
        for (int c=0;c<datos.length;c++){
            String[] valores = datos[c].split(",");
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("RECIBO", valores[0]);
            map.put("FECHA", valores[1]);
            map.put("MONTO", valores[2]);
            fillMaps.add(map);
        }
        adapter = new SpecialAdapter(this, fillMaps, R.layout.grid_item_bandeja_cobro, from, to);
        lv.setAdapter(adapter);
    }

}
