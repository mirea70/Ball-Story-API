package com.ball_story.home.stadiums.api;

import com.ball_story.home.stadiums.dto.StadiumCreateRequest;
import com.ball_story.home.stadiums.dto.StadiumResponse;
import com.ball_story.home.stadiums.entity.HomeStadium;
import com.ball_story.home.stadiums.helper.HomeStadiumHelper;
import com.ball_story.home.stadiums.repository.HomeStadiumRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
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
    private HomeStadiumHelper homeStadiumHelper;
    @Autowired
    private HomeStadiumRepository homeStadiumRepository;

    @BeforeAll
    public void setup() {
        restClient = RestClient.create("http://localhost:" + port);
    }

    @Test
    public void createTest() {
        ResponseEntity<Long> response = create(
                homeStadiumHelper.getTestCreateRequest()
        );

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        log.info("stadiumId = {}", response.getBody());

        HomeStadium homeStadium = homeStadiumRepository.selectById(response.getBody());
        System.out.println("homeStadium = " + homeStadium);
    }

    @Test
    public void updateNameTest() {
        Long stadiumId = create(
                homeStadiumHelper.getTestCreateRequest()
        ).getBody();
        StadiumResponse createResponse = findOne(stadiumId).getBody();

        String testName = "변경네임";
        ResponseEntity<?> response = updateName(createResponse.getStadiumId(), testName);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        StadiumResponse afterUpdateResponse = findOne(
                createResponse.getStadiumId()
        ).getBody();
        assertThat(afterUpdateResponse.getName()).isEqualTo(testName);
        assertThat(afterUpdateResponse.getUpdatedAt()).isAfter(createResponse.getUpdatedAt());

        log.info("stadiumId = {}, name = {}", afterUpdateResponse.getStadiumId(), afterUpdateResponse.getName());
    }

    private ResponseEntity<Long> create(StadiumCreateRequest request) {
        return restClient.post()
                .uri("/v1/stadiums")
                .body(request)
                .retrieve()
                .toEntity(Long.class);
    }

    private ResponseEntity<StadiumResponse> findOne(Long stadiumId) {
        return restClient.get()
                .uri("/v1/stadiums/{stadiumId}", stadiumId)
                .retrieve()
                .toEntity(StadiumResponse.class);
    }

    private ResponseEntity<Void> updateName(Long stadiumId, String name) {
        return restClient.patch()
                .uri("/v1/stadiums/{stadiumId}?name={name}", stadiumId, name)
                .retrieve()
                .toBodilessEntity();
    }
}
