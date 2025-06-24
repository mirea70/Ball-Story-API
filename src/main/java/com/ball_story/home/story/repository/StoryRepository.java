package com.ball_story.home.story.repository;

import com.ball_story.home.story.dto.StoryPageResponse;
import com.ball_story.home.story.dto.StoryResponse;
import com.ball_story.home.story.entity.Story;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface StoryRepository extends BaseMapper<Story> {
    default Optional<Story> findById(Long storyId) {
        return Optional.ofNullable(
                selectById(storyId)
        );
    }

    Optional<StoryResponse> selectWithImgPaths(Long storyId);
    List<StoryPageResponse> selectAllInit(Integer size);
    List<StoryPageResponse> selectAll(Integer size, LocalDateTime lastStoryAt);
}
