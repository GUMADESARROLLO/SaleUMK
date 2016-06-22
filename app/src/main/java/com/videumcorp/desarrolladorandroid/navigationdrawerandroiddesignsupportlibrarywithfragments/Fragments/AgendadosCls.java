package com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Fragments;


import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Activity.CrearSaleActivity;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Activity.FrmCobro_Activity;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Activity.InfoClientesActivity;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Activity.ObservacionActivity;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Adapters.SpecialAdapter;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Lib.ClssURL;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.DataBase.DataBaseHelper;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Activity.MainActivity;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.R;


import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by marangelo.php on 04/06/2016.
 */
public class AgendadosCls extends Fragment  {

    DataBaseHelper myDB;
    SpecialAdapter adapter;
    ListView lv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_cls_agendados, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("AGENDA DE, ");

        lv = (ListView) view.findViewById(R.id.listview);
        myDB = new DataBaseHelper(getActivity());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFromList = String.valueOf(lv.getItemAtPosition(position));
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                ListView modeListView = new ListView(getActivity());
                //String Cod = ClearString(selectedFromList);
                String[] modes = new String[] { "PEDIDO", "COBRO","OBSERVACION" };
                ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1, modes);
                modeListView.setAdapter(modeAdapter);
                builder.setView(modeListView);
                final Dialog dialog = builder.create();
                dialog.show();
                //Toast.makeText(getActivity(), Cod, Toast.LENGTH_SHORT).show();
                modeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        switch (position){
                            case 0:
                                Intent Mint = new Intent(getActivity(),CrearSaleActivity.class);
                                getActivity().startActivity(Mint);
                                break;
                            case 1:
                                Intent Mint2 = new Intent(getActivity(),FrmCobro_Activity.class);
                                getActivity().startActivity(Mint2);
                                break;
                            case 2:
                                Intent Mint3 = new Intent(getActivity(),ObservacionActivity.class);
                                getActivity().startActivity(Mint3);
                                break;
                        }


                        dialog.dismiss();
                    }
                });
            }


        });


/*      FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearList();
                CallInve();
            }
        });


        */


        loadData();


        return view;
    }
    private void loadData(){
        ArrayList<String> datos = new ArrayList<String>();
        String[] from = new String[] {"rowid", "D1", "D2","D3","D4","D5"};
        int[] to = new int[] { R.id.d1, R.id.d2, R.id.d3,R.id.d4,R.id.d5};

        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();

        Cursor res =  myDB.GetData("Clientes");
        if (res.moveToFirst()) {

            do {
                HashMap<String, String> map = new HashMap<String, String>();
                datos.add(res.getString(1)+"\n["+res.getString(0)+"]");
                map.put("rowid", res.getString(0));
                map.put("D1", res.getString(0));
                map.put("D2",res.getString(0));
                map.put("D3",res.getString(0));
                map.put("D4",res.getString(0));
                map.put("D5",res.getString(0));
                fillMaps.add(map);

            } while(res.moveToNext());


        }
        res.close();



        // fill in the grid_item layout
        adapter = new SpecialAdapter(getActivity(), fillMaps, R.layout.grid_item_calendario, from, to);
        lv.setAdapter(adapter);
    }

}
