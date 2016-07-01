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
        final Spinner spinner = (Spinner) dialogView.findViewById(R.id.spinner_factura);


        Cursor res =  myDB.InfoClienteFactura(vrb.getCliente_recibo_factura().toString());
        if (res.moveToFirst()) {

            do {
                Log.d("FACTURA: ",res.getString(0));

            } while(res.moveToNext());


        }

        String[] valores = {"uno","dos","tres","cuatro","cinco","seis", "siete", "ocho"};
        spinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, valores));


        //final EditText edt = (EditText) dialogView.findViewById(R.id.IdCantidad);
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
