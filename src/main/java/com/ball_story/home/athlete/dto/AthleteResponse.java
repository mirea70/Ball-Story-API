package com.ball_story.home.athlete.dto;

import com.ball_story.common.enums.Team;
import com.ball_story.common.files.service.AttachFileService;
import com.ball_story.home.athlete.enums.AthleteType;
import lombok.Getter;

@Getter
public class AthleteResponse {
    private Long code;
    private String name;
    private Team team;
    private AthleteType type;
    private Integer win;
    private Integer loose;
    private Integer hold;
    private Integer save;
    private Double era;
    private Double whip;
    private Double hitAvg;
    private Integer rbi;
    private Integer hitCount;
    private Integer homeRunCount;
    private String imageUrl;

    public void setImageUrl(String imageUrl) {
        this.imageUrl = AttachFileService.generateFileUrl(imageUrl);
    }
}
