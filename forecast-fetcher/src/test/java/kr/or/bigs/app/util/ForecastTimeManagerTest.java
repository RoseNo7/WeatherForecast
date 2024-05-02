package kr.or.bigs.app.util;

import kr.or.bigs.app.util.ForecastTimeManager;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ForecastTimeManagerTest {
    private final ForecastTimeManager forecastTimeManager = new ForecastTimeManager();

    @Test
    public void testGetFetchTime_Success_Case1() {
        // 2024-04-29 00:00:00
        LocalDateTime requestDateTime = LocalDateTime.of(2024, Month.APRIL, 29, 0, 0, 0);

        LocalDateTime fetchDateTime = forecastTimeManager.getFetchTime(requestDateTime);

        // 2024-04-28 23:00:00
        LocalDateTime expectedFetchDateTime = LocalDateTime.of(2024, Month.APRIL, 28, 23, 0, 0);
        assertEquals(expectedFetchDateTime, fetchDateTime);
    }

    @Test
    public void testGetFetchTime_Success_Case2() {
        // 2024-04-29 02:00:00
        LocalDateTime requestDateTime = LocalDateTime.of(2024, Month.APRIL, 29, 2, 0, 0);

        LocalDateTime fetchDateTime = forecastTimeManager.getFetchTime(requestDateTime);

        // 2024-04-28 23:00:00
        LocalDateTime expectedFetchDateTime = LocalDateTime.of(2024, Month.APRIL, 28, 23, 0, 0);
        assertEquals(expectedFetchDateTime, fetchDateTime);
    }

    @Test
    public void testGetFetchTime_Success_Case3() {
        // 2024-04-29 02:15:00
        LocalDateTime requestDateTime = LocalDateTime.of(2024, Month.APRIL, 29, 2, 15, 0);

        LocalDateTime fetchDateTime = forecastTimeManager.getFetchTime(requestDateTime);

        // 2024-04-29 02:00:00
        LocalDateTime expectedFetchDateTime = LocalDateTime.of(2024, Month.APRIL, 29, 2, 0, 0);
        assertEquals(expectedFetchDateTime, fetchDateTime);
    }

    @Test
    public void testGetFetchTime_Success_Case4() {
        // 2024-04-29 23:00:00
        LocalDateTime requestDateTime = LocalDateTime.of(2024, Month.APRIL, 29, 23, 0, 0);

        LocalDateTime fetchDateTime = forecastTimeManager.getFetchTime(requestDateTime);

        // 2024-04-28 20:00:00
        LocalDateTime expectedFetchDateTime = LocalDateTime.of(2024, Month.APRIL, 29, 20, 0, 0);
        assertEquals(expectedFetchDateTime, fetchDateTime);
    }
}