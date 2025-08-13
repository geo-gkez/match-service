package com.github.geo_gkez.match_service.mapper;

import com.github.geo_gkez.match_service.dto.PageMatchOddResponse;
import com.github.geo_gkez.match_service.entity.Match;
import com.github.geo_gkez.match_service.entity.MatchOdd;
import com.github.geo_gkez.match_service.entity.enums.SportEnum;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MatchOddMapperTest {
    @Test
    void matchOddsPageToPageMatchOddResponse() {
        // given
        Match match = Match.builder()
                .id(1L)
                .description("Champions League")
                .matchDate(LocalDate.now())
                .matchTime(LocalTime.of(20, 30))
                .teamA("Team A")
                .teamB("Team B")
                .sport(SportEnum.FOOTBALL)
                .build();

        MatchOdd matchOdd1 = MatchOdd.builder()
                .id(1L)
                .specifier("1")
                .odd(1.5)
                .match(match)
                .build();

        MatchOdd matchOdd2 = MatchOdd.builder()
                .id(2L)
                .specifier("X")
                .odd(3.2)
                .match(match)
                .build();

        List<MatchOdd> matchOddList = List.of(matchOdd1, matchOdd2);
        Page<MatchOdd> matchOddsPage = new PageImpl<>(matchOddList, PageRequest.of(0, 10), matchOddList.size());

        // when
        PageMatchOddResponse result = MatchOddMapper.INSTANCE.matchOddsToPageMatchOddResponse(matchOddsPage);

        // then
        assertNotNull(result);
        assertEquals(2, result.matchOddsDtos().size());
        assertEquals(matchOddsPage.getTotalElements(), result.totalElements());
        assertEquals(matchOddsPage.getTotalPages(), result.totalPages());
        assertEquals(matchOddsPage.isLast(), result.isLast());

        assertEquals(matchOdd1.getId(), result.matchOddsDtos().get(0).id());
        assertEquals(matchOdd1.getSpecifier(), result.matchOddsDtos().get(0).specifier());
        assertEquals(matchOdd1.getOdd(), result.matchOddsDtos().get(0).odd());
        assertEquals(match.getId(), result.matchOddsDtos().get(0).matchId());
        assertEquals(matchOdd2.getId(), result.matchOddsDtos().get(1).id());
        assertEquals(matchOdd2.getSpecifier(), result.matchOddsDtos().get(1).specifier());
        assertEquals(matchOdd2.getOdd(), result.matchOddsDtos().get(1).odd());
        assertEquals(match.getId(), result.matchOddsDtos().get(1).matchId());

    }
}
