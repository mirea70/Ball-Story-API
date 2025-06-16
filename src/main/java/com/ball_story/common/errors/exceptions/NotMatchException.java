package com.ball_story.common.errors.exceptions;

import com.ball_story.common.errors.results.NotMatchErrorResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NotMatchException extends RuntimeException {
    private final NotMatchErrorResult errorResult;
}
