package com.ball_story.home.athlete.repository;

import com.ball_story.home.athlete.entity.Athlete;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AthleteRepository extends BaseMapper<Athlete> {
    List<Athlete> findByTeamAndName(String team, String name, String type);
}
