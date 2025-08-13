package com.github.geo_gkez.match_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "match_odds")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchOdd {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "specifier")
    private String specifier;

    @Column(name = "odd")
    private Double odd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_match_id"))
    private Match match;
}
