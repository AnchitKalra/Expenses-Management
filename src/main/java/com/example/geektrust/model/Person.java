package com.example.geektrust.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Person {

    private String name;


    public static final int maxMembers = 3;
    public static List<String> nameList = new ArrayList<>();
    private Map<String, Integer> map;

    public Person() {
        map = new HashMap<>();
    }


    public Map<String, Integer> getMap() {
        return map;
    }

    public void setMap(Map<String, Integer> map) {
        this.map = map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
