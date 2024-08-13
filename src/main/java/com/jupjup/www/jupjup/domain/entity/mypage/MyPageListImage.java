package com.jupjup.www.jupjup.domain.entity.mypage;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class MyPageListImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id ;
    @Column(nullable = false)
    private String imgUUID;

    @ManyToOne
    @JoinColumn(name="mypagalist_id")
    private MyPageList myPageListCreateId;


}
