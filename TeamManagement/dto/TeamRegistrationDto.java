package com.hackathon.KDT_HACK.TeamManagement.dto;

import com.hackathon.KDT_HACK.TeamManagement.model.TeamHackathonRegistration;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class TeamRegistrationDto {
    Long id;
    String teamId;
    String hackathonId;
    String hackathonName;
    String registeredBy;
    LocalDateTime registeredAt;

    public static TeamRegistrationDto fromEntity(TeamHackathonRegistration registration) {
        return TeamRegistrationDto.builder()
                .id(registration.getId())
                .teamId(registration.getTeam().getId())
                .hackathonId(registration.getHackathonId())
                .hackathonName(registration.getHackathonName())
                .registeredBy(registration.getRegisteredBy())
                .registeredAt(registration.getRegisteredAt())
                .build();
    }
}
