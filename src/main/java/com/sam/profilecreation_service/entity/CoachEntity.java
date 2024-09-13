package com.sam.profilecreation_service.entity;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Table(name = "coach")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoachEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coachId;

    @Column(nullable = false, unique = true)
    private String name;
    private String country;
    private String gender;
    private LocalDate dateOfBirth;
    private String profilePicture;
    private String specialization;
    private String username;

    @Enumerated(EnumType.STRING)
    private ERole role;
    private String email;

    @Column(name = "team_id")
    private Long teamId;
}
