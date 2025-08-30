package com.ball_story.common.scheduler;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KBOSchedulerTest {
    @Autowired
    private KBOScheduler scheduler;

    @Test
    void dailyAthleteDataUpdateTest() {
        scheduler.dailyAthleteDataUpdate();
    }
}