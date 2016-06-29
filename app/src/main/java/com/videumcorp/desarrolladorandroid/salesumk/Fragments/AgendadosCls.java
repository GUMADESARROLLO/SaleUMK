package com.videumcorp.desarrolladorandroid.salesumk.Fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import com.videumcorp.desarrolladorandroid.salesumk.Adapters.MyExpandableListAdapter;
import com.videumcorp.desarrolladorandroid.salesumk.DataBase.DataBaseHelper;
import com.videumcorp.desarrolladorandroid.salesumk.Activity.MainActivity;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.R;
import com.videumcorp.desarrolladorandroid.salesumk.Lib.ChildRow;
import com.videumcorp.desarrolladorandroid.salesumk.Lib.ParentRow;
import java.util.ArrayList;


/**
 * Created by marangelo.php on 04/06/2016.
 */
public class AgendadosCls extends Fragment  implements SearchView.OnQueryTextListener,SearchView.OnCloseListener{

    DataBaseHelper myDB;

    private MyExpandableListAdapter listAdapter;
    private ExpandableListView myList;
    private ArrayList<ParentRow> parentList = new ArrayList<ParentRow>();
    private ArrayList<ParentRow> showTheseParentList = new ArrayList<ParentRow>();
    private MenuItem searchItem;
    private View view;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cls_agendados, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("PLAN DE TRABAJO");
        myDB = new DataBaseHelper(getActivity());

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

        String[] Clientes ={"00931,FARMACIA FARMA DESCUENTO","00943,FARMACIA FARMA VALUE/ RUC J310000170967","00950,FARMACIA FARMEX","01191,FARMACIA LA FAMILIAR - RUC 0013108770039L"};

        String[] empry = new String[0];

        CrearList("LUNES", Clientes);
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
