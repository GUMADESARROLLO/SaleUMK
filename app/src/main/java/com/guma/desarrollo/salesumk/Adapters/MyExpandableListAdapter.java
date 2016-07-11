package com.guma.desarrollo.salesumk.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.guma.desarrollo.salesumk.Activity.AgendaActivity;
import com.guma.desarrollo.salesumk.Activity.BandejaPedido;
import com.guma.desarrollo.salesumk.Activity.CrearSaleActivity;
import com.guma.desarrollo.salesumk.Activity.ObservacionActivity;
import com.guma.desarrollo.salesumk.Activity.bandejaCobroActivity;
import com.guma.desarrollo.salesumk.Lib.ChildRow;
import com.guma.desarrollo.salesumk.Lib.ParentRow;
import com.guma.desarrollo.salesumk.R;
import com.guma.desarrollo.salesumk.Lib.Variables;

import java.util.ArrayList;

/**
 * Created by marangelo.php on 13/06/2016.
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    Variables vrb;



    private Context context;
    private ArrayList<ParentRow> parentRowList;
    private ArrayList<ParentRow> originalList;
    private AgendaActivity Agenda;


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
        TextView add = (TextView) convertView.findViewById(R.id.add_cls);


        final View finalConvertView = convertView;

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"Cliente 1","Cliente 2"};


                AlertDialog.Builder builder = new AlertDialog.Builder(finalConvertView.getContext());
                builder.setSingleChoiceItems(items,-1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        Toast.makeText(finalConvertView.getContext(), items[item], Toast.LENGTH_SHORT).show();

                    }
                }).create().show();
               /*
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                builder.setView(inflater.inflate(R.layout.fragment_starred, null))
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // sign in the user ...
                            }
                        })
                        .setNegativeButton("NEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        }).create().show();*/

                //Toast.makeText(finalConvertView.getContext(), "AGREGA AL PUTO!!!....", Toast.LENGTH_SHORT).show();
            }
        });


        heading.setText(parentRow.getName().trim());
        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
        final ChildRow childRow = (ChildRow) getChild(groupPosition,childPosition);
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.child_row,null);
        }
        ImageView childIcon = (ImageView) convertView.findViewById(R.id.icon);
        childIcon.setImageResource(R.drawable.ic_close_black_36dp);
        final TextView childText = (TextView) convertView.findViewById(R.id.chil_text);
        childText.setText(childRow.getText().trim());
        final TextView childCod = (TextView) convertView.findViewById(R.id.chil_text_cod);
        childCod.setText(childRow.getCod().trim());
        final View finalConvertView = convertView;
        childIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Toast.makeText(finalConvertView.getContext(), childCod.getText(), Toast.LENGTH_SHORT).show();
            }
        });


        childText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(finalConvertView.getContext());
                ListView modeListView = new ListView(finalConvertView.getContext());
                String[] modes = new String[] { "PEDIDO", "COBRO","VISITA"};
                ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(finalConvertView.getContext(),android.R.layout.simple_list_item_1, android.R.id.text1, modes);
                modeListView.setAdapter(modeAdapter);
                builder.setView(modeListView).create().show();


                vrb.setCliente_recibo_factura(childCod.getText().toString());

                //Toast.makeText(finalConvertView.getContext(), "Grupo: "+String.valueOf(grupo)+" Posicion "+String.valueOf(childPosition), Toast.LENGTH_SHORT).show();
                modeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        switch (position){
                            case 0:
                                Intent Mint = new Intent(finalConvertView.getContext(),BandejaPedido.class);
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

                    }
                });

            }
        });
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
