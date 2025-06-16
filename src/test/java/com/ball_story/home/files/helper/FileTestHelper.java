package com.ball_story.home.files.helper;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class FileTestHelper {
    public MockMultipartFile getTestFile() throws IOException {
        String testResourceName = "testImage.jpg";
        ClassPathResource resource = new ClassPathResource(testResourceName);

        return new MockMultipartFile(
                "testFile",
                testResourceName,
                "image/jpeg",
                resource.getInputStream()
        );
    }
}
