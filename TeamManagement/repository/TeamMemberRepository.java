package com.hackathon.KDT_HACK.TeamManagement.repository;

import com.hackathon.KDT_HACK.TeamManagement.model.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    List<TeamMember> findByTeamId(String teamId);
    List<TeamMember> findByUserId(String userId);
    Optional<TeamMember> findByTeamIdAndUserId(String teamId, String userId);
    boolean existsByTeamIdAndUserId(String teamId, String userId);
}
