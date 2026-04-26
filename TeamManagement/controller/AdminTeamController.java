package com.hackathon.KDT_HACK.TeamManagement.controller;

import com.hackathon.KDT_HACK.TeamManagement.dto.TeamDto;
import com.hackathon.KDT_HACK.TeamManagement.dto.UpdateTeamRequest;
import com.hackathon.KDT_HACK.TeamManagement.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/teams")
@PreAuthorize("hasRole('ADMIN')")
public class AdminTeamController {

    private final TeamService teamService;

    @GetMapping
    public List<TeamDto> getAll() {
        return teamService.getAll().stream()
                .map(TeamDto::fromEntity)
                .toList();
    }

    @GetMapping("/{teamId}")
    public TeamDto getById(@PathVariable String teamId) {
        return TeamDto.fromEntity(teamService.getById(teamId));
    }

    @PutMapping("/{teamId}")
    public TeamDto update(@PathVariable String teamId, @RequestBody UpdateTeamRequest request) {
        return TeamDto.fromEntity(teamService.updateTeam(teamId, request));
    }

    @DeleteMapping("/{teamId}")
    public Map<String, String> delete(@PathVariable String teamId) {
        teamService.deleteTeam(teamId);
        return Map.of("message", "Команда удалена");
    }

    @GetMapping("/search")
    public List<TeamDto> search(@RequestParam String query) {
        return teamService.search(query).stream()
                .map(TeamDto::fromEntity)
                .toList();
    }

    @GetMapping("/active")
    public List<TeamDto> active() {
        return teamService.getActive().stream()
                .map(TeamDto::fromEntity)
                .toList();
    }

    @GetMapping("/user/{userId}")
    public List<TeamDto> getUserTeams(@PathVariable String userId) {
        return teamService.getUserTeams(userId).stream()
                .map(TeamDto::fromEntity)
                .toList();
    }
}
