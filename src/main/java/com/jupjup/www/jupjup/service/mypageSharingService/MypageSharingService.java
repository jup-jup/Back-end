package com.jupjup.www.jupjup.service.mypageSharingService;


import com.jupjup.www.jupjup.domain.entity.mypage.MyPageSharingList;
import com.jupjup.www.jupjup.domain.repository.MypageSharingRepository.MypageSharingRepository;
import com.jupjup.www.jupjup.domain.repository.MypageSharingRepository.MypageSharingRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MypageSharingService {

    private final MypageSharingRepositoryImpl MypageSharingRepositoryImpl;
    private final MypageSharingRepository mypageSharingRepository;

    public List<MyPageSharingList> mypageSharingList(String userNickName) {
        List<MyPageSharingList> list = MypageSharingRepositoryImpl.findAllByUserName(userNickName);
        if (list.isEmpty()) {
            throw new NullPointerException();
        }
        return list;
    }

    public MyPageSharingList mypageSharingDetailList(long id) {
        Optional<MyPageSharingList> DetailList = mypageSharingRepository.findById(id);
        if (DetailList.isEmpty()) {
            throw new NullPointerException("Not found for ID: " + id);
        }
        return DetailList.get();
    }

    public List<MyPageSharingList> modifyMyPageSharing(long id) {
        List<MyPageSharingList> DetailList = MypageSharingRepositoryImpl.findSharingListWithImages(id);
        if (DetailList == null) {
            throw new NullPointerException("Not found for ID: " + id);
        }
        return DetailList;
    }



}
