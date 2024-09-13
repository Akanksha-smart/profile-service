package com.sam.profilecreation_service.service;


import com.sam.profilecreation_service.dto.TeamRegisterDTO;
import com.sam.profilecreation_service.entity.TeamEntity;
import jakarta.transaction.Transactional;

import java.util.List;

public interface TeamService {

    @Transactional
    TeamEntity createTeam(TeamRegisterDTO teamRegisterDTO) throws Exception;

    TeamEntity getTeamById(Long id);

    List<TeamEntity> getAllTeams();

    TeamEntity updateTeam(TeamEntity teamEntity, Long id);

    void deleteTeam(Long id);

    void addPlayerToTeam(Long teamId, Long playerId);

    Integer getTeamsByCoachName(String name);


}
