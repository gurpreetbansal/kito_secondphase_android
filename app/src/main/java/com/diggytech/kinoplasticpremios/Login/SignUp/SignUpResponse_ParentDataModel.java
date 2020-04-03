package com.diggytech.kinoplasticpremios.Login.SignUp;

import java.util.ArrayList;

public class SignUpResponse_ParentDataModel {
    private String email;
    private String phone_number;
    private String device_type;
    private String device_token;
    private String password;
    private String username;
    private String user_type;
    private String cpf_number;
    private String profile_pic;
    private String fcmtoken;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private ArrayList<SignUpResponse_ChildeDataModel> user_Location = null;

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

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getFcmtoken() {
        return fcmtoken;
    }

    public void setFcmtoken(String fcmtoken) {
        this.fcmtoken = fcmtoken;
    }

    public ArrayList<SignUpResponse_ChildeDataModel> getUser_Location() {
        return user_Location;
    }

    public void setUser_Location(ArrayList<SignUpResponse_ChildeDataModel> user_Location) {
        this.user_Location = user_Location;
    }


}
