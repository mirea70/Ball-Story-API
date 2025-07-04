package com.ball_story.common.errors.results;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonErrorResult implements ErrorResult {
    UNKNOWN_EXCEPTION("Unknown Exception");

    private final String message;
}
