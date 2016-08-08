package com.guma.desarrollo.salesumk.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.guma.desarrollo.salesumk.Activity.AgendaActivity;
import com.guma.desarrollo.salesumk.Activity.BandejaPedido;
import com.guma.desarrollo.salesumk.Activity.CrearSaleActivity;
import com.guma.desarrollo.salesumk.Activity.ObservacionActivity;
import com.guma.desarrollo.salesumk.Activity.bandejaCobroActivity;
import com.guma.desarrollo.salesumk.DataBase.DataBaseHelper;
import com.guma.desarrollo.salesumk.Fragments.AgendadosCls;
import com.guma.desarrollo.salesumk.Lib.ChildRow;
import com.guma.desarrollo.salesumk.Lib.Funciones;
import com.guma.desarrollo.salesumk.Lib.ParentRow;
import com.guma.desarrollo.salesumk.R;
import com.guma.desarrollo.salesumk.Lib.Variables;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by marangelo.php on 13/06/2016.
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    Variables vrb;
    Funciones vrf;
    DataBaseHelper myDB;



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

        final ParentRow parentRow = (ParentRow) getGroup(groupPosition);
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.parent_row,null);
        }
        TextView heading = (TextView) convertView.findViewById(R.id.parent_row);




        final View finalConvertView = convertView;
        myDB = new DataBaseHelper(finalConvertView.getContext());

        convertView.findViewById(R.id.add_cls).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res =  myDB.GetData("CLIENTES");
                String Cde="";
                if (res.moveToFirst()) {
                    do {
                        Cde += "["+res.getString(0)+"] "+res.getString(1)+",";
                    } while(res.moveToNext());
                    Cde = Cde.substring(0,Cde.length()-1);
                }

                final CharSequence[] items = Cde.split(",");
                final int[] ItemSelect = {0};
                final AlertDialog.Builder builder = new AlertDialog.Builder(finalConvertView.getContext());
                builder.setSingleChoiceItems(items,-1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        ItemSelect[0] = item;
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (myDB.UpdateVCliente(vrb.getIdPlan(),parentRow.getName(),vrf.prefixCod(items[ItemSelect[0]].toString()))==true){
                            Toast.makeText(finalConvertView.getContext(), "Correcto", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        }else{
                            Toast.makeText(finalConvertView.getContext(), "Incorrecto", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();

            }
        });



        heading.setText(parentRow.getName().trim());
        return convertView;
    }


    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
        final ChildRow childRow = (ChildRow) getChild(groupPosition,childPosition);
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.child_row,null);
        }


        final TextView childText = (TextView) convertView.findViewById(R.id.chil_text);
        childText.setText(childRow.getText().trim());
        final TextView childCod = (TextView) convertView.findViewById(R.id.chil_text_cod);
        childCod.setText(childRow.getCod().trim());
        final View finalConvertView = convertView;
        childText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(finalConvertView.getContext());
                final CharSequence[]items = { "PEDIDO", "COBRO","MOTIVO DE VISITA","ELIMINAR"};
                vrb.setCliente_Name_recibo_factura(childText.getText().toString());
                vrb.setCliente_recibo_factura(childCod.getText().toString());
                builder.setTitle( "CLIENTE: " + childCod.getText().toString());
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals(items[0])){
                            Intent Mint = new Intent(finalConvertView.getContext(),BandejaPedido.class);
                            finalConvertView.getContext().startActivity(Mint);
                        }else{
                            if (items[item].equals(items[1])){
                                Intent Mint2 = new Intent(finalConvertView.getContext(),bandejaCobroActivity.class);
                                finalConvertView.getContext().startActivity(Mint2);
                            }else{
                                if (items[item].equals(items[2])){
                                    Intent Mint3 = new Intent(finalConvertView.getContext(),ObservacionActivity.class);
                                    finalConvertView.getContext().startActivity(Mint3);
                                }else{
                                    builder.setMessage("Desea Eliminar este Registro")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    myDB.DeleteClienteDia(vrb.getIdPlan(),groupPosition,childCod.getText().toString());
                                                    Toast.makeText(finalConvertView.getContext(), "REGISTRO ELIMINADO", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                }
                                            }).create().show();
                                }

                            }
                        }


                    }
                }).create().show();

            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    public void filterData(String query){


        parentRowList.clear();
        parentRowList.addAll(originalList);
        //query = query.toLowerCase();
       // parentRowList.clear();
        /*if (query.isEmpty()){
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

        }*/
        notifyDataSetChanged();
    }

}
