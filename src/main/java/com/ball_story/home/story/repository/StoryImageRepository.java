package com.ball_story.home.story.repository;

import com.ball_story.home.story.entity.StoryImage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoryImageRepository extends BaseMapper<StoryImage> {
    void insertBatch(List<StoryImage> storyImages);
    List<StoryImage> selectByStoryId(Long storyId);
}
