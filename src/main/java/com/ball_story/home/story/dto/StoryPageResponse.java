package com.ball_story.home.story.dto;

import com.ball_story.common.files.service.AttachFileService;
import com.ball_story.home.story.entity.Story;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class StoryPageResponse {
    private Long storyId;
    private String content;
    private Story.Category category;
    private String thumbNameImgUrl;
    private LocalDateTime storyAt;

    public void setThumbNameImgUrl(String thumbNameImgUrl) {
        // ToDo: Mybatis Setter 매핑시 Setter 두 번 호출되는 문제 해결 필요
//        System.out.println("[MyBatis] setThumbNameImgUrl called with: " + storyId + " - " + thumbNameImgUrl);
        this.thumbNameImgUrl = AttachFileService.generateFileUrl(thumbNameImgUrl);
    }
}
