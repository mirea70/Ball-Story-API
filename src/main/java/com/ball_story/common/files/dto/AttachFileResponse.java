package com.ball_story.common.files.dto;


import com.ball_story.common.files.entity.AttachFile;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AttachFileResponse {
    private Long fileId;
    private String name;
    private String path;

    @Builder(access = AccessLevel.PRIVATE)
    public AttachFileResponse(Long fileId, String name, String path) {
        this.fileId = fileId;
        this.name = name;
        this.path = path;
    }

    public static AttachFileResponse from(AttachFile attachFile) {
        return AttachFileResponse.builder()
                .fileId(attachFile.getFileId())
                .name(attachFile.getName())
                .path(attachFile.getPath())
                .build();
    }
}
