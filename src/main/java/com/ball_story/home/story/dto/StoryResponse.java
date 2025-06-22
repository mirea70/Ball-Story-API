package com.ball_story.home.story.dto;

import com.ball_story.home.story.entity.Story;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class StoryResponse {
    private final Long storyId;
    private final Story.Category category;
    private final List<String> storyImgPaths;
    private final String analysisTarget;
    private final String viewPlace;
    private final String content;
    private final Long writerId;
    private final LocalDateTime storyAt;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static StoryResponse from(Story story) {
        return StoryResponse.builder()
                .storyId(story.getStoryId())
                .category(story.getCategory())
//                .storyImgPaths(story.getStoryImgIds())
                .analysisTarget(story.getAnalysisTarget())
                .viewPlace(story.getViewPlace())
                .content(story.getContent())
                .writerId(story.getWriterId())
                .storyAt(story.getStoryAt())
                .createdAt(story.getCreatedAt())
                .updatedAt(story.getUpdatedAt())
                .build();
    }

    @Builder(access = AccessLevel.PRIVATE)
    private StoryResponse(Long storyId, Story.Category category, List<String> storyImgPaths, String analysisTarget, String viewPlace, String content, Long writerId, LocalDateTime storyAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.storyId = storyId;
        this.category = category;
        this.storyImgPaths = storyImgPaths;
        this.analysisTarget = analysisTarget;
        this.viewPlace = viewPlace;
        this.content = content;
        this.writerId = writerId;
        this.storyAt = storyAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
