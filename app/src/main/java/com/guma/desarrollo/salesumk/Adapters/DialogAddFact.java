package com.guma.desarrollo.salesumk.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.guma.desarrollo.salesumk.R;
import com.guma.desarrollo.salesumk.DataBase.DataBaseHelper;
import com.guma.desarrollo.salesumk.Lib.Variables;

import java.util.HashMap;

/**
 * Created by marangelo.php on 01/07/2016.
 */
public class DialogAddFact extends DialogFragment {
    DataBaseHelper myDB;
    Variables vrb;
    public DialogAddFact() {
    }

    private String[] from = new String[] { "FacturaNo","vfactura","ValorNC","Retencion","Descuento","VRecibido","Saldo"};
    private int[] to = new int[] { R.id.Item_recibo,R.id.Item_vFactura,R.id.Item_VNC,R.id.Item_Retencion,R.id.Item_Descuento,R.id.Item_VRecibo,R.id.Item_Saldo};


    SpecialAdapter adapter = null;
    private String Factura;
    private String Monto;
    private String NC;
    private String Retencion;
    private String Descuento;
    private String ValorFactura;
    private String ValorRecibo;
    private Float Sld;







    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);

        final View dialogView = inflater.inflate(R.layout.dialog_addfacturarecibo, null);
        alertDialogBuilder.setView(dialogView);

        //adapter = new SpecialAdapter(getActivity(), fillMaps, R.layout.grid_item_recibo, from, to);
        vrb.setAdapter(new SpecialAdapter(getActivity(), vrb.getFillMaps(), R.layout.grid_item_recibo, from, to));

        Log.d(getTag(),vrb.getFillMaps().toString());


        Spinner spinner = (Spinner) dialogView.findViewById(R.id.spinner_factura);



        int i=0;
        String[] valores = new String[0];
        myDB = new DataBaseHelper(getActivity());

        final EditText edtVFact = (EditText) dialogView.findViewById(R.id.idRecFactura);
        final EditText edt = (EditText) dialogView.findViewById(R.id.idRecVR);
        final EditText saldo = (EditText) dialogView.findViewById(R.id.idRecSaldo);

        final EditText txtNC = (EditText) dialogView.findViewById(R.id.idRecNC);
        final EditText txtRetencion = (EditText) dialogView.findViewById(R.id.idRecRetencion);
        final EditText txtDescuento = (EditText) dialogView.findViewById(R.id.idRecDescuento);


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
        alertDialogBuilder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                ValorFactura = (TextUtils.isEmpty(edtVFact.getText().toString())) ? "0" : edtVFact.getText().toString() ;
                NC = (TextUtils.isEmpty(txtNC.getText().toString())) ? "0" : txtNC.getText().toString() ;
                Retencion = (TextUtils.isEmpty(txtRetencion.getText().toString())) ? "0" : txtRetencion.getText().toString() ;
                Descuento = (TextUtils.isEmpty(txtDescuento.getText().toString())) ? "0" : txtDescuento.getText().toString() ;
                ValorRecibo = (TextUtils.isEmpty(edt.getText().toString())) ? "0" : edt.getText().toString() ;
                Sld = Float.parseFloat(ValorFactura) - Float.parseFloat(NC) - Float.parseFloat(Retencion) - Float.parseFloat(Descuento) - Float.parseFloat(ValorRecibo);


                //vrb.getLv_list_facturaCobro().setAdapter(adapter);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("FacturaNo", Factura);
                map.put("vfactura", ValorFactura);
                map.put("ValorNC", NC);
                map.put("Retencion", Retencion);
                map.put("Descuento", Descuento);
                map.put("VRecibido", ValorRecibo);
                map.put("Saldo", String.valueOf(Sld));
                vrb.getFillMaps().add(map);


                //vrb.getAdapter().notifyDataSetChanged();
                vrb.getLv_list_facturaCobro().setAdapter(vrb.getAdapter());
                //adapter.notifyDataSetChanged();





                String Insert = "Facturas: " + Factura +" Saldo:  "+ ValorFactura + " NC: " + NC + " Retencion: "+ Retencion + " Descuento: " + Descuento + " ValorRecibo: " + ValorRecibo + " Saldo: "+ Sld;
                //Toast.makeText(getActivity(), Insert, Toast.LENGTH_SHORT).show();
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

        edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ValorFactura = (TextUtils.isEmpty(edtVFact.getText().toString())) ? "0" : edtVFact.getText().toString() ;
                    NC = (TextUtils.isEmpty(txtNC.getText().toString())) ? "0" : txtNC.getText().toString() ;
                    Retencion = (TextUtils.isEmpty(txtRetencion.getText().toString())) ? "0" : txtRetencion.getText().toString() ;
                    Descuento = (TextUtils.isEmpty(txtDescuento.getText().toString())) ? "0" : txtDescuento.getText().toString() ;
                    ValorRecibo = (TextUtils.isEmpty(edt.getText().toString())) ? "0" : edt.getText().toString() ;

                    Sld = Float.parseFloat(ValorRecibo) - Float.parseFloat(NC) - Float.parseFloat(Retencion) - Float.parseFloat(Descuento) - Float.parseFloat(ValorRecibo);
                    saldo.setText(Sld.toString());

                }

            }

        });





        //String[] valores = {"uno","dos","tres","cuatro","cinco","seis", "siete", "ocho"};
        spinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, valores));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Factura = parent.getItemAtPosition(position).toString();
                Monto = myDB.GetValorFacCobro(Factura);
                edtVFact.setText(Monto);
                //Toast.makeText(getActivity(),Monto , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return alertDialogBuilder.create();
    }
}
