package com.hackathon.KDT_HACK.TeamManagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterTeamRequest {
    @NotBlank
    private String teamId;
    @NotBlank
    private String hackathonId;
    private String hackathonName;
    @NotBlank
    private String registeredBy;
}
