package com.guma.desarrollo.salesumk.Activity;

import android.content.Intent;
import android.database.Cursor;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.guma.desarrollo.salesumk.Adapters.SpecialAdapter;
import com.guma.desarrollo.salesumk.DataBase.DataBaseHelper;
import com.guma.desarrollo.salesumk.Lib.Funciones;
import com.guma.desarrollo.salesumk.R;
import com.lvrenyang.myprinter.Global;
import com.lvrenyang.myprinter.WorkService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewTicketActivity extends AppCompatActivity {

    Intent iPedido;
    TextView CodPedido,NombreVendedor,NombreCL,CountListArticulo,TotalPagar;
    DataBaseHelper myDB;
    String IdPedido;
    ListView lv;
    LinearLayout view;
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
        InitGlobalString();
        iPedido = getIntent();
        view = (LinearLayout)findViewById(R.id.idViewTicket);

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
    private void InitGlobalString() {
        Global.toast_success = getString(R.string.toast_success);
        Global.toast_fail = getString(R.string.toast_fail);
        Global.toast_notconnect = getString(R.string.toast_notconnect);
        Global.toast_usbpermit = getString(R.string.toast_usbpermit);
        if (null == WorkService.workThread) {
            Intent intent = new Intent(this, WorkService.class);
            startService(intent);
        }


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_print,menu);
        return true;
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

        if(id == R.id.action_print){


            if (WorkService.workThread.isConnected()){
                //PrintImage();
            }else{

                startActivityForResult(new Intent(ViewTicketActivity.this,ListaBTActivity.class),0);
            }
            //storeImage(b);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0 && resultCode==RESULT_OK){
            PrintImage();
        }else{
            Toast.makeText(ViewTicketActivity.this, "No se pudo Vincular con el Dispositivo", Toast.LENGTH_SHORT).show();
        }
    }

    public void PrintImage(){
        Bitmap b = viewToBitmap(view);
        Bundle data = new Bundle();
        data.putParcelable(Global.PARCE1, b);
        data.putInt(Global.INTPARA1, 384);
        data.putInt(Global.INTPARA2, 0);
        WorkService.workThread.handleCmd(Global.CMD_POS_PRINTPICTURE, data);
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
            Toast.makeText(ViewTicketActivity.this, file.toString(), Toast.LENGTH_LONG).show();
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
