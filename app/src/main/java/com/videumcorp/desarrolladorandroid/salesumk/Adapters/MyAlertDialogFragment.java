package com.videumcorp.desarrolladorandroid.salesumk.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.R;

/**
 * Created by marangelo.php on 29/06/2016.
 */

public class MyAlertDialogFragment extends DialogFragment {

    private String Articulo;

    public String getArticulo() {
        return Articulo;
    }

    public void setArticulo(String articulo) {
        Articulo = articulo;
    }

    public MyAlertDialogFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.dialog_addcls, null);
        alertDialogBuilder.setView(dialogView);
        alertDialogBuilder.setTitle(getArticulo());
        TextView title = new TextView(getActivity());
        title.setText(getArticulo());
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(20);

        alertDialogBuilder.setCustomTitle(title);

        final EditText edt = (EditText) dialogView.findViewById(R.id.IdCantidad);
        final  EditText edtBoni = (EditText) dialogView.findViewById(R.id.IdBonificado);

        alertDialogBuilder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Toast.makeText(getActivity(), edt.getText().toString(), Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), edtBoni.getText().toString(), Toast.LENGTH_LONG).show();
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
