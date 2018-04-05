package com.example.learning.weather_app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.learning.weather_app.model.City;
import com.example.learning.weather_app.model.Coordinates;
import com.example.learning.weather_app.model.Country;
import com.example.learning.weather_app.model.State;
import com.example.learning.weather_app.model.Weather;
import com.example.learning.weather_app.service.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.RoadsApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.LatLng;
import com.google.maps.model.SnappedPoint;
import com.google.maps.model.TravelMode;

@RestController
public class Controller {

    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    @Autowired
    private WeatherService weatherService;
    
    @Autowired
    private GeoApiContext context;

    @RequestMapping(value = "/ok", method = RequestMethod.GET)
    public String Ok() throws ApiException, InterruptedException, IOException {
        return "Ok";
    }
    
    @RequestMapping(value = "/direction/{origin}/{destination}", method = RequestMethod.GET)
    public String direction(@PathVariable("origin") String origin, @PathVariable("destination") String destination) throws ApiException, InterruptedException, IOException {
        /*
        GeocodingResult[] results = GeocodingApi.geocode(context, "Elnet")
                .await();
        
        System.out.println(gson.toJson(results[0].addressComponents));*/

        //GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyDroL_C_HBoUoS7VfCUX7hCOEWFF76cH4I").build();
        
        DirectionsApiRequest apiRequest = DirectionsApi.getDirections(context, origin, destination);
        apiRequest.alternatives(true).mode(TravelMode.DRIVING);
        DirectionsResult directionsResult = apiRequest.await();
        
        DirectionsLeg[] legs = directionsResult.routes[0].legs;
        
        List<String> points = new ArrayList<String>();
        for (DirectionsLeg leg : legs) {
            for (DirectionsStep step : leg.steps) {
                points.add(step.endLocation.lat + ","+ step.endLocation.lng);
            }
        }
        
        return gson.toJson(points);
    }

    
    @RequestMapping(value = "/matrix/{origins}/{destinations}", method = RequestMethod.GET)
    public String matrix(@PathVariable("origins") String origins, @PathVariable("destinations") String destinations) throws ApiException, InterruptedException, IOException {
        /*
        GeocodingResult[] results = GeocodingApi.geocode(context, "Elnet")
                .await();
        
        System.out.println(gson.toJson(results[0].addressComponents));*/

        String[] or = new String[1];
        or[0] = origins;
        
        
        String[] de = new String[1];
        de[0] = destinations;
        
        DistanceMatrixApiRequest distanceMatrixApiRequest = DistanceMatrixApi.getDistanceMatrix(context, or, de);
        DistanceMatrix distanceMatrix = distanceMatrixApiRequest.await();
        
        return gson.toJson(distanceMatrix);
    }
    
    @RequestMapping(value = "/snaproad", method = RequestMethod.GET)
    public String snapRoads() throws ApiException, InterruptedException, IOException {
        /*
        GeocodingResult[] results = GeocodingApi.geocode(context, "Elnet")
                .await();
        
        System.out.println(gson.toJson(results[0].addressComponents));*/

        LatLng[] paths = new LatLng[100];
        
        String pathStr = "12.9878538,80.2509547|12.9877768,80.2557415|13.0065606,80.2574413|13.0081313,80.25890869999999|13.0076323,80.2593622|13.0077493,80.2661828|13.0081654,80.2662414|13.0099419,80.26801549999999|13.0099783,80.26834219999999";

        int i=0;
        for (String path : pathStr.split("|")) {
            LatLng latLng = new LatLng(Double.valueOf(path.split(",")[0]), Double.valueOf(path.split(",")[1]));
            paths[i] = latLng;
            i++;
        }
        PendingResult<SnappedPoint[]> snapToRoads = RoadsApi.snapToRoads(context, paths);
        
        SnappedPoint[] snappedPoints = snapToRoads.await();
        for (SnappedPoint snappedPoint : snappedPoints) {
            System.out.println(snappedPoint.location.lat +","+snappedPoint.location.lng);
        }
        return gson.toJson(snappedPoints);
    }
    
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list() throws JsonProcessingException {

        Country country = getSamplesCountry();

        return mapper.writeValueAsString(country);

    }

    @RequestMapping(value = "/weather/{country}/{city}", method = RequestMethod.GET)
    public String getWeather(@PathVariable("country") String country, @PathVariable("city") String city)
            throws Exception {

        Weather weather = weatherService.getWeather(country, city);

        return mapper.writeValueAsString(weather);

    }

    private Country getSamplesCountry() {

        City city = new City();
        city.setName("Thiruvanmiyur");
        city.setLocation(new Coordinates(12.9830269, 80.2594001));

        List<City> cities = new ArrayList<City>();
        cities.add(city);

        State state = new State();
        state.setName("Chennai");
        state.setCities(cities);

        List<State> states = new ArrayList<State>();
        states.add(state);

        Country country = new Country();
        country.setName("India");
        country.setStates(states);
        ;

        return country;
    }
}
