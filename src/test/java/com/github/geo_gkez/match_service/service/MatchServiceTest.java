package com.github.geo_gkez.match_service.service;

import com.github.geo_gkez.match_service.dto.MatchDto;
import com.github.geo_gkez.match_service.entity.Match;
import com.github.geo_gkez.match_service.mapper.MatchMapper;
import com.github.geo_gkez.match_service.repository.MatchOddRepository;
import com.github.geo_gkez.match_service.repository.MatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {
    @Mock
    private MatchRepository matchRepository;
    @Mock
    private MatchOddRepository matchOddRepository;
    private final MatchMapper matchMapper = MatchMapper.INSTANCE;

    private MatchService matchService;

    @BeforeEach
    void setUp() {
        matchService = new MatchService(matchRepository, matchOddRepository, matchMapper);
    }

    @Test
    void findMatchById_shouldReturnMatchDto_whenMatchDtoExists() {
        //given
        Long matchId = 1L;

        Match match = Match.builder()
                .id(matchId)
                .description("Champions League Final")
                .matchDate(LocalDate.of(2025, 8, 15))
                .matchTime(LocalTime.of(20, 45))
                .teamA("Real Madrid")
                .teamB("Manchester City")
                .build();

        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));

        //when
        MatchDto actualMatchDto = matchService.findMatchDtoById(matchId);

        //then
        assertThat(actualMatchDto).isNotNull();
        assertThat(actualMatchDto.id()).isEqualTo(matchId);
        assertThat(actualMatchDto.description()).isEqualTo("Champions League Final");
        assertThat(actualMatchDto.matchDate()).isEqualTo(LocalDate.of(2025, 8, 15));
        assertThat(actualMatchDto.matchTime()).isEqualTo(LocalTime.of(20, 45));
        assertThat(actualMatchDto.teamA()).isEqualTo("Real Madrid");
        assertThat(actualMatchDto.teamB()).isEqualTo("Manchester City");
    }
}