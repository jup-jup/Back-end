CREATE TABLE `product`
(
    `table_id`         bigint       NOT NULL,
    `email`            varchar(100) NOT NULL,
    `nick_name`        varchar(100) NULL,
    `title`            varchar(100) NULL,
    `content`          text         NULL,
    `price`            int          NULL,
    `menu`             varchar(100) NULL,
    `like_cnt`         int          NULL,
    `Field`            VARCHAR(255) NULL,
    `read_cnt`         int          NULL,
    `reg_at`           datetime     NULL,
    `is_del`           tinyint(1)   NULL,
    `product_state`    int          NULL,
    `thumb_nail_image` varchar(200) NULL
);

CREATE TABLE `auth`
(
    `user_id` varchar(100) NOT NULL,
    `auth`    varchar(50)  NULL
);

CREATE TABLE `product_file`
(
    `uuid`      varchar(256) NOT NULL,
    `bno`       bigint       NOT NULL,
    `save_dir`  varchar(256) NULL,
    `file_name` varchar(256) NULL,
    `file_type` tinyint(1)   NULL,
    `file_size` bigint       NULL,
    `reg_at`    datetime     NULL
);

CREATE TABLE `message`
(
    `message_id`  bigint       NOT NULL,
    `room_id`     bigint       NOT NULL,
    `user_id`     varchar(100) NOT NULL,
    `content`     text         NULL,
    `read_or_not` varchar(10)  NULL,
    `reg_at`      datetime     NULL
);

CREATE TABLE `chatRoom`
(
    `room_id` bigint       NOT NULL,
    `user_id` varchar(100) NOT NULL,
    `bno`     bigint       NOT NULL,
    `reg_at`  datetime     NULL
);

CREATE TABLE `profile`
(
    `uuid`      varchar(256) NOT NULL,
    `user_id`   varchar(100) NOT NULL,
    `save_dir`  varchar(256) NULL,
    `file_name` varchar(256) NULL,
    `file_type` tinyint(1)   NULL,
    `file_size` bigint       NULL
);

CREATE TABLE `user`
(
    `email`     varchar(100)  NOT NULL,
    `pw`        varchar(1000) NULL,
    `nick_name` varchar(100)  NULL COMMENT '중복 안되게',
    `phone`     varchar(100)  NULL,
    `reg_at`    datetime      NULL,
    `mod_at`    datetime      NULL,
    `is_active` tinyint(1)    NULL,
    `sido`      varchar(100)  NULL,
    `sigg`      varchar(100)  NULL,
    `emd`       varchar(100)  NULL
);

CREATE TABLE `FavoriteProduct`
(
    `favorite_id` VARCHAR(255) NOT NULL,
    `product_id`  bigint       NOT NULL,
    `table_id`    bigint       NOT NULL,
    `Field`       VARCHAR(255) NULL,
    `created_at`  timestamp    NULL,
    `updated_at`  timestamp    NULL
);

ALTER TABLE `product`
    ADD CONSTRAINT `PK_PRODUCT` PRIMARY KEY (
                                             `table_id`
        );

ALTER TABLE `product_file`
    ADD CONSTRAINT `PK_PRODUCT_FILE` PRIMARY KEY (
                                                  `uuid`
        );

ALTER TABLE `message`
    ADD CONSTRAINT `PK_MESSAGE` PRIMARY KEY (
                                             `message_id`
        );

ALTER TABLE `chatRoom`
    ADD CONSTRAINT `PK_CHATROOM` PRIMARY KEY (
                                              `room_id`
        );

ALTER TABLE `profile`
    ADD CONSTRAINT `PK_PROFILE` PRIMARY KEY (
                                             `uuid`
        );

ALTER TABLE `user`
    ADD CONSTRAINT `PK_USER` PRIMARY KEY (
                                          `email`
        );

ALTER TABLE `FavoriteProduct`
    ADD CONSTRAINT `PK_FAVORITEPRODUCT` PRIMARY KEY (
                                                     `favorite_id`
        );
