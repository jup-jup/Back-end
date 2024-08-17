package com.jupjup.www.jupjup.service.mypageSharingService;


import com.jupjup.www.jupjup.domain.entity.mypage.MyPageSharingList;
import com.jupjup.www.jupjup.domain.repository.MypageSharingRepository;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MypageSharingService {

    private final MypageSharingRepository mypageSharingRepository;

    public List<MyPageSharingList> mypageSharingList(String userNickName) {
        List<MyPageSharingList> list = mypageSharingRepository.findAllByUserName(userNickName);
        if (list.isEmpty()) {
            throw new NullPointerException();
        }
        return list;
    }

    public MyPageSharingList mypageSharingDetailList(long id) {
        MyPageSharingList DetailList = mypageSharingRepository.findById(id);
        if (DetailList == null) {
            throw new NullPointerException();
        }
        return DetailList;
    }

    public MyPageSharingList modifyMyPageSharing(long id) {
        MyPageSharingList DetailList = mypageSharingRepository.findById(id);
        if (DetailList == null) {
            throw new NullPointerException();
        }
        return DetailList;
    }



}
