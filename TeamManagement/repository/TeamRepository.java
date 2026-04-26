package com.hackathon.KDT_HACK.TeamManagement.repository;

import com.hackathon.KDT_HACK.TeamManagement.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, String> {
    List<Team> findByCreatorId(String creatorId);
    List<Team> findByIsActiveTrue();
    List<Team> findByNameContainingIgnoreCase(String query);

    @Query("""
        SELECT t FROM Team t
        WHERE t.creatorId = :userId
           OR EXISTS (
                SELECT 1 FROM TeamMember tm
                WHERE tm.team = t AND tm.userId = :userId
           )
        """)
    List<Team> findTeamsByUserId(@Param("userId") String userId);
}
