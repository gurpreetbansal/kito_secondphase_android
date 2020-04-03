package com.diggytech.kinoplasticpremios.Login.SignUp;

import java.io.File;
import java.util.ArrayList;

public class SignUpRequest_ParentDataModel {
    private String email;
    private String phone_number;
    private String device_type;
    private String device_token;
    private String password;
    private String username;
    private String user_type;
    private String cpf_number;
    private ArrayList<SignUpRequest_ChlidDataModel> user_Location = null;



    public File getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(File profile_pic) {
        this.profile_pic = profile_pic;
    }

    private File profile_pic;
    private String fcmtoken;

    public ArrayList<SignUpRequest_ChlidDataModel> getUser_Location()
    {
        return user_Location;
    }

    public void setUser_Location(ArrayList<SignUpRequest_ChlidDataModel> user_Location) {
        this.user_Location = user_Location;
    }





    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getCpf_number() {
        return cpf_number;
    }

    public void setCpf_number(String cpf_number) {
        this.cpf_number = cpf_number;
    }

//    public String getProfile_pic() {
//        return profile_pic;
//    }
//
//    public void setProfile_pic(String profile_pic) {
//        this.profile_pic = profile_pic;
//    }

    public String getFcmtoken() {
        return fcmtoken;
    }

    public void setFcmtoken(String fcmtoken) {
        this.fcmtoken = fcmtoken;
    }






//    {
//        "email":"radha12@gmail.com",
//            "phone_number":"+55123-45678-4548",
//            "device_type":"ios",
//            "device_token":"f2e03f6e2a81c4974c883bb4fbd896a83d388da21158047310de94c849072d10",
//            "user_Location":[{"Brand":"CITROEN","Location":"CANNES","City":"MACA\u00c9","State":"RJ","LocationID":"99"}],
//        "password":"123",
//            "username":"radha",
//            "user_type":"2",
//            "cpf_number":"12341234",
//            "profile_pic":null,
//            "fcmtoken":"fVBnwZDztm4:APA91bHgRCrt2xme0HYCXi03IFMzeZq6Gdm5qMDI5QySi0VjfmwzLa1rfxlXaj7kcTfddA_s7bQO7ak2rgaDMW_lCQIpuvz6touCuxAezdUdK6pacSSc0pw49a3sx27CZK6R9ktwD_Cb"
//
//    }


}
