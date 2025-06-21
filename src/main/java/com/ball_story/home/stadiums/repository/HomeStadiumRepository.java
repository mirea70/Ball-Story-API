package com.ball_story.home.stadiums.repository;

import com.ball_story.home.stadiums.entity.HomeStadium;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface HomeStadiumRepository extends BaseMapper<HomeStadium> {
    void save(HomeStadium homeStadium);
    void update(HomeStadium homeStadium);

    Optional<HomeStadium> findById(Long stadiumId);
}
