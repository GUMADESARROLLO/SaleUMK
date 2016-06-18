package com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.Lib;

/**
 * Created by marangelo.php on 13/06/2016.
 */
public class ChildRow {
    public int getIcon() {
        return icon;
    }
    public ChildRow(int icon, String text) {
        this.icon = icon;
        this.text = text;
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

    private int icon;
    private String text;


}
