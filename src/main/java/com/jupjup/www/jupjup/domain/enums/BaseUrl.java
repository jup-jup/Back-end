package com.jupjup.www.jupjup.domain.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@RequiredArgsConstructor
public enum BaseUrl {

    REACT("react", "localhost:3000");

    private final String location;
    private final String url;

}
