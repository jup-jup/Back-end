package com.jupjup.www.jupjup.domain.entity.mypage;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mypage_sharing_list_images")
public class MyPageSharingListImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "img_uuid", nullable = false)
    private String imgUUID;

    @ManyToOne
    @JoinColumn(name = "mypage_sharing_list_id")
    private MyPageSharingList myPageSharingListId;

}