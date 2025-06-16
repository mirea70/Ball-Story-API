package com.ball_story.common.errors.exceptions;

import com.ball_story.common.errors.results.LimitErrorResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LimitException extends RuntimeException {
    private final LimitErrorResult errorResult;
}
