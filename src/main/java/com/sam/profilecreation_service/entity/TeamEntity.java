package com.sam.profilecreation_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "team")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String name;

    @Column(unique = true)
    private  String country;
    private String teamCaptain;

    @Column(name = "coach_id")
    private Long coachId;


    private String coachName;
    private String owner;

    //

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PlayerEntity> players;// total 15 player 5 bowlers, 5 batsmen, 5 all-rounder this is simple


    private int totalPoints;

    private String logo; // For storing the team's logo this is for the landing page of the teams
    // according to the requirement we will use
    private String icon; // For storing a text or URL for the team's icon  this for the icon on the matches


}
