package kr.or.bigs.app.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ApiResult {
    SUCCESS("success"),
    FAILED("failed"),
    ERROR("error");

    private final String status;

    public String status() {
        return this.status;
    }
}
