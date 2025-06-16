package com.ball_story.common.errors.exceptions;

import com.ball_story.common.errors.results.DuplicatedErrorResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DuplicatedException extends RuntimeException{
    private final DuplicatedErrorResult errorResult;
}
