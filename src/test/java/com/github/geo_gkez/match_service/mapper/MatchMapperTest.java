package com.github.geo_gkez.match_service.mapper;

import com.github.geo_gkez.match_service.dto.MatchCreateOrUpdateRequest;
import com.github.geo_gkez.match_service.dto.MatchDto;
import com.github.geo_gkez.match_service.entity.Match;
import com.github.geo_gkez.match_service.entity.enums.SportEnum;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class MatchMapperTest {
    @Test
    void givenMatch_whenMappingToMatchDto_thenCorrectMatchDtoReturned() {
        //given
        Match match = Match.builder()
                .id(1L)
                .description("Champions League Final")
                .matchDate(LocalDate.of(2025, 5, 28))
                .matchTime(LocalTime.of(20, 45))
                .teamA("Real Madrid")
                .teamB("Bayern Munich")
                .sport(SportEnum.FOOTBALL)
                .build();

        //when
        MatchDto matchDto = MatchMapper.INSTANCE.matchToMatchDto(match);

        //then
        assertThat(matchDto).isNotNull();
        assertThat(matchDto.id()).isEqualTo(1L);
        assertThat(matchDto.description()).isEqualTo("Champions League Final");
        assertThat(matchDto.matchDate()).isEqualTo(LocalDate.of(2025, 5, 28));
        assertThat(matchDto.matchTime()).isEqualTo(LocalTime.of(20, 45));
        assertThat(matchDto.teamA()).isEqualTo("Real Madrid");
        assertThat(matchDto.teamB()).isEqualTo("Bayern Munich");
        assertThat(matchDto.sport()).isEqualTo(SportEnum.FOOTBALL.getCode());
    }

    @Test
    void givenMatchDto_whenMappingToMatch_thenCorrectMatchReturned() {
        //given
        MatchDto matchDto = new MatchDto(
                1L,
                "EuroLeague Final",
                LocalDate.of(2025, 5, 28),
                LocalTime.of(20, 45),
                "Real Madrid",
                "Fenerbahce",
                SportEnum.BASKETBALL.getCode()
        );

        //when
        Match match = MatchMapper.INSTANCE.matchDtoToMatch(matchDto);

        //then
        assertThat(match).isNotNull();
        assertThat(match.getId()).isEqualTo(1L);
        assertThat(match.getDescription()).isEqualTo("EuroLeague Final");
        assertThat(match.getMatchDate()).isEqualTo(LocalDate.of(2025, 5, 28));
        assertThat(match.getMatchTime()).isEqualTo(LocalTime.of(20, 45));
        assertThat(match.getTeamA()).isEqualTo("Real Madrid");
        assertThat(match.getTeamB()).isEqualTo("Fenerbahce");
        assertThat(match.getSport()).isEqualTo(SportEnum.BASKETBALL);
    }

    @Test
    void givenMatchDto_whenMappingToMatchWithUnsupportedSportCode_thenThrowsException() {
        //given
        MatchDto matchDto = new MatchDto(
                1L,
                "Champions League Final",
                LocalDate.of(2025, 5, 28),
                LocalTime.of(20, 45),
                "Real Madrid",
                "Bayern Munich",
                0
        );

        //when
        ThrowableAssert.ThrowingCallable throwingCallable = () -> MatchMapper.INSTANCE.matchDtoToMatch(matchDto);

        //then
        assertThat(catchThrowable(throwingCallable))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown sport code: 0");
    }

    @Test
    void givenMatchCreateRequest_whenMappingToMatch_thenCorrectMatchReturned() {
        // given
        MatchCreateOrUpdateRequest matchCreateOrUpdateRequest = new MatchCreateOrUpdateRequest(
                LocalDate.of(2025, 5, 28),
                LocalTime.of(20, 45),
                "Real Madrid",
                "Bayern Munich",
                SportEnum.FOOTBALL.getCode()
        );

        // when
        Match match = MatchMapper.INSTANCE.matchCreateOrUpdateRequestToMatch(matchCreateOrUpdateRequest);

        // then
        assertThat(match).isNotNull();
        assertThat(match.getId()).isNull();
        assertThat(match.getDescription()).isEqualTo("Real Madrid - Bayern Munich");
        assertThat(match.getMatchDate()).isEqualTo(LocalDate.of(2025, 5, 28));
        assertThat(match.getMatchTime()).isEqualTo(LocalTime.of(20, 45));
        assertThat(match.getTeamA()).isEqualTo("Real Madrid");
        assertThat(match.getTeamB()).isEqualTo("Bayern Munich");
        assertThat(match.getSport()).isEqualTo(SportEnum.FOOTBALL);
    }

    @Test
    void givenMatchCreateRequest_whenMappingToMatchWithUnsupportedSportCode_thenThrowsException() {
        // given
        MatchCreateOrUpdateRequest matchCreateOrUpdateRequest = new MatchCreateOrUpdateRequest(
                "Champions League Final",
                LocalDate.of(2025, 5, 28),
                LocalTime.of(20, 45),
                "Real Madrid",
                "Bayern Munich",
                0 // Invalid sport code
        );

        // when
        ThrowableAssert.ThrowingCallable throwingCallable = () -> MatchMapper.INSTANCE.matchCreateOrUpdateRequestToMatch(matchCreateOrUpdateRequest);

        // then
        assertThat(catchThrowable(throwingCallable))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown sport code: 0");
    }

    @ParameterizedTest
    @MethodSource("provideSportCodes")
    void givenMatchUpdateRequest_whenMappingToMatch_thenCorrectMatchReturned(Integer sportCode, SportEnum sportEnum) {
        // given
        MatchCreateOrUpdateRequest matchCreateOrUpdateRequest = new MatchCreateOrUpdateRequest(
                LocalDate.of(2025, 6, 28),
                LocalTime.of(21, 50),
                null,
                null,
                sportCode
        );
        Match existingMatch = Match.builder()
                .id(1L)
                .description("Real Madrid - Bayern Munich")
                .matchDate(LocalDate.of(2025, 5, 28))
                .matchTime(LocalTime.of(20, 45))
                .teamA("Real Madrid")
                .teamB("Bayern Munich")
                .sport(sportEnum)
                .build();

        // when
        Match updatedMatch = MatchMapper.INSTANCE.updateMatchFromMatchCreateOrUpdateRequest(matchCreateOrUpdateRequest, existingMatch);

        // then
        assertThat(updatedMatch).isNotNull();
        assertThat(updatedMatch.getId()).isEqualTo(1L);
        assertThat(updatedMatch.getDescription()).isEqualTo("Real Madrid - Bayern Munich");
        assertThat(updatedMatch.getMatchDate()).isEqualTo(LocalDate.of(2025, 6, 28));
        assertThat(updatedMatch.getMatchTime()).isEqualTo(LocalTime.of(21, 50));
        assertThat(updatedMatch.getTeamA()).isEqualTo("Real Madrid");
        assertThat(updatedMatch.getTeamB()).isEqualTo("Bayern Munich");
        assertThat(updatedMatch.getSport()).isEqualTo(SportEnum.fromValue(sportCode));
    }

    private static Stream<Arguments> provideSportCodes() {
        return Stream.of(
                Arguments.of(1, SportEnum.BASKETBALL),
                Arguments.of(2, SportEnum.FOOTBALL)
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidSportCodes")
    void givenMatchUpdateRequestWithInvalidSport_whenMappingToMatch_thenThrowsException(Integer invalidSportCode) {
        // given
        MatchCreateOrUpdateRequest matchCreateOrUpdateRequest = new MatchCreateOrUpdateRequest(
                LocalDate.of(2025, 6, 28),
                LocalTime.of(21, 50),
                null,
                null,
                invalidSportCode
        );
        Match existingMatch = Match.builder()
                .id(1L)
                .description("Real Madrid - Bayern Munich")
                .matchDate(LocalDate.of(2025, 5, 28))
                .matchTime(LocalTime.of(20, 45))
                .teamA("Real Madrid")
                .teamB("Bayern Munich")
                .sport(SportEnum.FOOTBALL)
                .build();

        // when
        ThrowableAssert.ThrowingCallable throwingCallable = () ->
                MatchMapper.INSTANCE.updateMatchFromMatchCreateOrUpdateRequest(matchCreateOrUpdateRequest, existingMatch);

        // then
        assertThat(catchThrowable(throwingCallable))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown sport code: " + invalidSportCode);
    }

    private static Stream<Arguments> provideInvalidSportCodes() {
        return Stream.of(
                Arguments.of(0),
                Arguments.of(99),
                Arguments.of(-1)
        );
    }

}