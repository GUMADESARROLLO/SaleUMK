package com.guma.desarrollo.salesumk.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.guma.desarrollo.salesumk.Adapters.SpecialAdapter;
import com.guma.desarrollo.salesumk.DataBase.DataBaseHelper;
import com.guma.desarrollo.salesumk.Lib.ClsVariblesPedido;
import com.guma.desarrollo.salesumk.Lib.Funciones;
import com.guma.desarrollo.salesumk.Lib.Variables;
import com.guma.desarrollo.salesumk.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TicketActivity extends AppCompatActivity {
    ListView lv;
    Variables vrb;
    Funciones vrF;
    SpecialAdapter adapter;
    TextView txtNombreVendedor,txtNameCliente,CountListArticulo,TotalPagar,Npedido;
    LinearLayout view;
    ClsVariblesPedido vrbFactura;
    DataBaseHelper myDB;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        setTitle("RESUMEN");
        myDB = new DataBaseHelper(this);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }




        lv = (ListView) findViewById(R.id.ListView1);

        view = (LinearLayout)findViewById(R.id.idViewTicket);

        txtNameCliente = (TextView) findViewById(R.id.NombreCliente);
        txtNameCliente.setText(vrb.getCliente_Name_recibo_factura());

        txtNombreVendedor = (TextView) findViewById(R.id.NombreVendedor);
        txtNombreVendedor.setText(vrb.getNameVendedor());
        CountListArticulo = (TextView) findViewById(R.id.txtCountArti);
        TotalPagar= (TextView) findViewById(R.id.Total);
        Npedido= (TextView) findViewById(R.id.IdPedido);
        Npedido.setText( getIdPedido());
        loadData();

        GuardarPedido();




    }

    private void GuardarPedido() {
        String SqlInsertDetalle="";
        String SqlInsertTOP;

        String ID = String.valueOf(getIdPedido());
        String IdCliente = vrb.getCliente_recibo_factura();
        String Fecha =  vrF.fDate().toString();
        String Vendedor = vrb.getNameVendedor();
        String Cliente = vrb.getCliente_Name_recibo_factura();
        String Monto = TotalPagar.getText().toString();
        Monto = Monto.replace("C$","");
        String Dir = "Dirección:";
        String Nota = "Nota:";

        SqlInsertTOP ="("+"'"+ID+"'," +"'"+IdCliente+"'," +""+"'"+Fecha+"',"+"'"+Vendedor+"',"+"'"+Cliente+"',"+"'DIRECCION',"+"'0',"+"'0',"+"'PLAZO',"+"'0',"+"'0',"+"'NOTA'"+")";

        //{Precio=C$ 70, Boni=0, Cantidad=123, Articulo=40951217, Descr=Abre Boca Transparente Grande Unidad , Valor=8,610}
        for (int i = 0; i < vrbFactura.getMapListaFactura().size(); i++) {
            String Cadena = String.valueOf(vrbFactura.getMapListaFactura().get(i));
            String Arry[] = vrF.ClearStrgList(Cadena,"").split(", ");

            String IdArticulo      = Arry[3].substring(Arry[3].indexOf("=")+1);
            String Descripcion     = Arry[4].substring(Arry[4].indexOf("=")+1);
            String Cantidad        = Arry[2].substring(Arry[2].indexOf("=")+1);
            String Precio          = Arry[0].substring(Arry[0].indexOf("=")+1).replace("C$","");
            String Boni            = Arry[1].substring(Arry[1].indexOf("=")+1);

            SqlInsertDetalle +="("+"'"+ID+"'," +"'"+IdArticulo+"'," +""+"'"+Descripcion+"',"+"'"+Cantidad+"',"+"'"+Precio+"',"+"'"+Boni+"'"+"),";
        }
        SqlInsertDetalle = SqlInsertDetalle.substring(0,SqlInsertDetalle.length()-1);

        //Alerta(SqlInsertTOP);
       // Alerta(ID + "\n" + IdCliente + "\n" + Fecha + "\n" + Vendedor + "\n" + Cliente + "\n" + Monto + "\n" + Dir + "\n" + Nota);
        myDB.SavePedido(SqlInsertTOP,SqlInsertDetalle);
    }

    public void Alerta(String TipoError){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage(TipoError)
                .setNegativeButton("OK",null)
                .create()
                .show();
    }

    private CharSequence getIdPedido(){
        Date d = new Date();
        return vrb.getIdVendedor() + "-" + DateFormat.format("MMyyhm", d.getTime());

    }
    private void loadData() {
        String[] from = new String[] { "Articulo","Descr","Cantidad","Precio","Valor"};
        int[] to = new int[] { R.id.Item_articulo,R.id.Item_descr,R.id.Item_cant,R.id.Item_precio,R.id.Item_valor};
        float SubT = 0;
        adapter = new SpecialAdapter(this,  vrbFactura.getMapListaFactura(), R.layout.grid_item_ticket, from, to);
        CountListArticulo.setText(vrbFactura.getMapListaFactura().size()+" Articulos");
        lv.setAdapter(adapter);
        for (int i = 0; i<vrbFactura.getMapListaFactura().size();i++){
            SubT += Float.parseFloat(vrbFactura.getMapListaFactura().get(i).get("Valor").replace(",",""));
        }
        TotalPagar.setText("C$ " + vrF.number_format(SubT,2));


    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();



        if (id == 16908332){
            finish();
        }

        if(id == R.id.action_print){

            Bitmap b = viewToBitmap(view);
            storeImage(b);

        }
        /*switch (titulo){
            case "send":

                break;
            case "Cancelar":
                finish();
                break;

        }*/
        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_print,menu);
        return true;
    }

    public Bitmap viewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.rgb(255,255,255));
        canvas.setDensity(24);
        view.draw(canvas);
        return bitmap;
    }
    private void storeImage(Bitmap image) {

        try {
            FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory() + "/DCIM/file.PNG");
            File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/file.PNG");
            image.compress(Bitmap.CompressFormat.PNG, 100, output);
            output.close();

            file.setReadable(true, false);
            Toast.makeText(TicketActivity.this, file.toString(), Toast.LENGTH_LONG).show();
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("image/png");
            startActivity(intent);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
