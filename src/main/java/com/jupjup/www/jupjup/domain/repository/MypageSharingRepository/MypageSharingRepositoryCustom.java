package com.jupjup.www.jupjup.domain.repository.MypageSharingRepository;

import com.jupjup.www.jupjup.domain.entity.mypage.MyPageSharingList;
import org.springframework.stereotype.Repository;

import java.util.List;



public interface MypageSharingRepositoryCustom {

    List<MyPageSharingList> findAllByUserName(String username);
    List<MyPageSharingList> findSharingListWithImages(long id);

}
