package com.github.geo_gkez.match_service.integration_test;

import com.github.geo_gkez.match_service.entity.Match;
import com.github.geo_gkez.match_service.entity.MatchOdd;
import com.github.geo_gkez.match_service.entity.enums.SportEnum;
import com.github.geo_gkez.match_service.repository.MatchOddRepository;
import com.github.geo_gkez.match_service.repository.MatchRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.github.geo_gkez.match_service.constant.UrlPathConstants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

class MatchOddIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private MatchOddRepository matchOddRepository;

    @Test
    void givenMatchOddCreateRequest_whenSaveMatchOdd_thenReturnCreatedMatchOdd() {
        Match save = matchRepository.save(
                Match.builder()
                        .description("PAOK-Barcelona")
                        .matchDate(LocalDate.of(2026, 3, 20))
                        .matchTime(LocalTime.of(22, 25))
                        .teamA("PAOK")
                        .teamB("Barcelona")
                        .sport(SportEnum.FOOTBALL)
                        .build()
        );

        Long matchId = save.getId();

        given(requestSpecification)
                .body("""
                        {
                            "specifier": "X",
                            "odd": 1.5
                        }
                        """)
                .when()
                .post(API + V1_MATCHES + "/" + matchId + ODDS)
                .then()
                .statusCode(201)
                .header("Location", matchesPattern(API + V1_MATCHES + "/" + matchId + ODDS + "/\\d+"));
    }

    @Test
    void givenMatchId_whenGetAllMatchOdds_thenReturnPageMatchOddResponse() {
        Match match = matchRepository.save(
                Match.builder()
                        .description("AEK-Barcelona")
                        .matchDate(LocalDate.of(2026, 4, 20))
                        .matchTime(LocalTime.of(22, 25))
                        .teamA("AEK")
                        .teamB("Barcelona")
                        .sport(SportEnum.FOOTBALL)
                        .build()
        );

        Long matchId = match.getId();
        MatchOdd matchOdd = matchOddRepository.save(
                MatchOdd.builder()
                        .specifier("1")
                        .odd(2.0)
                        .match(match)
                        .build()
        );

        given(requestSpecification)
                .when()
                .get(API + V1_MATCHES + "/" + matchId + ODDS)
                .then()
                .statusCode(200)
                .body("matchOddsDtos", Matchers.hasSize(1))
                .body("isLast", Matchers.is(true))
                .body("matchOddsDtos[0].id", Matchers.equalTo(matchOdd.getId().intValue()))
                .body("matchOddsDtos[0].specifier", Matchers.equalTo("1"));
    }

    @Test
    void givenMatchOdd_whenUpdateMatchOdd_thenReturnUpdatedMatchOdd() {
        Match match = matchRepository.save(
                Match.builder()
                        .description("Olympiakos-Barcelona")
                        .matchDate(LocalDate.of(2026, 10, 20))
                        .matchTime(LocalTime.of(22, 35))
                        .teamA("Olympiakos")
                        .teamB("Barcelona")
                        .sport(SportEnum.FOOTBALL)
                        .build()
        );

        Long matchId = match.getId();
        MatchOdd matchOdd = matchOddRepository.save(
                MatchOdd.builder()
                        .specifier("2")
                        .odd(3.0)
                        .match(match)
                        .build()
        );

        given(requestSpecification)
                .body("""
                        {
                            "specifier": "X",
                            "odd": 1.8
                        }
                        """)
                .when()
                .put(API + V1_MATCHES + "/" + matchId + ODDS + "/" + matchOdd.getId())
                .then()
                .statusCode(204)
                .header("Location", equalTo(API + V1_MATCHES + "/" + matchId + ODDS + "/" + matchOdd.getId()));

        given(requestSpecification)
                .when()
                .get(API + V1_MATCHES + "/" + matchId + ODDS + "/" + matchOdd.getId())
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(matchOdd.getId().intValue()))
                .body("specifier", Matchers.equalTo("X"))
                .body("odd", Matchers.equalTo(1.8f));
    }
}
