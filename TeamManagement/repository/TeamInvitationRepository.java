package com.hackathon.KDT_HACK.TeamManagement.repository;

import com.hackathon.KDT_HACK.TeamManagement.model.TeamInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamInvitationRepository extends JpaRepository<TeamInvitation, Long> {
    List<TeamInvitation> findByInvitedUserId(String userId);
    List<TeamInvitation> findByInvitedUserIdAndStatus(String userId, TeamInvitation.Status status);
    List<TeamInvitation> findByTeamId(String teamId);
    List<TeamInvitation> findByTeamIdAndStatus(String teamId, TeamInvitation.Status status);
    boolean existsByTeamIdAndInvitedUserIdAndStatus(String teamId, String userId, TeamInvitation.Status status);
}
