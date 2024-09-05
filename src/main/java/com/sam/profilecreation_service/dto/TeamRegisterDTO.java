package com.sam.profilecreation_service.dto;

import com.sam.profilecreation_service.entity.PlayerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamRegisterDTO {

    private String name;
    private String country;
    private String teamCaptain;
    private String coach;
    private String owner;
    private String icon;  // Base64 encoded icon data
    private int totalPoints;
    private List<PlayerDTO> players = new ArrayList<>();
}
