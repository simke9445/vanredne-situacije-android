package com.etf.pajseri.vanrednesituacije;

import java.io.Serializable;

public class VolunteerObject implements Serializable {
    private String title;
    private String description;
    private String coord_name;
    private String phone_num;

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

    public String getCoordName() {
        return coord_name;
    }

    public void setCoordName(String coord_name) {
        this.coord_name = coord_name;
    }

    public String getPhoneNum() {
        return phone_num;
    }

    public void setPhoneNum(String phone_num) {
        this.phone_num = phone_num;
    }



}
