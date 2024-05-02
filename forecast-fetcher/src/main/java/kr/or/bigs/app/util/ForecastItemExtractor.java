package kr.or.bigs.app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.or.bigs.app.model.ForecastItem;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 기상청 단기 예보 정보 구조
 * {
 *   "response": {
 *     "header": {
 *       "resultCode": "00",
 *       "resultMsg": "NORMAL_SERVICE"
 *     },
 *     "body": {
 *       "dataType": "JSON",
 *       "items": {
 *         "item": [
 *           {
 *             "baseDate": "20240427",
 *             "baseTime": "0500",
 *             "category": "TMP",
 *             "fcstDate": "20240427",
 *             "fcstTime": "0600",
 *             "fcstValue": "10",
 *             "nx": 61,
 *             "ny": 130
 *           }
 *         ]
 *       },
 *       "pageNo": 1,
 *       "numOfRows": 100,
 *       "totalCount": 809
 *     }
 *   }
 * }
 */
@Component
public class ForecastItemExtractor {
    private static final String EXTRACT_KEY_FORECAST_ITEM = "item";

    /**
     * 단기 예보 데이터 추출
     * @param value         단기 예보 API 응답 데이터
     * @return              단기 예보 데이터
     * @throws JsonProcessingException
     */
    public List<ForecastItem> extractForecast(@NonNull String value) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        JsonNode node = mapper.readTree(value);
        JsonNode itemNode = node.findValue(EXTRACT_KEY_FORECAST_ITEM);

        return itemNode != null ? Arrays.asList(mapper.treeToValue(itemNode, ForecastItem[].class)) : new ArrayList<>();
    }
}
