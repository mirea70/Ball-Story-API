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
import com.ball_story.home.story.entity.StoryImage;
import com.ball_story.home.story.repository.StoryImageRepository;
import com.ball_story.home.story.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoryService {
    private final StoryRepository storyRepository;
    private final StoryImageRepository storyImageRepository;
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
    // Todo: 이미지들 Story에 String으로 관리 -> 중계 테이블 관리로 변경
    @Transactional
    public Long create(StoryCreateRequest request, List<MultipartFile> storyImgs) {
        List<Long> storyImgIds = snowflakeIDGenerator.generateIds(storyImgs != null ? storyImgs.size() : 0);

        CompletableFuture<List<AttachFileResponse>> uploadFuture = attachFileService.uploadFiles(
                storyImgIds,
                storyImgs
        );

        Story story = saveWithImages(request, uploadFuture, storyImgIds);
        waitImgUpload(uploadFuture);

        return story.getStoryId();
    }

    private Story saveWithImages(StoryCreateRequest request,
                                           CompletableFuture<List<AttachFileResponse>> uploadFuture,
                                           List<Long> storyImgIds) {
        try {
            Story story = Story.of(request);
            storyRepository.insert(story);

            Long storyId = story.getStoryId();
            List<StoryImage> storyImages = new ArrayList<>();
            for(Long storyImgId : storyImgIds) {
                storyImages.add(StoryImage.of(storyId, storyImgId));
            }
            storyImageRepository.insertBatch(storyImages);
            return story;
        } catch (Exception e) {
            waitImgUploadNoMatter(uploadFuture); // 업로드 처리 완료 대기
            attachFileService.deleteFiles(storyImgIds); // 보상 처리
            log.error("[StoryService.saveStoryOrCleanupOnFail] Failed. error :\n", e);
            throw new SystemException(SystemErrorResult.UNKNOWN);
        }
    }

    private void waitImgUpload(CompletableFuture<List<AttachFileResponse>> uploadFuture) {
        try {
            uploadFuture.join();
        } catch (CompletionException ce) {
            // Todo: 어떤 파일 업로드 시 문제 생겼었는지 클라이언트에 알려주도록 개선 필요
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
