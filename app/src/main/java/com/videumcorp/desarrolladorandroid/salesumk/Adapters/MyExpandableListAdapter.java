package com.videumcorp.desarrolladorandroid.salesumk.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.videumcorp.desarrolladorandroid.salesumk.Activity.CrearSaleActivity;
import com.videumcorp.desarrolladorandroid.salesumk.Activity.ObservacionActivity;
import com.videumcorp.desarrolladorandroid.salesumk.Activity.bandejaCobroActivity;
import com.videumcorp.desarrolladorandroid.salesumk.Lib.ChildRow;
import com.videumcorp.desarrolladorandroid.salesumk.Lib.ParentRow;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.R;

import java.util.ArrayList;

/**
 * Created by marangelo.php on 13/06/2016.
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<ParentRow> parentRowList;
    private ArrayList<ParentRow> originalList;


    public MyExpandableListAdapter(Context context, ArrayList<ParentRow> originalList) {
        this.context = context;
        this.parentRowList= new ArrayList<>();
        this.parentRowList.addAll(originalList);
        this.originalList = new ArrayList<>();
        this.originalList.addAll(originalList);
    }



    @Override
    public int getGroupCount() {
        return parentRowList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return parentRowList.get(groupPosition).getChildList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parentRowList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return parentRowList.get(groupPosition).getChildList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentRow parentRow = (ParentRow) getGroup(groupPosition);

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.parent_row,null);
        }


        TextView heading = (TextView) convertView.findViewById(R.id.parent_row);



        heading.setText(parentRow.getName().trim());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildRow childRow = (ChildRow) getChild(groupPosition,childPosition);
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.child_row,null);
        }
        final TextView childText = (TextView) convertView.findViewById(R.id.chil_text);
        childText.setText(childRow.getText().trim());
        final View finalConvertView = convertView;
        final int grupo = groupPosition;
        childText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(finalConvertView.getContext());
                ListView modeListView = new ListView(finalConvertView.getContext());
                String[] modes = new String[] { "PEDIDO", "COBRO","VISITA" };
                ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(finalConvertView.getContext(),android.R.layout.simple_list_item_1, android.R.id.text1, modes);
                modeListView.setAdapter(modeAdapter);
                builder.setView(modeListView);
                final Dialog dialog = builder.create();
                dialog.show();
                modeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        switch (position){
                            case 0:
                                Intent Mint = new Intent(finalConvertView.getContext(),CrearSaleActivity.class);
                                finalConvertView.getContext().startActivity(Mint);
                                break;
                            case 1:
                                Intent Mint2 = new Intent(finalConvertView.getContext(),bandejaCobroActivity.class);
                                finalConvertView.getContext().startActivity(Mint2);
                                break;
                            case 2:
                                Intent Mint3 = new Intent(finalConvertView.getContext(),ObservacionActivity.class);
                                finalConvertView.getContext().startActivity(Mint3);
                                break;
                        }


                        dialog.dismiss();
                    }
                });
            }
        });

       /* childText.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Toast.makeText(finalConvertView.getContext(),childText.getText(),Toast.LENGTH_SHORT).show();

                String Nombre = String.valueOf(finalConvertView.getContext());
                int Posicion = Nombre.indexOf("mtcl");
                if (Posicion != -1){
                    Log.d("Vista de","CLIENTES");
                    Toast.makeText(finalConvertView.getContext(),String.valueOf(finalConvertView.getContext()),Toast.LENGTH_SHORT).show();
                    //Intent MenuIntent = new Intent(context,Sale_Activity.class);
                    //MenuIntent.putExtra("Vendedor",User.toUpperCase());
                    //context.startActivity(MenuIntent);

                }


            }
        });
        */
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    public void filterData(String query){

        query = query.toLowerCase();
        parentRowList.clear();
        if (query.isEmpty()){
            parentRowList.addAll(originalList);
        }else{

            for (ParentRow parentRow : originalList){
                if (parentRow.getName().toLowerCase().contains(query)){
                    ArrayList<ChildRow> childList = parentRow.getChildList();
                    //newList.add(childRow);
                    ArrayList<ChildRow> newList = new ArrayList<ChildRow>();
                    for (ChildRow childRow: childList){
                        newList.add(childRow);
                    }
                    if (newList.size() > 0){
                        ParentRow nParentRow = new ParentRow(parentRow.getName(),newList);
                        parentRowList.add(nParentRow);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }
}
