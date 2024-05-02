package kr.or.bigs.app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.or.bigs.app.model.ForecastItem;
import kr.or.bigs.app.util.ForecastItemExtractor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ForecastItemExtractorTest {
    private final ForecastItemExtractor forecastItemExtractor = new ForecastItemExtractor();

    @Test
    public void testExtractForecast_Success_Case1() throws JsonProcessingException {
        String value = "{\"items\":{\"item\":[{\"baseDate\":\"20240427\",\"baseTime\":\"0500\",\"category\":\"TMP\",\"fcstDate\":\"20240427\",\"fcstTime\":\"0600\",\"fcstValue\":\"10\",\"nx\":61,\"ny\":130}]}}";

        List<ForecastItem> forecastItems = forecastItemExtractor.extractForecast(value);

        assertEquals(1, forecastItems.size());

        ForecastItem forecastItem = forecastItems.get(0);
        assertEquals("20240427", forecastItem.getBaseDate());
        assertEquals("0500", forecastItem.getBaseTime());
        assertEquals("TMP", forecastItem.getCategory());
        assertEquals("20240427", forecastItem.getForecastDate());
        assertEquals("0600", forecastItem.getForecastTime());
        assertEquals("10", forecastItem.getForecastValue());
        assertEquals(61, forecastItem.getNx());
        assertEquals(130, forecastItem.getNy());
    }

    @Test
    public void testExtractForecast_Failure_Case1() throws JsonProcessingException {
        String value = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}";

        List<ForecastItem> forecastItems = forecastItemExtractor.extractForecast(value);

        assertTrue(forecastItems.isEmpty());
    }

    @Test
    public void testExtractForecast_Failure_Case2() throws JsonProcessingException {
        String value = null;

        assertThrows(NullPointerException.class, () -> {
            forecastItemExtractor.extractForecast(value);
        });
    }
}