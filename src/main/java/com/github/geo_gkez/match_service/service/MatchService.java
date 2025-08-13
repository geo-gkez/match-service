package com.github.geo_gkez.match_service.service;

import com.github.geo_gkez.match_service.dto.MatchCreateOrUpdateRequest;
import com.github.geo_gkez.match_service.dto.MatchDto;
import com.github.geo_gkez.match_service.entity.Match;
import com.github.geo_gkez.match_service.exception.CRUDOperationsException;
import com.github.geo_gkez.match_service.mapper.MatchMapper;
import com.github.geo_gkez.match_service.repository.MatchOddRepository;
import com.github.geo_gkez.match_service.repository.MatchRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.github.geo_gkez.match_service.exception.CRUDOpertionsEnum.*;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;
    private final MatchOddRepository matchOddRepository;
    private final MatchMapper matchMapper;

    public MatchDto findMatchDtoById(Long matchId) {
        Match match = findMatchById(matchId);

        return matchMapper.matchToMatchDto(match);
    }

    private Match findMatchById(Long matchId) {
        return matchRepository.findById(matchId)
                .orElseThrow(() -> new CRUDOperationsException(READ, matchId, 404));
    }

    public Long saveMatch(MatchCreateOrUpdateRequest matchCreateOrUpdateRequest) {
        Match match = matchMapper.matchCreateOrUpdateRequestToMatch(matchCreateOrUpdateRequest);
        Match save = saveMatch(match);

        return save.getId();
    }

    private Match saveMatch(Match match) {
        try {
            return matchRepository.save(match);
        } catch (Exception e) {
            throw new CRUDOperationsException(CREATE, e, 500);
        }
    }

    public void updateMatch(Long matchId, MatchCreateOrUpdateRequest matchCreateOrUpdateRequest) {
        Match match = findMatchById(matchId);
        Match updatedMatch = matchMapper.updateMatchFromMatchCreateOrUpdateRequest(matchCreateOrUpdateRequest, match);

        saveMatch(updatedMatch);
    }

    @Transactional
    public void deleteMatch(Long matchId) {
        try {
            matchOddRepository.deleteAllByMatch_Id(matchId);
            matchRepository.deleteById(matchId);
        } catch (Exception e) {
            throw new CRUDOperationsException(DELETE, matchId, 500);
        }
    }
}
