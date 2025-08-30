package com.ball_story.home.athlete.api;

import com.ball_story.common.enums.Team;
import com.ball_story.home.athlete.dto.AthleteResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class AthleteApiTest {
    @LocalServerPort
    private int port;
    private RestClient restClient;

    @BeforeAll
    public void setup() {
        restClient = RestClient.create("http://localhost:" + port);
    }

    @Test
    public void findAllByTeamTest() {
        Team team = Team.SAMSUNG;
        ResponseEntity<List<AthleteResponse>> response = findAllByTeam(team);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isNotEmpty();

        int athleteCnt = response.getBody().size();
        log.info("{} team athleteCnt = {}", team.toString(), athleteCnt);
        assertThat(athleteCnt).isBetween(60, 70);
    }

    private ResponseEntity<List<AthleteResponse>> findAllByTeam(Team team) {
        return restClient.get()
                .uri("/v1/athletes?team={team}", team)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>(){});
    }
}
