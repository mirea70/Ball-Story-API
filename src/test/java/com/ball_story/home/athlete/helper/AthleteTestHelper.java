package com.ball_story.home.athlete.helper;

import com.ball_story.common.enums.Team;
import com.ball_story.home.athlete.entity.Athlete;
import com.ball_story.home.athlete.enums.AthleteType;
import org.springframework.stereotype.Component;

@Component
public class AthleteTestHelper {
    public Athlete getTestHitter() {
        return Athlete.createHitter(
                123L,
                "tName",
                Team.SAMSUNG,
                AthleteType.HITTER,
                0.123,
                13,
                33,
                5
        );
    }
}
