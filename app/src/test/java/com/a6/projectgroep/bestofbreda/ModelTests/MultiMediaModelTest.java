package com.a6.projectgroep.bestofbreda.ModelTests;

import com.a6.projectgroep.bestofbreda.Model.MultimediaModel;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MultiMediaModelTest {
    @Test
    public void multiMediaModelTest() {
        List<String> urls = Arrays.asList("url_1", "url_2");
        MultimediaModel model = new MultimediaModel(Arrays.asList("url_1", "url_2"), "videoURL");
        assertEquals(model.getPictureUrls(), urls);
        assertEquals("videoURL", model.getVideoUrls());
    }
}
