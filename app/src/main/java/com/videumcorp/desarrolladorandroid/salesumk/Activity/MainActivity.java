package com.videumcorp.desarrolladorandroid.salesumk.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.videumcorp.desarrolladorandroid.salesumk.Fragments.AgendadosCls;
import com.videumcorp.desarrolladorandroid.salesumk.Fragments.InboxFragment;
import com.videumcorp.desarrolladorandroid.salesumk.Fragments.StarredFragment;
import com.videumcorp.desarrolladorandroid.salesumk.Lib.Variables;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.R;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    TextView nameUser;
    TextView nameUserName;

    Variables myVar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);


        Intent intent = getIntent();
        String Vendedor = intent.getStringExtra("Vendedor");


        nameUser = (TextView) findViewById(R.id.nav_drw_header_lbl);
        nameUserName = (TextView) findViewById(R.id.nav_drw_header_lbl1);
        nameUser.setText(Vendedor.toUpperCase());
        nameUserName.setText(myVar.getNameVendedor().toUpperCase());

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }

        setupNavigationDrawerContent(navigationView);

        //First fragment
        //setFragment(4);
        setFragment(4);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.item_home:
                                menuItem.setChecked(true);
                                setFragment(4);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_Inv:
                                menuItem.setChecked(true);
                                setFragment(0);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_Cls:
                                menuItem.setChecked(true);
                                setFragment(1);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                           /* case R.id.item_liq6:
                                menuItem.setChecked(true);
                                setFragment(2);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_liq12:
                                menuItem.setChecked(true);
                                setFragment(3);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_calendario:
                                menuItem.setChecked(true);
                                setFragment(5);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;*/
                            case R.id.item_aout:
                                finish();
                                return true;
                        }
                        return true;
                    }
                });
    }

    public void setFragment(int position) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        final Intent intent = getIntent();

        switch (position) {
            case 0:
                InboxFragment inboxFragment = new InboxFragment();
                fragmentTransaction.replace(R.id.fragment, inboxFragment);
                break;
            case 1:
                StarredFragment starredFragment = new StarredFragment();
                Intent IntMtcl = new Intent(MainActivity.this,StarredFragment.class);
                IntMtcl.putExtra("Vendedor", intent.getStringExtra("Vendedor").toUpperCase());
                fragmentTransaction.replace(R.id.fragment, starredFragment);
                break;
            /*case 2:
                Liq6Fragment liq6Fragment = new Liq6Fragment();
                fragmentTransaction.replace(R.id.fragment, liq6Fragment);
                break;
            case 3:
                Liq12Fragment liq12Fragment = new Liq12Fragment();
                fragmentTransaction.replace(R.id.fragment, liq12Fragment);
                break;*/
            case 4:
                AgendadosCls agendadosCls = new AgendadosCls();
                fragmentTransaction.replace(R.id.fragment, agendadosCls);
                break;
            case 5:
                Intent Agenda = new Intent(MainActivity.this,AgendaActivity.class);
                MainActivity.this.startActivity(Agenda);
                break;

        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}

