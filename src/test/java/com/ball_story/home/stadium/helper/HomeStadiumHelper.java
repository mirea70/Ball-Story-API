package com.ball_story.home.stadium.helper;

import com.ball_story.common.enums.Team;
import com.ball_story.home.stadium.dto.StadiumCreateRequest;
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
