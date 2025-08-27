package com.ball_story.home.athlete.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AthleteType {
    PITCHER("투수"),
    HITTER("타자");

    private final String label;
}
