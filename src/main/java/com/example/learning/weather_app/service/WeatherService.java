package com.example.learning.weather_app.service;

import org.springframework.stereotype.Service;

import com.example.learning.weather_app.model.Weather;

@Service
public interface WeatherService {

    Weather getWeather(String country, String city) throws Exception;
    
}
