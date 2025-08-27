package com.ball_story.common.crawlling;

import com.ball_story.common.crawlling.constant.CrawlingConstant;
import com.ball_story.common.enums.Team;
import com.ball_story.common.errors.exceptions.CrawlingException;
import com.ball_story.common.errors.exceptions.SystemException;
import com.ball_story.common.errors.results.CrawlingErrorResult;
import com.ball_story.common.errors.results.SystemErrorResult;
import com.ball_story.common.utils.SnowflakeIDGenerator;
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
    private final SnowflakeIDGenerator idGenerator;
    private ChromeDriver driver;
    private WebElement totalBox;
    private WebDriverWait wait;


    public List<Athlete> getKboAthleteData(AthleteType type) throws InterruptedException {
        switch (type) {
            case HITTER -> {
                driver = ChromeDriverProvider.getChromeDriver(CrawlingConstant.KBO_HITTER_RECORD_URL);
                wait = new WebDriverWait(driver, Duration.ofSeconds(WEB_OPEN_WAIT_TIME_SC));
                return getKboHitterData();
            }
            case PITCHER -> {
                driver = ChromeDriverProvider.getChromeDriver(CrawlingConstant.KBO_PITCHER_RECORD_URL);
                wait = new WebDriverWait(driver, Duration.ofSeconds(WEB_OPEN_WAIT_TIME_SC));
                return getKboPitcherData();
            }
        }

        throw new SystemException(SystemErrorResult.UNKNOWN);
    }

    private List<Athlete> getKboHitterData() {
        try {
            driver.executeScript("window.scrollTo(0,0)");
            totalBox = driver.findElement(By.id("cphContents_cphContents_cphContents_udpContent"));
            // 팀 선택해서 팀별 데이터 순회
            List<Athlete> athletes = new ArrayList<>();

            for(int i=1; i<=10; i++) {
                // 팀 선택
                pageInit();
                WebElement teamSelect = totalBox.findElement(By.id("cphContents_cphContents_cphContents_ddlTeam_ddlTeam"));

                teamSelect.click();
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"cphContents_cphContents_cphContents_udpContent\"]")));
                List<WebElement> teams = teamSelect.findElements(By.tagName("option"));
                WebElement teamButton = teams.get(i);
                String team = teamButton.getText();

                teamButton.click();
                pageInit();

                WebElement recordResultBoxInit = totalBox.findElement(By.className("record_result"));
                WebElement pageInfo = recordResultBoxInit.findElement(By.className("paging"));
                WebElement page1Button = pageInfo.findElement(By.id("cphContents_cphContents_cphContents_ucPager_btnNo1"));
                page1Button.click();
                pageInit();

                recordResultBoxInit = totalBox.findElement(By.className("record_result"));
                pageInfo = recordResultBoxInit.findElement(By.className("paging"));

                List<WebElement> elements = pageInfo.findElements(By.cssSelector("[id^='cphContents_cphContents_cphContents_ucPager_btnNo']"));
                int page_count = elements.size();
                int current_page = 1;

                if(page_count == 0) {
                    log.info("[KBOCrawler.getKboBatterData] " + team + " data not exist");
                    continue;
                }

                addHitterData(recordResultBoxInit, athletes);
                current_page++;

                while (current_page <= page_count) {
                    // 현재 페이지로 이동
                    WebElement button = pageInfo.findElement(By.id("cphContents_cphContents_cphContents_ucPager_btnNo" + current_page));
                    button.click();
                    pageInit();

                    // 데이터 가져오기
                    WebElement recordResultBox = totalBox.findElement(By.className("record_result"));
                    addHitterData(recordResultBox, athletes);
                    current_page++;
                }
            }
            return athletes;

        } catch (Exception e) {
            log.error("[KBOCrawler.getKboBatterData] failed.", e);
            throw new CrawlingException(CrawlingErrorResult.CRAWLING_FAILED);
        }
    }

    private List<Athlete> getKboPitcherData() {
        try {
            // 투수 선택
            WebElement subContainer = driver.findElement(By.className("sub-content"));
            WebElement tab2 = subContainer.findElement(By.className("tab-depth2"));
            WebElement tab = tab2.findElement(By.className("tab"));
            WebElement pitcherOn = tab.findElement(By.className("on"));
            pitcherOn.click();

            List<Athlete> athletes = new ArrayList<>();
            // 팀 선택해서 팀별 데이터 순회
            for(int i=1; i<=10; i++) {
                // 팀 선택
                pageInit();
                WebElement teamSelect = totalBox.findElement(By.id("cphContents_cphContents_cphContents_ddlTeam_ddlTeam"));

                teamSelect.click();
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"cphContents_cphContents_cphContents_udpContent\"]")));
                List<WebElement> teams = teamSelect.findElements(By.tagName("option"));
                WebElement teamButton = teams.get(i);
                String team = teamButton.getText();

                teamButton.click();
                pageInit();

                WebElement recordResultBoxInit = totalBox.findElement(By.className("record_result"));
                WebElement pageInfo = recordResultBoxInit.findElement(By.className("paging"));
                WebElement page1Button = pageInfo.findElement(By.id("cphContents_cphContents_cphContents_ucPager_btnNo1"));
                page1Button.click();
                pageInit();

                recordResultBoxInit = totalBox.findElement(By.className("record_result"));
                pageInfo = recordResultBoxInit.findElement(By.className("paging"));

                List<WebElement> elements = pageInfo.findElements(By.cssSelector("[id^='cphContents_cphContents_cphContents_ucPager_btnNo']"));
                int page_count = elements.size();
                int current_page = 1;

                if(page_count == 0) {
                    log.info("[KBOCrawler.getKboPitcherData] " + team + " data not exist");
                    continue;
                }

                addPitcherData(recordResultBoxInit, athletes);
                current_page++;

                while (current_page <= page_count) {
                    // 현재 페이지로 이동
                    WebElement button = pageInfo.findElement(By.id("cphContents_cphContents_cphContents_ucPager_btnNo" + current_page));
                    button.click();
                    pageInit();

                    // 데이터 가져오기
                    WebElement recordResultBox = totalBox.findElement(By.className("record_result"));
                    addHitterData(recordResultBox, athletes);
                    current_page++;
                }
            }
            return athletes;

        } catch (Exception e) {
            log.error("[KBOCrawler.getKboBatterData] failed.", e);
            throw new CrawlingException(CrawlingErrorResult.CRAWLING_FAILED);
        }
    }

    private void pageInit() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#cphContents_cphContents_cphContents_udpContent")));
        totalBox = driver.findElement(By.id("cphContents_cphContents_cphContents_udpContent"));
        driver.executeScript("window.scrollTo(0,0)");
    }


    private void addHitterData(WebElement recordResultBox, List<Athlete> athletes) throws InterruptedException {
        WebElement table = recordResultBox.findElement(By.className("tData01"));

        int startIdx = athletes.size();
        List<WebElement> rows = table.findElements(By.tagName("tr"));

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
                                idGenerator.nextId(), playerName, Team.valueOfName(teamName), AthleteType.HITTER,
                                hitAvg, rbi, hitCount, homeRunCount
                        )
                );
            }
        }
//        updateHitterOps(totalBox, athletes, startIdx);
    }

    private void addPitcherData(WebElement recordResultBox, List<Athlete> athletes) throws InterruptedException {
        WebElement table = recordResultBox.findElement(By.className("tData01"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));

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
                                idGenerator.nextId(), playerName, Team.valueOfName(teamName), AthleteType.PITCHER,
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
