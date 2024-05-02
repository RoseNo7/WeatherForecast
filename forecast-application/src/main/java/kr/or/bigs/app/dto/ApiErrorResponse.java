package kr.or.bigs.app.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ApiErrorResponse {
    private int code;
    private String status;
    private String message;
}
