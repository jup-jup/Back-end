package com.jupjup.www.jupjup.domain.repository.MypageSharingRepository;


import com.jupjup.www.jupjup.domain.entity.mypage.MyPageGiveawayList;
import com.jupjup.www.jupjup.model.dto.mypage.MyPageSharingListRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.jupjup.www.jupjup.domain.entity.mypage.QMyPageGiveawayList.myPageGiveawayList;
import static com.jupjup.www.jupjup.domain.entity.mypage.QMyPageGiveawayListImages.myPageGiveawayListImages;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MypageGiveawayRepositoryImpl implements MypageGiveawayRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MyPageGiveawayList> findAllByUserName(String username) {
        return queryFactory
                .selectFrom(myPageGiveawayList)
                .where(myPageGiveawayList.userName.eq(username))
                .fetch(); // 리스트 형태로 반환
    }

    // TODO : 이미지 받아 저장하는 방법 확인
    @Override
    public List<MyPageGiveawayList> findSharingListWithImages(long id) {
        return queryFactory
                .selectFrom(myPageGiveawayList)
                .join(myPageGiveawayList).on(myPageGiveawayListImages.id.eq(myPageGiveawayListImages.id))
                .fetch();
    }

    @Transactional
    public void updateItem(MyPageSharingListRequest req) {

        log.info("req.toString() : {}", req.toString());

        if (req.getId() != null) {
            long affectedRows = queryFactory
                    .update(myPageGiveawayList)
                    .set(myPageGiveawayList.title, req.getTitle())
                    .set(myPageGiveawayList.content, req.getContent())
                    .where(myPageGiveawayList.id.eq(req.getId()))
                    .execute();
            log.info("affected rows: {}", affectedRows);
        } else {
            throw new IllegalArgumentException("ID는 null이 될 수 없습니다.");
        }
    }

}
