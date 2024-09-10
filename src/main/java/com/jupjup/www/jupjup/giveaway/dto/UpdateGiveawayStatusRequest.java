package com.jupjup.www.jupjup.giveaway.dto;

import com.jupjup.www.jupjup.giveaway.enums.GiveawayStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateGiveawayStatusRequest {

    @NotNull
    private GiveawayStatus status;

    private Long receiverId;

}
