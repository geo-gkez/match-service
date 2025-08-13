package com.github.geo_gkez.match_service.controller;

import com.github.geo_gkez.match_service.dto.MatchCreateOrUpdateRequest;
import com.github.geo_gkez.match_service.dto.MatchDto;
import com.github.geo_gkez.match_service.service.MatchService;
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

import static com.github.geo_gkez.match_service.constant.UrlPathConstants.API;
import static com.github.geo_gkez.match_service.constant.UrlPathConstants.V1_MATCHES;

@RestController
@RequestMapping(API)
@RequiredArgsConstructor
@Tag(name = "Match Management", description = "Operations for managing sports matches")
public class MatchController {
    private final MatchService matchService;

    @GetMapping(V1_MATCHES + "/{matchId}")
    @Operation(summary = "Get match by ID", description = "Retrieve a specific match by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Match found successfully",
                    content = @Content(schema = @Schema(implementation = MatchDto.class))),
            @ApiResponse(responseCode = "404", description = "Match not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid match ID format",
                    content = @Content)
    })
    public ResponseEntity<MatchDto> getMatch(
            @Parameter(description = "Unique identifier of the match", required = true, example = "1")
            @PathVariable Long matchId) {
        MatchDto match = matchService.findMatchDtoById(matchId);

        return ResponseEntity.ok(match);
    }

    @PostMapping(V1_MATCHES)
    @Operation(summary = "Create a new match", description = "Create a new sports match with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Match created successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid match data provided",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Validation error in match data",
                    content = @Content)
    })
    public ResponseEntity<Void> createMatch(
            @Parameter(description = "Match details for creation", required = true)
            @Valid @RequestBody MatchCreateOrUpdateRequest matchCreateOrUpdateRequest) {
        var matchId = matchService.saveMatch(matchCreateOrUpdateRequest);

        return ResponseEntity.created(createLocationUri(matchId)).build();
    }

    @PutMapping(V1_MATCHES + "/{matchId}")
    @Operation(summary = "Update an existing match", description = "Update the details of an existing match")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Match updated successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Match not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid match data provided",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Validation error in match data",
                    content = @Content)
    })
    public ResponseEntity<Void> updateMatch(
            @Parameter(description = "Unique identifier of the match to update", required = true, example = "1")
            @PathVariable Long matchId,
            @Parameter(description = "Updated match details", required = true)
            @RequestBody MatchCreateOrUpdateRequest matchCreateOrUpdateRequest) {
        matchService.updateMatch(matchId, matchCreateOrUpdateRequest);

        return ResponseEntity.noContent().location(createLocationUri(matchId)).build();
    }

    private static URI createLocationUri(Long matchId) {
        return URI.create(API + V1_MATCHES + "/" + matchId);
    }

    @DeleteMapping(V1_MATCHES + "/{matchId}")
    @Operation(summary = "Delete a match", description = "Delete an existing match by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Match deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Match not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid match ID format",
                    content = @Content)
    })
    public ResponseEntity<Void> deleteMatch(
            @Parameter(description = "Unique identifier of the match to delete", required = true, example = "1")
            @PathVariable Long matchId) {
        matchService.deleteMatch(matchId);

        return ResponseEntity.noContent().build();
    }
}
