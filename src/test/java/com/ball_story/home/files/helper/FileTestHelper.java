package com.ball_story.home.files.helper;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
public class FileTestHelper {

    private final String rootPath = System.getProperty("user.home");

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

    public List<MockMultipartFile> getTestFiles() throws IOException {
        String testResourceName1 = "testImage2.jpeg";
        String testResourceName2 = "testImage3.jpeg";
        ClassPathResource resource1 = new ClassPathResource(testResourceName1);
        ClassPathResource resource2 = new ClassPathResource(testResourceName2);

        return List.of(
                new MockMultipartFile(
                        "testFile1",
                        testResourceName1,
                        "image/jpeg",
                        resource1.getInputStream()
                ),
                new MockMultipartFile(
                        "testFile2",
                        testResourceName2,
                        "image/jpeg",
                        resource2.getInputStream()
                )
        );
    }

    public HttpEntity<ByteArrayResource> convertToResource(String requestName, MultipartFile file) throws IOException {
        ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
            @Override
            @NonNull
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(file.getContentType()));
        headers.setContentDispositionFormData(requestName, file.getOriginalFilename());

        return new HttpEntity<>(resource, headers);
    }

    public String getFullPath(String uploadFileName) {
        return rootPath + "/" + uploadFileName;
    }
}
