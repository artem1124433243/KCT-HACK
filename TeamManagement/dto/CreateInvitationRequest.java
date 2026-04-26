package com.hackathon.KDT_HACK.TeamManagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateInvitationRequest {
    @NotBlank
    private String teamId;
    @NotBlank
    private String invitedUserId;
    private String invitedUserName;
    @NotBlank
    private String invitedByUserId;
    private String invitedByUserName;
    private String message;
}
