create table user
(
    id           bigint auto_increment primary key,
    email        varchar(255)                        not null,
    name         varchar(255)                        not null,
    provider_key varchar(255)                        not null,
    created_at   timestamp default CURRENT_TIMESTAMP null,
    updated_at   datetime(6)                         null
);

create table giveaway
(
    id          bigint auto_increment primary key,
    giver_id    bigint                                    null,
    view_count  bigint default 0                          null,
    description varchar(255)                              null,
    location    varchar(255)                              null,
    title       varchar(255)                              not null,
    status      enum ('COMPLETED', 'PENDING', 'RESERVED') null,
    receiver_id bigint                                    null,
    created_at  datetime(6)                               null,
    updated_at  datetime(6)                               null,
    received_at datetime(6)                               null,
    constraint fk_giver
        foreign key (giver_id) references user (id),
    constraint fk_receiver
        foreign key (receiver_id) references user (id)
);

# 검색을 위한 fulltext 설정
create fulltext index ft_index
    on giveaway (title, description);
