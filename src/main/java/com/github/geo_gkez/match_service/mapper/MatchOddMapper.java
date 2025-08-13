package com.github.geo_gkez.match_service.mapper;

import com.github.geo_gkez.match_service.dto.MatchOddCreateOrUpdateRequest;
import com.github.geo_gkez.match_service.dto.MatchOddDto;
import com.github.geo_gkez.match_service.dto.PageMatchOddResponse;
import com.github.geo_gkez.match_service.entity.MatchOdd;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface MatchOddMapper {
    MatchOddMapper INSTANCE = Mappers.getMapper(MatchOddMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "match", ignore = true)
    MatchOdd matchOddCreateOrUpdateRequestToMatchOdd(MatchOddCreateOrUpdateRequest matchOddCreateOrUpdateRequest);

    @Mapping(target = "matchId", source = "match.id")
    MatchOddDto matchOddToMatchOddDto(MatchOdd matchOdd);

    @Mapping(target = "matchOddsDtos", source = "matchOdds.content")
    @Mapping(target = "isLast", expression = "java(matchOdds.isLast())")
    PageMatchOddResponse matchOddsToPageMatchOddResponse(Page<MatchOdd> matchOdds);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    MatchOdd updateMatchOddFromMatchOddCreateOrUpdateRequest(MatchOddCreateOrUpdateRequest matchOddCreateOrUpdateRequest, @MappingTarget MatchOdd matchOdd);
}
