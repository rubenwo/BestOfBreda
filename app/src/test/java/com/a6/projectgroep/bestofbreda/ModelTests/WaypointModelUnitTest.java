package com.a6.projectgroep.bestofbreda.ModelTests;

import com.a6.projectgroep.bestofbreda.Model.MultimediaModel;
import com.a6.projectgroep.bestofbreda.Model.WaypointModel;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WaypointModelUnitTest {
    private static final MultimediaModel multimediaModel = new MultimediaModel(
            Arrays.asList("url1", "url2"), "urlstring");

    @Test
    public void createWaypointModel_isCorrect() {
        WaypointModel model = new WaypointModel(
                "name",
                "beschrijving",
                "description",
                new LatLng(1, 2),
                true,
                false, multimediaModel
        );
        assertEquals("name", model.getName());
        assertEquals("beschrijving", model.getDescriptionNL());
        assertEquals("description", model.getDescriptionEN());
        assertTrue(model.isAlreadySeen());
        assertFalse(model.isFavorite());
        assertEquals(multimediaModel, model.getMultiMediaModel());
    }
}
