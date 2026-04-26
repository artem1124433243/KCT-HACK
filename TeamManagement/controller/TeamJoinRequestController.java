package com.hackathon.KDT_HACK.TeamManagement.controller;

import com.hackathon.KDT_HACK.TeamManagement.dto.CreateJoinRequestRequest;
import com.hackathon.KDT_HACK.TeamManagement.dto.JoinRequestDto;
import com.hackathon.KDT_HACK.TeamManagement.dto.ProcessJoinRequestRequest;
import com.hackathon.KDT_HACK.TeamManagement.service.TeamJoinRequestService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/join-requests")
public class TeamJoinRequestController {

    private final TeamJoinRequestService joinRequestService;
    private final TeamService teamService;

    @PostMapping
    public JoinRequestDto create(@Valid @RequestBody CreateJoinRequestRequest request) {
        return JoinRequestDto.fromEntity(joinRequestService.create(request));
    }

    @PostMapping("/process")
    public JoinRequestDto process(@Valid @RequestBody ProcessJoinRequestRequest request) {
        return JoinRequestDto.fromEntity(joinRequestService.process(request.getRequestId(), request.getAction()));
    }

    @GetMapping("/team/{teamId}/pending")
    public List<JoinRequestDto> getPendingTeamRequests(@PathVariable String teamId, @RequestParam String userId) {
        if (!teamService.isCreator(userId, teamId)) {
            throw new IllegalStateException("Только создатель команды может просматривать заявки");
        }
        return joinRequestService.getPendingByTeam(teamId).stream()
                .map(JoinRequestDto::fromEntity)
                .toList();
    }

    @GetMapping("/user/{userId}")
    public List<JoinRequestDto> getUserRequests(@PathVariable String userId) {
        return joinRequestService.getByUser(userId).stream()
                .map(JoinRequestDto::fromEntity)
                .toList();
    }
}
