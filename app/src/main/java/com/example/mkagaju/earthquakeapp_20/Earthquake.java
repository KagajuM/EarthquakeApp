package com.example.mkagaju.earthquakeapp_20;

public class Earthquake {
    private String location;
    private String exact_time;
    private String magnitude;
    private String detailsWebpage;

    public Earthquake (String loc, String t, String mag, String det){
        this.location = loc;
        this.exact_time = t;
        this.magnitude = mag;
        this.detailsWebpage = det;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExact_time() {
        return exact_time;
    }

    public void setExact_time(String exact_time) {
        this.exact_time = exact_time;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    public String getDetailsWebpage() {
        return detailsWebpage;
    }

    public void setDetailsWebpage(String detailsWebpage) {
        this.detailsWebpage = detailsWebpage;
    }

    @Override
    public String toString(){
        return "Creating Earthquake Object:"
                + "\nLocation = "+ this.location
                + "\nMagnitude = "+ this.magnitude
                + "\nTime = "+ this.exact_time
                + "\nURL = " + this.detailsWebpage;
    }
}
