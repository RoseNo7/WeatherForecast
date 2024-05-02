package kr.or.bigs.app.service.impl;

import kr.or.bigs.app.domain.Forecast;
import kr.or.bigs.app.dto.ForecastCategory;
import kr.or.bigs.app.dto.ForecastResponse;
import kr.or.bigs.app.dto.PageResponse;
import kr.or.bigs.app.model.ForecastItem;
import kr.or.bigs.app.model.Grid;
import kr.or.bigs.app.repository.ForecastRepository;
import kr.or.bigs.app.service.ForecastFetcher;
import kr.or.bigs.app.service.ForecastService;
import kr.or.bigs.app.util.ForecastTimeManager;
import kr.or.bigs.app.util.GridConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ForecastServiceImpl implements ForecastService {
    private static final DateTimeFormatter FORMAT_PATTERN_BAST_DATE_TIME = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    private final ForecastRepository forecastRepository;

    private final ForecastFetcher forecastFetcher;
    private final GridConverter gridConverter;
    private final ForecastTimeManager forecastTimeManager;
    
    /**
     * 단기 예보 조회
     * @param page              페이지 번호
     * @param count             목록 개수
     * @param latitude          위도
     * @param longitude         경도
     * @param requestDateTime   요청 날짜
     * @return                  단기 예보 정보
     */
    @Override
    @Transactional(readOnly = true)
    public PageResponse<ForecastResponse> getForecasts(int page,
                                                       int count,
                                                       double latitude,
                                                       double longitude,
                                                       LocalDateTime requestDateTime) {
        int pageNumber = page - 1;
        Pageable pageable = PageRequest.of(pageNumber, count);

        Grid grid = gridConverter.convertLocationToGrid(latitude, longitude);
        int nx = grid.getX();
        int ny = grid.getY();

        LocalDateTime fetchDateTime = forecastTimeManager.getFetchTime(requestDateTime);

        Page<Forecast> pagination = forecastRepository.findForecastsByNxAndNyAndBaseAt(nx, ny, fetchDateTime, pageable);

        PageResponse<ForecastResponse> pageResponse = new PageResponse<>();
        pageResponse.setPage(page);
        pageResponse.setCount(pagination.getContent().size());
        pageResponse.setTotalCount((int) pagination.getTotalElements());
        pageResponse.setItems(pagination.getContent().stream()
                .map(item -> {
                    ForecastResponse forecastResponse = new ForecastResponse();
                    forecastResponse.setId(item.getId());
                    forecastResponse.setCategory(item.getCategory());
                    forecastResponse.setCategoryName(item.getCategory().getName());
                    forecastResponse.setForecastValue(item.getForecastValue());
                    forecastResponse.setForecastUnit(item.getCategory().getUnit());
                    forecastResponse.setForecastDescription(item.getCategory().getValueDescription(item.getForecastValue()));
                    forecastResponse.setNx(item.getNx());
                    forecastResponse.setNy(item.getNy());
                    forecastResponse.setBaseAt(item.getBaseAt());
                    forecastResponse.setForecastAt(item.getForecastAt());

                    return forecastResponse;
                })
                .toList()
        );

        return pageResponse;
    }

    /**
     * 단기 예보 저장
     * @param latitude          위도
     * @param longitude         경도
     * @param requestDateTime   요청 날짜
     * @return                  저장 여부
     */
    @Override
    @Transactional
    public boolean setForecasts(double latitude,
                                double longitude,
                                LocalDateTime requestDateTime) {
        Grid grid = gridConverter.convertLocationToGrid(latitude, longitude);
        int nx = grid.getX();
        int ny = grid.getY();

        LocalDateTime fetchDateTime = forecastTimeManager.getFetchTime(requestDateTime);

        long count = forecastRepository.countByNxAndNyAndBaseAt(nx, ny, fetchDateTime);

        if (count > 0) {
            // 발표 시간 데이터가 이미 저장되어 있는 경우, 단기 예보를 가져오지 않음
            return false;
        }

        List<ForecastItem> forecastItems = forecastFetcher.fetchForecast(nx, ny, fetchDateTime);

        List<Forecast> forecasts = forecastItems.stream()
                .map(item -> {
                    String baseAt = item.getBaseDate() + item.getBaseTime();
                    String forecastAt = item.getForecastDate() + item.getForecastTime();

                    Forecast forecast = new Forecast();
                    forecast.setCategory(ForecastCategory.valueOf(item.getCategory()));
                    forecast.setForecastValue(item.getForecastValue());
                    forecast.setNx(item.getNx());
                    forecast.setNy(item.getNy());
                    forecast.setBaseAt(LocalDateTime.parse(baseAt, FORMAT_PATTERN_BAST_DATE_TIME));
                    forecast.setForecastAt(LocalDateTime.parse(forecastAt, FORMAT_PATTERN_BAST_DATE_TIME));

                    return forecast;
                })
                .toList();

        return forecastRepository.saveAll(forecasts).stream()
                .allMatch(entity -> entity.getId() != null);
    }
}
