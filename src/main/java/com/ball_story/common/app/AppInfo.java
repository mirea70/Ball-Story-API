package com.ball_story.common.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppInfo {
    @Value("${app.scheme}")
    private String scheme;
    @Value("${app.host}")
    private String host;

    public String getUrlPrefix() {
        return scheme +  "://" + host;
    }
}
