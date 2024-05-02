package kr.or.bigs.app.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.or.bigs.app.exception.ExternalApiException;
import kr.or.bigs.app.model.ForecastItem;
import kr.or.bigs.app.service.ForecastFetcher;
import kr.or.bigs.app.util.ForecastItemExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ForecastFetcherImpl implements ForecastFetcher {
    private static final DateTimeFormatter FORMAT_PATTERN_BASE_DATE = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter FORMAT_PATTERN_BASE_TIME = DateTimeFormatter.ofPattern("HHmm");

    private final RestTemplate restTemplate;
    private final ForecastItemExtractor forecastItemExtractor;

    @Value("${api.forecast.endpoint}")
    private String endpoint;

    @Value("${api.forecast.key}")
    private String key;

    /**
     * 기상청 단기예보 조회
     * @param nx                예보지점 X 좌표
     * @param ny                예보지점 Y 좌표
     * @param fetchDateTime     호출 날짜 (발표 날짜)
     * @return                  단기 예보 정보
     */
    @Override
    public List<ForecastItem> fetchForecast(int nx, int ny, LocalDateTime fetchDateTime) {
        URI uri = buildUri(nx, ny, fetchDateTime);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        HttpStatusCode statusCode = responseEntity.getStatusCode();
        MediaType contentType = responseEntity.getHeaders().getContentType();

        if (!statusCode.is2xxSuccessful()) {
            throw new ExternalApiException("기상청 단기 예보 API 호출 실패");
        }

        if (contentType != null && contentType.toString().contains("text/xml")) {
            throw new ExternalApiException("기상청 단기 예보 API 응답 실패");
        }

        List<ForecastItem> forecastItems = new ArrayList<>();
        String body = responseEntity.getBody();

        if (body != null) {
            try {
                forecastItems = forecastItemExtractor.extractForecast(body);
            } catch (JsonProcessingException e) {
                throw new ExternalApiException("JSON 데이터 추출 실패");
            }
        }

        return forecastItems;
    }

    /**
     * 기상청 단기 예비 URL 생성
     * @param nx                예보지점 X 좌표
     * @param ny                예보지점 Y 좌표
     * @param fetchDateTime     호출 날짜 (발표 날짜)
     * @return                  기상청 단기 예비 URL
     */
    private URI buildUri(int nx, int ny, LocalDateTime fetchDateTime) {
        String fetchDate = fetchDateTime.format(FORMAT_PATTERN_BASE_DATE);
        String fetchTime = fetchDateTime.format(FORMAT_PATTERN_BASE_TIME);

        // OpenAPI의 ServiceKey에 '/', '+'가 포함되어 있어 파라미터를 먼저 인코딩해서 사용한다.
        String encodeKey = URLEncoder.encode(key, StandardCharsets.UTF_8);

        return UriComponentsBuilder.fromUriString(endpoint)
                .queryParam("serviceKey", encodeKey)
                .queryParam("pageNo", "1")
                .queryParam("numOfRows", 10000)
                .queryParam("dataType", "JSON")
                .queryParam("base_date", fetchDate)
                .queryParam("base_time", fetchTime)
                .queryParam("nx", nx)
                .queryParam("ny", ny)
                .build(true)
                .toUri();
    }
}
