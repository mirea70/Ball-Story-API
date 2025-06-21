package com.ball_story.home.stadiums.service;

import com.ball_story.home.stadiums.dto.StadiumCreateRequest;
import com.ball_story.home.stadiums.dto.StadiumResponse;
import com.ball_story.home.stadiums.entity.HomeStadium;
import com.ball_story.home.stadiums.repository.HomeStadiumRepository;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class HomeStadiumService {
    private final HomeStadiumRepository homeStadiumRepository;

    @Transactional
    public Long create(StadiumCreateRequest request) {
        HomeStadium homeStadium = HomeStadium.of(request);
        homeStadiumRepository.insert(homeStadium);

        return homeStadium.getStadiumId();
    }

    @Transactional
    public void updateName(Long stadiumId, String name) throws NotFoundException {
        HomeStadium homeStadium = homeStadiumRepository.findById(stadiumId)
                .orElseThrow(() -> new NotFoundException("not exist stadium"));
        homeStadium.updateName(name);

        homeStadiumRepository.update(homeStadium);
    }

    @Transactional(readOnly = true)
    public StadiumResponse findOne(Long stadiumId) throws NotFoundException {
        HomeStadium homeStadium = homeStadiumRepository.findById(stadiumId)
                .orElseThrow(() -> new NotFoundException("not exist stadium"));
        return StadiumResponse.from(homeStadium);
    }
}
