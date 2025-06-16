package com.ball_story.home.stadiums.helper;

import com.ball_story.common.enums.Team;
import com.ball_story.home.stadiums.dto.StadiumCreateRequest;
import org.springframework.stereotype.Component;

@Component
public class HomeStadiumHelper {
    public StadiumCreateRequest getTestCreateRequest() {
        return new StadiumCreateRequest(
                "홈구장명",
                Team.SAMSUNG,
                1L
        );
    }
}
