package com.ball_story.home.story.helper;

import com.ball_story.home.story.dto.StoryCreateRequest;
import com.ball_story.home.story.entity.Story;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class StoryTestHelper {
    public StoryCreateRequest getTestCreateRequest() {
        return StoryCreateRequest.builder()
                .category(Story.Category.REVIEW)
                .analysisTarget("분석대상")
                .viewPlace("본 장소")
                .content("스토리 내용")
                .writerId(1L)
                .storyAt(LocalDateTime.now())
                .build();
    }
}
