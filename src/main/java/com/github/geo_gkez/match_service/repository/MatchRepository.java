package com.github.geo_gkez.match_service.repository;

import com.github.geo_gkez.match_service.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
}
