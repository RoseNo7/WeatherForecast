package kr.or.bigs.app.domain;

import jakarta.persistence.*;
import kr.or.bigs.app.dto.ForecastCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "forecasts")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Forecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ForecastCategory category;
    private String forecastValue;
    private int nx;
    private int ny;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime baseAt;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime forecastAt;
}
