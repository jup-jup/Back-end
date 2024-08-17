package com.jupjup.www.jupjup.domain.repository;


import com.jupjup.www.jupjup.domain.entity.mypage.MyPageSharingList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MypageSharingRepository extends JpaRepository<MyPageSharingList, Long> {

    List<MyPageSharingList> findAllByUserName(String username);

    MyPageSharingList findById(long id);
}
