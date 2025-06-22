package com.ball_story.common.app;

import org.springframework.beans.factory.annotation.Value;

public class AppInfo {
    @Value("${app.scheme}")
    private static String scheme;
    @Value("${app.host}")
    private static String host;

    public static String getUrlPrefix() {
        return scheme +  "://" + host + "/";
    }
}
