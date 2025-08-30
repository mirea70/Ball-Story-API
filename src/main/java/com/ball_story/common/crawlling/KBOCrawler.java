package com.ball_story.common.crawlling;

import com.ball_story.common.crawlling.constant.CrawlingConstant;
import com.ball_story.common.enums.Team;
import com.ball_story.common.errors.exceptions.CrawlingException;
import com.ball_story.common.errors.results.CrawlingErrorResult;
import com.ball_story.home.athlete.entity.Athlete;
import com.ball_story.home.athlete.enums.AthleteType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.ball_story.common.crawlling.constant.CrawlingConstant.WEB_OPEN_WAIT_TIME_SC;

@Component
@Slf4j
@RequiredArgsConstructor
public class KBOCrawler {
    private ChromeDriver driver;
    private WebElement totalBox;
    private WebDriverWait wait;


    public List<Athlete> getKboAthleteData() throws InterruptedException {
        log.info("[KBOCrawler] getKboAthleteData start...");
        driver = ChromeDriverProvider.getChromeDriver(CrawlingConstant.KBO_HITTER_RECORD_URL);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<Athlete> athletes = new ArrayList<>();
        getKboHitterData(athletes);

        driver = ChromeDriverProvider.getChromeDriver(CrawlingConstant.KBO_PITCHER_RECORD_URL);
        wait = new WebDriverWait(driver, Duration.ofSeconds(WEB_OPEN_WAIT_TIME_SC));
        getKboPitcherData(athletes);

        return athletes;
    }

    private void getKboHitterData(List<Athlete> athletes) {
        try {
            log.info("[KBOCrawler] getKboHitterData start...");
            addAthleteDAta(athletes, AthleteType.HITTER);
        } catch (Exception e) {
            log.error("[KBOCrawler.getKboHitterData] failed.", e);
            throw new CrawlingException(CrawlingErrorResult.CRAWLING_FAILED);
        }
    }

    private void getKboPitcherData(List<Athlete> athletes) {
        try {
            log.info("[KBOCrawler] getKboPitcherData start...");

            // 투수 선택
            WebElement subContainer = driver.findElement(By.className("sub-content"));
            WebElement tab2 = subContainer.findElement(By.className("tab-depth2"));
            WebElement tab = tab2.findElement(By.className("tab"));
            WebElement pitcherOn = tab.findElement(By.className("on"));
            pitcherOn.click();

            addAthleteDAta(athletes, AthleteType.PITCHER);

        } catch (Exception e) {
            log.error("[KBOCrawler.getKboPitcherData] failed.", e);
            throw new CrawlingException(CrawlingErrorResult.CRAWLING_FAILED);
        }
    }

    private void pageInit() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("cphContents_cphContents_cphContents_udpContent")));
        totalBox = driver.findElement(By.id("cphContents_cphContents_cphContents_udpContent"));

        driver.executeScript("window.scrollTo(0,0)");
    }

    private void addAthleteDAta(List<Athlete> athletes, AthleteType type) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("cphContents_cphContents_cphContents_udpContent")));
        totalBox = driver.findElement(By.id("cphContents_cphContents_cphContents_udpContent"));

        for(int i=1; i<=10; i++) {
            // 팀 선택
            driver.executeScript("window.scrollTo(0,0)");
            WebElement teamSelect = totalBox.findElement(By.id("cphContents_cphContents_cphContents_ddlTeam_ddlTeam"));

            teamSelect.click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"cphContents_cphContents_cphContents_udpContent\"]")));
            List<WebElement> teams = teamSelect.findElements(By.tagName("option"));
            WebElement teamButton = teams.get(i);
            String team = teamButton.getText();
            log.info("${} team crawling start...", team);

            teamButton.click();
            pageInit();
            wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(totalBox, By.className("record_result")));
            WebElement recordResultBox = totalBox.findElement(By.className("record_result"));
            wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(recordResultBox, By.className("paging")));
            WebElement pageInfo = recordResultBox.findElement(By.className("paging"));

            wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(pageInfo, By.id("cphContents_cphContents_cphContents_ucPager_btnNo1")));
            WebElement page1Button = pageInfo.findElement(By.id("cphContents_cphContents_cphContents_ucPager_btnNo1"));
            page1Button.click();

            pageInit();
//            recordResultBoxInit = wait.until(driver -> {
//                WebElement freshTotalBox = driver.findElement(By.id("cphContents_cphContents_cphContents_udpContent"));
//                return freshTotalBox.findElement(By.className("record_result"));
//            });
            wait.until(ExpectedConditions.stalenessOf(recordResultBox));
            recordResultBox = totalBox.findElement(By.className("record_result"));

//            wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(recordResultBoxInit, By.className("paging")));
            wait.until(ExpectedConditions.stalenessOf(pageInfo));
            pageInfo = recordResultBox.findElement(By.className("paging"));

            List<WebElement> elements = pageInfo.findElements(By.cssSelector("[id^='cphContents_cphContents_cphContents_ucPager_btnNo']"));
            int page_count = elements.size();
            int current_page = 1;

            if(page_count == 0) {
                log.info("[KBOCrawler.getKbo" + type.toString() + "] " + team + " data not exist");
                continue;
            }

            switch (type) {
                case HITTER -> addHitterData(recordResultBox, athletes);
                case PITCHER -> addPitcherData(recordResultBox, athletes);
            }

            current_page++;

            while (current_page <= page_count) {
                // 현재 페이지로 이동
                WebElement button = pageInfo.findElement(By.id("cphContents_cphContents_cphContents_ucPager_btnNo" + current_page));
                button.click();
                pageInit();

                // 데이터 가져오기
                wait.until(ExpectedConditions.stalenessOf(recordResultBox));
                recordResultBox = totalBox.findElement(By.className("record_result"));
//                wait.until(ExpectedConditions.visibilityOf(recordResultBox));
                switch (type) {
                    case HITTER -> addHitterData(recordResultBox, athletes);
                    case PITCHER -> addPitcherData(recordResultBox, athletes);
                }
                current_page++;
            }
        }
    }


    private void addHitterData(WebElement recordResultBox, List<Athlete> athletes) {
//        wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(recordResultBox, By.className("tData01")));
        WebElement table = recordResultBox.findElement(By.className("tData01"));

        int startIdx = athletes.size();
//        wait.until(ExpectedConditions.visibilityOfAllElements(table));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
//        wait.until(ExpectedConditions.visibilityOfAllElements(rows));

        for (WebElement row : rows) {
            List<WebElement> cols = row.findElements(By.tagName("td"));
            if (cols.size() >= 2) {
                String playerName = cols.get(1).getText();
                String teamName = cols.get(2).getText();
                Double hitAvg = !cols.get(3).getText().equals("-") ? Double.parseDouble(cols.get(3).getText()) : 0.000;
                Integer hitCount = Integer.valueOf(cols.get(8).getText());
                Integer homeRunCount = Integer.valueOf(cols.get(11).getText());
                Integer rbi = Integer.valueOf(cols.get(12).getText());

                athletes.add(
                        Athlete.createHitter(
                                null, playerName, Team.valueOfName(teamName), AthleteType.HITTER,
                                hitAvg, rbi, hitCount, homeRunCount
                        )
                );
            }
        }
//        updateHitterOps(totalBox, athletes, startIdx);
    }

    private void addPitcherData(WebElement recordResultBox, List<Athlete> athletes) {
//        wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(recordResultBox, By.className("tData01")));
        WebElement table = recordResultBox.findElement(By.className("tData01"));

//        wait.until(ExpectedConditions.visibilityOfAllElements(table));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
//        wait.until(ExpectedConditions.visibilityOfAllElements(rows));

        for (WebElement row : rows) {
            List<WebElement> cols = row.findElements(By.tagName("td"));
            if (cols.size() >= 2) {
                String playerName = cols.get(1).getText();
                String teamName = cols.get(2).getText();
                Double era = !cols.get(3).getText().equals("-") ? Double.parseDouble(cols.get(3).getText()) : 0.00;
                Integer win = Integer.valueOf(cols.get(5).getText());
                Integer loose = Integer.valueOf(cols.get(6).getText());
                Integer save = Integer.valueOf(cols.get(7).getText());
                Integer hold = Integer.valueOf(cols.get(8).getText());
                Double whip = !cols.get(3).getText().equals("-") ? Double.parseDouble(cols.get(18).getText()) : 0.00;

                athletes.add(
                        Athlete.createPitcher(
                                null, playerName, Team.valueOfName(teamName), AthleteType.PITCHER,
                                win, loose, hold, save, era, whip
                        )
                );
            }
        }
    }

    /**
     *  타자 데이터 OPS 데이터 업데이트 -> 셀레니움 라이브러리 이슈 존재로 추정
     *  -> 분명히 존재하는 class 요소를 http 정보 가져올때부터 못가져옴 class=row만 빼고 가져옴
     *  -> 추후 라이브러리 코드 수정해서 해결 시도해보기
     */
    private void updateHitterOps(WebElement totalBox, List<Athlete> athletes, int startIdx) throws InterruptedException {
        WebElement nextButton = totalBox.findElement(By.className("row"))
                .findElement(By.className("more_record"))
                .findElement(By.className("next"));

        nextButton.click();
        Thread.sleep(Duration.ofSeconds(WEB_OPEN_WAIT_TIME_SC));

        WebElement recordResultBox = totalBox.findElement(By.className("record_result"));
        WebElement table = recordResultBox.findElement(By.className("tData01"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        for (int i=0; i<rows.size(); i++) {
            List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));
            if (cols.size() >= 2) {
                Double ops = Double.valueOf(cols.get(11).getText());

//                athletes.get(startIdx + i).updateOps(ops);
            }
        }
    }

    private void updateHitterOpsBackup(WebElement totalBox, List<Athlete> athletes, int startIdx) throws InterruptedException {
        WebElement nextButton = totalBox.findElement(By.className("row"))
                .findElement(By.className("more_record"))
                .findElement(By.className("next"));
//        WebElement nextButton = totalBox.findElement(
//                By.cssSelector(".row .more_record .next")
//        );

        nextButton.click();
        Thread.sleep(Duration.ofSeconds(WEB_OPEN_WAIT_TIME_SC));

        WebElement recordResultBox = totalBox.findElement(By.className("record_result"));
        WebElement table = recordResultBox.findElement(By.className("tData01"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        for (int i=0; i<rows.size(); i++) {
            List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));
            if (cols.size() >= 2) {
                Double ops = Double.valueOf(cols.get(11).getText());

//                athletes.get(startIdx + i).updateOps(ops);
            }
        }
    }
}
