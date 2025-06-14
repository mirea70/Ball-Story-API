package com.ball_story.home.stadium.api;

import com.ball_story.common.enums.Team;
import com.ball_story.home.account.dto.AccountJoinRequest;
import com.ball_story.home.stadium.dto.StadiumCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;

public class HomeStadiumApiTest {
    RestClient restClient = RestClient.create("http://localhost:8080");

    @Test
    public void createTest() {
        ResponseEntity<?> response = create(new StadiumCreateRequest(
                "홈구장명",
                Team.SAMSUNG,
                1L
        ));

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    private ResponseEntity<Void> create(StadiumCreateRequest request) {
        return restClient.post()
                .uri("/v1/stadiums")
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }
}
