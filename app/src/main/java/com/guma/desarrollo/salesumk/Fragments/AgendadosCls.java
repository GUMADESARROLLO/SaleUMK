package com.guma.desarrollo.salesumk.Fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.guma.desarrollo.salesumk.Adapters.MyExpandableListAdapter;
import com.guma.desarrollo.salesumk.DataBase.DataBaseHelper;
import com.guma.desarrollo.salesumk.Activity.MainActivity;
import com.guma.desarrollo.salesumk.Lib.Funciones;
import com.guma.desarrollo.salesumk.Lib.Variables;
import com.guma.desarrollo.salesumk.R;
import com.guma.desarrollo.salesumk.Lib.ChildRow;
import com.guma.desarrollo.salesumk.Lib.ParentRow;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by marangelo.php on 04/06/2016.
 */
public class AgendadosCls extends Fragment  implements SearchView.OnQueryTextListener,SearchView.OnCloseListener{

    DataBaseHelper myDB;
    Variables vrb;
    Funciones vrf;

    private MyExpandableListAdapter listAdapter;
    private ExpandableListView myList;
    private ArrayList<ParentRow> parentList = new ArrayList<ParentRow>();
    private ArrayList<ParentRow> showTheseParentList = new ArrayList<ParentRow>();
    private MenuItem searchItem;
    private View view;

    TextView WeekStar,WeekEnd,RUTA,NombreVendedor,ZONA;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cls_agendados, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("PLAN DE TRABAJO");
        myDB = new DataBaseHelper(getActivity());

        ImageView imgSave = (ImageView) view.findViewById(R.id.uno);
        ImageView imgUpdate = (ImageView) view.findViewById(R.id.dos);

        NombreVendedor = (TextView) view.findViewById(R.id.FrmEjecutivo);
        RUTA = (TextView) view.findViewById(R.id.txtRuta);
        ZONA = (TextView) view.findViewById(R.id.txtZona);
        WeekStar = (TextView) view.findViewById(R.id.txtWDEL);
        WeekEnd = (TextView) view.findViewById(R.id.txtWAL);

        NombreVendedor.setText(vrb.getNameVendedor());
        RUTA.setText(vrb.getIdVendedor());


        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "DOS", Toast.LENGTH_SHORT).show();
            }
        });

        parentList = new ArrayList<ParentRow>();
        showTheseParentList = new ArrayList<ParentRow>();
        displayList();
        expandAll();
        return view;
    }




    private void expandAll(){
        int count = listAdapter.getGroupCount();
        for (int i=0;i<count;i++){
            myList.expandGroup(i);
        }
    }

    private boolean FrmValida(){
        boolean OK = false;
        WeekStar.setError(null);
        WeekEnd.setError(null);
        RUTA.setError(null);
        ZONA.setError(null);

        if (TextUtils.isEmpty(NombreVendedor.getText())){
            NombreVendedor.setError("Requerido");
        }else{
            if (TextUtils.isEmpty(RUTA.getText())){
                RUTA.setError("Requerido");
            }else{
                if (TextUtils.isEmpty(ZONA.getText())){
                    ZONA.setError("Requerido");
                }else{
                    if (TextUtils.isEmpty(WeekStar.getText())||TextUtils.isEmpty(WeekEnd.getText())){
                        WeekStar.setError("Requerido");
                        WeekEnd.setError("Requerido");
                    }else{
                        OK = true;


                    }
                }

            }

        }

        return OK;
    }
    private String OncreteIdPlan(){
        String IdPlan = "";
        if (FrmValida() == true){
            int Year = Calendar.getInstance().get(Calendar.YEAR);
            IdPlan = RUTA.getText().toString()+ String.valueOf(Year).substring(2,4) + "-" + vrf.prefixZero(WeekStar.getText().toString()) + vrf.prefixZero(WeekEnd.getText().toString());
        }
        return IdPlan;
    }
    private  Boolean SaveIdPlan(){
        boolean OK = false;

        String ID =  OncreteIdPlan();
        NombreVendedor.getText();
        RUTA.getText();
        ZONA.getText();
        WeekStar.getText().toString();
        WeekEnd.getText().toString();

        return OK;

    };
    private void displayList(){
        loadData();
        myList = (ExpandableListView) view.findViewById(R.id.expandableListView_search);
        listAdapter = new MyExpandableListAdapter(getActivity(),parentList);
        myList.setAdapter(listAdapter);

    }
    private void loadData(){
        /*Cursor res =  myDB.GetData("Articulo");
        if (res.moveToFirst()) {
            do {
                CrearList(res.getString(0), res.getString(1),res.getString(2),res.getString(3));

            } while(res.moveToNext());
        }*/

        String[] Clientes ={"00042,COMISARIATO DE LA POLICIA NACIONAL - RUC J0130000001725","00931,FARMACIA FARMA DESCUENTO","00943,FARMACIA FARMA VALUE/ RUC J310000170967","00950,FARMACIA FARMEX","01191,FARMACIA LA FAMILIAR - RUC 0013108770039L"};

        String[] empry = new String[0];

        CrearList("LUNES", empry);
        CrearList("MARTES", empry);
        CrearList("MIERCOLES", empry);
        CrearList("JUEVES", empry);
        CrearList("VIERNES", empry);




    }
    private void CrearList(String Dia, String[] Cliente){
        ArrayList<ChildRow> childRows=new ArrayList<ChildRow>();

        ParentRow parentRow = null;
        childRows.clear();


        parentRow = new ParentRow(Dia,childRows);
        for (int i=0;i<Cliente.length;i++){
            String[] items = Cliente[i].toString().split(",");
            childRows.add(new ChildRow(R.drawable.ic_remove_white_24dp,items[1],items[0]));
        }


        parentList.add(parentRow);
    }


    @Override
    public boolean onClose() {
        listAdapter.filterData("");
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        listAdapter.filterData(query);
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listAdapter.filterData(newText);
        expandAll();
        return false;
    }
}
