package com.ball_story.home.stadium.dto;

import com.ball_story.common.enums.Team;
import com.ball_story.home.stadium.entity.HomeStadium;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StadiumResponse {
    private final Long stadiumId;
    private final String name;
    private final Team team;
    private final Long ownerId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private StadiumResponse(Long stadiumId, String name, Team team, Long ownerId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.stadiumId = stadiumId;
        this.name = name;
        this.team = team;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static StadiumResponse from(HomeStadium homeStadium) {
        return StadiumResponse.builder()
                .stadiumId(homeStadium.getStadiumId())
                .name(homeStadium.getName())
                .team(homeStadium.getTeam())
                .ownerId(homeStadium.getOwnerId())
                .createdAt(homeStadium.getCreatedAt())
                .updatedAt(homeStadium.getUpdatedAt())
                .build();
    }
}
