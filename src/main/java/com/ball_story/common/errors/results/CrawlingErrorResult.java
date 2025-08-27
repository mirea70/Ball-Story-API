package com.ball_story.common.errors.results;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CrawlingErrorResult implements ErrorResult {
    CRAWLING_FAILED("크롤링에 실패하였습니다.");

    private final String message;
}
