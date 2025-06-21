package com.ball_story.home.story.dto;

import com.ball_story.home.story.entity.Story;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class StoryCreateRequest {
    private Story.Category category;
    @JsonIgnore
    @Setter
    private List<Long> storyImgIds;
    private String analysisTarget;
    private String viewPlace;
    private String content;
    private Long writerId;
    private LocalDateTime storyAt;
}
