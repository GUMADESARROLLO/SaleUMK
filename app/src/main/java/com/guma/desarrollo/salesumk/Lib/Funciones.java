package com.guma.desarrollo.salesumk.Lib;

import android.location.LocationListener;
import android.location.LocationManager;
import android.text.format.DateFormat;

import java.text.NumberFormat;
import java.util.Date;

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
    public static String ClearStrgList(String Cadena,String strCl){
        Cadena = Cadena.substring(0,Cadena.length()-1);
        Cadena = Cadena.substring(1,Cadena.length());
        Cadena = Cadena.replace(strCl,"");
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
    public static String number_format(float str,int lng){
        NumberFormat nf = NumberFormat.getInstance(); // get instance for your locale
        nf.setMaximumFractionDigits(lng);
        return nf.format(str);
    }
    public static CharSequence fDate(){
        Date d = new Date();
        return DateFormat.format("d/MM/yyyy", d.getTime());
    }



}
