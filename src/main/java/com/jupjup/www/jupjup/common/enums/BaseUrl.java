package com.jupjup.www.jupjup.common.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BaseUrl {

//    REACT("react", "https://jupjup.shop/");
    REACT("react", "http://localhost:3000/");

    private final String location;
    private final String url;

}
