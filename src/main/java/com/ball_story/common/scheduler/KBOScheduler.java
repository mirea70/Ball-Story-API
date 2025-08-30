package com.ball_story.common.scheduler;

import com.ball_story.common.errors.exceptions.CrawlingException;
import com.ball_story.common.errors.results.CrawlingErrorResult;
import com.ball_story.home.athlete.service.AthleteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KBOScheduler {
    private final AthleteService athleteService;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void dailyAthleteDataUpdate() {
        log.info("[DailySaveJob] dailyAthleteDataUpdate start...");
        try {
            athleteService.updateAthleteData();
            log.info("[DailySaveJob] dailyAthleteDataUpdate success!");
        } catch (InterruptedException e) {
            throw new CrawlingException(CrawlingErrorResult.CRAWLING_FAILED);
        }
    }
}
