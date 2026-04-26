package com.hackathon.KDT_HACK.TeamManagement.controller;

import com.hackathon.KDT_HACK.TeamManagement.dto.CreateInvitationRequest;
import com.hackathon.KDT_HACK.TeamManagement.dto.InvitationDto;
import com.hackathon.KDT_HACK.TeamManagement.dto.RespondInvitationRequest;
import com.hackathon.KDT_HACK.TeamManagement.service.TeamInvitationService;
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
@RequestMapping("/api/invitations")
public class TeamInvitationController {

    private final TeamInvitationService invitationService;
    private final TeamService teamService;

    @PostMapping
    public InvitationDto create(@Valid @RequestBody CreateInvitationRequest request) {
        return InvitationDto.fromEntity(invitationService.create(request));
    }

    @PostMapping("/respond")
    public InvitationDto respond(@Valid @RequestBody RespondInvitationRequest request) {
        return InvitationDto.fromEntity(
                invitationService.respond(request.getInvitationId(), request.getUserId(), request.getAction())
        );
    }

    @GetMapping("/user/{userId}")
    public List<InvitationDto> getUserInvitations(@PathVariable String userId) {
        return invitationService.getByUser(userId).stream()
                .map(InvitationDto::fromEntity)
                .toList();
    }

    @GetMapping("/user/{userId}/pending")
    public List<InvitationDto> getUserPendingInvitations(@PathVariable String userId) {
        return invitationService.getPendingByUser(userId).stream()
                .map(InvitationDto::fromEntity)
                .toList();
    }

    @GetMapping("/team/{teamId}")
    public List<InvitationDto> getTeamInvitations(@PathVariable String teamId, @RequestParam String userId) {
        if (!teamService.isCreator(userId, teamId)) {
            throw new IllegalStateException("Только создатель команды может просматривать инвайты");
        }
        return invitationService.getByTeam(teamId).stream()
                .map(InvitationDto::fromEntity)
                .toList();
    }

    @GetMapping("/team/{teamId}/pending")
    public List<InvitationDto> getTeamPendingInvitations(@PathVariable String teamId, @RequestParam String userId) {
        if (!teamService.isCreator(userId, teamId)) {
            throw new IllegalStateException("Только создатель команды может просматривать инвайты");
        }
        return invitationService.getPendingByTeam(teamId).stream()
                .map(InvitationDto::fromEntity)
                .toList();
    }
}
