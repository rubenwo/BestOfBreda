package com.a6.projectgroep.bestofbreda.ModelTests;

import com.a6.projectgroep.bestofbreda.Model.MultimediaModel;

import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MultiMediaModelTest {
    private static final MultimediaModel multiMediaModel = mock(MultimediaModel.class);
    private static final List<String> pictureUrls = mock(List.class);
    @Test
    public void createMultiMediaModel_isCorrect(){
        multiMediaModel.setId(1);
        verify(multiMediaModel).setId(1);
        pictureUrls.add("pictureUrl1");
        pictureUrls.add("pictureUrl2");
        multiMediaModel.setPictureUrls(pictureUrls);
        verify(multiMediaModel).setPictureUrls(pictureUrls);
        multiMediaModel.setVideoUrls("videoUrl");
        verify(multiMediaModel).setVideoUrls("videoUrl");
    }
}
