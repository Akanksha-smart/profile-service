package com.sam.profilecreation_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamRegisterDTO {

    private String name;
    private String country;
    private String teamCaptain;
    private String coachName;
    private Long coachId;
    private String owner;
    private String icon;  // Base64 encoded icon data
    private int totalPoints;
    private List<PlayerDTO> players = new ArrayList<>();
}
