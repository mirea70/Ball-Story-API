package com.ball_story.common.errors.exceptions;

import com.ball_story.common.errors.results.CrawlingErrorResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CrawlingException extends RuntimeException {
    private final CrawlingErrorResult errorResult;
}
