package com.ball_story.common.errors;

import com.ball_story.common.errors.exceptions.FileRequestException;
import com.ball_story.common.errors.exceptions.SystemException;
import com.ball_story.common.errors.results.SystemErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final Exception e) {
        log.error(SystemErrorResult.UNKNOWN.name() + "occur.\n", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(SystemErrorResult.UNKNOWN.name(), SystemErrorResult.UNKNOWN.getMessage()));
    }

    @ExceptionHandler(SystemException.class)
    public ResponseEntity<ErrorResponse> handleSystemException(final SystemException e) {
        log.error(e.getErrorResult().name() + "occur.\n", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(e.getErrorResult()));
    }

    @ExceptionHandler(FileRequestException.class)
    public ResponseEntity<ErrorResponse> handleFileRequestException(final FileRequestException e) {
        log.warn(e.getErrorResult().name() + "occur.\nfile name : {}", e.getFileName(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getErrorResult()));
    }
}
