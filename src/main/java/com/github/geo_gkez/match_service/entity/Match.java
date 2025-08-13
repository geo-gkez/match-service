package com.github.geo_gkez.match_service.entity;

import com.github.geo_gkez.match_service.converter.SportEnumConverter;
import com.github.geo_gkez.match_service.entity.enums.SportEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.github.geo_gkez.match_service.constant.MatchServiceConstants.MATCH_UNIQUE_CONSTRAIN;

@Entity
@Table(name = "matches",
        uniqueConstraints = @UniqueConstraint(
                name = MATCH_UNIQUE_CONSTRAIN,
                columnNames = {"match_date", "match_time", "team_a", "team_b", "sport"}
        )
)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "match_date")
    private LocalDate matchDate;

    @Column(name = "match_time")
    private LocalTime matchTime;

    @Column(name = "team_a")
    private String teamA;

    @Column(name = "team_b")
    private String teamB;

    @Column(name = "sport")
    @Convert(converter = SportEnumConverter.class)
    private SportEnum sport;
}
