package com.ball_story.home.story.repository;

import com.ball_story.home.story.dto.StoryResponse;
import com.ball_story.home.story.entity.Story;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface StoryRepository extends BaseMapper<Story> {
    default Optional<Story> findById(Long storyId) {
        return Optional.ofNullable(
                selectById(storyId)
        );
    }

    Optional<StoryResponse> selectWithImgPaths(Long storyId);
}
