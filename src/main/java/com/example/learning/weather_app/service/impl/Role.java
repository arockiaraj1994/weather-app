package com.example.learning.weather_app.service.impl;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Role {

    public String id;

    public String name;

    public String description;

    @JsonProperty("permissions")
    public Set<RolePermission> rolePermissions;

    /*
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "created_by")
    public User createdBy;

    @ManyToOne
    @JoinColumn(name = "node_id")
    public Node node;

    @JsonProperty("is_editable")
    @Column(name = "is_editable")
    public boolean isEditable;

    @JsonProperty("is_active")
    @Column(name = "is_active")
    public Boolean isActive = true;*/
}
