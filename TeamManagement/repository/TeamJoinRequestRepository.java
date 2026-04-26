package com.hackathon.KDT_HACK.TeamManagement.repository;

import com.hackathon.KDT_HACK.TeamManagement.model.TeamJoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamJoinRequestRepository extends JpaRepository<TeamJoinRequest, Long> {
    List<TeamJoinRequest> findByUserId(String userId);
    List<TeamJoinRequest> findByTeamId(String teamId);
    List<TeamJoinRequest> findByTeamIdAndStatus(String teamId, TeamJoinRequest.Status status);
    boolean existsByTeamIdAndUserIdAndStatus(String teamId, String userId, TeamJoinRequest.Status status);
}
