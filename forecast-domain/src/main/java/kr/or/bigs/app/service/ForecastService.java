package kr.or.bigs.app.service;

import kr.or.bigs.app.dto.ForecastResponse;
import kr.or.bigs.app.dto.PageResponse;

import java.time.LocalDateTime;

public interface ForecastService {
    PageResponse<ForecastResponse> getForecasts(int page, int count, double latitude, double longitude, LocalDateTime dateTime);    // 단기 예보 조회
    boolean setForecasts(double latitude, double longitude, LocalDateTime dateTime);                                                // 단기 예보 저장
}
