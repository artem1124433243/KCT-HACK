package com.hackathon.KDT_HACK.TeamManagement.controller;

import com.hackathon.KDT_HACK.TeamManagement.dto.CreateTeamRequest;
import com.hackathon.KDT_HACK.TeamManagement.dto.TeamDto;
import com.hackathon.KDT_HACK.TeamManagement.dto.TeamMemberDto;
import com.hackathon.KDT_HACK.TeamManagement.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public TeamDto create(@Valid @RequestBody CreateTeamRequest request) {
        return TeamDto.fromEntity(teamService.createTeam(request));
    }

    @GetMapping("/{teamId}")
    public TeamDto getById(@PathVariable String teamId) {
        return TeamDto.fromEntity(teamService.getById(teamId));
    }

    @GetMapping("/user/{userId}")
    public List<TeamDto> getUserTeams(@PathVariable String userId) {
        return teamService.getUserTeams(userId).stream()
                .map(TeamDto::fromEntity)
                .toList();
    }

    @GetMapping("/{teamId}/members")
    public List<TeamMemberDto> getMembers(@PathVariable String teamId) {
        return teamService.getMembers(teamId);
    }

    @GetMapping("/{teamId}/is-creator/{userId}")
    public Map<String, Boolean> isCreator(@PathVariable String teamId, @PathVariable String userId) {
        return Map.of("isCreator", teamService.isCreator(userId, teamId));
    }

    @GetMapping("/{teamId}/is-member/{userId}")
    public Map<String, Boolean> isMember(@PathVariable String teamId, @PathVariable String userId) {
        return Map.of("isMember", teamService.isMember(teamId, userId));
    }
}
