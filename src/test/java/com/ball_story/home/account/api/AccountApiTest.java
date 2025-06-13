package com.ball_story.home.account.api;

import com.ball_story.home.account.dto.AccountJoinRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountApiTest {
    RestClient restClient = RestClient.create("http://localhost:8080");

    @Test
    public void joinTest() {
        ResponseEntity<?> response = join(new AccountJoinRequest(
                "name",
                "01031233333",
                null
        ));

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    private ResponseEntity<Void> join(AccountJoinRequest request) {
        return restClient.post()
                .uri("/v1/accounts")
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }
}
