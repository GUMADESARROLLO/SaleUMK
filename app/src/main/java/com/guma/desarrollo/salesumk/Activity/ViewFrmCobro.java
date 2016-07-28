package com.guma.desarrollo.salesumk.Activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.guma.desarrollo.salesumk.DataBase.DataBaseHelper;
import com.guma.desarrollo.salesumk.Lib.Variables;
import com.guma.desarrollo.salesumk.R;

public class ViewFrmCobro extends AppCompatActivity{

    Variables vrb;
    DataBaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_frm_cobro);
        setTitle("RECIBO OFICIAL DE COBRO [VISTA]");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Spinner TM               = (Spinner) findViewById(R.id.spinner);

        EditText Monto           = (EditText) findViewById(R.id.txtVRecibo);

        EditText CodCliente      = (EditText) findViewById(R.id.txtRCliente);
        EditText CodRecibo       = (EditText) findViewById(R.id.txtCodRC);
        EditText TC              = (EditText) findViewById(R.id.txtTC);
        EditText FechaRecibo     = (EditText) findViewById(R.id.txtDateR);

        EditText RecibimosDe     = (EditText) findViewById(R.id.txtRecibmosDe);
        EditText LaCantidadDe    = (EditText) findViewById(R.id.txtCntDe);
        EditText EnConceptoDe    = (EditText) findViewById(R.id.txtEnCntDe);

        RadioButton Efectivo     = (RadioButton) findViewById(R.id.chckBxEfectivo);
        RadioButton Banco        = (RadioButton) findViewById(R.id.chckBxBanco);

        EditText NBanco          = (EditText) findViewById(R.id.txtNoBanco);
        EditText NombreBanco     = (EditText) findViewById(R.id.txtNameBanco);


        myDB = new DataBaseHelper(ViewFrmCobro.this);




        Cursor res = myDB.GetInfoRecibo(vrb.getIdViewRecibo());
        if (res.getCount()!=0){
            if (res.moveToFirst()) {
                do {
                    TM.setSelection(Integer.parseInt(res.getString(6)));
                    Monto.setText(res.getString(4));
                    CodCliente.setText(res.getString(1));
                    CodRecibo.setText(res.getString(0));
                    TC.setText(res.getString(5));
                    FechaRecibo.setText(res.getString(3));
                    RecibimosDe.setText(res.getString(7));
                    LaCantidadDe.setText(res.getString(8));
                    EnConceptoDe.setText(res.getString(9));


                    if (Integer.parseInt(res.getString(10)) == 1){
                        Efectivo.setChecked(true);
                        Banco.setChecked(false);
                    }else{
                        Efectivo.setChecked(false);
                        Banco.setChecked(true);
                    }

                    NBanco.setText(res.getString(12));
                    NombreBanco.setText(res.getString(13));



                } while(res.moveToNext());
            }
        }
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == 16908332){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
