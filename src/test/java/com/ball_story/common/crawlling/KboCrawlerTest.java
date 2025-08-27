package com.ball_story.common.crawlling;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class KboCrawlerTest {
    @Autowired KBOCrawler kboCrawler;

    @Test
    void getKboBatterDataTest() throws Exception {
        kboCrawler.getKboBatterData();
    }
}
