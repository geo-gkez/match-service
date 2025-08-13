package com.github.geo_gkez.match_service.controller;

import com.github.geo_gkez.match_service.dto.MatchOddCreateOrUpdateRequest;
import com.github.geo_gkez.match_service.dto.MatchOddDto;
import com.github.geo_gkez.match_service.dto.PageMatchOddResponse;
import com.github.geo_gkez.match_service.service.MatchOddService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.github.geo_gkez.match_service.constant.UrlPathConstants.*;

@RestController
@RequestMapping(API)
@RequiredArgsConstructor
public class MatchOddsController {
    private final MatchOddService matchOddService;

    @GetMapping(V1_MATCHES + "/{matchId}" + ODDS)
    public ResponseEntity<PageMatchOddResponse> getMatch(@PathVariable Long matchId,
                                                         @RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "10") int size) {
        PageMatchOddResponse pageMatchOddResponse = matchOddService.findAllMatchOddsByMatchId(matchId, page, size);

        return ResponseEntity.ok(pageMatchOddResponse);
    }

    @GetMapping(V1_MATCHES + "/{matchId}" + ODDS + "/{matchOddId}")
    public ResponseEntity<MatchOddDto> getMatchOdd(@PathVariable Long matchId, @PathVariable Long matchOddId) {
        MatchOddDto matchOddDto = matchOddService.findMatchOdd(matchId, matchOddId);

        return ResponseEntity.ok(matchOddDto);
    }

    @PostMapping(V1_MATCHES + "/{matchId}" + ODDS)
    public ResponseEntity<Void> createMatch(@PathVariable Long matchId, @Valid @RequestBody MatchOddCreateOrUpdateRequest matchOddCreateOrUpdateRequest) {
        var matchOddId = matchOddService.saveMatchOddToMatch(matchId, matchOddCreateOrUpdateRequest);

        return ResponseEntity.created(createLocationUri(matchId, matchOddId)).build();
    }

    @PutMapping(V1_MATCHES + "/{matchId}" + ODDS + "/{matchOddId}")
    public ResponseEntity<Void> updateMatchOdd(@PathVariable Long matchId, @PathVariable Long matchOddId, @Valid @RequestBody MatchOddCreateOrUpdateRequest matchOddCreateOrUpdateRequest) {
        matchOddService.updateMatchOdd(matchId, matchOddId, matchOddCreateOrUpdateRequest);

        return ResponseEntity.noContent().location(createLocationUri(matchId, matchOddId)).build();
    }

    private static URI createLocationUri(Long matchId, Long matchOddId) {
        return URI.create(API + V1_MATCHES + "/" + matchId + ODDS + "/" + matchOddId);
    }

    @DeleteMapping(V1_MATCHES + "/{matchId}" + ODDS + "/{matchOddId}")
    public ResponseEntity<Void> deleteMatchOdd(@PathVariable Long matchId, @PathVariable Long matchOddId) {
        matchOddService.deleteMatchOdd(matchId, matchOddId);

        return ResponseEntity.noContent().build();
    }
}
