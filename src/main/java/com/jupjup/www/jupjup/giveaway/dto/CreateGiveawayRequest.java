package com.jupjup.www.jupjup.giveaway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateGiveawayRequest {

    private String title;
    private String description;
    private String location;
    @JsonProperty("image_ids")
    private List<Long> imageIds;

    @Override
    public String toString() {
        return "CreateGiveawayRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", imageIds=" + imageIds +
                '}';
    }
}
