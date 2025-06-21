package com.ball_story.home.stadiums.entity;

import com.ball_story.common.enums.Team;
import com.ball_story.home.stadiums.dto.StadiumCreateRequest;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class HomeStadium {
    @TableId(type = IdType.AUTO)
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
