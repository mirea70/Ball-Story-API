package com.ball_story.common.files.service;

import com.ball_story.common.app.AppInfo;
import com.ball_story.common.errors.exceptions.FileRequestException;
import com.ball_story.common.errors.exceptions.SystemException;
import com.ball_story.common.errors.results.FileErrorResult;
import com.ball_story.common.errors.results.NotFoundErrorResult;
import com.ball_story.common.errors.results.SystemErrorResult;
import com.ball_story.common.files.dto.AttachFileResponse;
import com.ball_story.common.files.entity.AttachFile;
import com.ball_story.common.files.enums.FileExtension;
import com.ball_story.common.files.repository.AttachFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttachFileService {
    private final AttachFileRepository attachFileRepository;
//    private static final AppInfo appInfo;

    private final String rootPath = System.getProperty("user.home");
    private final String filePrefix = "/files";
    private final String fileDefaultDir = rootPath + filePrefix;

    @Async
    public CompletableFuture<List<AttachFileResponse>> uploadFiles(List<Long> fileIds, List<MultipartFile> multipartFiles) {
        log.info("[AttachFileService.uploadFiles] start..");
        if(multipartFiles == null || multipartFiles.isEmpty()) return  null;
        if(fileIds.size() != multipartFiles.size()) {
            throw new SystemException(SystemErrorResult.NOT_MATCH_FILE_ID_COUNT);
        }

        List<AttachFileResponse> responses = new ArrayList<>();
        for(int i=0; i< fileIds.size(); i++) {
            responses.add(this.uploadFile(fileIds.get(i), multipartFiles.get(i)));
        }
        return CompletableFuture.completedFuture(responses);
    }

    @Async
    public CompletableFuture<AttachFileResponse> uploadFileAsync(Long fileId, MultipartFile multipartFile) {
        return CompletableFuture.completedFuture(uploadFile(fileId, multipartFile));
    }

    public AttachFileResponse uploadFile(Long fileId, MultipartFile multipartFile) {
        log.info("[AttachFileService.uploadFile] start..");
        if(multipartFile == null) return null;

        String path = getStoreFileName(multipartFile);
        File file = new File(getFullPath(path));
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            log.info("[IOException] occur. \nfile path = {}", path);
            throw new FileRequestException(FileErrorResult.FAIL_UPLOAD_FILE, multipartFile.getOriginalFilename());
        }

        AttachFile newFile = AttachFile.of(
                fileId,
                multipartFile.getOriginalFilename(),
                path
        );
        try {
            attachFileRepository.insert(newFile);
        } catch (Exception e) {
            log.error("[AttachFileRepository.insert] Failed. \nfile object info = {}\nerrors: \n{}", newFile.toString(), e.toString());
            deleteFile(file);
            throw new SystemException(SystemErrorResult.UNKNOWN);
        }

        return AttachFileResponse.from(newFile);
    }

    private String getStoreFileName(MultipartFile file) {
        String rawFileName = file.getName();
        validateFileName(rawFileName);

        String uploadFileName = file.getOriginalFilename();
        String extension = extractExt(uploadFileName);

        return filePrefix + "/" + rawFileName + "_" + UUID.randomUUID() + "." + extension;
    }

    private void validateFileName(String rawFileName) {
        boolean isMatched = Pattern.matches("^[a-zA-Zㄱ-힣0-9]*$", rawFileName);
        if(!isMatched) {
            log.error("[AttachFileService.validateFileName] Failed : {}", rawFileName);
            throw new FileRequestException(FileErrorResult.NOT_MATCHING_NAME_RULE, rawFileName);
        }
    }

    private String extractExt(String uploadFileName) {
        int dotIndex = uploadFileName.lastIndexOf(".");
        String extension = uploadFileName.substring(dotIndex + 1);
        if(!FileExtension.validate(extension)) {
            throw new FileRequestException(FileErrorResult.NOT_SUPPORT_EXT, uploadFileName);
        }

        return extension;
    }

    private String getFullPath(String uploadFileName) {
        return rootPath + "/" + uploadFileName;
    }

    /**
     * 비동기 작업 필요
     */
    public void deleteFiles(List<Long> fileIds) {
        for(Long fileId : fileIds) {
            deleteFile(fileId);
        }
    }

    public void deleteFile(Long fileId) {
        if(fileId == null) return;

        AttachFile attachFile = attachFileRepository.findById(fileId)
                .orElse(null);
        if(attachFile == null) {
            log.error(
                    NotFoundErrorResult.NOT_FOUND_FILE_DATA.name() + " occur.\n"
                            + NotFoundErrorResult.NOT_FOUND_FILE_DATA + "\n fileId = {}", fileId
            );
            return;
        }

        File file = new File(getFullPath(attachFile.getPath()));
        if(file.exists()) {
            if(file.delete()) {
                attachFileRepository.deleteById(fileId);
            }
            else {
                throw new FileRequestException(FileErrorResult.FAIL_REMOVE_FILE, attachFile.getName());
            }
        } else {
            log.error(
                    NotFoundErrorResult.NOT_EXIST_FILE_PATH.name() + " occur.\n"
                            + NotFoundErrorResult.NOT_EXIST_FILE_PATH + "\n fileId = {}, path = {}", fileId, attachFile.getPath()
            );
        }
    }

    public void deleteFile(File file) {
        if(!file.exists()) {
            log.error(
                    NotFoundErrorResult.NOT_EXIST_FILE_PATH.name() + " occur.\n"
                            + NotFoundErrorResult.NOT_EXIST_FILE_PATH + "\n path = {}", file.getPath()
            );
            return;
        }

        if(!file.delete()) {
            throw new FileRequestException(FileErrorResult.FAIL_REMOVE_FILE, file.getName());
        }
    }

    @Async
    public CompletableFuture<List<String>> generateFileUrl(List<String> paths) {
        List<String> fileUrls = new ArrayList<>();
        for(String path : paths) {
            fileUrls.add(generateFileUrl(path));
        }
        return CompletableFuture.completedFuture(fileUrls);
    }

    public void convertFileUrls(List<String> paths) {
        for(int i=0; i< paths.size(); i++) {
            paths.set(i, generateFileUrl(paths.get(i)));
        }
    }

    public static String generateFileUrl(String path) {
        if(path.startsWith(AppInfo.getUrlPrefix())) {
            return path;
        }
        return AppInfo.getUrlPrefix() + path;
    }
}
