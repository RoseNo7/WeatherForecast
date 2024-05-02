package kr.or.bigs.app.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ApiResponse<T> {
    private int code;
    private String status;
    private String message;
    private T data;
}
