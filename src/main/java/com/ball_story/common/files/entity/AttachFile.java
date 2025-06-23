package com.ball_story.common.files.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class AttachFile {
    @TableId(type = IdType.INPUT)
    private Long fileId;
    private String name;
    private String path;
    private LocalDateTime createdAt;

    @Builder(access = AccessLevel.PRIVATE)
    private AttachFile(Long fileId, String name, String path, LocalDateTime createdAt) {
        this.fileId = fileId;
        this.name = name;
        this.path = path;
        this.createdAt = createdAt;
    }

    public static AttachFile of(Long fileId, String name, String path) {
        return AttachFile.builder()
                .fileId(fileId)
                .name(name)
                .path(path)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
