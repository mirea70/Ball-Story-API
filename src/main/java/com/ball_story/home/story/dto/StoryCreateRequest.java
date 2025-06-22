package com.ball_story.home.story.dto;

import com.ball_story.home.story.entity.Story;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class StoryCreateRequest {
    private Story.Category category;
    private String analysisTarget;
    private String viewPlace;
    private String content;
    private Long writerId;
    private LocalDateTime storyAt;
}
