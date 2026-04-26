package com.hackathon.KDT_HACK.TeamManagement.dto;

import com.hackathon.KDT_HACK.TeamManagement.model.Team;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class TeamDto {
    String id;
    String name;
    String description;
    String projectName;
    String projectDescription;
    String creatorId;
    String creatorName;
    Boolean isActive;
    LocalDateTime createdAt;

    public static TeamDto fromEntity(Team team) {
        return TeamDto.builder()
                .id(team.getId())
                .name(team.getName())
                .description(team.getDescription())
                .projectName(team.getProjectName())
                .projectDescription(team.getProjectDescription())
                .creatorId(team.getCreatorId())
                .creatorName(team.getCreatorName())
                .isActive(team.getIsActive())
                .createdAt(team.getCreatedAt())
                .build();
    }
}
