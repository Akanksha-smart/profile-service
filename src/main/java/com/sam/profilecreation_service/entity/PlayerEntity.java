package com.sam.profilecreation_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "player")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlayerEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String username;
    private LocalDate dateOfBirth;
    private String specialization;
    private String gender;
    private String country;
    private String profilePicture;
    private ERole role;
    private Long teamid;
    @Column(name = "is_playing")
    private boolean Playing;
    @Column(name = "is_overseas")
    private boolean Overseas;
    @Column(name = "is_backup")
    private boolean is_backup;

}
