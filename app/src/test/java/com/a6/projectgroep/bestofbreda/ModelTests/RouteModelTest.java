package com.a6.projectgroep.bestofbreda.ModelTests;

import com.a6.projectgroep.bestofbreda.Model.RouteModel;

import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RouteModelTest {
    private static final RouteModel routeModel = mock(RouteModel.class);
    private static final List<String> route = mock(List.class);

    @Test
    public void createRouteModel_isCorrect() {
        routeModel.setName("routeName");
        verify(routeModel).setName("routeName");
        route.add("waypoint1");
        route.add("waypoint2");
        routeModel.setRoute(route);
        verify(routeModel).setRoute(route);
        routeModel.setDone(true);
        verify(routeModel).setDone(true);
        routeModel.setResourceID("resourceID");
        verify(routeModel).setResourceID("resourceID");
    }
}
