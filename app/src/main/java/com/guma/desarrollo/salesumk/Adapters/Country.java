package com.guma.desarrollo.salesumk.Adapters;

/**
 * Created by marangelo.php on 30/06/2016.
 */
public class Country {
    String code = null;
    String name = null;
    boolean selected = false;

    public Country(String code, String name, boolean selected) {
        this.code = code;
        this.name = name;
        this.selected = selected;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
