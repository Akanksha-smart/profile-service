package com.sam.profilecreation_service.repository;

import com.sam.profilecreation_service.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {
    List<PlayerEntity> findByCountry(String country);
    Optional<PlayerEntity> findById(Long playerId);

    List<PlayerEntity> findByTeamidIsNull();

}
