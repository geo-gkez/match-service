package com.github.geo_gkez.match_service.mapper;

import com.github.geo_gkez.match_service.dto.MatchCreateOrUpdateRequest;
import com.github.geo_gkez.match_service.dto.MatchDto;
import com.github.geo_gkez.match_service.dto.PageMatchResponse;
import com.github.geo_gkez.match_service.entity.Match;
import com.github.geo_gkez.match_service.entity.enums.SportEnum;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface MatchMapper {
    MatchMapper INSTANCE = Mappers.getMapper(MatchMapper.class);

    @Mapping(source = "sport.code", target = "sport")
    MatchDto matchToMatchDto(Match match);

    @Mapping(target = "sport", expression = "java(SportEnum.fromValue(matchDto.sport()))")
    Match matchDtoToMatch(MatchDto matchDto);

    @Mapping(target = "sport", expression = "java(SportEnum.fromValue(matchCreateOrUpdateRequest.sport()))")
    @Mapping(ignore = true, target = "id")
    Match matchCreateOrUpdateRequestToMatch(MatchCreateOrUpdateRequest matchCreateOrUpdateRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(ignore = true, target = "id")
    Match updateMatchFromMatchCreateOrUpdateRequest(MatchCreateOrUpdateRequest matchCreateOrUpdateRequest, @MappingTarget Match match);

    default SportEnum mapSport(Integer sport) {
        return sport != null ? SportEnum.fromValue(sport) : null;
    }

    @Mapping(target = "matchDtos", source = "matches.content")
    @Mapping(target = "isLast", expression = "java(matches.isLast())")
    PageMatchResponse matchesToPageMatchResponse(Page<Match> matches);
}
