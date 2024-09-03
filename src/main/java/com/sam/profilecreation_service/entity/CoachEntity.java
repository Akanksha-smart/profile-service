package com.sam.profilecreation_service.entity;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "coach")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CoachEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coachId;
    private String coachName;
    private String country;

    @Column(name = "team_id")
    private Long teamId;


}
