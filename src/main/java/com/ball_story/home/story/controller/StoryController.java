package com.ball_story.home.story.controller;

import com.ball_story.home.story.dto.StoryCreateRequest;
import com.ball_story.home.story.dto.StoryResponse;
import com.ball_story.home.story.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoryController {
    private final StoryService storyService;

    @PostMapping(value = "/v1/stories", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Long> create(
            @RequestPart StoryCreateRequest request,
            @RequestPart(required = false, value = "storyImgs") List<MultipartFile> storyImgs
            ) {
            return ResponseEntity.ok(
                    storyService.create(request, storyImgs)
            );
    }

    @GetMapping("/v1/stories/{storyId}")
    public ResponseEntity<StoryResponse> findOne(@PathVariable Long storyId) {
        return ResponseEntity.ok(
                storyService.findOne(storyId)
        );
    }
}
