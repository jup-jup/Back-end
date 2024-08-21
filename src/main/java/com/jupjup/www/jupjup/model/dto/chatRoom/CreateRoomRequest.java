package com.jupjup.www.jupjup.model.dto.chatRoom;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRoomRequest {

    @JsonProperty("giveaway_id")
    private Long giveawayId;

    @JsonProperty("giver_id")
    private Long giverId; // 나눔 유저 아이디

}
