package com.hackathon.KDT_HACK.TeamManagement.service;

import com.hackathon.KDT_HACK.TeamManagement.dto.CreateInvitationRequest;
import com.hackathon.KDT_HACK.TeamManagement.model.TeamInvitation;
import com.hackathon.KDT_HACK.TeamManagement.repository.TeamInvitationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamInvitationService {

    private final TeamInvitationRepository invitationRepository;
    private final TeamService teamService;

    @Transactional
    public TeamInvitation create(CreateInvitationRequest request) {
        if (!teamService.isCreator(request.getInvitedByUserId(), request.getTeamId())) {
            throw new IllegalStateException("Только создатель команды может отправлять инвайты");
        }
        if (teamService.isMember(request.getTeamId(), request.getInvitedUserId())) {
            throw new IllegalArgumentException("Пользователь уже состоит в команде");
        }
        if (invitationRepository.existsByTeamIdAndInvitedUserIdAndStatus(
                request.getTeamId(), request.getInvitedUserId(), TeamInvitation.Status.PENDING)) {
            throw new IllegalArgumentException("Инвайт уже отправлен и ожидает ответа");
        }

        TeamInvitation invitation = new TeamInvitation();
        invitation.setTeam(teamService.getById(request.getTeamId()));
        invitation.setInvitedUserId(request.getInvitedUserId());
        invitation.setInvitedUserName(request.getInvitedUserName());
        invitation.setInvitedByUserId(request.getInvitedByUserId());
        invitation.setInvitedByUserName(request.getInvitedByUserName());
        invitation.setMessage(request.getMessage());
        invitation.setStatus(TeamInvitation.Status.PENDING);
        return invitationRepository.save(invitation);
    }

    @Transactional
    public TeamInvitation respond(Long invitationId, String userId, String action) {
        TeamInvitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new EntityNotFoundException("Инвайт не найден: " + invitationId));

        if (!invitation.getInvitedUserId().equals(userId)) {
            throw new IllegalStateException("Инвайт адресован другому пользователю");
        }
        if (invitation.getStatus() != TeamInvitation.Status.PENDING) {
            throw new IllegalStateException("Инвайт уже обработан");
        }

        if ("accept".equalsIgnoreCase(action)) {
            invitation.setStatus(TeamInvitation.Status.ACCEPTED);
            teamService.addMember(
                    invitation.getTeam().getId(),
                    invitation.getInvitedUserId(),
                    invitation.getInvitedUserName()
            );
        } else if ("reject".equalsIgnoreCase(action)) {
            invitation.setStatus(TeamInvitation.Status.REJECTED);
        } else {
            throw new IllegalArgumentException("Некорректное действие, используйте accept/reject");
        }
        invitation.setRespondedAt(LocalDateTime.now());
        return invitationRepository.save(invitation);
    }

    @Transactional(readOnly = true)
    public List<TeamInvitation> getByUser(String userId) {
        return invitationRepository.findByInvitedUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<TeamInvitation> getPendingByUser(String userId) {
        return invitationRepository.findByInvitedUserIdAndStatus(userId, TeamInvitation.Status.PENDING);
    }

    @Transactional(readOnly = true)
    public List<TeamInvitation> getByTeam(String teamId) {
        return invitationRepository.findByTeamId(teamId);
    }

    @Transactional(readOnly = true)
    public List<TeamInvitation> getPendingByTeam(String teamId) {
        return invitationRepository.findByTeamIdAndStatus(teamId, TeamInvitation.Status.PENDING);
    }
}
