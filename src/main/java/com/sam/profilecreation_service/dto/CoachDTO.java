package com.sam.profilecreation_service.dto;

import com.sam.profilecreation_service.entity.CoachEntity;
import com.sam.profilecreation_service.entity.TeamEntity;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.List;

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
    private Long teamId;
}
