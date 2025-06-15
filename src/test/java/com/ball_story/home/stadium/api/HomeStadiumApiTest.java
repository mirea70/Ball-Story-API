package com.ball_story.home.stadium.api;

import com.ball_story.common.enums.Team;
import com.ball_story.home.stadium.dto.StadiumCreateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class HomeStadiumApiTest {
    @LocalServerPort
    private int port;
    private RestClient restClient;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    public void setup() {
        restClient = RestClient.create("http://localhost:" + port);
    }

    @Test
    public void createTest() {
        ResponseEntity<?> response = create(new StadiumCreateRequest(
                "홈구장명",
                Team.SAMSUNG,
                1L
        ));

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM home_stadium", Integer.class);
        log.info("Data count = {}", count);
    }

    private ResponseEntity<Void> create(StadiumCreateRequest request) {
        return restClient.post()
                .uri("/v1/stadiums")
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }
}
