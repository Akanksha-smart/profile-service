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
    private int totalPoints;
    private byte[] logo;
    private String icon;



//    public TeamDTO(Long id, Integer coachId ,String name, String country, String teamCaptain, String owner, String icon, int totalPoints, CoachDTO coach, List<PlayerDTO> players) {
//        this.id = id;
//        this.coachId = coachId;
//        this.name = name;
//        this.country = country;
//        this.teamCaptain = teamCaptain;
//        this.owner = owner;
//        this.icon = icon;
//        this.totalPoints = totalPoints;
//
//    }

//    public Integer getCoachId() {
//        return coachId;
//    }
//
//    public void setCoachId(Integer coachId) {
//        this.coachId = coachId;
//    }

}
