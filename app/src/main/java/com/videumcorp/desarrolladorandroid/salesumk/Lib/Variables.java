package com.videumcorp.desarrolladorandroid.salesumk.Lib;

/**
 * Created by marangelo.php on 21/06/2016.
 */
public class Variables {
    private static String Inv_Articulo ;
    private static String Inv_Cliente ;
    private static String NameVendedor;
    private static String IdVendedor;
    private static String Cliente_recibo_factura;

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
