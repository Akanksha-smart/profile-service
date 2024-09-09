package com.sam.profilecreation_service.service;


import com.sam.profilecreation_service.entity.CoachEntity;
import com.sam.profilecreation_service.repository.CoachRepository;
import com.sam.profilecreation_service.service.impl.CoachServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class CoachServiceTest {

    @Mock
    private CoachRepository coachRepository;

    @InjectMocks
    private CoachServiceImpl coachService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCoach_Success() {
        CoachEntity coach = new CoachEntity();
        coach.setCoachId(1L);
        coach.setName("John Doe");
        coach.setCountry("USA");
        coach.setTeamId(100L);

        when(coachRepository.save(coach)).thenReturn(coach);

        CoachEntity createdCoach = coachService.createCoach(coach);

        assertNotNull(createdCoach);
        assertEquals("John Doe", createdCoach.getName());
        assertEquals("USA", createdCoach.getCountry());
        assertEquals(100L, createdCoach.getTeamId());
        verify(coachRepository, times(1)).save(coach);
    }

    @Test
    void testFindCoachById_Success() {
        CoachEntity coach = new CoachEntity();
        coach.setCoachId(1L);
        coach.setName("John Doe");

        when(coachRepository.findById(1L)).thenReturn(Optional.of(coach));

        CoachEntity foundCoach = coachService.findCoachById(1L);

        assertNotNull(foundCoach);
        assertEquals(1L, foundCoach.getCoachId());
        assertEquals("John Doe", foundCoach.getName());
        verify(coachRepository, times(1)).findById(1L);
    }

    @Test
    void testFindCoachById_NotFound() {
        when(coachRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class,
                () -> coachService.findCoachById(1L));

        assertEquals("Coach not found with id: 1", thrown.getMessage());
        verify(coachRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAllCoaches_Success() {
        List<CoachEntity> coaches = new ArrayList<>();
        CoachEntity coach1 = new CoachEntity();
        coach1.setCoachId(1L);
        coach1.setName("John Doe");

        CoachEntity coach2 = new CoachEntity();
        coach2.setCoachId(2L);
        coach2.setName("Jane Smith");

        coaches.add(coach1);
        coaches.add(coach2);

        when(coachRepository.findAll()).thenReturn(coaches);

        List<CoachEntity> allCoaches = coachService.findAllCoaches();

        assertNotNull(allCoaches);
        assertEquals(2, allCoaches.size());
        verify(coachRepository, times(1)).findAll();
    }

    @Test
    void testUpdateCoach_Success() {
        CoachEntity existingCoach = new CoachEntity();
        existingCoach.setCoachId(1L);
        existingCoach.setName("Old Name");
        existingCoach.setCountry("USA");

        CoachEntity updatedData = new CoachEntity();
        updatedData.setCountry("UK");
        updatedData.setTeamId(200L);

        when(coachRepository.findById(1L)).thenReturn(Optional.of(existingCoach));
        when(coachRepository.save(any(CoachEntity.class))).thenReturn(existingCoach);

        CoachEntity updatedCoach = coachService.updateCoach(updatedData, 1L);

        assertNotNull(updatedCoach);
        assertEquals("UK", updatedCoach.getCountry());
        assertEquals(200L, updatedCoach.getTeamId());
        verify(coachRepository, times(1)).findById(1L);
        verify(coachRepository, times(1)).save(existingCoach);
    }

    @Test
    void testUpdateCoach_NotFound() {
        CoachEntity updatedData = new CoachEntity();
        updatedData.setCountry("UK");
        updatedData.setTeamId(200L);

        when(coachRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class,
                () -> coachService.updateCoach(updatedData, 1L));

        assertEquals("Coach not found with id: 1", thrown.getMessage());
        verify(coachRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteCoach_Success() {
        CoachEntity coach = new CoachEntity();
        coach.setCoachId(1L);
        coach.setName("John Doe");

        when(coachRepository.findById(1L)).thenReturn(Optional.of(coach));

        coachService.deleteCoach(1L);

        verify(coachRepository, times(1)).delete(coach);
    }

    @Test
    void testDeleteCoach_NotFound() {
        when(coachRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class,
                () -> coachService.deleteCoach(1L));

        assertEquals("Coach not found with id: 1", thrown.getMessage());
        verify(coachRepository, times(1)).findById(1L);
    }

}
