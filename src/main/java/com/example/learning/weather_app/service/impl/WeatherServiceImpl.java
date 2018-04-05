package com.example.learning.weather_app.service.impl;

import java.io.IOException;
import java.net.URI;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import com.example.learning.weather_app.model.Coordinates;
import com.example.learning.weather_app.model.Weather;
import com.example.learning.weather_app.service.WeatherService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service(value = "open.weather.map")
public class WeatherServiceImpl implements WeatherService{
    
    private static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

    @Value("${open.weather.map.app.key:2c443f6ac6e07c5f2baec8ee8c5e41ec}")
    private String apiKey;
    
    @Autowired
    private RestTemplate restTemplate;
    
    private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q={city},{country}&units=metric&APPID={key}";
    
    /*public WeatherServiceImpl() {
        this.restTemplate = restTemplateBuilder.build();
    }*/
    
    @Cacheable("weather")
    public Weather getWeather(String country, String city) throws Exception {
        URI url = new UriTemplate(WEATHER_URL).expand(city, country, this.apiKey);
        
        Map<String, Object> data = invoke(url, Map.class);
        List<Map<String, Object>> weatherData = (List<Map<String, Object>>) (data.get("weather") != null ? data.get("weather") : null);
        AbstractMap<String, Object> coordinates = (AbstractMap<String, Object>) data.get("coord");
        
        Weather weather = new Weather();
        weather.city = data.get("name").toString();
        weather.desc = getWeatherDesc(weatherData);
        weather.value = ((Map<String, Object> ) data.get("main")).get("temp").toString();
        weather.country = ((Map<String, Object> ) data.get("sys")).get("country").toString();
        weather.coordinates = mapper.readValue(coordinates.toString().replace("=", ":"), Coordinates.class);
        
        return weather;
    }

    private String getWeatherDesc(List<Map<String, Object>> weatherData) {
        String desc = "";
        
        for (Map<String, Object> data : weatherData) {
            desc += ","+ data.get("main").toString();
        }

        return desc;
    }

    private <T> T invoke(URI url, Class<T> responseType) {
        RequestEntity<?> request = RequestEntity.get(url).accept(MediaType.APPLICATION_JSON).build();
        ResponseEntity<T> exchange = this.restTemplate.exchange(request, responseType);
        return exchange.getBody();
    }


}
