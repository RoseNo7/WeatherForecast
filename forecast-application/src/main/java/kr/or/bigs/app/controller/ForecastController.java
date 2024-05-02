package kr.or.bigs.app.controller;

import kr.or.bigs.app.dto.ApiResponse;
import kr.or.bigs.app.dto.ApiResult;
import kr.or.bigs.app.dto.ForecastResponse;
import kr.or.bigs.app.dto.PageResponse;
import kr.or.bigs.app.service.ForecastService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class ForecastController {
                                                                    // 경기도 의정부시 문충로74
    private static final double BIGS_LATITUDE = 37.7382677;         // 위도
    private static final double BIGS_LONGITUDE = 127.1120196;       // 경도

    private final ForecastService forecastService;

    /**
     * 단기 예보 조회
     * @param page      페이지 번호
     * @param count     목록 개수
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/forecasts")
    public ResponseEntity<ApiResponse<Object>> getForecasts(@RequestParam(defaultValue = "1") int page,
                                                            @RequestParam(defaultValue = "10000") int count) {
        LocalDateTime now = LocalDateTime.now();
        PageResponse<ForecastResponse> forecastResponses = forecastService.getForecasts(page, count, BIGS_LATITUDE, BIGS_LONGITUDE, now);

        if (!forecastResponses.getItems().isEmpty()) {
            ApiResponse<Object> apiResponse = new ApiResponse<>();
            apiResponse.setCode(HttpStatus.OK.value());
            apiResponse.setStatus(ApiResult.SUCCESS.status());
            apiResponse.setData(forecastResponses);

            return ResponseEntity.ok(apiResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    /**
     * 단기 예보 저장
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/forecasts")
    public ResponseEntity<ApiResponse<Object>> addForecasts() {
        LocalDateTime now = LocalDateTime.now();
        boolean isSucceed = forecastService.setForecasts(BIGS_LATITUDE, BIGS_LONGITUDE, now);

        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setStatus(ApiResult.SUCCESS.status());

        if (isSucceed) {
            apiResponse.setMessage("단기 예보가 저장되었습니다.");
        } else {
            apiResponse.setMessage("단기 예보가 이미 존재합니다.");
        }

        return ResponseEntity.ok(apiResponse);
    }
}