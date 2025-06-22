package com.ball_story.home.story.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StoryImage {
    @TableField
    private final Long storyId;
    @TableField
    private final Long fileId;
    private final LocalDateTime createdAt;

    @Builder(access = AccessLevel.PRIVATE)
    private StoryImage(Long storyId, Long fileId, LocalDateTime createdAt) {
        this.storyId = storyId;
        this.fileId = fileId;
        this.createdAt = createdAt;
    }

    public static StoryImage of(Long storyId, Long fileId) {
        return StoryImage.builder()
                .storyId(storyId)
                .fileId(fileId)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
