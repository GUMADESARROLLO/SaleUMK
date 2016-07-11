package com.guma.desarrollo.salesumk.Lib;

/**
 * Created by marangelo.php on 13/06/2016.
 */
public class ChildRow {
    private int icon;
    private String text;
    private String Cod;

    public ChildRow(int icon, String text, String cod) {
        this.icon = icon;
        this.text = text;
        Cod = cod;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCod() {
        return Cod;
    }

    public void setCod(String cod) {
        Cod = cod;
    }
}
