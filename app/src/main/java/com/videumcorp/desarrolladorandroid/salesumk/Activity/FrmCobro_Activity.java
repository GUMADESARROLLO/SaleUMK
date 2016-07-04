package com.videumcorp.desarrolladorandroid.salesumk.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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



    TextView addfact;
    Spinner spinner;

    private EditText TC;
    private EditText ECS;
    private EditText monto;

    Validator validator;
    @Required(order = 1,message = "Campo requerido")




    @TextRule(order = 2, minLength = 2, maxLength = 999999999, message = "Ingrese al menos 2 Caracteres")
    private EditText CodCliente;

    @TextRule(order = 3, minLength = 2, maxLength = 999999999, message = "Ingrese al menos 2 Caracteres")
    private EditText CodRecibo;

    @TextRule(order = 4, minLength = 2, maxLength = 999999999, message = "Ingrese al menos 2 Caracteres")
    private EditText Recibimosde;

    @TextRule(order = 5, minLength = 2, maxLength = 999999999, message = "Ingrese al menos 2 Caracteres")
    private EditText LaCantidadde;

    @TextRule(order = 6, minLength = 2, maxLength = 999999999, message = "Ingrese al menos 2 Caracteres")
    private EditText EnConcepto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_cobro_);
        setTitle("RECIBO OFICIAL DE COBRO");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        final Spinner TipoMoneda = (Spinner) findViewById(R.id.spinner);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        vrb.setLv_list_facturaCobro((ListView) findViewById(R.id.listview_DRecibo));




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveRecibo();

                TC.setError(null);
                ECS.setError(null);
                monto.setError(null);

                int Tmoneda = (int) TipoMoneda.getSelectedItemId();
                Log.d("XXXXXX",String.valueOf(Tmoneda));


                if (Tmoneda == 0){
                    if (TextUtils.isEmpty(monto.getText().toString()) ){
                        monto.setError("Campo Requerido");
                    }else{
                        validator.validate();
                    }


                }else{
                    if (TextUtils.isEmpty(TC.getText().toString()) || TextUtils.isEmpty(ECS.getText().toString())){
                        TC.setError("Campo Requerido");
                        ECS.setError("Campo Requerido");
                    }else{
                        validator.validate();
                    }

                }





            }
        });



        TC = (EditText) findViewById(R.id.txtTC);
        ECS = (EditText) findViewById(R.id.txtENC);

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


                //Toast.makeText(FrmCobro_Activity.this, "Llenar Lista", Toast.LENGTH_SHORT).show();

            }
        });
        vrb.getLv_list_facturaCobro().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //vrb.getLv_list_facturaCobro().getItemAtPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(FrmCobro_Activity.this);
                builder.setMessage("Desea Eliminar este Registro")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                vrb.getFillMaps().remove(position);
                                vrb.getAdapter().notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        }).create().show();


                //String selectedFromList = String.valueOf(vrb.getLv_list_facturaCobro().getItemAtPosition(position));
                //Toast.makeText(FrmCobro_Activity.this, selectedFromList, Toast.LENGTH_SHORT).show();
            }
        });

        validator = new Validator(this);
        validator.setValidationListener(this);
        loadData();
    }
    private void SaveRecibo(){
        //{Saldo=4981.59, VRecibido=0, Retencion=0, FacturaNo=FC002649, Descuento=0, ValorNC=0, vfactura=4981.59}

        for (int i = 0; i < vrb.getLv_list_facturaCobro().getCount(); i++) {
            String Cadena = String.valueOf(vrb.getLv_list_facturaCobro().getItemAtPosition(i));
            Log.d("XXXXXX",Cadena);
            Cadena = Cadena.replace("{","");
            String[] Arry = Cadena.replace("}","").split(",");
            for (int Ar=0;Ar<Arry.length;Ar++){
                String[] ry = Arry[Ar].split("=");

                Toast.makeText(FrmCobro_Activity.this, ry[0].toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(FrmCobro_Activity.this, ry[1].toString(), Toast.LENGTH_SHORT).show();

            }

        }

    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == 16908332){
            vrb.getFillMaps().clear();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void loadData() {
        String[] from = new String[] { "FacturaNo","vfactura","ValorNC","Retencion","Descuento","VRecibido","Saldo"};
        int[] to = new int[] { R.id.Item_recibo,R.id.Item_vFactura,R.id.Item_VNC,R.id.Item_Retencion,R.id.Item_Descuento,R.id.Item_VRecibo,R.id.Item_Saldo};


        String[] datos ={""};
        /*for (int c=0;c<datos.length;c++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("FacturaNo", datos[c]);
            map.put("vfactura", datos[c]);
            map.put("ValorNC", datos[c]);
            map.put("Retencion", datos[c]);
            map.put("Descuento", datos[c]);
            map.put("VRecibido", datos[c]);
            map.put("Saldo", datos[c]);
            vrb.getFillMaps().add(map);
        }*/
        vrb.setAdapter(new SpecialAdapter(this, vrb.getFillMaps(), R.layout.grid_item_recibo, from, to));
        vrb.getLv_list_facturaCobro().setAdapter(vrb.getAdapter());

    }

    @Override
    public void onValidationSucceeded() {
        int cnt = vrb.getLv_list_facturaCobro().getAdapter().getCount();
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
