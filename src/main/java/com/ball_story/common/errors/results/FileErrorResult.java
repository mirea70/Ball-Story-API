package com.ball_story.common.errors.results;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileErrorResult implements ErrorResult {
    NOT_MATCHING_NAME_RULE("파일 이름은 특수문자를 제외해야합니다."),
    NOT_SUPPORT_EXT("지원하지 않는 확장자입니다."),
    INVALID_URL_RESOURCE("유효하지 않은 URL 형식입니다."),
    FAIL_UPLOAD_FILE("파일 업로드에 실패하였습니다."),
    FAIL_REMOVE_FILE("파일 삭제에 실패하였습니다."),
    TOO_LARGE_SIZE("허용 가능한 파일 크기를 초과하였습니다. 허용가능 크기: 최대 "),
    ;

    private final String message;
}
