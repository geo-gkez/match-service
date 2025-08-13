package com.github.geo_gkez.match_service.mapper;

import com.github.geo_gkez.match_service.dto.MatchCreateOrUpdateRequest;
import com.github.geo_gkez.match_service.dto.MatchDto;
import com.github.geo_gkez.match_service.entity.Match;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

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
}
