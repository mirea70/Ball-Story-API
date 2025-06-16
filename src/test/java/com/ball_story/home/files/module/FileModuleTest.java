package com.ball_story.home.files.module;

import com.ball_story.common.files.dto.AttachFileResponse;
import com.ball_story.common.files.repository.AttachFileRepository;
import com.ball_story.common.files.service.AttachFileService;
import com.ball_story.home.files.helper.FileTestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FileModuleTest {
    @Autowired
    private AttachFileService attachFileService;
    @Autowired
    private AttachFileRepository attachFileRepository;
    @Autowired
    private FileTestHelper fileTestHelper;

    private final String rootPath = System.getProperty("user.home");
    private final String fileDefaultDir = rootPath + "/files";

    @Test
    void uploadAndDeleteTest() throws Exception {
        MockMultipartFile mockFile = fileTestHelper.getTestFile();

        // 업로드 테스트
        AttachFileResponse response = attachFileService.uploadFile(mockFile);
        File file = new File(getFullPath(response.getPath()));
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

    private String getFullPath(String uploadFileName) {
        return fileDefaultDir + "/" + uploadFileName;
    }
}
