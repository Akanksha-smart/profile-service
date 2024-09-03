package com.sam.profilecreation_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sam.profilecreation_service.entity.PlayerEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {

    private Long id;
    private  String name;
    private  String country;
    private String teamCaptain;

    @OneToOne(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Long coachId;

    private String owner;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Long> playerIds = new ArrayList<>();
    private List<Long> playingPlayerIds;
    private int totalPoints;
    private byte[] logo;
    private String icon;
}
