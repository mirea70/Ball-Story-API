package com.ball_story.home.story.entity;

import com.ball_story.home.story.dto.StoryCreateRequest;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Story {
    @TableId(value = "story_id", type = IdType.AUTO)
    private Long storyId;
    private Category category;
    private String analysisTarget;
    private String viewPlace;
    private String content;
    private Long writerId;
    private LocalDateTime storyAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    // Todo:Mybatis 매핑 이슈 해결하기 -> selectById 실행시 category 값을 storyId val로 매핑하려고 시도됨

    /**
     * 기본생성자 없을 때 위 문제가 발생하고 있음.
     * 기본생성자가 있을 때와 없을 때, Mybatis plus의 selectById 함수 동작방식 확인 필요
     */
    @Getter
    @RequiredArgsConstructor
    public enum Category {
        LIVE_REVIEW("직관 리뷰"),
        REVIEW("일반 리뷰"),
        ANALYSIS("분석"),
        NEWS("소식");

        private final String label;
    }

    @Builder(access = AccessLevel.PRIVATE)
    private Story(Category category, String analysisTarget, String viewPlace, String content, Long writerId, LocalDateTime storyAt, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.category = category;
        this.analysisTarget = analysisTarget;
        this.viewPlace = viewPlace;
        this.content = content;
        this.writerId = writerId;
        this.storyAt = storyAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }


    public static Story of(StoryCreateRequest request) {
        return Story.builder()
                .category(request.getCategory())
                .analysisTarget(request.getAnalysisTarget())
                .viewPlace(request.getViewPlace())
                .content(request.getContent())
                .writerId(request.getWriterId())
                .storyAt(request.getStoryAt())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
