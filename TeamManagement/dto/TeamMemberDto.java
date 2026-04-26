package com.hackathon.KDT_HACK.TeamManagement.dto;

import com.hackathon.KDT_HACK.TeamManagement.model.TeamMember;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TeamMemberDto {
    Long id;
    String userId;
    String userName;
    Boolean isCreator;

    public static TeamMemberDto fromEntity(TeamMember member) {
        return TeamMemberDto.builder()
                .id(member.getId())
                .userId(member.getUserId())
                .userName(member.getUserName())
                .isCreator(member.getIsCreator())
                .build();
    }
}
