CREATE TABLE IF NOT EXISTS forecasts (
    id int auto_increment,
    base_at DATETIME NOT NULL COMMENT '발표 날짜',
    nx INTEGER NOT NULL COMMENT '예보지점 X 좌표',
    ny INTEGER NOT NULL COMMENT '예보지점 Y 좌표',
    forecast_at DATETIME NOT NULL COMMENT '예보 날짜' ,
    category VARCHAR(10) NOT NULL COMMENT '자료구분문자',
    forecast_value VARCHAR(10) NOT NULL COMMENT '예보 값',
    PRIMARY KEY(id)
) CHARSET=utf8mb4 COMMENT='단기 예보';