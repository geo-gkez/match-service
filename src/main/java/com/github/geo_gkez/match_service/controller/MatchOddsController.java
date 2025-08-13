package com.github.geo_gkez.match_service.controller;

import com.github.geo_gkez.match_service.dto.MatchOddCreateOrUpdateRequest;
import com.github.geo_gkez.match_service.dto.MatchOddDto;
import com.github.geo_gkez.match_service.dto.PageMatchOddResponse;
import com.github.geo_gkez.match_service.service.MatchOddService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.github.geo_gkez.match_service.constant.UrlPathConstants.*;

@RestController
@RequestMapping(API)
@RequiredArgsConstructor
@Tag(name = "Match Odds Management", description = "Operations for managing odds for sports matches")
public class MatchOddsController {
    private final MatchOddService matchOddService;

    @GetMapping(V1_MATCHES + "/{matchId}" + ODDS)
    @Operation(summary = "Get all odds for a match", description = "Retrieve paginated list of all odds for a specific match")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Odds retrieved successfully",
                    content = @Content(schema = @Schema(implementation = PageMatchOddResponse.class))),
            @ApiResponse(responseCode = "404", description = "Match not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid parameters",
                    content = @Content)
    })
    public ResponseEntity<PageMatchOddResponse> getMatch(
            @Parameter(description = "Unique identifier of the match", required = true, example = "1")
            @PathVariable Long matchId,
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(value = "size", defaultValue = "10") int size) {
        PageMatchOddResponse pageMatchOddResponse = matchOddService.findAllMatchOddsByMatchId(matchId, page, size);

        return ResponseEntity.ok(pageMatchOddResponse);
    }

    @GetMapping(V1_MATCHES + "/{matchId}" + ODDS + "/{matchOddId}")
    @Operation(summary = "Get specific match odd", description = "Retrieve a specific odd for a match by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Match odd found successfully",
                    content = @Content(schema = @Schema(implementation = MatchOddDto.class))),
            @ApiResponse(responseCode = "404", description = "Match or match odd not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid match ID or match odd ID format",
                    content = @Content)
    })
    public ResponseEntity<MatchOddDto> getMatchOdd(
            @Parameter(description = "Unique identifier of the match", required = true, example = "1")
            @PathVariable Long matchId,
            @Parameter(description = "Unique identifier of the match odd", required = true, example = "1")
            @PathVariable Long matchOddId) {
        MatchOddDto matchOddDto = matchOddService.findMatchOdd(matchId, matchOddId);

        return ResponseEntity.ok(matchOddDto);
    }

    @PostMapping(V1_MATCHES + "/{matchId}" + ODDS)
    @Operation(summary = "Create a new match odd", description = "Create a new odd for a specific match")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Match odd created successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Match not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid match odd data provided",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Validation error in match odd data",
                    content = @Content)
    })
    public ResponseEntity<Void> createMatch(
            @Parameter(description = "Unique identifier of the match", required = true, example = "1")
            @PathVariable Long matchId,
            @Parameter(description = "Match odd details for creation", required = true)
            @Valid @RequestBody MatchOddCreateOrUpdateRequest matchOddCreateOrUpdateRequest) {
        var matchOddId = matchOddService.saveMatchOddToMatch(matchId, matchOddCreateOrUpdateRequest);

        return ResponseEntity.created(createLocationUri(matchId, matchOddId)).build();
    }

    @PutMapping(V1_MATCHES + "/{matchId}" + ODDS + "/{matchOddId}")
    @Operation(summary = "Update an existing match odd", description = "Update the details of an existing match odd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Match odd updated successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Match or match odd not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid match odd data provided",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Validation error in match odd data",
                    content = @Content)
    })
    public ResponseEntity<Void> updateMatchOdd(
            @Parameter(description = "Unique identifier of the match", required = true, example = "1")
            @PathVariable Long matchId,
            @Parameter(description = "Unique identifier of the match odd to update", required = true, example = "1")
            @PathVariable Long matchOddId,
            @Parameter(description = "Updated match odd details", required = true)
            @Valid @RequestBody MatchOddCreateOrUpdateRequest matchOddCreateOrUpdateRequest) {
        matchOddService.updateMatchOdd(matchId, matchOddId, matchOddCreateOrUpdateRequest);

        return ResponseEntity.noContent().location(createLocationUri(matchId, matchOddId)).build();
    }

    private static URI createLocationUri(Long matchId, Long matchOddId) {
        return URI.create(API + V1_MATCHES + "/" + matchId + ODDS + "/" + matchOddId);
    }

    @DeleteMapping(V1_MATCHES + "/{matchId}" + ODDS + "/{matchOddId}")
    @Operation(summary = "Delete a match odd", description = "Delete an existing match odd by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Match odd deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Match or match odd not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid match ID or match odd ID format",
                    content = @Content)
    })
    public ResponseEntity<Void> deleteMatchOdd(
            @Parameter(description = "Unique identifier of the match", required = true, example = "1")
            @PathVariable Long matchId,
            @Parameter(description = "Unique identifier of the match odd to delete", required = true, example = "1")
            @PathVariable Long matchOddId) {
        matchOddService.deleteMatchOdd(matchId, matchOddId);

        return ResponseEntity.noContent().build();
    }
}
