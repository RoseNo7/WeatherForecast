package kr.or.bigs.app.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class ForecastTimeManager {
    /**
     * 단기 예보 발표 시간 (1일 8회)
     * 0200, 0500, 0800, 1100, 1400, 1700, 2000, 2300
     *
     * API 제공 시간(~이후)
     * 02:10, 05:10, 08:10, 11:10, 14:10, 17:10, 20:10, 23:10
     */
    private static final LocalTime[] FETCH_TIMES = {
            LocalTime.of(2, 0),
            LocalTime.of(5, 0),
            LocalTime.of(8, 0),
            LocalTime.of(11, 0),
            LocalTime.of(14, 0),
            LocalTime.of(17, 0),
            LocalTime.of(20, 0),
            LocalTime.of(23, 0)
    };

    private static final int FETCH_DELAY_MINUTES = 10;

    /**
     * 단기 예보 조회 시간 조회
     * @param requestDateTime   호출 시간
     * @return                  단기 예보 조회 시간
     */
    public LocalDateTime getFetchTime(LocalDateTime requestDateTime) {
        requestDateTime = requestDateTime.minusMinutes(FETCH_DELAY_MINUTES);

        LocalTime previousFetchTime = null;

        for (int i = FETCH_TIMES.length - 1; i >= 0; i--) {
            LocalTime fetchTime = FETCH_TIMES[i];

            if (requestDateTime.toLocalTime().isAfter(fetchTime)) {
                previousFetchTime = FETCH_TIMES[i];

                break;
            }
        }

        if (previousFetchTime == null) {
            previousFetchTime = FETCH_TIMES[FETCH_TIMES.length - 1];
            requestDateTime = requestDateTime.minusDays(1);
        }

        return LocalDateTime.of(requestDateTime.toLocalDate(), previousFetchTime);
    }
}
