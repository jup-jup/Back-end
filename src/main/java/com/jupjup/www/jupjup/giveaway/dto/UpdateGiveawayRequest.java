package com.jupjup.www.jupjup.giveaway.dto;

import com.jupjup.www.jupjup.giveaway.enums.GiveawayStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateGiveawayRequest {

    private String title;

    private String description;

    private GiveawayStatus status;

    private List<Long> imageIds;

}
