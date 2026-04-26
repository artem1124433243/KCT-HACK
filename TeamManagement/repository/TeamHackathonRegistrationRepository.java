package com.hackathon.KDT_HACK.TeamManagement.repository;

import com.hackathon.KDT_HACK.TeamManagement.model.TeamHackathonRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamHackathonRegistrationRepository extends JpaRepository<TeamHackathonRegistration, Long> {
    List<TeamHackathonRegistration> findByTeamId(String teamId);
    List<TeamHackathonRegistration> findByHackathonId(String hackathonId);
    Optional<TeamHackathonRegistration> findByTeamIdAndHackathonId(String teamId, String hackathonId);
}
