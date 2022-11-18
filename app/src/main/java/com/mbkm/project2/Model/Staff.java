package com.mbkm.project2.Model;

public class Staff {
    private String name, id, phone, address, email, loginpass;

    public Staff(){

    }

    public Staff(String name, String id, String phone, String address, String email, String loginpass) {
        this.name = name;
        this.id = id;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.loginpass = loginpass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginpass() {
        return loginpass;
    }

    public void setLoginpass(String loginpass) {
        this.loginpass = loginpass;
    }
}
