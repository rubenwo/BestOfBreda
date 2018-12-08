package com.a6.projectgroep.bestofbreda.Model;

import java.util.ArrayList;

public class RouteModel {
    private ArrayList<Integer> route;
    private String name;

    public RouteModel(ArrayList<Integer> route, String name) {
        this.route = route;
        this.name = name;
    }

    public ArrayList<Integer> getRoute() {
        return route;
    }

    public void setRoute(ArrayList<Integer> route) {
        this.route = route;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
