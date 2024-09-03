package com.sam.profilecreation_service.dto;

import com.sam.profilecreation_service.entity.PlayerEntity;
import com.sam.profilecreation_service.entity.TeamEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {

    private Long id;

    private String name;
    private LocalDate dateOfBirth;
    private String specialization;
    private String gender;
    private String country;

    private Long teamId;

    private byte[] profilePicture;

    private static boolean overseas;
    private boolean playing;





}
