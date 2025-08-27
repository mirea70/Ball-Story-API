package com.ball_story.common.crawlling;

import com.ball_story.common.crawlling.constant.CrawlingConstant;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

import static com.ball_story.common.crawlling.constant.CrawlingConstant.WEB_OPEN_WAIT_TIME_SC;

@Component
@Slf4j
public class KBOCrawler {
    public void getKboBatterData() throws InterruptedException {
        ChromeDriver driver = ChromeDriverProvider.getChromeDriver(CrawlingConstant.KBO_HITTER_RECORD_URL);
        try {
            WebElement totalBox = driver.findElement(By.id("cphContents_cphContents_cphContents_udpContent"));
            int page_count = -1;
            WebElement recordResultBoxInit = totalBox.findElement(By.className("record_result"));
            WebElement pageInfo = recordResultBoxInit.findElement(By.className("paging"));
            List<WebElement> elements = pageInfo.findElements(By.cssSelector("[id^='cphContents_cphContents_cphContents_ucPager_btnNo']"));
            page_count = elements.size();
            int current_page = 1;

            if(page_count <= 0) {
                log.info("[KBOCrawler.getKboBatterData] data not exist");
                return;
            }

            getDataByResultBox(recordResultBoxInit);
            current_page++;

            while (current_page <= page_count) {
                // 현재 페이지로 이동
                WebElement button = pageInfo.findElement(By.id("cphContents_cphContents_cphContents_ucPager_btnNo" + current_page));
                button.click();
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WEB_OPEN_WAIT_TIME_SC));
//                wait.wait();
                // 데이터 가져오기
                WebElement recordResultBox = totalBox.findElement(By.className("record_result"));
                getDataByResultBox(recordResultBox);
                current_page++;
            }
        } catch (Exception e) {
            log.error("[KBOCrawler.getKboBatterData] failed.", e);
        }
    }

    private void getDataByResultBox(WebElement recordResultBox) {
        WebElement table = recordResultBox.findElement(By.className("tData01"));

        List<WebElement> rows = table.findElements(By.tagName("tr"));
        for (WebElement row : rows) {
            List<WebElement> cols = row.findElements(By.tagName("td"));
            if (cols.size() >= 2) {
                String rank = cols.get(0).getText();
                String playerName = cols.get(1).getText();
                String teamName = cols.get(2).getText();
                log.info("팀명: " + teamName + " / 선수명: " + playerName);
            }
        }
    }
}
