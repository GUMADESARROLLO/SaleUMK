package com.guma.desarrollo.salesumk.Lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marangelo.php on 12/08/2016.
 */
public class ClsVariblesPedido {
    private static List<HashMap<String, String>> mapListaFactura = new ArrayList<HashMap<String, String>>();

    public static List<HashMap<String, String>> getMapListaFactura() {
        return mapListaFactura;
    }

    public static void setMapListaFactura(List<HashMap<String, String>> mapListaFactura) {
        ClsVariblesPedido.mapListaFactura = mapListaFactura;
    }
}
