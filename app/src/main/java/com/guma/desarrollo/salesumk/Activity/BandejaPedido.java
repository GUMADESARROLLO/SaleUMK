package com.guma.desarrollo.salesumk.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.guma.desarrollo.salesumk.Adapters.SpecialAdapter;
import com.guma.desarrollo.salesumk.DataBase.DataBaseHelper;
import com.guma.desarrollo.salesumk.Lib.ClssURL;
import com.guma.desarrollo.salesumk.Lib.Funciones;
import com.guma.desarrollo.salesumk.Lib.Variables;
import com.guma.desarrollo.salesumk.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BandejaPedido extends AppCompatActivity
{
    Variables vrb;
    Funciones vrF;
    DataBaseHelper myDB;
    SpecialAdapter adapter;
    ListView lv;
    ProgressDialog pdialog;
    List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
    ClssURL Url;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bandeja_pedido);
        if (getSupportActionBar() != null){ getSupportActionBar().setDisplayHomeAsUpEnabled(true); }
        setTitle(vrb.getCliente_Name_recibo_factura());
        myDB = new DataBaseHelper(this);
        lv = (ListView) findViewById(R.id.listview_bandeja_pedido);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id)
            {
                final AlertDialog.Builder builder = new AlertDialog.Builder(BandejaPedido.this);
                final CharSequence[] items = {"VER", "ELIMINAR"};
                final String COD = ClearString(String.valueOf(lv.getItemAtPosition(position)),"COD=",1);
                builder.setTitle(COD);
                builder.setItems
                (items, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int item)
                        {
                            if (items[item].equals("ELIMINAR"))
                            {
                                builder.setMessage("Desea Eliminar este Registro").setPositiveButton
                                        ("OK", new DialogInterface.OnClickListener()
                                            {
                                                public void onClick(DialogInterface dialog, int id) { }
                                            }
                                        )
                                        .setNegativeButton
                                        ("Cancelar", new DialogInterface.OnClickListener()
                                            {
                                                public void onClick(DialogInterface dialog, int id) { }
                                            }
                                        ).create().show();
                            }
                            else
                            {
                                startActivity(new Intent(BandejaPedido.this, ViewTicketActivity.class).putExtra("COD",COD));
                            }

                        }
                    }
                ).create().show();
                return false;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Push();
            }
        });
        loadData();
    }
    private void Push()
    {
        String mPedidos ="",mSqlPedidos="",mSqlPDetalle="";
        boolean mUpload = false;
        AsyncHttpClient Cnx = new AsyncHttpClient();
        RequestParams paramentros = new RequestParams();
        pdialog = ProgressDialog.show(this, "","Procesando, ya casi terminamos...", true);
        if (fillMaps.size()>0)
        {
            mUpload = true;
            for (int c=0;c<fillMaps.size();c++)
            {
                if (Integer.parseInt(ClearString(adapter.getItem(c).toString(),"ESTADO=",2))==0)
                {
                    mPedidos += "'" + ClearString(adapter.getItem(c).toString(),"COD=",1)+ "',";
                }
            }
        }
        mPedidos = mPedidos.substring(0,mPedidos.length()-1);
        if (mUpload)
        {
            //BUILDER QUERRY DETALLE PEDIDO
            Cursor rPedido =  myDB.getAllPedido(mPedidos);
            if (rPedido.getCount()!=0)
            {
                if (rPedido.moveToFirst())
                {
                    do
                    {
                        mSqlPedidos +=
                                "CALL SP_pedidos ("+
                                        "'"+rPedido.getString(0)+"',"+
                                        "'"+rPedido.getString(1)+"',"+
                                        "'"+rPedido.getString(2)+"',"+
                                        "'"+rPedido.getString(3)+"',"+
                                        "'"+rPedido.getString(4)+"',"+
                                        "'"+rPedido.getString(5)+"',"+
                                        "'"+rPedido.getString(6)+"',"+
                                        "'"+rPedido.getString(7)+"',"+
                                        "'"+rPedido.getString(8)+"',"+
                                        "'"+rPedido.getString(9)+"',"+
                                        "'"+rPedido.getString(10)+"',"+
                                        "'"+rPedido.getString(11)+"',"+
                                        "'"+rPedido.getString(12)+"',"+
                                        "'0'"
                                        +");";
                    } while(rPedido.moveToNext());
                }
            }
            //BUILDER QUERRY DETALLE PEDIDO
            Cursor rPDetalle =  myDB.getAllPDetalle(mPedidos);
            if (rPDetalle.getCount()!=0)
            {
                mSqlPDetalle = "INSERT INTO PDetalle (IdPedido, IdArticulo, Descripcion, Cantidad, Precio, Bono) VALUES";
                if (rPDetalle.moveToFirst())
                {
                    do
                    {
                        mSqlPDetalle +=
                                " ("+
                                        "'"+rPDetalle.getString(0)+"',"+
                                        "'"+rPDetalle.getString(1)+"',"+
                                        "'"+rPDetalle.getString(2)+"',"+
                                        "'"+rPDetalle.getInt(3)+"',"+
                                        "'"+rPDetalle.getFloat(4)+"',"+
                                        "'"+rPDetalle.getString(5)+"'"
                                        +"),";
                    } while(rPDetalle.moveToNext());
                    mSqlPDetalle = mSqlPDetalle.substring(0,mSqlPDetalle.length()-1);
                }
            }
            paramentros.put("P",mSqlPedidos);
            paramentros.put("D",mSqlPDetalle);
            final String finalLogReg = mPedidos;
            Cnx.post
            (Url.getURL_Pedido(), paramentros, new AsyncHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody)
                    {
                        if (statusCode==200)
                        {
                            Toast.makeText(BandejaPedido.this, "Correcto", Toast.LENGTH_SHORT).show();
                            //myDB.Update(finalLogReg);
                            pdialog.dismiss();
                        }else
                        {
                            pdialog.dismiss();
                            Toast.makeText(BandejaPedido.this, "No hay respuesta del servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error)
                    {
                        pdialog.dismiss();
                        Toast.makeText(BandejaPedido.this, "Problemas de Cobertura de datos", Toast.LENGTH_SHORT).show();
                    }
                }
            );
        }
        else
        {
            pdialog.dismiss();
            Toast.makeText(BandejaPedido.this, "No hay Datos que Enviar", Toast.LENGTH_SHORT).show();
        }
    }
    private void loadData()
    {
        String[] from = new String[] { "COD","CCLIENTE","FECHA","MONTO","ESTADO"};
        int[] to = new int[] { R.id.Item_PBandeja_Cod,R.id.Item_PBandeja_Cliente,R.id.Item_PBandeja_fecha,R.id.Item_PBandeja_Monto,R.id.Item_PBandeja_Estado};
        String[] datos = myDB.GetPedido();
        for (int c=0;c<datos.length;c++)
        {
            String[] valores = datos[c].split(",");
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("COD", valores[0]);
            map.put("CCLIENTE", valores[1]);
            map.put("FECHA", valores[2]);
            map.put("MONTO", "C$ " + vrF.number_format(Float.parseFloat(valores[3]),2));
            map.put("ESTADO", valores[4]);
            fillMaps.add(map);
        }
        adapter = new SpecialAdapter(this, fillMaps, R.layout.grid_item_bandeja_pedido, from, to);
        lv.setAdapter(adapter);
    }
    private String ClearString(String cadena,String Star,int p)
    {
        String[] Posiciones = cadena.split(",");
        return Posiciones[p].substring(Posiciones[p].indexOf(Star)+Star.length());
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        String titulo = String.valueOf(item.getTitle());
        if (id == 16908332){ finish(); }
        switch (titulo)
        {
            case "add":
                Intent MenuIntent = new Intent(BandejaPedido.this,CrearSaleActivity.class);
                BandejaPedido.this.startActivity(MenuIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_cobro,menu);
        return true;
    }
    @Override
    protected void onRestart()
    {
        super.onRestart();
        fillMaps.clear();
        loadData();
    }
}
