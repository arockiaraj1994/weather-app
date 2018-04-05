package com.example.learning.weather_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import com.google.maps.GeoApiContext;

@SpringBootApplication
@EnableAutoConfiguration
//@EnableScheduling
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Bean
    public GeoApiContext setGeoApiContext() {
        return new GeoApiContext.Builder().apiKey("AIzaSyDroL_C_HBoUoS7VfCUX7hCOEWFF76cH4I").build();
    }
}
