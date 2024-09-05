package com.jupjup.www.jupjup.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum MyPageType {
    GIVER("나눔 하기", "giver"),
    RECEIVER("나눔 받기", "receiver");

    private final String display;
    private final String type;

}