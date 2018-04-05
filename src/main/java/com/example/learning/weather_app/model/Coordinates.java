package com.example.learning.weather_app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coordinates {
    
    private double latitude;
    
    private double longitude;
    
    public Coordinates() {}

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public double getLatitude() {
        return latitude;
    }

    @JsonProperty(value = "lat")
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @JsonProperty(value = "lon")
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    
}
