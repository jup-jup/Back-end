package com.jupjup.www.jupjup.domain.repository.MypageSharingRepository;


import com.jupjup.www.jupjup.domain.entity.mypage.MyPageSharingList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MypageSharingRepository extends JpaRepository<MyPageSharingList, Long> {

}
