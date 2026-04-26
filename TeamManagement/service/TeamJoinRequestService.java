package com.hackathon.KDT_HACK.TeamManagement.service;

import com.hackathon.KDT_HACK.TeamManagement.dto.CreateJoinRequestRequest;
import com.hackathon.KDT_HACK.TeamManagement.model.TeamJoinRequest;
import com.hackathon.KDT_HACK.TeamManagement.repository.TeamJoinRequestRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamJoinRequestService {

    private final TeamJoinRequestRepository joinRequestRepository;
    private final TeamService teamService;

    @Transactional
    public TeamJoinRequest create(CreateJoinRequestRequest request) {
        teamService.getById(request.getTeamId());

        if (teamService.isMember(request.getTeamId(), request.getUserId())) {
            throw new IllegalArgumentException("Пользователь уже состоит в команде");
        }
        if (joinRequestRepository.existsByTeamIdAndUserIdAndStatus(
                request.getTeamId(), request.getUserId(), TeamJoinRequest.Status.PENDING)) {
            throw new IllegalArgumentException("У пользователя уже есть активная заявка");
        }

        TeamJoinRequest joinRequest = new TeamJoinRequest();
        joinRequest.setTeam(teamService.getById(request.getTeamId()));
        joinRequest.setUserId(request.getUserId());
        joinRequest.setUserName(request.getUserName());
        joinRequest.setMessage(request.getMessage());
        joinRequest.setStatus(TeamJoinRequest.Status.PENDING);
        return joinRequestRepository.save(joinRequest);
    }

    @Transactional
    public TeamJoinRequest process(Long requestId, String action) {
        TeamJoinRequest joinRequest = joinRequestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Заявка не найдена: " + requestId));

        if (joinRequest.getStatus() != TeamJoinRequest.Status.PENDING) {
            throw new IllegalStateException("Заявка уже обработана");
        }

        if ("approve".equalsIgnoreCase(action)) {
            joinRequest.setStatus(TeamJoinRequest.Status.APPROVED);
            teamService.addMember(
                    joinRequest.getTeam().getId(),
                    joinRequest.getUserId(),
                    joinRequest.getUserName()
            );
        } else if ("reject".equalsIgnoreCase(action)) {
            joinRequest.setStatus(TeamJoinRequest.Status.REJECTED);
        } else {
            throw new IllegalArgumentException("Некорректное действие, используйте approve/reject");
        }
        joinRequest.setProcessedAt(LocalDateTime.now());
        return joinRequestRepository.save(joinRequest);
    }

    @Transactional(readOnly = true)
    public List<TeamJoinRequest> getPendingByTeam(String teamId) {
        return joinRequestRepository.findByTeamIdAndStatus(teamId, TeamJoinRequest.Status.PENDING);
    }

    @Transactional(readOnly = true)
    public List<TeamJoinRequest> getByUser(String userId) {
        return joinRequestRepository.findByUserId(userId);
    }
}
