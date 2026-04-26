package com.hackathon.KDT_HACK.TeamManagement.service;

import com.hackathon.KDT_HACK.Registration.UserRepository;
import com.hackathon.KDT_HACK.TeamManagement.dto.CreateTeamRequest;
import com.hackathon.KDT_HACK.TeamManagement.dto.TeamMemberDto;
import com.hackathon.KDT_HACK.TeamManagement.dto.UpdateTeamRequest;
import com.hackathon.KDT_HACK.TeamManagement.model.Team;
import com.hackathon.KDT_HACK.TeamManagement.model.TeamMember;
import com.hackathon.KDT_HACK.TeamManagement.repository.TeamMemberRepository;
import com.hackathon.KDT_HACK.TeamManagement.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final UserRepository userRepository;

    @Transactional
    public Team createTeam(CreateTeamRequest request) {
        userRepository.findUserById(request.getCreatorId())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден: " + request.getCreatorId()));

        Team team = new Team();
        team.setName(request.getName());
        team.setDescription(request.getDescription());
        team.setProjectName(request.getProjectName());
        team.setProjectDescription(request.getProjectDescription());
        team.setCreatorId(request.getCreatorId());
        team.setCreatorName(request.getCreatorName());
        team.setIsActive(true);
        Team saved = teamRepository.save(team);

        TeamMember creator = new TeamMember();
        creator.setTeam(saved);
        creator.setUserId(request.getCreatorId());
        creator.setUserName(request.getCreatorName());
        creator.setIsCreator(true);
        teamMemberRepository.save(creator);

        return saved;
    }

    @Transactional(readOnly = true)
    public Team getById(String teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Команда не найдена: " + teamId));
    }

    @Transactional(readOnly = true)
    public List<Team> getByCreator(String creatorId) {
        return teamRepository.findByCreatorId(creatorId);
    }

    @Transactional(readOnly = true)
    public List<Team> getUserTeams(String userId) {
        return teamRepository.findTeamsByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Team> getAll() {
        return teamRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Team> getActive() {
        return teamRepository.findByIsActiveTrue();
    }

    @Transactional(readOnly = true)
    public List<Team> search(String query) {
        return teamRepository.findByNameContainingIgnoreCase(query);
    }

    @Transactional
    public Team updateTeam(String teamId, UpdateTeamRequest request) {
        Team team = getById(teamId);
        if (request.getName() != null) {
            team.setName(request.getName());
        }
        if (request.getDescription() != null) {
            team.setDescription(request.getDescription());
        }
        if (request.getProjectName() != null) {
            team.setProjectName(request.getProjectName());
        }
        if (request.getProjectDescription() != null) {
            team.setProjectDescription(request.getProjectDescription());
        }
        if (request.getIsActive() != null) {
            team.setIsActive(request.getIsActive());
        }
        return teamRepository.save(team);
    }

    @Transactional
    public void deleteTeam(String teamId) {
        if (!teamRepository.existsById(teamId)) {
            throw new EntityNotFoundException("Команда не найдена: " + teamId);
        }
        teamRepository.deleteById(teamId);
    }

    @Transactional(readOnly = true)
    public boolean isCreator(String userId, String teamId) {
        Team team = getById(teamId);
        return team.getCreatorId().equals(userId);
    }

    @Transactional(readOnly = true)
    public List<TeamMemberDto> getMembers(String teamId) {
        getById(teamId);
        return teamMemberRepository.findByTeamId(teamId).stream()
                .map(TeamMemberDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public boolean isMember(String teamId, String userId) {
        return teamMemberRepository.existsByTeamIdAndUserId(teamId, userId);
    }

    @Transactional
    public void addMember(String teamId, String userId, String userName) {
        if (teamMemberRepository.existsByTeamIdAndUserId(teamId, userId)) {
            return;
        }
        TeamMember member = new TeamMember();
        member.setTeam(getById(teamId));
        member.setUserId(userId);
        member.setUserName(userName);
        member.setIsCreator(false);
        teamMemberRepository.save(member);
    }
}
