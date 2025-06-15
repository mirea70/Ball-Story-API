package com.ball_story.common.errors;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final Integer code;
    private final String message;

    public ErrorResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
