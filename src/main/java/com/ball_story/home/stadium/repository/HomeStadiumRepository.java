package com.ball_story.home.stadium.repository;

import com.ball_story.home.stadium.entity.HomeStadium;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HomeStadiumRepository {
    void save(HomeStadium homeStadium);
}
