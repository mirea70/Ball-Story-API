package com.ball_story.common.crawlling;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import static com.ball_story.common.crawlling.constant.CrawlingConstant.*;

@Slf4j
public class ChromeDriverProvider {
    private static ChromeDriver instance;

    public synchronized static ChromeDriver getChromeDriver(String url) throws InterruptedException {
        if (instance == null || instance.getSessionId() == null) {
            instance = createChromeDriver();
        }
        instance.get(url);
        WebDriverWait wait = new WebDriverWait(instance, Duration.ofSeconds(WEB_OPEN_WAIT_TIME_SC));
//        wait.wait();
        return instance;
    }

    private static ChromeDriver createChromeDriver() {
        ChromeDriver driver = null;
        try {
            Path path = Paths.get(System.getProperty("user.dir"), CHROME_DRIVER_PATH);
            System.setProperty(WEB_DRIVER_ID, path.toString());
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless"); // 창 없이 실행하고 싶을 때
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-blink-features=AutomationControlled");

            driver = new ChromeDriver(options);
            log.info("[ChromeDriverProvider.createChromeDriver] create success.");
        } catch (Exception e) {
            log.error("[ChromeDriverProvider.createChromeDriver] create fail.");
        }

        return driver;
    }

    public static void quitChromeDriver() {
        if (instance != null) {
            instance.quit();
            instance = null;
        }
        log.info("[ChromeDriverProvider.quitChromeDriver] quit success.");
    }
}
