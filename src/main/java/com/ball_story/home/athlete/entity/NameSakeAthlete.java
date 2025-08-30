package com.ball_story.home.athlete.entity;

import com.ball_story.common.enums.Team;
import com.ball_story.home.athlete.enums.AthleteType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NameSakeAthlete {
    @TableId(type = IdType.INPUT)
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
//    private Double ops;
    private Integer rbi; // 타점
    private Integer hitCount;
    @TableField(value = "homeRun_count")
    private Integer homeRunCount;

    public static NameSakeAthlete from(Athlete athlete) {
        NameSakeAthlete nameSakeAthlete = new NameSakeAthlete();
        nameSakeAthlete.code = athlete.getCode();
        nameSakeAthlete.name = athlete.getName();
        nameSakeAthlete.team = athlete.getTeam();
        nameSakeAthlete.type = athlete.getType();
        nameSakeAthlete.win = athlete.getWin();
        nameSakeAthlete.loose = athlete.getLoose();
        nameSakeAthlete.hold = athlete.getHold();
        nameSakeAthlete.save = athlete.getSave();
        nameSakeAthlete.era = athlete.getEra();
        nameSakeAthlete.whip = athlete.getWhip();
        nameSakeAthlete.hitAvg = athlete.getHitAvg();
        nameSakeAthlete.rbi = athlete.getRbi();
        nameSakeAthlete.hitCount = athlete.getHitCount();
        nameSakeAthlete.homeRunCount = athlete.getHomeRunCount();

        return nameSakeAthlete;
    }
}
