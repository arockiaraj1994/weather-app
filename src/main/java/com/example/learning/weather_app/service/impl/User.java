package com.example.learning.weather_app.service.impl;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

	
    public String id;

    @JsonProperty("email_address")
    public String emailAddress;

    @JsonProperty("first_name")
    public String firstName;

    @JsonProperty("last_name")
    public String lastName;

    @JsonProperty("is_active")
    public Boolean isActive = true;
    
    @JsonProperty("roles")
    public Set<Role> roles;

}
