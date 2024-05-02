package kr.or.bigs.app.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class ForecastResponse {
    private Long id;

    private LocalDateTime baseAt;               // 발표날짜
    private int nx;                             // 예보지점 X 좌표
    private int ny;                             // 예보지점 Y 좌표

    private ForecastCategory category;          // 자료구분문자
    private String categoryName;                // 자료구분 이름

    private LocalDateTime forecastAt;           // 예보날짜
    private String forecastValue;               // 예보 값
    private String forecastUnit;                // 예보 값 단위
    private String forecastDescription;         // 예보 값 설명
}
