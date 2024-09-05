//package com.jupjup.www.jupjup.user.controller;
//
//import com.jupjup.www.jupjup.giveaway.dto.GiveawayListResponse;
//import com.jupjup.www.jupjup.giveaway.service.GiveawayService;
//import com.jupjup.www.jupjup.user.enums.MyPageType;
//import com.jupjup.www.jupjup.user.service.MyPageService;
//import io.swagger.v3.oas.annotations.Operation;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//@RequestMapping("/api/v1/mypage")
//public class MyPageController {
//
//    private final MyPageService myPageService;
//
//    @Operation(summary = "마이페이지 리스트 조회 (페이징 지원 & type = giver or Receiver)")
//    @GetMapping("/{type}/list")
//    public ResponseEntity<List<GiveawayListResponse>> getGiveawayList(
//            @PageableDefault(size = 30, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable , @PathVariable String type) {
//
//        List<GiveawayListResponse> list = myPageService.findAll(pageable);
//
//        if(type.equals(MyPageType.GIVER.getType())){
//            return ResponseEntity.ok(list);
//        }else if (type.equals(MyPageType.RECEIVER.getType())){
//            return ResponseEntity.ok(list);
//        }else{
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//    }
//
//
//    @Operation(summary = "마이페이지 상세 페이지 조회 (페이징 지원 & type = giver or Receiver)")
//    @GetMapping("/{type}/detail/{id}")
//    public ResponseEntity<?> getSharingDetail(@PathVariable long id , @PathVariable String type) {
//
//        if(type.equals(MyPageType.GIVER.getType())){
//            return ResponseEntity.ok(myPageService.getGiverDetail(id));
//        }else if (type.equals(MyPageType.RECEIVER.getType())){
//            return ResponseEntity.ok(myPageService.getReceiverDetail(id));
//        }else{
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//    }
//
//}