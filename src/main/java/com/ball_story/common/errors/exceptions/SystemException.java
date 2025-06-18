package com.ball_story.common.errors.exceptions;

import com.ball_story.common.errors.results.SystemErrorResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SystemException extends RuntimeException {
    private final SystemErrorResult errorResult;
}
