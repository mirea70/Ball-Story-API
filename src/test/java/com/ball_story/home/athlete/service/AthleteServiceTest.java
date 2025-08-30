package com.ball_story.home.athlete.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class AthleteServiceTest {
    @Autowired
    private AthleteService athleteService;

    @Test
    void updateAthleteDataTest() throws Exception {
        athleteService.updateAthleteData();
    }

    @Test
    void initAthleteDataTest() throws Exception {
        athleteService.initAthleteData();
    }
}