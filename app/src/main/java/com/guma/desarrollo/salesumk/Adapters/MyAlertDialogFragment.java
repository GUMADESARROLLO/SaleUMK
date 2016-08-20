package com.guma.desarrollo.salesumk.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.guma.desarrollo.salesumk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marangelo.php on 29/06/2016.
 */

public class MyAlertDialogFragment extends DialogFragment {

    private String[] Articulo;

    public String[] getArticulo() {
        return Articulo;
    }

    public void setArticulo(String[] articulo) {
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
        alertDialogBuilder.setTitle(getArticulo()[0]);
        TextView title = new TextView(getActivity());
        title.setText(getArticulo()[1]+ "\n" + getArticulo()[0]);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(20);

        alertDialogBuilder.setCustomTitle(title);

        final EditText edt = (EditText) dialogView.findViewById(R.id.IdCantidad);

        String[] ReglasBoni = getArticulo()[3].split(",");
        final Spinner ListBonificado = (Spinner) dialogView.findViewById(R.id.spnBonificado);

        List<String> Reglas = new ArrayList<String>();

        for (int i =0;i<ReglasBoni.length;i++){
            Reglas.add(ReglasBoni[i]);
        }


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(dialogView.getContext(), android.R.layout.simple_spinner_item, Reglas);
        ListBonificado.setAdapter(dataAdapter);

        final Intent BackList = getActivity().getIntent();
        final String[] Send = new String[5];
        final Bundle b = new Bundle();



        alertDialogBuilder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                edt.setError(null);


                if (TextUtils.isEmpty(edt.getText())){
                    edt.setError("Requerido");

                }else{
                    Send[0] = getArticulo()[0];
                    Send[1] = getArticulo()[1];
                    Send[2] = getArticulo()[2];
                    Send[3] = edt.getText().toString();
                    Send[4] = ListBonificado.getSelectedItem().toString();

                    b.putStringArray("Articulo", Send);
                    BackList.putExtras(b);
                    getActivity().setResult(getActivity().RESULT_OK, BackList);
                    getActivity().finish();
                }
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        return alertDialogBuilder.create();
    }



}
