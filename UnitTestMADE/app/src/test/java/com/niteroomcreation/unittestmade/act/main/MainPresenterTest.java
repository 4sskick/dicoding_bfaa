package com.niteroomcreation.unittestmade.act.main;

import com.niteroomcreation.unittestmade.models.CuboidModel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainPresenterTest {

    private MainPresenter presenter;
    private CuboidModel cuboidModel;

    private final double dummyLength = 12.0;
    private final double dummyWidth = 7.0;
    private final double dummyHeight = 6.0;
    private final double dummyVolume = 504.0;
    private final double dummyCircumference = 2016.0;
    private final double dummySurfaceArea = 396.0;

    @Before
    public void before() {
        cuboidModel = mock(CuboidModel.class);
        presenter = new MainPresenter(cuboidModel);
    }

    @Test
    public void getCircumference() {
        cuboidModel = new CuboidModel();
        presenter = new MainPresenter(cuboidModel);
        presenter.save(dummyWidth, dummyLength, dummyHeight);

        double volume = presenter.getCircumference();
        assertEquals(dummyCircumference, volume, 0.001);
    }

    @Test
    public void getSurfaceArea() {
        cuboidModel = new CuboidModel();
        presenter = new MainPresenter(cuboidModel);
        presenter.save(dummyWidth, dummyLength, dummyHeight);

        double volume = presenter.getSurfaceArea();
        assertEquals(dummySurfaceArea, volume, 0.001);
    }

    @Test
    public void getVolume() {
        cuboidModel = new CuboidModel();
        presenter = new MainPresenter(cuboidModel);
        presenter.save(dummyWidth, dummyLength, dummyHeight);

        double volume = presenter.getVolume();
        assertEquals(dummyVolume, volume, 0.001);
    }

    //test mock
//    @Test
//    public void testMockVolume() {
//        when(presenter.getVolume()).thenReturn(dummyVolume);
//        double volume = presenter.getVolume();
//        verify(cuboidModel).getVolume();
//        assertEquals(dummyVolume, volume, 0.001);
//    }
}