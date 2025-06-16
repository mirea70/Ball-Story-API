package com.ball_story.common.files.repository;

import com.ball_story.common.files.entity.AttachFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface AttachFileRepository {
    void save(AttachFile attachFile);
    Optional<AttachFile> findById(Long id);
    void delete(Long id);
}
