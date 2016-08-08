package com.guma.desarrollo.salesumk.Lib;

import android.location.LocationListener;
import android.location.LocationManager;

/**
 * Created by marangelo.php on 29/07/2016.
 */
public class Funciones {

    public Funciones() {
    }
    public static String DateFormat(String Cadena){

        String PartDate[] = Cadena.split("/");

        Cadena = PartDate[2] + "-" +PartDate[1]+ "-" + PartDate[0];

        return Cadena;
    }
    public static String ClearStrgList(String Cadena){
        Cadena = Cadena.substring(0,Cadena.length()-1);
        Cadena = Cadena.substring(1,Cadena.length());
        Cadena = Cadena.replace(" ","");
        //Cadena = Cadena.replace("=","");
    return Cadena;
    }
    public static String prefixZero(String Str){
        if (Integer.parseInt(Str)<=9){
            Str = "0" +Str;
        }
        return Str;
    }
    public static String prefixCod(String Str){
        int Start = Str.indexOf("[");
        int End = Str.indexOf("]");
        Str = Str.substring(Start+1,End);
        return Str;
    }


}
