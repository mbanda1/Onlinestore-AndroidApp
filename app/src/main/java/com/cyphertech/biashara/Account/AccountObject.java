package com.cyphertech.biashara.Account;

public class AccountObject {

    private String name;
    private String phone;
    private String address;
    private String region;
    private String station;
    private Boolean isSelected;


    public AccountObject(String name, String phone, String address, String region, String station, Boolean isSelected) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.region = region;
        this.station = station;
        this.isSelected = isSelected;
    }


    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getRegion() {
        return region;
    }

    public String getStation() {
        return station;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
