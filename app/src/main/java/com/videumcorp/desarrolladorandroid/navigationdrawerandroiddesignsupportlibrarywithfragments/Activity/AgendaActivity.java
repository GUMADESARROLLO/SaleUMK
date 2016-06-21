package com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.DataBase.DataBaseHelper;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Adapters.MyExpandableListAdapter;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Lib.ChildRow;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Lib.ClssURL;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Lib.ParentRow;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.R;

import org.json.JSONArray;

import java.util.ArrayList;

public class AgendaActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,SearchView.OnCloseListener {


    DataBaseHelper myDB;
    private SearchManager searchManager;
    private android.widget.SearchView searchView;
    private MyExpandableListAdapter listAdapter;
    private ExpandableListView myList;
    private ArrayList<ParentRow> parentList = new ArrayList<ParentRow>();
    private ArrayList<ParentRow> showTheseParentList = new ArrayList<ParentRow>();
    private MenuItem searchItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);


        this.getSupportActionBar().setTitle("");


        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        myDB = new DataBaseHelper(this);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearList();
                Loading();
                CallInve();
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });*/
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        parentList = new ArrayList<ParentRow>();
        showTheseParentList = new ArrayList<ParentRow>();



        displayList();
       

    }

    private void expandAll(){
        int count = listAdapter.getGroupCount();
        for (int i=0;i<count;i++){
            myList.expandGroup(i);
        }
    }
    private void ClearList(){
        parentList.clear();
        myList = (ExpandableListView) findViewById(R.id.expandableListView_search);
        listAdapter = new MyExpandableListAdapter(AgendaActivity.this,parentList);
        myList.setAdapter(listAdapter);

    }
    private void Loading(){
        ArrayList<ChildRow> childRows=new ArrayList<ChildRow>();
        ParentRow parentRow = null;
        childRows.clear();
        parentRow = new ParentRow("Actualizando lista...",childRows);
        parentList.add(parentRow);


        myList = (ExpandableListView) findViewById(R.id.expandableListView_search);
        listAdapter = new MyExpandableListAdapter(AgendaActivity.this,parentList);
        myList.setAdapter(listAdapter);
    }
    private void displayList(){
        loadData();
        myList = (ExpandableListView) findViewById(R.id.expandableListView_search);
        listAdapter = new MyExpandableListAdapter(AgendaActivity.this,parentList);
        myList.setAdapter(listAdapter);

    }
    private void loadData(){
        /*Cursor res =  myDB.GetData("Articulo");
        if (res.moveToFirst()) {
            do {
                CrearList(res.getString(0), res.getString(1),res.getString(2),res.getString(3));

            } while(res.moveToNext());
        }*/
        CrearList("LUNEsS", "CLIENTE 1");
        CrearList("MARTES", "CLIENTE 1");
        CrearList("MIERCOLES", "CLIENTE 1");




    }
    private void CrearList(String Dia, String Cliente){
        ArrayList<ChildRow> childRows=new ArrayList<ChildRow>();

        ParentRow parentRow = null;
        childRows.clear();


        parentRow = new ParentRow(Dia,childRows);
        childRows.add(new ChildRow(R.mipmap.ic_launcher,Cliente));

        parentList.add(parentRow);
    }
    public void Error404(String TipoError){
        AlertDialog.Builder builder = new AlertDialog.Builder(AgendaActivity.this);
        builder.setMessage(TipoError)
                .setNegativeButton("OK",null)
                .create()
                .show();
    }
    public void CallInve(){
        AsyncHttpClient Cnx = new AsyncHttpClient();
        RequestParams paramentros = new RequestParams();
        //paramentros.put("U",User);
        //paramentros.put("P",Passw);


                Cnx.post(ClssURL.getUrlInve(), paramentros, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {

                        boolean Inserted = false;
                        if (statusCode == 200) {
                            ArrayList<String> MeEncontro = obtDatosUS(new String(responseBody));
                            SQLiteDatabase db = myDB.getWritableDatabase();

                            db.execSQL("DELETE FROM Articulo");


                            for (int n = 0; n < MeEncontro.size(); n++) {
                                String[] items = MeEncontro.get(n).toString().split(",");
                                Inserted = myDB.insertDataArticulo(
                                        items[0].toString(),
                                        items[1].toString(),
                                        items[2].toString(),
                                        items[3].toString(),
                                        items[4].toString(),
                                        items[5].toString(),
                                        items[6].toString()
                                );
                            }


                            if (Inserted == true) {
                                Toast.makeText(getApplicationContext(), "Ya Casi terminamos.....", Toast.LENGTH_SHORT).show();
                                //CallView(items[0].toString());
                                //progressDialog.dismiss();
                            } else {
                                Error404("Error de Actualizacion de datos");
                            }


                        } else {
                            Error404("Sin Cobertura de datos.");
                        }


                    }

                    @Override
                    public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {
                        Error404("Sin Cobertura de datos.");
                    }


                });
    }
    public ArrayList<String> obtDatosUS(String response){
        ArrayList<String> listado = new ArrayList<String>();
        try{
            JSONArray jsonArray = new JSONArray(response);
            String texto;

            for (int i=0; i<jsonArray.length(); i++){
                texto = jsonArray.getJSONObject(i).getString("ARTICULO")+ "," +
                        jsonArray.getJSONObject(i).getString("DESCRIPCION")+ "," +
                        jsonArray.getJSONObject(i).getString("TOTAL_EXISTENCIA")+ "," +
                        jsonArray.getJSONObject(i).getString("PRECIO")+ "," +
                        jsonArray.getJSONObject(i).getString("PUNTOS")+ "," +
                        jsonArray.getJSONObject(i).getString("BODEGAS")+ "," +
                        jsonArray.getJSONObject(i).getString("REGLAS");
                listado.add(texto);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return listado;

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        searchItem = menu.findItem(R.id.action_search);
        searchView = (android.widget.SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.requestFocus();
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == 16908332){
            finish();
        }
        return super.onOptionsItemSelected(item);
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
