package com.ball_story.common.errors.exceptions;

import com.ball_story.common.errors.results.NotFoundErrorResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NotFoundException extends RuntimeException{
    private final NotFoundErrorResult errorResult;
}
