package com.ball_story.common.errors.results;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SystemErrorResult implements ErrorResult {
    UNKNOWN("알 수 없는 에러가 발생하였습니다."),
    NOT_MATCH_FILE_ID_COUNT("저장할 파일의 개수와 ID의 개수가 일치하지 않습니다."),
    ;

    private final String message;
}
