/*
 *
 *  *
 *  * UBIXI CONFIDENTIAL
 *  * __________________
 *  *
 *  * [2016] - [2026] UBIXI Incorporated
 *  * All Rights Reserved.
 *  *
 *  * NOTICE:  All information contained herein is, and remains the property of UBIXI Incorporated.
 *  *
 *  * The intellectual and technical concepts contained herein are proprietary to UBIXI Incorporated and may be covered by U.S. and Foreign Patents,
 *  * patents in process, and are protected by trade secret or copyright law. Dissemination of this information or reproduction of this material is strictly
 *  * forbidden unless prior written permission is obtained from UBIXI Incorporated.
 *
 */

package com.example.learning.weather_app.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Sivakumar on 05-Oct-16.
 */
public class RolePermission {

    public String id;

    @JsonProperty("resource_uri")
    public String resourceUri;

    public String entity;

    public String action;

    public Role role;
}
