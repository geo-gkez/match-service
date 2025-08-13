package com.github.geo_gkez.match_service.controller;

import com.github.geo_gkez.match_service.dto.MatchCreateOrUpdateRequest;
import com.github.geo_gkez.match_service.dto.MatchDto;
import com.github.geo_gkez.match_service.service.MatchService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.github.geo_gkez.match_service.constant.UrlPathConstants.API;
import static com.github.geo_gkez.match_service.constant.UrlPathConstants.V1_MATCHES;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(MatchController.class)
class MatchControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MatchService matchService;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void givenMatchExists_whenGetMatchById_thenReturnMatch() {
        // Arrange
        Long matchId = 500L;
        MatchDto expected = new MatchDto(
                10L,
                "Team A - Team B",
                LocalDate.of(2025, 2, 10),
                LocalTime.of(18, 30),
                "Team A",
                "Team B",
                1);
        when(matchService.findMatchDtoById(matchId))
                .thenReturn(
                        expected);

        // Act & Assert
        RestAssuredMockMvc.given()
                .pathParam("matchId", matchId)
                .when()
                .get(API + V1_MATCHES + "/{matchId}")
                .then()
                .statusCode(200)
                .body("id", equalTo(expected.id().intValue()))
                .body("description", equalTo(expected.description()))
                .body("matchDate", equalTo("10/02/2025"))
                .body("matchTime", equalTo("18:30"))
                .body("teamA", equalTo(expected.teamA()))
                .body("teamB", equalTo(expected.teamB()))
                .body("sport", equalTo(expected.sport()));
    }

    @Test
    void givenValidMatchCreateRequest_whenCreateMatch_thenReturnCreatedWithLocation() {
        // Arrange
        String jsonBody = """
                {
                    "description": "Real Madrid - Barcelona",
                    "matchDate": "15/03/2025",
                    "matchTime": "20:00",
                    "teamA": "Real Madrid",
                    "teamB": "Barcelona",
                    "sport": 1
                }
                """;
        Long createdMatchId = 123L;

        when(matchService.saveMatch(any(MatchCreateOrUpdateRequest.class))).thenReturn(createdMatchId);

        // Act & Assert
        RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(jsonBody)
                .when()
                .post(API + V1_MATCHES)
                .then()
                .statusCode(201)
                .header("Location", equalTo(API + V1_MATCHES + "/" + createdMatchId));
    }

    @Test
    void givenInvalidMatchCreateRequest_whenCreateMatch_thenReturnBadRequest() {
        // Arrange
        String jsonBody = """
                {
                    "description": "",
                    "matchDate": "15/03/2025",
                    "matchTime": "20:00",
                    "teamA": "",
                    "sport": 5
                }
                """;

        // Act & Assert
        RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(jsonBody)
                .when()
                .post(API + V1_MATCHES)
                .then()
                .statusCode(400)
                .body("title", equalTo("Bad Request"))
                .body("status", equalTo(400))
                .body("detail", equalTo("Validation failed for one or more arguments"))
                .body("constrainViolations.teamA", equalTo("must not be blank"))
                .body("constrainViolations.teamB", equalTo("must not be blank"))
                .body("constrainViolations.sport", equalTo("Sport must have a valid code {1=Football, 2=Basketball}"));
    }
}