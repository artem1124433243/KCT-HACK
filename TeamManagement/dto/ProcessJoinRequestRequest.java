package com.hackathon.KDT_HACK.TeamManagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProcessJoinRequestRequest {
    @NotNull
    private Long requestId;
    private String action;
}
