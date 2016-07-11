package com.guma.desarrollo.salesumk.Lib;

/**
 * Created by marangelo.php on 13/06/2016.
 */
public class Anime {
    private String nombre;
    private int visitas;

    public Anime(String nombre, int visitas) {
        this.nombre = nombre;
        this.visitas = visitas;
    }

    public String getNombre() {
        return nombre;
    }

    public int getVisitas() {
        return visitas;
    }

}
