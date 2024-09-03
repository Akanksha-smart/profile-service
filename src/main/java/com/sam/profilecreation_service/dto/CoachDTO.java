package com.sam.profilecreation_service.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CoachDTO {

    private Long coachId;
    private String coachName;
    private String country;

    @Column(name = "team_id")
    @JoinColumn(name = "teamId")
    private Long teamId;
}
