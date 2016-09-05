package com.guma.desarrollo.salesumk.Lib;


public class ClssURL {
    private static String SERVER = "192.168.1.78:8448";
    //private static String SERVER = "165.98.75.219:8448";

    private static String URL_INVE  = "http://"+ SERVER +"/APIREST/Inventario.php";
    private static String URL_login = "http://"+ SERVER +"/APIREST/Login.php";
    private static String URL_liq6  = "http://"+ SERVER +"/APIREST/InventarioLiq6.php";
    private static String URL_liq12 = "http://"+ SERVER +"/APIREST/InventarioLiq12.php";
    private static String URL_mtlc = "http://"+ SERVER +"/APIREST/MTCL.php";
    private static String URL_ExistenciaLotes = "http://"+ SERVER +"/APIREST/ExistenciaLotes.php";
    private static String URL_Factura = "http://"+ SERVER +"/APIREST/FACTURAS.php";
    private static String URL_doom = "http://"+ SERVER +"/APIREST/Doom.php";
    private static String URL_Pedido= "http://"+ SERVER +"/APIREST/Pedido.php";
    private static String URL_Asyn= "http://"+ SERVER +"/APIREST/Asyn.php";
    private static String URL_AsynRecibo= "http://"+ SERVER +"/APIREST/allRecibo.php";
    public ClssURL() {
    }
    public static String getURL_Asyn() {
        return URL_Asyn;
    }

    public static String getURL_AsynRecibo() {
        return URL_AsynRecibo;
    }


    public static String getURL_Pedido() {
        return URL_Pedido;
    }
    public static String getURL_doom() {
        return URL_doom;
    }
    public static String getUrlInve() {
        return URL_INVE;
    }
    public static String getURL_login() {
        return URL_login;
    }
    public static String getURL_liq6() {
        return URL_liq6;
    }
    public static String getURL_liq12() {
        return URL_liq12;
    }
    public static String getURL_mtlc() {
        return URL_mtlc;
    }
    public static String getURL_ExistenciaLotes() {
        return URL_ExistenciaLotes;
    }
    public static String getURL_Factura() {
        return URL_Factura;
    }


}
