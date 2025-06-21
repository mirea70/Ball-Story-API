package com.ball_story.home.story.service;

import com.ball_story.common.errors.exceptions.NotFoundException;
import com.ball_story.common.errors.exceptions.SystemException;
import com.ball_story.common.errors.results.FileErrorResult;
import com.ball_story.common.errors.results.NotFoundErrorResult;
import com.ball_story.common.errors.results.SystemErrorResult;
import com.ball_story.common.files.dto.AttachFileResponse;
import com.ball_story.common.files.service.AttachFileService;
import com.ball_story.common.utils.SnowflakeIDGenerator;
import com.ball_story.home.story.dto.StoryCreateRequest;
import com.ball_story.home.story.dto.StoryResponse;
import com.ball_story.home.story.entity.Story;
import com.ball_story.home.story.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoryService {
    private final StoryRepository storyRepository;
    private final AttachFileService attachFileService;
    private final SnowflakeIDGenerator snowflakeIDGenerator;

    public StoryResponse findOne(Long storyId) {
        return StoryResponse.from(
                storyRepository.findById(storyId)
                        .orElseThrow(() -> new NotFoundException(NotFoundErrorResult.NOT_FOUND_STORY_DATA))
        );
    }

    /**
     * 스토리 저장 && 이미지 저장 비동기 처리
     */
    @Transactional
    public Long create(StoryCreateRequest request, List<MultipartFile> storyImgs) {
        List<Long> storyImgIds = snowflakeIDGenerator.generateIds(storyImgs != null ? storyImgs.size() : 0);
        request.setStoryImgIds(storyImgIds);

        CompletableFuture<List<AttachFileResponse>> uploadFuture = attachFileService.uploadFiles(
                storyImgIds,
                storyImgs
        );
        Story story = saveStoryOrCleanupOnFail(request, uploadFuture, storyImgIds);
        waitImgUpload(uploadFuture);

        return story.getStoryId();
    }

    private Story saveStoryOrCleanupOnFail(StoryCreateRequest request,
                                           CompletableFuture<List<AttachFileResponse>> uploadFuture,
                                           List<Long> imgIds) {
        try {
            Story story = Story.of(request);
            storyRepository.insert(story);
            return story;
        } catch (Exception e) {
            waitImgUploadNoMatter(uploadFuture); // 업로드 완료 대기
            attachFileService.deleteFiles(imgIds); // 보상 처리
            log.error("[StoryService.saveStoryOrCleanupOnFail] Failed. error :\n", e);
            throw new SystemException(SystemErrorResult.UNKNOWN);
        }
    }

    private void waitImgUpload(CompletableFuture<List<AttachFileResponse>> uploadFuture) {
        try {
            uploadFuture.join();
        } catch (CompletionException ce) {
            log.error("파일 업로드 실패", ce.getCause());
            throw new SystemException(FileErrorResult.FAIL_UPLOAD_FILE);
        }
    }

    private void waitImgUploadNoMatter(CompletableFuture<?> future) {
        try {
            future.join();
        } catch (Exception ignore) {
            // 업로드 실패든 뭐든 일단 기다리기만 하면 됨
        }
    }
}
