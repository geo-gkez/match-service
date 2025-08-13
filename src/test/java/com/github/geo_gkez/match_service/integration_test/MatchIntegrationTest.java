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
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

class MatchIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private MatchOddRepository matchOddRepository;

    @Test
    void givenMatchId_whenGetMatch_thenReturnMatch() {
        Match save = matchRepository.save(
                Match.builder()
                        .description("Real Madrid-Barcelona")
                        .matchDate(LocalDate.of(2025, 3, 20))
                        .matchTime(LocalTime.of(18, 25))
                        .teamA("Real Madrid")
                        .teamB("Barcelona")
                        .sport(SportEnum.BASKETBALL)
                        .build()
        );

        assertThat(save).isNotNull();

        Long matchId = save.getId();

        given(requestSpecification)
                .when()
                .get(API + V1_MATCHES + "/" + matchId)
                .then()
                .statusCode(200)
                .body("id", equalTo(matchId.intValue()))
                .body("description", equalTo("Real Madrid-Barcelona"))
                .body("matchDate", equalTo("20/03/2025"))
                .body("matchTime", equalTo("18:25"))
                .body("teamA", equalTo("Real Madrid"))
                .body("teamB", equalTo("Barcelona"))
                .body("sport", equalTo(SportEnum.BASKETBALL.getCode()));
    }

    @Test
    void givenMatchCreateRequest_whenSaveMatch_thenReturnCreatedMatch() {
        given(requestSpecification)
                .body("""
                            {
                              "matchDate": "15/03/2025",
                              "matchTime": "20:01",
                              "teamA": "Ajax",
                              "teamB": "PSV",
                              "sport": 1
                            }
                        """)
                .when()
                .post(API + V1_MATCHES)
                .then()
                .statusCode(201)
                .header("Location", matchesPattern(API + V1_MATCHES + "/\\d+"));
    }

    @Test
    void givenMatchUpdateRequest_whenUpdateMatch_thenReturnUpdatedMatch() {
        Match save = matchRepository.save(
                Match.builder()
                        .description("Arsenal-Chelsea")
                        .matchDate(LocalDate.of(2025, 4, 10))
                        .matchTime(LocalTime.of(19, 30))
                        .teamA("Arsenal")
                        .teamB("Chelsea")
                        .sport(SportEnum.FOOTBALL)
                        .build()
        );

        assertThat(save).isNotNull();

        Long matchId = save.getId();

        given(requestSpecification)
                .body("""
                            {
                              "matchDate": "20/06/2025",
                              "matchTime": "21:00"
                            }
                        """)
                .when()
                .put(API + V1_MATCHES + "/" + matchId)
                .then()
                .statusCode(204);

        given(requestSpecification)
                .when()
                .get(API + V1_MATCHES + "/" + matchId)
                .then()
                .statusCode(200)
                .body("id", equalTo(matchId.intValue()))
                .body("description", equalTo("Arsenal-Chelsea"))
                .body("matchDate", equalTo("20/06/2025"))
                .body("matchTime", equalTo("21:00"))
                .body("teamA", equalTo("Arsenal"))
                .body("teamB", equalTo("Chelsea"))
                .body("sport", equalTo(SportEnum.FOOTBALL.getCode()));
    }

    @Test
    void givenMatchId_whenDeleteMatch_thenReturnNoContent() {
        Match save = matchRepository.save(
                Match.builder()
                        .description("Liverpool-Manchester City")
                        .matchDate(LocalDate.of(2025, 5, 15))
                        .matchTime(LocalTime.of(16, 45))
                        .teamA("Liverpool")
                        .teamB("Manchester City")
                        .sport(SportEnum.FOOTBALL)
                        .build()
        );

        assertThat(save).isNotNull();

        Long matchId = save.getId();

        given(requestSpecification)
                .when()
                .delete(API + V1_MATCHES + "/" + matchId)
                .then()
                .statusCode(204);

        given(requestSpecification)
                .when()
                .get(API + V1_MATCHES + "/" + matchId)
                .then()
                .statusCode(404);
    }

    @Test
    void givenMatchCreateRequest_whenSaveMatchAlreadyExists_thenReturnProblemDetails() {
        Match save = matchRepository.save(
                Match.builder()
                        .description("Liverpool-Manchester City")
                        .matchDate(LocalDate.of(2025, 5, 15))
                        .matchTime(LocalTime.of(16, 45))
                        .teamA("Liverpool")
                        .teamB("Manchester City")
                        .sport(SportEnum.FOOTBALL)
                        .build()
        );

        assertThat(save).isNotNull();

        given(requestSpecification)
                .body("""
                            {
                              "matchDate": "15/05/2025",
                              "matchTime": "16:45",
                              "teamA": "Liverpool",
                              "teamB": "Manchester City",
                              "sport": 1
                            }
                        """)
                .when()
                .post(API + V1_MATCHES)
                .then()
                .statusCode(409)
                .body("title", equalTo("Data Integrity Violation"))
                .body("detail", equalTo("Match with the same date, time, teams and sport already exists"));
    }

    @Test
    void givenMatch_whenDeleteMatch_thenReturnDeletedMatchOdd() {
        Match match = matchRepository.save(
                Match.builder()
                        .description("Aris-Barcelona")
                        .matchDate(LocalDate.of(2026, 11, 20))
                        .matchTime(LocalTime.of(22, 45))
                        .teamA("Aris")
                        .teamB("Barcelona")
                        .sport(SportEnum.FOOTBALL)
                        .build()
        );

        Long matchId = match.getId();

        matchOddRepository.save(
                MatchOdd.builder()
                        .specifier("1")
                        .odd(2.5)
                        .match(match)
                        .build()
        );

        given(requestSpecification)
                .when()
                .delete(API + V1_MATCHES + "/" + matchId)
                .then()
                .statusCode(204);

        given(requestSpecification)
                .when()
                .get(API + V1_MATCHES + "/" + matchId + ODDS)
                .then()
                .statusCode(200)
                .body("matchOddsDtos", Matchers.nullValue())
                .body("isLast", Matchers.is(true));

    }
}
