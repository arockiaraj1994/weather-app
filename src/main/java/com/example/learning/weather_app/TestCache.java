package com.example.learning.weather_app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;

public class TestCache {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        
        
        
        
        
        /*CacheLoader<String, String> loader;
        loader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) {
                System.err.println("Load");
                return key.toUpperCase();
            }
        };
        
        LoadingCache<String, String> cache;
        cache = CacheBuilder.newBuilder()
          .refreshAfterWrite(10,TimeUnit.SECONDS)
          .build(loader);*/
        
        
        //cache.put("1", "tes");
        //System.out.println(loader.cache("1"));
        
        
        LoadingCache<String, String> employeeCache = CacheBuilder.newBuilder().refreshAfterWrite(10, TimeUnit.SECONDS)
                .maximumSize(100)
                .build(new CacheLoader<String, String>() {

                    @Override
                    public String load(String empId) throws Exception {
                        // make the expensive call
                        System.err.println("Getting");
                        return getNames(empId);
                    }

                    @Override
                    public Map<String, String> loadAll(Iterable<? extends String> keys) throws Exception {
                        // TODO Auto-generated method stub
                        System.err.println("Getting All");
                        return super.loadAll(keys);
                    }
                    

                });
        
        System.out.println(employeeCache.get("1"));
        employeeCache.put("23", "Name");
        Thread.sleep(1000000);
        
        /*// create a cache for employees based on their employee id
        

        try {
            employeeCache.put("0", "Test");
            System.out.println(employeeCache.get("0"));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
    }

    private static String getNames(String empId) {
        System.err.println("get from db");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (empId.equals("1")) {
            return "Name 1";
        } else if (empId.equals("2")) {
            return "Name 2";
        } else if (empId.equals("3")) {
            return "Name 3";
        } else if (empId.equals("4")) {
            return "Name 4";
        } else {
            return "";
        }
    }
}
