package com.diggytech.kinoplasticpremios.Profile.EditProfile;

public class EditModelTwo {

    //	"user_Location":[{"Brand":"CITROEN","Location":"CANNES","City":"MACA\u00c9","State":"RJ","LocationID":"99"}],
    private String Brand;
    private String Location;
    private String City;
    private String State;
    private String LocationID;

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getLocationID() {
        return LocationID;
    }

    public void setLocationID(String locationID) {
        LocationID = locationID;
    }



    public String getLocationStatus() {
        return LocationStatus;
    }

    public void setLocationStatus(String locationStatus) {
        LocationStatus = locationStatus;
    }

    private String LocationStatus;

}
