package kr.or.bigs.app.util;

import kr.or.bigs.app.model.Grid;
import kr.or.bigs.app.util.GridConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GridConverterTest {
    private final GridConverter gridConverter = new GridConverter();

    @Test
    public void testConvertLocationToGrid_Success_Case1() {
        double latitude = 37.5635694444444;
        double longitude = 126.980008333333;

        Grid grid = gridConverter.convertLocationToGrid(latitude, longitude);

        assertEquals(60, grid.getX());
        assertEquals(127, grid.getY());
    }

    @Test
    public void testConvertLocationToGrid_Success_Case2() {
        double latitude = 37.7352888888888;
        double longitude = 127.035841666666;

        Grid grid = gridConverter.convertLocationToGrid(latitude, longitude);

        assertEquals(61, grid.getX());
        assertEquals(130, grid.getY());
    }

    @Test
    public void testConvertLocationToGrid_Success_Case3() {
        double latitude = 38.1472416666666;
        double longitude = 128.6098;

        Grid grid = gridConverter.convertLocationToGrid(latitude, longitude);

        assertEquals(87, grid.getX());
        assertEquals(140, grid.getY());
    }
}