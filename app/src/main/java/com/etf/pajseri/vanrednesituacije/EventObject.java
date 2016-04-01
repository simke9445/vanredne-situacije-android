package com.etf.pajseri.vanrednesituacije;

/**
 * Created by Djordje on 4/1/2016.
 */
public class EventObject {
    private String title;
    private String description;
    private String id;
    private String manpower;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString(){ return this.title + " " + this.description;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManpower() {
        return manpower;
    }

    public void setManpower(String manpower) {
        this.manpower = manpower;
    }
}
