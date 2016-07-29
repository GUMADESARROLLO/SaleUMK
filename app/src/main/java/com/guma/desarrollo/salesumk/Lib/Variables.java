package com.guma.desarrollo.salesumk.Lib;

import android.widget.ListView;

import com.guma.desarrollo.salesumk.Adapters.SpecialAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marangelo.php on 21/06/2016.
 */
public class Variables {
    private static String Inv_Articulo ;
    private static String Inv_Cliente ;
    private static String NameVendedor;
    private static String IdVendedor;
    private static String Cliente_recibo_factura;
    private static String Cliente_Name_recibo_factura;
    private static String idViewRecibo;

    public static String getIdViewRecibo() {
        return idViewRecibo;
    }

    public static void setIdViewRecibo(String idViewRecibo) {
        Variables.idViewRecibo = idViewRecibo;
    }

    public static String getCliente_Name_recibo_factura() {
        return Cliente_Name_recibo_factura;
    }

    public static void setCliente_Name_recibo_factura(String cliente_Name_recibo_factura) {
        Cliente_Name_recibo_factura = cliente_Name_recibo_factura;
    }

    private static ListView lv_list_facturaCobro;
    private static SpecialAdapter adapter = null;
    private static List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();

    public static List<HashMap<String, String>> getFillMaps() {
        return fillMaps;
    }

    public static void setFillMaps(List<HashMap<String, String>> fillMaps) {
        Variables.fillMaps = fillMaps;
    }

    public static SpecialAdapter getAdapter() {
        return adapter;
    }

    public static void setAdapter(SpecialAdapter adapter) {
        Variables.adapter = adapter;
    }

    public static ListView getLv_list_facturaCobro() {
        return lv_list_facturaCobro;
    }

    public static void setLv_list_facturaCobro(ListView lv_list_facturaCobro) {
        Variables.lv_list_facturaCobro = lv_list_facturaCobro;
    }

    public static String getCliente_recibo_factura() {
        return Cliente_recibo_factura;
    }

    public static void setCliente_recibo_factura(String cliente_recibo_factura) {
        Cliente_recibo_factura = cliente_recibo_factura;
    }

    public static String getNameVendedor() {
        return NameVendedor;
    }

    public static void setNameVendedor(String nameVendedor) {
        NameVendedor = nameVendedor;
    }

    public static String getIdVendedor() {
        return IdVendedor;
    }

    public static void setIdVendedor(String idVendedor) {
        IdVendedor = idVendedor;
    }

    public Variables() {
    }

    public static String getInv_Articulo() {
        return Inv_Articulo;
    }

    public static void setInv_Articulo(String inv_Articulo) {
        Inv_Articulo = inv_Articulo;
    }

    public static String getInv_Cliente() {
        return Inv_Cliente;
    }

    public static void setInv_Cliente(String inv_Cliente) {
        Inv_Cliente = inv_Cliente;
    }

}
