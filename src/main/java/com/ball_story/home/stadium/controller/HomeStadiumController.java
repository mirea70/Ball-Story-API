package com.ball_story.home.stadium.controller;

import com.ball_story.home.stadium.dto.StadiumCreateRequest;
import com.ball_story.home.stadium.service.HomeStadiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeStadiumController {
    private final HomeStadiumService homeStadiumService;

    @PostMapping("/v1/stadiums")
    public ResponseEntity<?> create(@RequestBody StadiumCreateRequest request) {
        homeStadiumService.create(request);
        return ResponseEntity.ok().build();
    }
}
