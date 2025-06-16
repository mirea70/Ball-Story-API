package com.ball_story.home.stadiums.dto;

import com.ball_story.common.enums.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StadiumCreateRequest {
    private String name;
    private Team team;
    private Long ownerId;
}
