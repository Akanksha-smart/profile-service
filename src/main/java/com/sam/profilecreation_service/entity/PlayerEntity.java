package com.sam.profilecreation_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sam.profilecreation_service.dto.PlayerDTO;
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
    private LocalDate dateOfBirth;
    private String specialization;
    private String gender;
    private String country;
    private String profilePicture;
    private Long teamid;
    private boolean Playing;
    private boolean Overseas;
    private boolean Backup;


}
