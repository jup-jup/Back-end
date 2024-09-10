package com.jupjup.www.jupjup.giveaway.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateGiveawayRequest {

    private String title;

    private String description;

    private List<Long> imageIds;

}
