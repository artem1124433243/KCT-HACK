package com.hackathon.KDT_HACK.TeamManagement.controller;

import com.hackathon.KDT_HACK.TeamManagement.dto.RegisterTeamRequest;
import com.hackathon.KDT_HACK.TeamManagement.dto.TeamRegistrationDto;
import com.hackathon.KDT_HACK.TeamManagement.service.TeamRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hackathon-registrations")
public class TeamRegistrationController {

    private final TeamRegistrationService registrationService;

    @PostMapping
    public TeamRegistrationDto register(@Valid @RequestBody RegisterTeamRequest request) {
        return TeamRegistrationDto.fromEntity(registrationService.registerTeam(request));
    }

    @GetMapping("/team/{teamId}")
    public List<TeamRegistrationDto> getByTeam(@PathVariable String teamId) {
        return registrationService.getByTeam(teamId).stream()
                .map(TeamRegistrationDto::fromEntity)
                .toList();
    }

    @GetMapping("/hackathon/{hackathonId}")
    public List<TeamRegistrationDto> getByHackathon(@PathVariable String hackathonId) {
        return registrationService.getByHackathon(hackathonId).stream()
                .map(TeamRegistrationDto::fromEntity)
                .toList();
    }
}
