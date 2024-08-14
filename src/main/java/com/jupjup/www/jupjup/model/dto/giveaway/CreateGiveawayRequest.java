package com.jupjup.www.jupjup.model.dto.giveaway;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateGiveawayRequest {

    private String title;

    private String description;

    private String location;

    private List<String> imagePaths;

}
