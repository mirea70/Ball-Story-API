package com.ball_story.common.enums;

import com.ball_story.common.errors.exceptions.NotFoundException;
import com.ball_story.common.errors.results.NotFoundErrorResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Team {
    LG("LG"),
    HANWHA("한화"),
    SAMSUNG("삼성"),
    LOTTE("롯데"),
    KT("KT"),
    KIA("KIA"),
    NC("NC"),
    KIWOOM("키움"),
    DOOSAN("두산"),
    SSG("SSG");

    private final String name;

    public static Team valueOfName(String name) {
        for(Team value : Team.values()) {
            if(value.name.equals(name)) {
                return value;
            }
        }

        throw new NotFoundException(NotFoundErrorResult.NOT_FOUND_TEAM_NAME);
    }
}
