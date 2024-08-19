package com.jupjup.www.jupjup.domain.repository.MypageSharingRepository;


import com.jupjup.www.jupjup.domain.entity.mypage.MyPageSharingList;
import com.jupjup.www.jupjup.domain.entity.mypage.QMyPageSharingList;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jupjup.www.jupjup.domain.entity.mypage.QMyPageSharingList.myPageSharingList;
import static com.jupjup.www.jupjup.domain.entity.mypage.QMyPageSharingListImages.myPageSharingListImages;

@Repository
@RequiredArgsConstructor
public class MypageSharingRepositoryImpl implements MypageSharingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MyPageSharingList> findAllByUserName(String username) {
        return queryFactory
                .selectFrom(myPageSharingList)
                .where(myPageSharingList.userName.eq(username))
                .fetch(); // 리스트 형태로 반환
    }

    @Override
    public MyPageSharingList findById(long id) {
        return null;
    }

    // TODO : 이미지 받아 저장하는 방법 확인
    @Override
    public List<MyPageSharingList> findSharingListWithImages(long id) {
        return queryFactory
                .selectFrom(myPageSharingList)
                .join(myPageSharingList).on(myPageSharingListImages.id.eq(myPageSharingListImages.id))
                .fetch();
    }


}
