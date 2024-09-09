package com.jupjup.www.jupjup.chat.dto.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRoomRequest {

    @JsonProperty("giveaway_id")
    private Long giveawayId;

}
