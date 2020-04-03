package com.diggytech.kinoplasticpremios.Newsletter;

import com.diggytech.kinoplasticpremios.Login.SignUp.SignUpRequest_ChlidDataModel;

import java.util.ArrayList;

public class ParentNewsdataModel {

    public ArrayList<ChildNewsDataModel> getLocation_brand() {
        return location_brand;
    }

    public void setLocation_brand(ArrayList<ChildNewsDataModel> location_brand) {
        this.location_brand = location_brand;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    private ArrayList<ChildNewsDataModel> location_brand = null;
    private String user_type;
}
