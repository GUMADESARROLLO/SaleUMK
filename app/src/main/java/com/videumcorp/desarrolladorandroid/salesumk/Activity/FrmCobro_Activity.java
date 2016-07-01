package com.videumcorp.desarrolladorandroid.salesumk.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NumberRule;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;
import com.videumcorp.desarrolladorandroid.salesumk.Adapters.DialogAddFact;
import com.videumcorp.desarrolladorandroid.salesumk.Adapters.MyAlertDialogFragment;
import com.videumcorp.desarrolladorandroid.salesumk.Adapters.SpecialAdapter;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.R;
import com.videumcorp.desarrolladorandroid.salesumk.DataBase.DataBaseHelper;
import com.videumcorp.desarrolladorandroid.salesumk.Lib.Variables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FrmCobro_Activity extends AppCompatActivity
        implements Validator.ValidationListener{

    Variables vrb;
    SpecialAdapter adapter=null;
    ListView lv;


    TextView addfact;
    Spinner spinner;

    Validator validator;
    @Required(order = 1,message = "Campo requerido")

    @TextRule(order = 2, minLength = 2, maxLength = 999999999, message = "Ingrese al menos 2 Caracteres")
    private EditText monto;

    @TextRule(order = 3, minLength = 2, maxLength = 999999999, message = "Ingrese al menos 2 Caracteres")
    private EditText CodCliente;

    @TextRule(order = 4, minLength = 2, maxLength = 999999999, message = "Ingrese al menos 2 Caracteres")
    private EditText CodRecibo;

    @TextRule(order = 5, minLength = 2, maxLength = 999999999, message = "Ingrese al menos 2 Caracteres")
    private EditText Recibimosde;

    @TextRule(order = 6, minLength = 2, maxLength = 999999999, message = "Ingrese al menos 2 Caracteres")
    private EditText LaCantidadde;

    @TextRule(order = 7, minLength = 2, maxLength = 999999999, message = "Ingrese al menos 2 Caracteres")
    private EditText EnConcepto;

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
                validator.validate();
            }
        });

        monto = (EditText) findViewById(R.id.txtVRecibo);
        CodCliente = (EditText) findViewById(R.id.txtRCliente);
        CodRecibo = (EditText) findViewById(R.id.txtCodRC);

        Recibimosde = (EditText) findViewById(R.id.txtRecibmosDe);
        LaCantidadde = (EditText) findViewById(R.id.txtCntDe);
        EnConcepto = (EditText) findViewById(R.id.txtEnCntDe);

        addfact = (TextView) findViewById(R.id.AddFact);
        addfact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getSupportFragmentManager();
                DialogAddFact alertDialog = new DialogAddFact();
                //alertDialog.setArticulo(Cod);
                alertDialog.show(fm, "dialog_addfacturarecibo");


                Toast.makeText(FrmCobro_Activity.this, "Llenar Lista", Toast.LENGTH_SHORT).show();

            }
        });

        validator = new Validator(this);
        validator.setValidationListener(this);
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

        String[] datos ={""};
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

    @Override
    public void onValidationSucceeded() {
        int cnt = lv.getAdapter().getCount();
        if (cnt > 1){
            Toast.makeText(this, "Datos ingresados correctamente", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No hay Facturas Ingresadas", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        final String failureMessage = failedRule.getFailureMessage();
        if (failedView instanceof EditText) {
            EditText failed = (EditText) failedView;
            failed.requestFocus();
            failed.setError(failureMessage);
        } else {
            Toast.makeText(getApplicationContext(), failureMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
