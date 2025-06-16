package com.ball_story.common.errors.exceptions;

import com.ball_story.common.errors.results.FileErrorResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FileRequestException extends RuntimeException {
    private final FileErrorResult errorResult;
}
