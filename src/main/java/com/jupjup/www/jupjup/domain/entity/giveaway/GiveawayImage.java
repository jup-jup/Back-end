package com.jupjup.www.jupjup.domain.entity.giveaway;

import jakarta.persistence.*;
import lombok.Getter;

@Table(name = "giveaway_image")
@Getter
@Entity
public class GiveawayImage {
    // TODO: 이미지 저장 방식을 어떤 식으로 구현할 것인지 학습 필요

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "path")
    private String path; // 이미지 파일이 저장된 경로를 나타낸다.

    private boolean isRepresent; // 대표 이미지

    // @OneToMany 단방향을 써서 부모 엔티티가 주인이 되기보다 양방향 연관관계를 이용해 자식 엔티티가 FK를 관리하는 것이 권장된다고 함.
    @ManyToOne
    @JoinColumn(name = "giveaway_id")
    private Giveaway giveaway;

}
