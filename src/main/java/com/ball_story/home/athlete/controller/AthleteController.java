package com.ball_story.home.athlete.controller;

import com.ball_story.common.enums.Team;
import com.ball_story.home.athlete.dto.AthleteResponse;
import com.ball_story.home.athlete.service.AthleteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AthleteController {
    private final AthleteService athleteService;

    @GetMapping("/v1/athletes")
    public ResponseEntity<List<AthleteResponse>> findAllByTeam(@RequestParam Team team) {
        log.info("[AthleteController] findAllByTeam start...");

        return ResponseEntity.ok(
                athleteService.findAll(team)
        );
    }
}
