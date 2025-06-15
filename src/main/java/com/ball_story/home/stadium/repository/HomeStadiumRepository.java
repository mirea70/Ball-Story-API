package com.ball_story.home.stadium.repository;

import com.ball_story.home.stadium.entity.HomeStadium;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface HomeStadiumRepository {
    void save(HomeStadium homeStadium);
    void update(HomeStadium homeStadium);

    Optional<HomeStadium> findById(Long stadiumId);
}
