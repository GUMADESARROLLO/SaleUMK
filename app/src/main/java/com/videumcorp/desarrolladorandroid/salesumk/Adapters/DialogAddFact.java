package com.videumcorp.desarrolladorandroid.salesumk.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.R;
import com.videumcorp.desarrolladorandroid.salesumk.DataBase.DataBaseHelper;
import com.videumcorp.desarrolladorandroid.salesumk.Lib.Variables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marangelo.php on 01/07/2016.
 */
public class DialogAddFact extends DialogFragment {
    DataBaseHelper myDB;
    Variables vrb;
    public DialogAddFact() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.dialog_addfacturarecibo, null);
        alertDialogBuilder.setView(dialogView);
        Spinner spinner = (Spinner) dialogView.findViewById(R.id.spinner_factura);

        int i=0;
        String[] valores = new String[0];
        myDB = new DataBaseHelper(getActivity());
        final EditText edt = (EditText) dialogView.findViewById(R.id.idRecVR);

        Cursor res =  myDB.InfoClienteFactura(vrb.getCliente_recibo_factura());
        if (res.getCount()==0){
            valores = new String[]{"Sin Datos..."};
        }else{
            if (res.moveToFirst()) {
                valores = new String[res.getCount()];
                do {
                    valores[i]=res.getString(0);
                    i++;

                } while(res.moveToNext());
            }
        }

        edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Toast.makeText(getActivity(), "HACER LOS CALCULOS", Toast.LENGTH_SHORT).show();
            }
        });


        //String[] valores = {"uno","dos","tres","cuatro","cinco","seis", "siete", "ocho"};
        spinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, valores));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String scountry = parent.getItemAtPosition(position).toString();

                Toast.makeText(getActivity(),scountry, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        //final  EditText edtBoni = (EditText) dialogView.findViewById(R.id.IdBonificado);

        alertDialogBuilder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Toast.makeText(getActivity(), edt.getText().toString(), Toast.LENGTH_LONG).show();
                //Toast.makeText(getActivity(), edtBoni.getText().toString(), Toast.LENGTH_LONG).show();
                //getActivity().finish();
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        return alertDialogBuilder.create();
    }
}
