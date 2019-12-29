package com.example.campussystem.CompanyPanel;

public class CompanyClass {
    String id;
    String Address;
    String OwnerName;

    public CompanyClass() {

    }
    public CompanyClass(String id, String address, String ownerName) {
        this.id = id;
        Address = address;
        OwnerName = ownerName;
    }
    public String getId() {
        return id;
    }
    public String getAddress() {
        return Address;
    }
    public String getOwnerName() {
        return OwnerName;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setAddress(String address) {
        Address = address;
    }
    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }

}

