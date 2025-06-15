package com.ball_story.home.stadium.entity;

import com.ball_story.common.enums.Team;
import com.ball_story.home.stadium.dto.StadiumCreateRequest;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class HomeStadium {
    private Long stadiumId;
    private String name;
    private Team team;
    private Long ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public static HomeStadium of(StadiumCreateRequest request) {
        HomeStadium homeStadium = new HomeStadium();

        homeStadium.name = request.getName();
        homeStadium.team = request.getTeam();
        homeStadium.ownerId = request.getOwnerId();
        homeStadium.createdAt = LocalDateTime.now();
        homeStadium.updatedAt = LocalDateTime.now();
        return homeStadium;
    }

    public void updateName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }
}
