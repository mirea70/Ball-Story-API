package com.ball_story.home.stadium.service;

import com.ball_story.home.stadium.dto.StadiumCreateRequest;
import com.ball_story.home.stadium.entity.HomeStadium;
import com.ball_story.home.stadium.repository.HomeStadiumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HomeStadiumService {
    private final HomeStadiumRepository homeStadiumRepository;

    @Transactional
    public void create(StadiumCreateRequest request) {
        homeStadiumRepository.save(
                HomeStadium.of(request)
        );
    }
}
