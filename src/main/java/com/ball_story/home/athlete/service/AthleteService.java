package com.ball_story.home.athlete.service;

import com.ball_story.common.crawlling.KBOCrawler;
import com.ball_story.common.utils.SnowflakeIDGenerator;
import com.ball_story.home.athlete.entity.Athlete;
import com.ball_story.home.athlete.entity.NameSakeAthlete;
import com.ball_story.home.athlete.repository.AthleteRepository;
import com.ball_story.home.athlete.repository.NameSakeAthleteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AthleteService {
    private final AthleteRepository athleteRepository;
    private final NameSakeAthleteRepository nameSakeAthleteRepository;
    private final SnowflakeIDGenerator idGenerator;
    private final KBOCrawler kboCrawler;

    public void initAthleteData() throws InterruptedException {
        List<Athlete> athletes = kboCrawler.getKboAthleteData();

        for(Athlete athlete : athletes) {
            List<Athlete> origins = athleteRepository.findByTeamAndName(athlete.getTeam().toString(), athlete.getName(), athlete.getType().toString());
            int cnt = origins.size();

            // 새롭게 선수 데이터 삽입 중, 중복데이터는 동명이인이므로 전부 동명이인 테이블에 저장
            if(cnt > 0) {
                athlete.setCode(idGenerator.nextId());
                nameSakeAthleteRepository.insert(
                        NameSakeAthlete.from(athlete)
                );
            }
            else {
                athlete.setCode(idGenerator.nextId());
                athleteRepository.insert(athlete);
            }
        }
    }

    public void updateAthleteData() throws InterruptedException {
        List<Athlete> athletes = kboCrawler.getKboAthleteData();

        for(Athlete athlete : athletes) {
            List<Athlete> origins = athleteRepository.findByTeamAndName(athlete.getTeam().toString(), athlete.getName(), athlete.getType().toString());
            int cnt = origins.size();

            // 둘 이상이면 동명이인 테이블에 담기
            if(cnt > 1) {
                athlete.setCode(idGenerator.nextId());
                nameSakeAthleteRepository.insert(
                        NameSakeAthlete.from(athlete)
                );
            }
            // 1개이면 해당 데이터 업데이트
            else if(cnt > 0) {
                Athlete origin = origins.get(0);
                origin.update(athlete);
                athleteRepository.updateById(origin);
            }
            // 없으면 새로 삽입
            else {
                athlete.setCode(idGenerator.nextId());
                athleteRepository.insert(athlete);
            }
        }
    }
}
