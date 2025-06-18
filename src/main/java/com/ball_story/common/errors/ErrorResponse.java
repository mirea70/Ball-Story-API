package com.ball_story.common.errors;

import com.ball_story.common.errors.results.ErrorResult;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private final String name;
    private final String message;

    public ErrorResponse(ErrorResult errorResult) {
        this.name = errorResult.name();
        this.message = errorResult.getMessage();
    }

    public ErrorResponse(String name, String message) {
        this.name = name;
        this.message = message;
    }
}
