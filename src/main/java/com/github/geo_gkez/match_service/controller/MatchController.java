package com.github.geo_gkez.match_service.controller;

import com.github.geo_gkez.match_service.dto.MatchCreateOrUpdateRequest;
import com.github.geo_gkez.match_service.dto.MatchDto;
import com.github.geo_gkez.match_service.service.MatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.github.geo_gkez.match_service.constant.UrlPathConstants.API;
import static com.github.geo_gkez.match_service.constant.UrlPathConstants.V1_MATCHES;

@RestController
@RequestMapping(API)
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @GetMapping(V1_MATCHES + "/{matchId}")
    public ResponseEntity<MatchDto> getMatch(@PathVariable Long matchId) {
        MatchDto match = matchService.findMatchDtoById(matchId);

        return ResponseEntity.ok(match);
    }

    @PostMapping(V1_MATCHES)
    public ResponseEntity<Void> createMatch(@Valid @RequestBody MatchCreateOrUpdateRequest matchCreateOrUpdateRequest) {
        var matchId = matchService.saveMatch(matchCreateOrUpdateRequest);

        return ResponseEntity.created(createLocationUri(matchId)).build();
    }

    @PutMapping(V1_MATCHES + "/{matchId}")
    public ResponseEntity<Void> updateMatch(@PathVariable Long matchId, @RequestBody MatchCreateOrUpdateRequest matchCreateOrUpdateRequest) {
        matchService.updateMatch(matchId, matchCreateOrUpdateRequest);

        return ResponseEntity.noContent().location(createLocationUri(matchId)).build();
    }

    private static URI createLocationUri(Long matchId) {
        return URI.create(API + V1_MATCHES + "/" + matchId);
    }

    @DeleteMapping(V1_MATCHES + "/{matchId}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long matchId) {
        matchService.deleteMatch(matchId);

        return ResponseEntity.noContent().build();
    }
}
