package com.jupjup.www.jupjup.config;

import com.jupjup.www.jupjup.user.enums.MyPageType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class Util {

    public static ResponseEntity<?> test(String type,Object giverData,Object receiverData){

        if (type.toLowerCase().equals(MyPageType.GIVER.getType())) {
            return ResponseEntity.ok(giverData);
        } else if (type.toLowerCase().equals(MyPageType.RECEIVER.getType())) {
            return ResponseEntity.ok(receiverData);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }




}
