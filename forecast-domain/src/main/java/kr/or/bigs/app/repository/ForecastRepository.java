package kr.or.bigs.app.repository;

import kr.or.bigs.app.domain.Forecast;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ForecastRepository extends JpaRepository<Forecast, Long> {
    Long countByNxAndNyAndBaseAt(int nx, int ny, LocalDateTime fetchDateTime);                                            // 단기 예보 데이터 수 조회
    Page<Forecast> findForecastsByNxAndNyAndBaseAt(int nx, int ny, LocalDateTime fetchDateTime, Pageable pageable);       // 단기 예보 조회
}
