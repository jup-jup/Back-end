package com.jupjup.www.jupjup.image.entity;

import com.jupjup.www.jupjup.domain.entity.User;
import com.jupjup.www.jupjup.domain.entity.giveaway.Giveaway;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    // TODO: 어디에 저장할 것인지는 정할 필요가 있음 (AWS S3, Google Cloud Storage, 혹은 VM 에 볼륨 마운트해서 저장한다거나..)
    // 이미지 저장 경로
    @Column(name = "path", nullable = false, length = 1024)
    private String path;

    // 파일명
    @Column(name = "file_name", nullable = false)
    private String fileName;

    // 업로드 유저
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User user;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "giveaway_id")
    private Giveaway giveaway;

    @Builder
    public Image(String path, String fileName, Long userId) {
        this.path = path;
        this.fileName = fileName;
        this.userId = userId;
    }
}
