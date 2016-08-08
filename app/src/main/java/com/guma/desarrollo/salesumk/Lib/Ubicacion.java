package com.guma.desarrollo.salesumk.Lib;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Ubicacion implements LocationListener {
    private Context ctx;
    LocationManager locationManager;
    String proveedor;
    private boolean networkOn;

    public Ubicacion(Context ctx) {
        this.ctx = ctx;
        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        proveedor = LocationManager.NETWORK_PROVIDER;
        networkOn = locationManager.isProviderEnabled(proveedor);
        locationManager.requestLocationUpdates(proveedor,1000,1,this);
        getLocation();
    }

    private void getLocation(){

        if (networkOn){
            Location lc = locationManager.getLastKnownLocation(proveedor);
            if (lc != null){
                StringBuilder builder = new StringBuilder();
                builder.append("Altitud :").append(lc.getAltitude()).append("Longitud :").append(lc.getLongitude());
                Toast.makeText(ctx, builder.toString(), Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        getLocation();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        //ESTA ACTIVADO PROVEEDOR
    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
