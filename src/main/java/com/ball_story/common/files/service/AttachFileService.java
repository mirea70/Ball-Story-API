package com.ball_story.common.files.service;

import com.ball_story.common.errors.exceptions.FileRequestException;
import com.ball_story.common.errors.exceptions.NotFoundException;
import com.ball_story.common.errors.results.FileErrorResult;
import com.ball_story.common.errors.results.NotFoundErrorResult;
import com.ball_story.common.files.dto.AttachFileResponse;
import com.ball_story.common.files.entity.AttachFile;
import com.ball_story.common.files.enums.FileExtension;
import com.ball_story.common.files.repository.AttachFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttachFileService {
    private final AttachFileRepository attachFileRepository;

    private final String rootPath = System.getProperty("user.home");
    private final String fileDefaultDir = rootPath + "/files";

    public List<AttachFileResponse> uploadFiles(List<MultipartFile> multipartFiles) throws IOException {
        if(multipartFiles == null || multipartFiles.isEmpty()) return  null;

        List<AttachFileResponse> responses = new ArrayList<>();
        for(MultipartFile file : multipartFiles) {
            responses.add(this.uploadFile(file));
        }
        return responses;
    }

    public AttachFileResponse uploadFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile == null) return null;

        String path = getStoreFileName(multipartFile);
        File file = new File(getFullPath(path));
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        multipartFile.transferTo(file);

        AttachFile newFile = AttachFile.of(
                multipartFile.getOriginalFilename(),
                path
        );
        attachFileRepository.save(newFile);

        return AttachFileResponse.from(newFile);
    }

    private String getStoreFileName(MultipartFile file) {
        String rawFileName = file.getName();
        validateFileName(rawFileName);

        String uploadFileName = file.getOriginalFilename();
        String extension = extractExt(uploadFileName);
        validateFileExtension(extension);

        return rawFileName + "_" + UUID.randomUUID() + "." + extension;
    }

    private void validateFileName(String rawFileName) {
        boolean isMatched = Pattern.matches("^[a-zA-Zㄱ-힣0-9]*$", rawFileName);
        if(!isMatched) {
            log.error("[AttachFileService.validateFileName] Failed : {}", rawFileName);
            throw new FileRequestException(FileErrorResult.NOT_MATCHING_NAME_RULE);
        }
    }

    private void validateFileExtension(String requestExtension) {
        List<String> extensions = FileExtension.getExtensions();
        if(!extensions.contains(requestExtension))
            throw new FileRequestException(FileErrorResult.NOT_SUPPORT_EXT);
    }

    private String extractExt(String uploadFileName) {
        int dotIndex = uploadFileName.lastIndexOf(".");
        return uploadFileName.substring(dotIndex + 1);
    }

    private String getFullPath(String uploadFileName) {
        return fileDefaultDir + "/" + uploadFileName;
    }

    public void deleteFile(Long fileId) {
        if(fileId == null) return;

        AttachFile attachFile = attachFileRepository.findById(fileId)
                .orElseThrow(() -> new NotFoundException(NotFoundErrorResult.NOT_FOUND_FILE_DATA));

        File file = new File(getFullPath(attachFile.getPath()));
        if(file.exists()) {
            if(file.delete()) {
                attachFileRepository.delete(fileId);
            }
            else {
                throw new FileRequestException(FileErrorResult.FAIL_REMOVE_FILE);
            }
        } else {
            log.error("not exist file - id : {}, - path : {}", fileId, attachFile.getPath());
        }
    }
}
