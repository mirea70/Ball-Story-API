package com.ball_story.common.errors.results;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotFoundErrorResult implements ErrorResult {
    NOT_FOUND_ACCOUNT_DATA("존재하지 않는 회원입니다."),
    NOT_FOUND_FILE_DATA("존재하지 않는 파일입니다."),
    NOT_EXIST_FILE_PATH("존재하지 않는 파일 경로입니다."),
    NOT_FOUND_ENUM_DATA("존재하지 않는 열거형 값입니다."),
    NOT_FOUND_STORY_DATA("존재하지 않는 스토리 값입니다."),
    NOT_FOUND_TEAM_NAME("존재하지 않는 팀 명입니다."),
    NOT_FOUND_ATHLETE("존재하지 않는 선수입니다."),
    ;

    private final String message;
}
