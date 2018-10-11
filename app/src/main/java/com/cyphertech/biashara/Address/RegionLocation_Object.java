package com.cyphertech.biashara.Address;

import java.util.List;

public class RegionLocation_Object {
    private String region;
    private List<String> location;

    public RegionLocation_Object(String region, List<String> location){

        this.region = region;
        this.location = location;
    }

    public String getRegion() {
        return region;
    }

    public List <String> getLocation() {
        return location;
    }
}
