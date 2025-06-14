package com.ball_story.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Team {
    LG("LG 트윈스"),
    HANWHA("한화 이글스"),
    SAMSUNG("삼성 라이온즈"),
    LOTTE("롯데 자이언츠"),
    KT("KT 위즈"),
    KIA("기아 타이거즈"),
    NC("NC 다이노스"),
    KIWOOM("키움 히어로즈"),
    DOOSAN("두산 베어스"),
    SSG("SSG 랜더스");

    private final String name;
}
