package com.jupjup.www.jupjup.domain.repository.MypageSharingRepository;

import com.jupjup.www.jupjup.domain.entity.mypage.MyPageGiveawayList;

import java.util.List;



public interface MypageGiveawayRepositoryCustom {

    List<MyPageGiveawayList> findAllByUserName(String username);
    List<MyPageGiveawayList> findSharingListWithImages(long id);

}
