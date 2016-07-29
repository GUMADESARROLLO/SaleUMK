package com.guma.desarrollo.salesumk.Adapters;

/**
 * Created by marangelo.php on 14/07/2016.
 */
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;
import com.guma.desarrollo.salesumk.R;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        TextView tv1= (TextView) getActivity().findViewById(R.id.txtDateR);
        String mes,dia;

        if (view.getDayOfMonth()<=9){
            dia = "0" + String.valueOf(view.getDayOfMonth());
        }else{
            dia = String.valueOf(view.getDayOfMonth());
        }

        if (view.getMonth()<=9){
            mes = "0" + String.valueOf(view.getMonth());
        }else{
            mes = String.valueOf(view.getMonth());
        }
        tv1.setText(dia+"/"+mes+"/"+view.getYear());

    }
}
