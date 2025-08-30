package com.ball_story.home.athlete.entity;

import com.ball_story.common.enums.Team;
import com.ball_story.home.athlete.enums.AthleteType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class Athlete {
    @TableId(type = IdType.INPUT)
    @Setter
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
    private Long imageId;

    // Todo: 타점 -> OPS로 데이터 바뀌어야함 (셀레니움 이슈)
    public static Athlete createHitter(Long code, String name, Team team, AthleteType type, Double hitAvg, Integer rbi, Integer hitCount, Integer homeRunCount) {
        Athlete athlete = new Athlete();
        athlete.code = code;
        athlete.name = name;
        athlete.team = team;
        athlete.type = type;
        athlete.hitAvg = hitAvg;
        athlete.rbi = rbi;
//        athlete.ops = ops;
        athlete.hitCount = hitCount;
        athlete.homeRunCount = homeRunCount;

        return athlete;
    }

    public static Athlete createPitcher(Long code, String name, Team team, AthleteType type, Integer win, Integer loose, Integer hold, Integer save, Double era, Double whip) {
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

        return athlete;
    }

    public void updateImgId(Long imageId) {
        this.imageId = imageId;
    }

    public void update(Athlete athlete) {
        this.win = athlete.getWin();
        this.loose = athlete.getLoose();
        this.hold = athlete.getHold();
        this.save = athlete.getSave();
        this.era = athlete.getEra();
        this.whip = athlete.getWhip();
        this.hitAvg = athlete.getHitAvg();
        this.rbi = athlete.getRbi();
        this.hitCount = athlete.getHitCount();
        this.homeRunCount = athlete.getHomeRunCount();
    }

//    public void updateOps(Double ops) {
//        this.ops = ops;
//    }
}
