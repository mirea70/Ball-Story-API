package com.ball_story.common.files.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AttachFile {
    @TableId(type = IdType.INPUT)
    private Long fileId;
    private String name;
    private String path;

    @Builder(access = AccessLevel.PRIVATE)
    public AttachFile(Long fileId, String name, String path) {
        this.fileId = fileId;
        this.name = name;
        this.path = path;
    }

    public static AttachFile of(Long fileId, String name, String path) {
        return AttachFile.builder()
                .fileId(fileId)
                .name(name)
                .path(path)
                .build();
    }
}
