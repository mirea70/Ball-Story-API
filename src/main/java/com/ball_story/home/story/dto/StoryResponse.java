package com.ball_story.home.story.dto;

import com.ball_story.home.story.entity.Story;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
public class StoryResponse {
    private Long storyId;
    private Story.Category category;
    private List<String> storyImgUrls;
    private String analysisTarget;
    private String viewPlace;
    private String content;
    private Long writerId;
    private LocalDateTime storyAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
