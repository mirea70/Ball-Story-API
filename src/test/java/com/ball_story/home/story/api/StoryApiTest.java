package com.ball_story.home.story.api;

import com.ball_story.common.files.entity.AttachFile;
import com.ball_story.common.files.repository.AttachFileRepository;
import com.ball_story.common.files.helper.FileTestHelper;
import com.ball_story.home.story.dto.StoryCreateRequest;
import com.ball_story.home.story.dto.StoryPageResponse;
import com.ball_story.home.story.dto.StoryResponse;
import com.ball_story.home.story.entity.Story;
import com.ball_story.home.story.entity.StoryImage;
import com.ball_story.home.story.helper.StoryTestHelper;
import com.ball_story.home.story.repository.StoryImageRepository;
import com.ball_story.home.story.repository.StoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class StoryApiTest {
    @Autowired
    private StoryTestHelper storyTestHelper;
    @Autowired
    private FileTestHelper fileTestHelper;
    @Autowired
    private AttachFileRepository attachFileRepository;
    @Autowired
    private StoryRepository storyRepository;
    @Autowired
    private StoryImageRepository storyImageRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @LocalServerPort
    private int port;
    private RestClient restClient;

    @BeforeAll
    public void setup() {
//        restClient = RestClient.create("http://localhost:" + port);
        restClient = RestClient.builder()
                .requestInterceptor((request, body, execution) -> {
                    System.out.println("Request URI: " + request.getURI());
                    return execution.execute(request, body);
                })
                .baseUrl("http://localhost:" + port)
                .build();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    public void createTest() throws Exception {
        List<MockMultipartFile> storyImgs = fileTestHelper.getTestFiles();

        ResponseEntity<Long> response = create(
                storyTestHelper.getTestCreateRequest(),
                storyImgs
        );
        Long savedStoryId = response.getBody();

        // 1. 스토리가 잘 저장되었는가
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        log.info("storyId = {}", savedStoryId);

        // 2. 저장된 스토리 데이터의 이미지 키값들의 개수가 요청 이미지 개수와 동일한가?
        int requestImgCount = storyImgs.size();
        Story created = storyRepository.selectById(savedStoryId);
        assertThat(created).isNotNull();

        List<StoryImage> savedImgs = storyImageRepository.selectByStoryId(response.getBody());
        assertThat(savedImgs.size()).isEqualTo(requestImgCount);

        // 3. 저장된 이미지들의 키값들의 정보로 파일이 존재하는지 확인하면 그 파일이 로컬에 제대로 존재하고 있는가?
        for(StoryImage storyImage : savedImgs) {
            Optional<AttachFile> savedImg = attachFileRepository.findById(storyImage.getFileId());
            assertThat(savedImg).isNotEmpty();
            assertThat(new File(fileTestHelper.getFullPath(savedImg.get().getPath()))).exists();
        }
    }

    private ResponseEntity<Long> create(StoryCreateRequest request, List<MockMultipartFile> storyImgs) throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(request);
        System.out.println("jsonRequest = " + jsonRequest);
        HttpHeaders jsonHeaders = new HttpHeaders();
        jsonHeaders.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("request", new HttpEntity<>(jsonRequest, jsonHeaders));
        for(MockMultipartFile storyImg : storyImgs) {
            body.add("storyImgs", fileTestHelper.convertToResource("storyImgs", storyImg));
        }

        return restClient.post()
                .uri("/v1/stories")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body)
                .retrieve()
                .toEntity(Long.class);
    }

    @Test
    void findOneTest() throws Exception {
        ResponseEntity<Long> savedResponse = create(
                storyTestHelper.getTestCreateRequest(),
                fileTestHelper.getTestFiles()
        );
        Long savedStoryId = savedResponse.getBody();

        ResponseEntity<StoryResponse> response = findOne(savedStoryId);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();

        log.info("story = \n{}", response);
    }

    private ResponseEntity<StoryResponse> findOne(Long storyId) {
        return restClient.get()
                .uri("/v1/stories/{storyId}", storyId)
                .retrieve()
                .toEntity(StoryResponse.class);
    }

    @Test
    void findAllTest() throws Exception {
        // given
        Long storyId1 = create(
                storyTestHelper.getTestCreateRequest("테스트1"),
                List.of(fileTestHelper.getTestFile())
        ).getBody();
        Long storyId2 = create(
                storyTestHelper.getTestCreateRequest("테스트2"),
                List.of(fileTestHelper.getTestFile())
        ).getBody();
        Long storyId3 = create(
                storyTestHelper.getTestCreateRequest("테스트3"),
                List.of(fileTestHelper.getTestFile())
        ).getBody();
        Long storyId4 = create(
                storyTestHelper.getTestCreateRequest("테스트4"),
                List.of(fileTestHelper.getTestFile())
        ).getBody();

        int requestSize = 2;
        LocalDateTime lastStoryAt = findOne(storyId3).getBody().getStoryAt();

        // when
        ResponseEntity<List<StoryPageResponse>> response1 = findAll(
                requestSize, null
        );
        log.info("Story List API Response1 :\n{}", response1.getBody());

        ResponseEntity<List<StoryPageResponse>> response2 = findAll(
                requestSize, lastStoryAt
        );
        log.info("Story List API Response2 :\n{}", response2.getBody());

        // then
        assertThat(response1.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response2.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response1.getBody()).hasSize(requestSize);
        assertThat(response2.getBody()).hasSize(requestSize);

        // 스토리 일자 기준 정렬 체크
        List<LocalDateTime> storyAts = response1.getBody().stream()
                .map(StoryPageResponse::getStoryAt)
                .toList();
        assertThat(storyAts).isSortedAccordingTo(Comparator.reverseOrder());

        // 중복 없는 다음 페이지 확인
        List<Long> firstIds = response1.getBody().stream().map(StoryPageResponse::getStoryId).toList();
        List<Long> secondIds = response2.getBody().stream().map(StoryPageResponse::getStoryId).toList();
        assertThat(Collections.disjoint(firstIds, secondIds)).isTrue();
    }

    private ResponseEntity<List<StoryPageResponse>> findAll(Integer size, LocalDateTime lastStoryAt) {
        return restClient.get()
                .uri("/v1/stories/list?size={size}&lastStoryAt={lastStoryAt}", size, lastStoryAt)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
    }
}
