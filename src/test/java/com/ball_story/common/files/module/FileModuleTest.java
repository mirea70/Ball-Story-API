package com.ball_story.common.files.module;

import com.ball_story.common.files.dto.AttachFileResponse;
import com.ball_story.common.files.helper.FileTestHelper;
import com.ball_story.common.files.repository.AttachFileRepository;
import com.ball_story.common.files.service.AttachFileService;
import com.ball_story.common.utils.SnowflakeIDGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class FileModuleTest {
    @Autowired
    private AttachFileService attachFileService;
    @Autowired
    private AttachFileRepository attachFileRepository;
    @Autowired
    private SnowflakeIDGenerator idGenerator;
    @Autowired
    private FileTestHelper fileTestHelper;

    @Test
    void uploadAndDeleteTest() throws Exception {
        MockMultipartFile mockFile = fileTestHelper.getTestFile();

        // 업로드 테스트
        AttachFileResponse response = attachFileService.uploadFile(idGenerator.nextId(), mockFile);
        File file = new File(fileTestHelper.getFullPath(response.getPath()));
        assertThat(file).exists();
        assertThat(attachFileRepository.findById(
                response.getFileId())
        ).isNotEmpty();

        // 삭제 테스트
        attachFileService.deleteFile(response.getFileId());
        assertThat(file).doesNotExist();
        assertThat(attachFileRepository.findById(
                response.getFileId())
        ).isEmpty();
    }
}
