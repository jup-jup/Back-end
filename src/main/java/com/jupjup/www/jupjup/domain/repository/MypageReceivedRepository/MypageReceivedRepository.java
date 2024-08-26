package com.jupjup.www.jupjup.domain.repository.MypageReceivedRepository;

import com.jupjup.www.jupjup.domain.entity.mypage.MypageReceivedList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MypageReceivedRepository extends JpaRepository<MypageReceivedList,Long> {

    List<MypageReceivedList> findAllByUserName(String userName);



}
