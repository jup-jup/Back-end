package com.jupjup.www.jupjup.model.dto.giveaway;

import com.jupjup.www.jupjup.domain.enums.GiveawayStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateGiveawayRequest {

    private String title;

    private String description;

    private GiveawayStatus status;

    private List<String> imagePaths;

}
