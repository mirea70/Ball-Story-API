package com.ball_story.home.stadium.controller;

import com.ball_story.home.stadium.dto.StadiumCreateRequest;
import com.ball_story.home.stadium.dto.StadiumResponse;
import com.ball_story.home.stadium.service.HomeStadiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HomeStadiumController {
    private final HomeStadiumService homeStadiumService;

    @PostMapping("/v1/stadiums")
    public ResponseEntity<Long> create(@RequestBody StadiumCreateRequest request) {
        return ResponseEntity.ok(
                homeStadiumService.create(request)
        );
    }

    @PatchMapping("/v1/stadiums/{stadiumId}")
    public ResponseEntity<?> updateName(@PathVariable Long stadiumId,
                                        @RequestParam String name) throws Exception {
        homeStadiumService.updateName(stadiumId, name);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/v1/stadiums/{stadiumId}")
    public ResponseEntity<StadiumResponse> findOne(@PathVariable Long stadiumId) throws Exception {
        return ResponseEntity.ok(
                homeStadiumService.findOne(stadiumId)
        );
    }
}
