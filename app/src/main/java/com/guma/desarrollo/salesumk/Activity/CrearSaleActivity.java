package com.guma.desarrollo.salesumk.Activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.guma.desarrollo.salesumk.Adapters.SpecialAdapter;
import com.guma.desarrollo.salesumk.Lib.ClsVariblesPedido;
import com.guma.desarrollo.salesumk.Lib.Funciones;
import com.guma.desarrollo.salesumk.Lib.Variables;
import com.guma.desarrollo.salesumk.R;


import java.util.HashMap;



public class CrearSaleActivity extends AppCompatActivity {
    SpecialAdapter adapter;
    Variables vrb;
    Funciones vrf;
    ClsVariblesPedido vrbFactura;
    ListView lv;
    TextView txtMontoTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_sale);
        setTitle(vrb.getCliente_Name_recibo_factura());
        lv = (ListView) findViewById(R.id.listview_sale);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        txtMontoTotal = (TextView) findViewById(R.id.Total);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CrearSaleActivity.this);
                builder.setMessage("Desea Eliminar este Registro")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                vrbFactura.getMapListaFactura().remove(position);
                                adapter.notifyDataSetChanged();
                                SubTotal();

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        }).create().show();
            }
        });

    }

    private void loadData(String[] Datos) {
        String[] from = new String[] { "Articulo","Descr","Cantidad","Precio","Boni","Valor"};
        int[] to = new int[] { R.id.Item_articulo,R.id.Item_descr,R.id.Item_cant,R.id.Item_precio,R.id.Item_boni,R.id.Item_valor};
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("Articulo", Datos[0]);
        map.put("Descr", Datos[1]);
        map.put("Cantidad", vrf.number_format(Float.parseFloat(Datos[3]),2));
        map.put("Precio", "C$ " + vrf.number_format(Float.parseFloat(Datos[2]),2));
        map.put("Boni", Datos[4]);
        map.put("Valor", vrf.number_format(Float.parseFloat(Datos[2]) * Float.parseFloat(Datos[3]),2));

        vrbFactura.getMapListaFactura().add(map);

        adapter = new SpecialAdapter(this, vrbFactura.getMapListaFactura(), R.layout.grid_item_sale, from, to);
        lv.setAdapter(adapter);
        SubTotal();
    }
    private void SubTotal(){
        float SubT = 0;
        for (int i = 0; i<vrbFactura.getMapListaFactura().size();i++){
            SubT += Float.parseFloat(vrbFactura.getMapListaFactura().get(i).get("Valor").replace(",",""));
        }
        txtMontoTotal.setText("TOTAL: C$ "+ vrf.number_format(SubT,2));

    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        String titulo = String.valueOf(item.getTitle());

        if (id == 16908332){
            vrbFactura.getMapListaFactura().clear();
            finish();
        }
        switch (titulo){
            case "add":

                Intent ListaProductos = new Intent(CrearSaleActivity.this,PedidoActivity.class);
                startActivityForResult(ListaProductos,0);
            break;
            case "send":
                /*if (vrbFactura.getMapListaFactura().size() != 1){


                }else{
                    Toast.makeText(CrearSaleActivity.this, "La Lista de Articulo esta Vacia", Toast.LENGTH_SHORT).show();
                }*/
                AlertDialog.Builder builder = new AlertDialog.Builder(CrearSaleActivity.this);
                builder.setMessage("¿Esta Seguro de que Desea Generar el Pedido?")
                        .setPositiveButton("Proceder", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent itns = new Intent(CrearSaleActivity.this,TicketActivity.class);
                                startActivity(itns);
                                finish();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        }).create().show();


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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0&&resultCode==RESULT_OK){
            loadData(data.getStringArrayExtra("Articulo"));
        }
    }
}
