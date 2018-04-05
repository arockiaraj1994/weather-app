package com.example.learning.weather_app.task;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.learning.weather_app.model.Weather;
import com.example.learning.weather_app.service.WeatherService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    private static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Autowired
    private WeatherService weatherService;
    
    @Scheduled(fixedRate = 60000)
    public void scheduleTaskWithFixedRate() throws Exception {
        logger.error("Fixed Rate Task :: Execution Time - {}", dateFormat.format(new Date()) );

        ObjectMapper mapper = new ObjectMapper();
        
        Weather weather = weatherService.getWeather("India", "Chennai");
        
        logger.info(mapper.writeValueAsString(weather));
    }
    
}
