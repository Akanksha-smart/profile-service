package com.sam.profilecreation_service.service;

import com.sam.profilecreation_service.dto.CoachDTO;
import com.sam.profilecreation_service.dto.PlayerDTO;
import com.sam.profilecreation_service.dto.TeamDTO;
import com.sam.profilecreation_service.entity.TeamEntity;

import java.util.List;

public interface TeamService {
    TeamDTO convertToTeamDTO(TeamEntity teamEntity);
    TeamEntity convertToTeamEntity(TeamDTO teamDTO);
    TeamDTO createTeam(TeamDTO teamDTO);

    TeamDTO getTeamById(Long id);
    List<TeamDTO> getAllTeams();

    TeamDTO updateTeam(TeamDTO team , Long id);

    void deleteTeam(Long id);
    void addPlayerToTeam(Long teamId, PlayerDTO playerDTO);
    void addCoachToTeam(Long teamId, CoachDTO coachDTO);

    void validateTeamDTO(TeamDTO teamDTO);
}
