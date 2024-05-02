package kr.or.bigs.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Grid {
    private int x;          // 예보지점 X 좌표
    private int y;          // 예보지점 Y 좌표
}
