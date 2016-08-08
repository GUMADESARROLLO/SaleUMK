package com.guma.desarrollo.salesumk.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.guma.desarrollo.salesumk.Adapters.DatePickerFragment;
import com.guma.desarrollo.salesumk.DataBase.DataBaseHelper;
import com.guma.desarrollo.salesumk.Lib.Funciones;
import com.guma.desarrollo.salesumk.Lib.Numero_a_Letra;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;
import com.guma.desarrollo.salesumk.Adapters.DialogAddFact;
import com.guma.desarrollo.salesumk.Adapters.SpecialAdapter;
import com.guma.desarrollo.salesumk.R;
import com.guma.desarrollo.salesumk.Lib.Variables;

public class FrmCobro_Activity extends AppCompatActivity
        implements Validator.ValidationListener{
    DataBaseHelper myDB;
    Variables vrb;
    Funciones vrf;
    SpecialAdapter adapter=null;
    TextView addfact;
    Spinner spinner;
    private EditText TC;
    private EditText monto;
    private EditText FechaRecibo;
    private Button BtnCallDatePciket;


    bandejaCobroActivity Bandeja = new bandejaCobroActivity() ;



// TODO: FALTA LA VALIDACION DEL INGRESO DE LA INFORMACION Y CORRECCION DE BUG

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


    private RadioButton rbtnEfectivo;
    private RadioButton rbtnBanco;

    private RadioGroup GrupoBanco;;

    private EditText NoBanco;
    private EditText ChkBanco;
    private Spinner TipoMoneda;
    private  String SqlInsert="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_cobro_);
        setTitle("RECIBO OFICIAL DE COBRO");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        myDB = new DataBaseHelper(this);
       TipoMoneda = (Spinner) findViewById(R.id.spinner);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        vrb.setLv_list_facturaCobro((ListView) findViewById(R.id.listview_DRecibo));

        final Numero_a_Letra NumLetra = new Numero_a_Letra();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                TC.setError(null);

                monto.setError(null);
                FechaRecibo.setError(null);

                int Tmoneda = (int) TipoMoneda.getSelectedItemId();
                if (GrupoBanco.getCheckedRadioButtonId()==-1){
                    Toast.makeText(FrmCobro_Activity.this, "EFECTIVO 0 CHEQUE", Toast.LENGTH_SHORT).show();

                }else{
                    if (TextUtils.isEmpty(FechaRecibo.getText().toString())){
                        FechaRecibo.setError("Campo Requerido");
                    }else{
                        if (Tmoneda == 0){
                            if (TextUtils.isEmpty(monto.getText().toString()) ){
                                monto.setError("Campo Requerido");
                            }else{
                                validator.validate();
                            }

                        }else{
                            if (TextUtils.isEmpty(TC.getText().toString()) ){
                                TC.setError("Campo Requerido");
                            }else{
                                validator.validate();
                            }

                        }
                    }
                }


            }
        });



        TC = (EditText) findViewById(R.id.txtTC);


        monto = (EditText) findViewById(R.id.txtVRecibo);
        CodCliente = (EditText) findViewById(R.id.txtRCliente);
        CodCliente.setText(vrb.getCliente_recibo_factura());

        CodRecibo = (EditText) findViewById(R.id.txtCodRC);

        FechaRecibo = (EditText) findViewById(R.id.txtDateR);
        BtnCallDatePciket= (Button) findViewById(R.id.BtnCallDatePicket);

        rbtnEfectivo = (RadioButton) findViewById(R.id.chckBxEfectivo);
        rbtnBanco=(RadioButton) findViewById(R.id.chckBxBanco);

        NoBanco = (EditText) findViewById(R.id.txtNoBanco);
        ChkBanco= (EditText) findViewById(R.id.txtNameBanco);

        GrupoBanco = (RadioGroup) findViewById(R.id.opciones_banco);

        BtnCallDatePciket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        Recibimosde = (EditText) findViewById(R.id.txtRecibmosDe);
        Recibimosde.setText(vrb.getCliente_Name_recibo_factura());

        LaCantidadde = (EditText) findViewById(R.id.txtCntDe);

        monto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (monto.getText().toString().trim()==""){
                        monto.setError("Campo vacio");
                    }else{



                        LaCantidadde.setText(NumLetra.Convertir(monto.getText().toString(),true, (int) TipoMoneda.getSelectedItemId()));
                    }

                }

            }

        });
        EnConcepto = (EditText) findViewById(R.id.txtEnCntDe);

        addfact = (TextView) findViewById(R.id.AddFact);
        addfact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                DialogAddFact alertDialog = new DialogAddFact();
                alertDialog.show(fm, "dialog_addfacturarecibo");
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

                            }
                        }).create().show();
            }
        });

        validator = new Validator(this);
        validator.setValidationListener(this);
        loadData();
    }

    private void SaveRecibo(){
        int Tmoneda = (int) TipoMoneda.getSelectedItemId();
        boolean Inserted = false;
        Inserted = myDB.SaveRecibo(
                    CodRecibo.getText().toString(),
                    CodCliente.getText().toString(),
                    vrb.getIdVendedor().toUpperCase(),
                    vrb.getNameVendedor().toUpperCase(),
                    FechaRecibo.getText().toString(),
                    monto.getText().toString(),
                    TC.getText().toString(),
                    String.valueOf(Tmoneda),
                    Recibimosde.getText().toString(),
                    LaCantidadde.getText().toString(),
                    EnConcepto.getText().toString(),
                    rbtnEfectivo.isChecked(),rbtnBanco.isChecked(),
                    NoBanco.getText().toString(),
                    ChkBanco.getText().toString()
        );
        String CodFact = vrb.getIdVendedor().toUpperCase()+ "-"+CodRecibo.getText().toString();


        //{Saldo=4981.59, VRecibido=0, Retencion=0, FacturaNo=FC002649, Descuento=0, ValorNC=0, vfactura=4981.59}
        for (int i = 0; i < vrb.getLv_list_facturaCobro().getCount(); i++) {

            String Cadena = String.valueOf(vrb.getLv_list_facturaCobro().getItemAtPosition(i));
            String Arry[] = vrf.ClearStrgList(Cadena).split(",");

            String Factura      = Arry[3].substring(Arry[3].indexOf("=")+1);
            String vfactura     = Arry[6].substring(Arry[6].indexOf("=")+1);
            String NC           = Arry[5].substring(Arry[5].indexOf("=")+1);
            String Retencion    = Arry[2].substring(Arry[2].indexOf("=")+1);
            String Descuento    = Arry[4].substring(Arry[4].indexOf("=")+1);
            String VRecibido    = Arry[1].substring(Arry[1].indexOf("=")+1);
            String Saldo        = Arry[0].substring(Arry[0].indexOf("=")+1);

            SqlInsert +="("+"'"+CodFact+"'," +"'"+Factura+"'," +""+"'"+vfactura+"',"+"'"+NC+"',"+"'"+Retencion+"',"+"'"+Descuento+"',"+"'"+VRecibido+"',"+"'"+Saldo+"'"+"),";
        }
        SqlInsert = SqlInsert.substring(0,SqlInsert.length()-1);
        myDB.SaveDetalleRecibo(SqlInsert);

        if (Inserted == true){
            Toast.makeText(this, "Datos Ingresados Correctamente", Toast.LENGTH_SHORT).show();
            Back();
        }else{
            Toast.makeText(this, "Datos No Ingresados Correctamente", Toast.LENGTH_SHORT).show();
        }





    }

    public void Back(){
        vrb.getFillMaps().clear();
        vrb.getFillMapsBandejaCobro().clear();
        Intent MenuIntent = new Intent(FrmCobro_Activity.this,bandejaCobroActivity.class);
        FrmCobro_Activity.this.startActivity(MenuIntent);
        finish();

    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == 16908332){
            Back();
        }
        return super.onOptionsItemSelected(item);
    }
    private void loadData() {
        String[] from = new String[] { "FacturaNo","vfactura","ValorNC","Retencion","Descuento","VRecibido","Saldo"};
        int[] to = new int[] { R.id.Item_recibo,R.id.Item_vFactura,R.id.Item_VNC,R.id.Item_Retencion,R.id.Item_Descuento,R.id.Item_VRecibo,R.id.Item_Saldo};
        String[] datos ={""};
        vrb.setAdapter(new SpecialAdapter(this, vrb.getFillMaps(), R.layout.grid_item_recibo, from, to));
        vrb.getLv_list_facturaCobro().setAdapter(vrb.getAdapter());

    }

    @Override
    public void onValidationSucceeded() {
        int cnt = vrb.getLv_list_facturaCobro().getAdapter().getCount();
        if (cnt > 0){
            SaveRecibo();
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
