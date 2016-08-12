package com.guma.desarrollo.salesumk.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.guma.desarrollo.salesumk.Adapters.SpecialAdapter;
import com.guma.desarrollo.salesumk.Lib.ClsVariblesPedido;
import com.guma.desarrollo.salesumk.Lib.Variables;
import com.guma.desarrollo.salesumk.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TicketActivity extends AppCompatActivity {
    ListView lv;
    Variables vrb;

    SpecialAdapter adapter;
    TextView txtNombreVendedor,txtNameCliente;
    LinearLayout view;
    ClsVariblesPedido vrbFactura;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        setTitle("");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        lv = (ListView) findViewById(R.id.ListView1);

        view = (LinearLayout)findViewById(R.id.idViewTicket);

        txtNameCliente = (TextView) findViewById(R.id.NombreCliente);
        txtNameCliente.setText(vrb.getCliente_Name_recibo_factura());

        txtNombreVendedor = (TextView) findViewById(R.id.NombreVendedor);
        txtNombreVendedor.setText(vrb.getNameVendedor());

        loadData();
    }

    private void loadData() {
        String[] from = new String[] { "Articulo","Descr","Cantidad","Precio","Valor"};
        int[] to = new int[] { R.id.Item_articulo,R.id.Item_descr,R.id.Item_cant,R.id.Item_precio,R.id.Item_valor};
        List<HashMap<String, String>> mapListaFactura = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();

            map.put("Articulo", "0000");
            map.put("Descr", "NOMBRE del ARTICULO");

            map.put("Cantidad", "3");
            map.put("Precio", "C$ 100");
            map.put("Valor", "C$ 300");

        mapListaFactura.add(map);

        adapter = new SpecialAdapter(this, mapListaFactura, R.layout.grid_item_ticket, from, to);
        lv.setAdapter(adapter);
        //SubTotal();
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
        getMenuInflater().inflate(R.menu.menu_print,menu);
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
