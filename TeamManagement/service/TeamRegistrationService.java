package com.hackathon.KDT_HACK.TeamManagement.service;

import com.hackathon.KDT_HACK.TeamManagement.dto.RegisterTeamRequest;
import com.hackathon.KDT_HACK.TeamManagement.model.Team;
import com.hackathon.KDT_HACK.TeamManagement.model.TeamHackathonRegistration;
import com.hackathon.KDT_HACK.TeamManagement.repository.TeamHackathonRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamRegistrationService {

    private final TeamService teamService;
    private final TeamHackathonRegistrationRepository registrationRepository;

    @Transactional
    public TeamHackathonRegistration registerTeam(RegisterTeamRequest request) {
        Team team = teamService.getById(request.getTeamId());

        if (!team.getCreatorId().equals(request.getRegisteredBy())) {
            throw new IllegalStateException("Только создатель команды может зарегистрировать ее на хакатон");
        }

        registrationRepository.findByTeamIdAndHackathonId(request.getTeamId(), request.getHackathonId())
                .ifPresent(registration -> {
                    throw new IllegalArgumentException("Команда уже зарегистрирована на этот хакатон");
                });

        TeamHackathonRegistration registration = new TeamHackathonRegistration();
        registration.setTeam(team);
        registration.setHackathonId(request.getHackathonId());
        registration.setHackathonName(request.getHackathonName());
        registration.setRegisteredBy(request.getRegisteredBy());
        return registrationRepository.save(registration);
    }

    @Transactional(readOnly = true)
    public List<TeamHackathonRegistration> getByTeam(String teamId) {
        return registrationRepository.findByTeamId(teamId);
    }

    @Transactional(readOnly = true)
    public List<TeamHackathonRegistration> getByHackathon(String hackathonId) {
        return registrationRepository.findByHackathonId(hackathonId);
    }
}
