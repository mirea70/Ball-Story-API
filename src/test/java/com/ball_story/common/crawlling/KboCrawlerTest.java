package com.ball_story.common.crawlling;

import com.ball_story.home.athlete.entity.Athlete;
import com.ball_story.home.athlete.enums.AthleteType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class KboCrawlerTest {
    @Autowired
    private KBOCrawler kboCrawler;

    @Test
    void getKboHitterDataTest() throws Exception {
        List<Athlete> athletes = kboCrawler.getKboAthleteData(AthleteType.HITTER);
        athletesTest(athletes);
    }

    @Test
    void getKboPitcherDataTest() throws Exception {
        List<Athlete> athletes = kboCrawler.getKboAthleteData(AthleteType.PITCHER);
        athletesTest(athletes);
    }

    private void athletesTest(List<Athlete> athletes) {
        assertThat(athletes).isNotNull();
        assertThat(athletes).isNotEmpty();

        System.out.println(athletes.get(0));
        System.out.println("athletes.size() = " + athletes.size());
    }
}
