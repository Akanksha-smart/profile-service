package com.sam.profilecreation_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sam.profilecreation_service.entity.CoachEntity;
import com.sam.profilecreation_service.entity.ERole;
import com.sam.profilecreation_service.service.CoachService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CaochController.class)
public class CoachControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CoachService coachService;

    @Autowired
    private ObjectMapper objectMapper;

    private CoachEntity coachEntity;

    @BeforeEach
    public void setup() {
        coachEntity = new CoachEntity();
        coachEntity.setCoachId(1L);
        coachEntity.setName("John Doe");
        coachEntity.setCountry("India");
        coachEntity.setGender("Male");
        coachEntity.setDateOfBirth(LocalDate.of(1980, 1, 1));
        coachEntity.setProfilePicture("profile.jpg");
        coachEntity.setSpecialization("Batting");
        coachEntity.setUsername("johndoe");
        coachEntity.setRole(ERole.COACH);
        coachEntity.setEmail("john.doe@example.com");
        coachEntity.setTeamId(10L);
    }

    @Test
    public void testCreateCoach() throws Exception {
        when(coachService.createCoach(any(CoachEntity.class))).thenReturn(coachEntity);

        mockMvc.perform(post("/api/coaches/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coachEntity)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.coachId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.country").value("India"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("Male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth").value("1980-01-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.profilePicture").value("profile.jpg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.specialization").value("Batting"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("johndoe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("COACH"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.teamId").value(10));
    }

    @Test
    public void testFindCoachIdByCoachName() throws Exception {
        when(coachService.findCoachByName(anyString())).thenReturn(1L);

        mockMvc.perform(get("/api/coaches/John%20Doe"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("1"));
    }

    @Test
    public void testFindCoachIdByCoachName_NotFound() throws Exception {
        when(coachService.findCoachByName(anyString())).thenThrow(new EntityNotFoundException("Coach not found"));

        mockMvc.perform(get("/api/coaches/Unknown%20Coach"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testFindAllCoaches() throws Exception {
        when(coachService.findAllCoaches()).thenReturn(Collections.singletonList(coachEntity));

        mockMvc.perform(get("/api/coaches"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].coachId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].country").value("India"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("Male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dateOfBirth").value("1980-01-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].profilePicture").value("profile.jpg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].specialization").value("Batting"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("johndoe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].role").value("COACH"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("john.doe@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].teamId").value(10));
    }

    @Test
    public void testUpdateCoach() throws Exception {
        when(coachService.updateCoach(any(CoachEntity.class), anyLong())).thenReturn(coachEntity);

        mockMvc.perform(put("/api/coaches/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coachEntity)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.coachId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.country").value("India"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("Male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth").value("1980-01-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.profilePicture").value("profile.jpg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.specialization").value("Batting"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("johndoe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("COACH"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.teamId").value(10));
    }

    @Test
    public void testUpdateCoach_NotFound() throws Exception {
        when(coachService.updateCoach(any(CoachEntity.class), anyLong())).thenThrow(new EntityNotFoundException("Coach not found"));

        mockMvc.perform(put("/api/coaches/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coachEntity)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteCoach() throws Exception {
        Mockito.doNothing().when(coachService).deleteCoach(anyLong());

        mockMvc.perform(delete("/api/coaches/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testDeleteCoach_NotFound() throws Exception {
        Mockito.doThrow(new EntityNotFoundException("Coach not found")).when(coachService).deleteCoach(anyLong());

        mockMvc.perform(delete("/api/coaches/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
