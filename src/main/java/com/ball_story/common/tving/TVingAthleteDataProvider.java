package com.ball_story.common.tving;

import com.ball_story.common.enums.Team;
import com.ball_story.common.tving.constant.TVingConstant;
import com.ball_story.home.athlete.entity.Athlete;
import com.ball_story.home.athlete.enums.AthleteType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TVingAthleteDataProvider {
    private RestClient restClient;

    @PostConstruct
    void initRestClient() {
        restClient = RestClient.create(TVingConstant.athleteDataUrl);
    }

    public List<Athlete> getTVingHitterData() {
        try {
            String response = restClient.get()
                    .uri("/v2/kbo/history/athlete?screenCode=CSSD0100&osCode=CSOD0900&tab=athlete&yearSeason=2025&gameSeason=0&athleteType=hitter")
                    .retrieve()
                    .body(String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            if(!root.path("message").asText("-").equals("Success")) {
                throw new Exception();
            }

            JsonNode bands = root.path("data").path("bands");
            if(!bands.isArray() || bands.size()< 2 || !bands.get(1).path("bandName").asText().equals("전체 선수")) {
                throw new Exception();
            }

            List<Athlete> athletes = new ArrayList<>();

            for(JsonNode athlete : bands.get(1).path("items")) {
                Long code = athlete.path("code").asLong();
                String name = athlete.path("name").asText();
                Team team = Team.valueOfName(athlete.path("teamName").asText());
                Double hitAvg = athlete.path("battingAverage").asDouble();
                Double ops = athlete.path("ops").asDouble();
                Double wrcPlus = athlete.path("wrcPlus").asDouble();
                Double war = athlete.path("war").asDouble();

                athletes.add(Athlete.createHitter(
                        code, name, team, AthleteType.HITTER, hitAvg, ops, wrcPlus, war
                ));
            }
            return athletes;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
