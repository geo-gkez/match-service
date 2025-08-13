package com.github.geo_gkez.match_service.repository;

import com.github.geo_gkez.match_service.entity.MatchOdd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchOddRepository extends JpaRepository<MatchOdd, Long> {
    Page<MatchOdd> findAllByMatch_Id(Long matchId, Pageable pageable);

    void deleteAllByMatch_Id(Long matchId);
}
