package kr.or.bigs.app.handler;

import kr.or.bigs.app.dto.ApiErrorResponse;
import kr.or.bigs.app.dto.ApiResult;
import kr.or.bigs.app.exception.ExternalApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 외부 API 호출 예외 처리
     * @param ex
     * @return
     */
    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<Object> handleExternalApiException(ExternalApiException ex) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        apiErrorResponse.setStatus(ApiResult.ERROR.status());
        apiErrorResponse.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorResponse);
    }
}
