package com.jupjup.www.jupjup.service.mypageSharingService;


import com.jupjup.www.jupjup.domain.entity.mypage.MyPageSharingList;
import com.jupjup.www.jupjup.domain.repository.MypageSharingRepository.MypageSharingRepository;
import com.jupjup.www.jupjup.domain.repository.MypageSharingRepository.MypageSharingRepositoryImpl;
import com.jupjup.www.jupjup.model.dto.mypage.MyPageSharingListRequest;
import com.jupjup.www.jupjup.model.dto.mypage.MyPageSharingListResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class MypageSharingService {

    private final MypageSharingRepositoryImpl MypageSharingRepositoryImpl;
    private final MypageSharingRepository mypageSharingRepository;

    public List<MyPageSharingList> getMyPageSharingListByUser(String userNickName) {
        List<MyPageSharingList> list = MypageSharingRepositoryImpl.findAllByUserName(userNickName);
        if (list.isEmpty()) {
            throw new NoSuchElementException();
        }
        return list;
    }

    public MyPageSharingList getMyPageSharingById(long id) {
        return mypageSharingRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public boolean modifySharedItem(MyPageSharingListRequest myPageSharingListRequest) {
        log.info("modifySharedItem: {}", myPageSharingListRequest.toString());
        try {
            MypageSharingRepositoryImpl.updateItem(myPageSharingListRequest);
        } catch (IllegalArgumentException e) {
            log.info(e.toString());
            return false;
        }
        return true;
    }
}
