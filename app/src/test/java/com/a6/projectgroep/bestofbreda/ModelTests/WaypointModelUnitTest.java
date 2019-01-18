package com.a6.projectgroep.bestofbreda.ModelTests;

import com.a6.projectgroep.bestofbreda.Model.MultimediaModel;
import com.a6.projectgroep.bestofbreda.Model.WaypointModel;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class WaypointModelUnitTest {
    private static final WaypointModel waypointModel = mock(WaypointModel.class);
    private static final LatLng location = new LatLng(1, 2);
    private static final MultimediaModel multiMedia = mock(MultimediaModel.class);

    @Test
    public void createWaypointModel_isCorrect() {

        waypointModel.setName("name");
        verify(waypointModel).setName("name");
        waypointModel.setDescriptionNL("beschrijving");
        verify(waypointModel).setDescriptionNL("beschrijving");
        waypointModel.setDescriptionEN("description");
        verify(waypointModel).setDescriptionEN("description");
        waypointModel.setLocation(location);
        verify(waypointModel).setLocation(location);
        waypointModel.setAlreadySeen(true);
        verify(waypointModel).setAlreadySeen(true);
        waypointModel.setFavorite(true);
        verify(waypointModel).setFavorite(true);
        waypointModel.setMultiMediaModel(multiMedia);
        verify(waypointModel).setMultiMediaModel(multiMedia);
    }
}
