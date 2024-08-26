package com.jupjup.www.jupjup.domain.repository.MypageSharingRepository;


import com.jupjup.www.jupjup.domain.entity.mypage.MyPageSharingList;
import com.jupjup.www.jupjup.domain.entity.mypage.QMyPageSharingList;
import com.jupjup.www.jupjup.model.dto.mypage.MyPageSharingListImageRequest;
import com.jupjup.www.jupjup.model.dto.mypage.MyPageSharingListRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.jupjup.www.jupjup.domain.entity.mypage.QMyPageSharingList.myPageSharingList;
import static com.jupjup.www.jupjup.domain.entity.mypage.QMyPageSharingListImages.myPageSharingListImages;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MypageSharingRepositoryImpl implements MypageSharingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MyPageSharingList> findAllByUserName(String username) {
        return queryFactory
                .selectFrom(myPageSharingList)
                .where(myPageSharingList.userName.eq(username))
                .fetch(); // 리스트 형태로 반환
    }

    // TODO : 이미지 받아 저장하는 방법 확인
    @Override
    public List<MyPageSharingList> findSharingListWithImages(long id) {
        return queryFactory
                .selectFrom(myPageSharingList)
                .join(myPageSharingList).on(myPageSharingListImages.id.eq(myPageSharingListImages.id))
                .fetch();
    }

    @Transactional
    public void updateItem(MyPageSharingListRequest req) {

        log.info("req.toString() : {}", req.toString());

        if (req.getId() != null) {
            long affectedRows = queryFactory
                    .update(QMyPageSharingList.myPageSharingList)
                    .set(QMyPageSharingList.myPageSharingList.title, req.getTitle())
                    .set(QMyPageSharingList.myPageSharingList.content, req.getContent())
                    .where(QMyPageSharingList.myPageSharingList.id.eq(req.getId()))
                    .execute();
            log.info("affected rows: {}", affectedRows);
        } else {
            throw new IllegalArgumentException("ID는 null이 될 수 없습니다.");
        }
    }

}
