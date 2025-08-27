package com.ball_story.home.athlete.entity;

import com.ball_story.common.enums.Team;
import com.ball_story.home.athlete.enums.AthleteType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;

@Getter
public class Athlete {
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
    private Double ops;
    private Double wrcPlus;
    private Double war;

    public static Athlete createHitter(Long code, String name, Team team, AthleteType type, Double hitAvg, Double ops, Double wrcPlus, Double war) {
        Athlete athlete = new Athlete();
        athlete.code = code;
        athlete.name = name;
        athlete.team = team;
        athlete.type = type;
        athlete.hitAvg = hitAvg;
        athlete.ops = ops;
        athlete.wrcPlus = wrcPlus;
        athlete.war = war;

        return athlete;
    }

    public static Athlete createPitcher(Long code, String name, Team team, AthleteType type, Integer win, Integer loose, Integer hold, Integer save, Double era, Double whip, Double war) {
        Athlete athlete = new Athlete();
        athlete.code = code;
        athlete.name = name;
        athlete.team = team;
        athlete.type = type;
        athlete.win = win;
        athlete.loose = loose;
        athlete.hold = hold;
        athlete.save = save;
        athlete.era = era;
        athlete.whip = whip;
        athlete.war = war;

        return athlete;
    }
}
