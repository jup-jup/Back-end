package com.jupjup.www.jupjup.chat.dto.room;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomUser {

    private Long id;

    private String name;

    private String profilePath; // TODO: 프로필 == 프로필 이미지를 의미하는게 맞는지?

}
