package com.sam.profilecreation_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "team")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;
    private  String name;

    @Column(unique = true)
    private  String country;
    private String teamCaptain;

    @Column(name = "coach_id")
    private Long coachId;


    private String coachName;
    private String owner;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PlayerEntity> players;// total 15 player 5 bowlers, 5 batsmen, 5 all-rounder this is simple


    private int totalPoints;

    private String logo;
    private String icon;


    public TeamEntity(String name, String country, String teamCaptain, String coachName, String owner, String icon, Long coachId, int totalPoints) {
        this.name = name;
        this.country = country;
        this.teamCaptain = teamCaptain;
        this.coachId = coachId;
        this.coachName = coachName;
        this.owner = owner;
        this.icon = icon;
        this.totalPoints = totalPoints;
    }
}
