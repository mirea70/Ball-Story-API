package com.ball_story.home.story.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StoryPageRequest {
    private Integer size;
    private LocalDateTime lastStoryAt;
}
