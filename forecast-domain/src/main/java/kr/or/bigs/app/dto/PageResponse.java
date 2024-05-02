package kr.or.bigs.app.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class PageResponse<T> {
    private int page;
    private int count;
    private int totalCount;

    private List<T> items;
}
