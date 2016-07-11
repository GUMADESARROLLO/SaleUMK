package com.guma.desarrollo.salesumk.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.guma.desarrollo.salesumk.R;
import com.guma.desarrollo.salesumk.Adapters.Country;

import java.util.ArrayList;

public class ObservacionActivity extends AppCompatActivity {
    MyCustomAdapter dataAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observacion);
        setTitle("");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        displayListView();
        checkFabClick();
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();



        if (id == 16908332){
            finish();
        }

        /*switch (titulo){
            case "send":

                break;
            case "Cancelar":
                finish();
                break;

        }*/
        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_observacion,menu);
        return true;
    }

    private void displayListView() {

        ArrayList<Country> countryList = new ArrayList<Country>();

        String[] obser = getResources().getStringArray(R.array.obs);
        for(int i=0;i<obser.length;i++){

            Country country = new Country("",obser[i].toString(),false);
            countryList.add(country);
        }






        dataAdapter = new MyCustomAdapter(this,R.layout.casos_visita, countryList);
        ListView listView = (ListView) findViewById(R.id.listView1);

        listView.setAdapter(dataAdapter);

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Country country = (Country) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),"Clicked on Row: " + country.getName(),Toast.LENGTH_LONG).show();
            }
        });*/

    }

    private class MyCustomAdapter extends ArrayAdapter<Country> {

        private ArrayList<Country> countryList;

        public MyCustomAdapter(Context context, int textViewResourceId,ArrayList<Country> countryList) {
            super(context, textViewResourceId, countryList);
            this.countryList = new ArrayList<Country>();
            this.countryList.addAll(countryList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            //Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.casos_visita, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Country country = (Country) cb.getTag();
                        //Toast.makeText(getApplicationContext(),"Clicked on Checkbox: " + cb.getText() +" is " + cb.isChecked(),Toast.LENGTH_LONG).show();
                        country.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Country country = countryList.get(position);
            holder.code.setText(" (" +  country.getCode() + ")");
            holder.name.setText(country.getName());
            holder.name.setChecked(country.isSelected());
            holder.name.setTag(country);

            return convertView;

        }

    }

    private void checkFabClick() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                ArrayList<Country> countryList = dataAdapter.countryList;
                for(int i=0;i<countryList.size();i++){
                    Country country = countryList.get(i);
                    if(country.isSelected()){
                        responseText.append("\n" + country.getName());
                    }
                }

                Toast.makeText(getApplicationContext(),responseText, Toast.LENGTH_LONG).show();
            }
        });

    }
}
