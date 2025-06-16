package com.ball_story.common.files.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.apache.ibatis.type.Alias;

@Getter
public class AttachFile {
    private Long fileId;
    private String name;
    private String path;

    @Builder(access = AccessLevel.PRIVATE)
    public AttachFile(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public static AttachFile of(String name, String path) {
        return AttachFile.builder()
                .name(name)
                .path(path)
                .build();
    }
}
