package kr.or.bigs.app.service;

import kr.or.bigs.app.model.ForecastItem;

import java.time.LocalDateTime;
import java.util.List;

public interface ForecastFetcher {
    List<ForecastItem> fetchForecast(int nx, int ny, LocalDateTime fetchDateTime);      // 기상청 단기예보 조회
}
