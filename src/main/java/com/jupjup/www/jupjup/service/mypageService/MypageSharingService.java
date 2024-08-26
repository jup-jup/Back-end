package com.jupjup.www.jupjup.service.mypageService;


import com.jupjup.www.jupjup.domain.entity.mypage.MyPageSharingList;
import com.jupjup.www.jupjup.domain.entity.mypage.MypageReceivedList;
import com.jupjup.www.jupjup.domain.repository.MypageReceivedRepository.MypageReceivedRepository;
import com.jupjup.www.jupjup.domain.repository.MypageSharingRepository.MypageSharingRepository;
import com.jupjup.www.jupjup.domain.repository.MypageSharingRepository.MypageSharingRepositoryImpl;
import com.jupjup.www.jupjup.model.dto.mypage.MyPageSharingListRequest;
import com.jupjup.www.jupjup.model.dto.mypage.MyPageSharingListResponse;
import com.jupjup.www.jupjup.model.dto.mypage.MypageReceivedListResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


/**
 * @fileName      : MypageSharingService.java
 * @author        : boramkim
 * @since         : 2024. 8. 21.
 * @description    : 마이페이지 나눔내역 서비스
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MypageSharingService {

    private final MypageSharingRepositoryImpl MypageSharingRepositoryImpl;
    private final MypageSharingRepository mypageSharingRepository;
    private final MypageReceivedRepository mypageReceivedRepository;

    /**
     * @author        : boramkim
     * @since         : 2024. 8. 21.
     * @description   : 나눔내역 모든 리스트 뿌려주는 메서드
     */
    public List<MyPageSharingListResponse> getMyPageSharingListByUserName(String userNickName) {

        List<MyPageSharingList> list = MypageSharingRepositoryImpl.findAllByUserName(userNickName);

        if (list.isEmpty()) {
            throw new NoSuchElementException();
        }

        return list.stream()
                .map(item -> MyPageSharingListResponse.builder()
                        .id(item.getId())
                        .title(item.getTitle())
                        .content(item.getContent())
                        .location(item.getTradeLocation())
                        .viewCount(item.getViewCount())
                        .chatCount(item.getChatCount())
                        .receivedAt(item.getCreatedDate())
                        .build())
                .collect(Collectors.toList());
    }


    /**
     * @author        : boramkim
     * @since         : 2024. 8. 21.
     * @description   : 게시물 id 값으로 마이페이지 나눔내역 디테일 리스트 메서드
     */
    public MyPageSharingList getMyPageSharingById(long id) {
        return mypageSharingRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }


    /**
     * @author        : boramkim
     * @since         : 2024. 8. 21.
     * @description   : 나눔 상품 업데이트 메서드
     */
    public boolean updateItem(MyPageSharingListRequest myPageSharingListRequest) {
        log.info("modifySharedItem: {}", myPageSharingListRequest.toString());
        try {
            MypageSharingRepositoryImpl.updateItem(myPageSharingListRequest);
        } catch (IllegalArgumentException e) {
            log.info(e.toString());
            return false;
        }
        return true;
    }

    /**
     * @author        : boramkim
     * @since         : 2024. 8. 21.
     * @description   : 받은내역 모든 리스트 뿌려주는 메서드
     */
    public List<MypageReceivedListResponse> getMyPageReceivedListByUserName(String username) {

        List<MypageReceivedList> list = mypageReceivedRepository.findAllByUserName(username);

        if (list.isEmpty()) {
            log.info("NoSuchElementException!!!!");
            throw new NoSuchElementException();
        }

        return list.stream()
                .map(item -> MypageReceivedListResponse.builder()
                        .id(item.getId())
                        .title(item.getTitle())
                        .content(item.getContent())
                        .location(item.getLocation())
                        .chatCount(item.getChatCount())
                        .viewCount(item.getViewCount())
                        .representativeImage(item.getRepresentativeImage())
                        .receivedAt(item.getReceivedAt())
                        .giverName(item.getGiverName())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * @author        : boramkim
     * @since         : 2024. 8. 21.
     * @description   : 게시물 id 값으로 마이페이지 받은내역 디테일 리스트 메서드
     */
    public MypageReceivedList getMyPageReceivedById(long id) {
        return mypageReceivedRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }


}
