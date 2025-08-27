package com.ball_story.common.tving;

import com.ball_story.home.athlete.entity.Athlete;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TVingAthleteDataProviderTest {
    @Autowired
    private TVingAthleteDataProvider tVingAthleteDataProvider;

    @Test
    void getAthleteHitterDataTest() {
        List<Athlete> athletes = tVingAthleteDataProvider.getTVingHitterData();

        assertThat(athletes).isNotNull();
        assertThat(athletes).isNotEmpty();

        System.out.println("1위 선수 이름 = " + athletes.get(0).getName());
        System.out.println("전체 데이터 수 = " + athletes.size());
    }
}