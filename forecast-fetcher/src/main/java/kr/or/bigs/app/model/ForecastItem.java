package kr.or.bigs.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ForecastItem {
    private String baseDate;            // 발표일자
    private String baseTime;            // 발표시각
    private String category;            // 자료구분문자

    @JsonProperty("fcstDate")
    private String forecastDate;        // 예보일자

    @JsonProperty("fcstTime")
    private String forecastTime;        // 예보시각

    @JsonProperty("fcstValue")
    private String forecastValue;       // 예보 값
    private int nx;                     // 예보지점 X 좌표
    private int ny;                     // 예보지점 Y 좌표
}
