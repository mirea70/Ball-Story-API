package com.ball_story.common.files.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum FileExtension {
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    GIF("gif");

    private final String label;

    private static final Set<String> LABELS = Arrays.stream(values())
            .map(ext -> ext.label.toLowerCase())
            .collect(Collectors.toSet());

    public static List<String> getExtensions() {
        return Arrays.stream(values())
                .map(extension -> extension.label).toList();
    }

    public static boolean validate(String extension) {
        return LABELS.contains(extension);
    }
}
