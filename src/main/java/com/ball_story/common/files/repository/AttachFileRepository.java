package com.ball_story.common.files.repository;

import com.ball_story.common.files.entity.AttachFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface AttachFileRepository extends BaseMapper<AttachFile> {
    default Optional<AttachFile> findById(Long id) {
        return Optional.ofNullable(selectById(id));
    }
}
