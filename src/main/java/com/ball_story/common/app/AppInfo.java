package com.ball_story.common.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppInfo {
    private static String scheme;
    private static String host;

    @Value("${app.scheme}")
    public void setScheme(String scheme) {
        AppInfo.scheme = scheme;
    }

    @Value("${app.host}")
    public void setHost(String host) {
        AppInfo.host = host;
    }

    public static String getUrlPrefix() {
        if (scheme == null || host == null) {
            throw new IllegalStateException("AppConfig not initialized yet");
        }

        return scheme +  "://" + host;
    }
}
