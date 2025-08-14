package com.github.geo_gkez.match_service.service;

import com.github.geo_gkez.match_service.dto.MatchOddCreateOrUpdateRequest;
import com.github.geo_gkez.match_service.dto.MatchOddDto;
import com.github.geo_gkez.match_service.dto.PageMatchOddResponse;
import com.github.geo_gkez.match_service.entity.Match;
import com.github.geo_gkez.match_service.entity.MatchOdd;
import com.github.geo_gkez.match_service.exception.CRUDOperationsException;
import com.github.geo_gkez.match_service.exception.CRUDOpertionsEnum;
import com.github.geo_gkez.match_service.mapper.MatchOddMapper;
import com.github.geo_gkez.match_service.repository.MatchOddRepository;
import com.github.geo_gkez.match_service.repository.MatchRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchOddService {
    private final MatchOddRepository matchOddRepository;
    private final MatchRepository matchRepository;
    private final MatchOddMapper matchOddMapper;

    public Long saveMatchOddToMatch(Long matchId, MatchOddCreateOrUpdateRequest matchOddCreateOrUpdateRequest) {
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new CRUDOperationsException(CRUDOpertionsEnum.READ, matchId, 404));

        MatchOdd matchOdd = matchOddMapper.matchOddCreateOrUpdateRequestToMatchOdd(matchOddCreateOrUpdateRequest);
        matchOdd.setMatch(match);

        return saveMatchOdd(matchOdd).getId();
    }

    private MatchOdd saveMatchOdd(MatchOdd matchOdd) {
        try {
            return matchOddRepository.save(matchOdd);
        } catch (Exception e) {
            throw new CRUDOperationsException(CRUDOpertionsEnum.CREATE, matchOdd.getMatch().getId(), matchOdd.getId(), 500);
        }
    }

    public MatchOddDto findMatchOdd(Long matchId, Long matchOddId) {
        MatchOdd matchOdd = findMatchOddByMatchIdAndMatchOddId(matchId, matchOddId);

        return matchOddMapper.matchOddToMatchOddDto(matchOdd);
    }

    private MatchOdd findMatchOddByMatchIdAndMatchOddId(Long matchId, Long matchOddId) {
        return matchOddRepository.findById(matchOddId)
                .orElseThrow(() -> new CRUDOperationsException(CRUDOpertionsEnum.READ, matchId, matchOddId, 404));
    }

    public PageMatchOddResponse findAllMatchOddsByMatchId(Long matchId, int page, int size) {
        Page<MatchOdd> allByMatchId = matchOddRepository.findAllByMatch_Id(matchId, PageRequest.of(page, size));

        return matchOddMapper.matchOddsToPageMatchOddResponse(allByMatchId);
    }

    public void updateMatchOdd(Long matchId, Long matchOddId, @Valid MatchOddCreateOrUpdateRequest matchOddCreateOrUpdateRequest) {
        MatchOdd matchOdd = findMatchOddByMatchIdAndMatchOddId(matchId, matchOddId);

        try {
            MatchOdd updatedMatchOdd = matchOddMapper.updateMatchOddFromMatchOddCreateOrUpdateRequest(matchOddCreateOrUpdateRequest, matchOdd);
            saveMatchOdd(updatedMatchOdd);
        } catch (Exception e) {
            throw new CRUDOperationsException(CRUDOpertionsEnum.UPDATE, matchId, matchOddId, 500);
        }
    }

    @Transactional
    public void deleteMatchOdd(Long matchId, Long matchOddId) {
        try {
            matchOddRepository.deleteById(matchOddId);
        } catch (Exception e) {
            throw new CRUDOperationsException(CRUDOpertionsEnum.DELETE, matchId, matchOddId, 500);
        }
    }
}
