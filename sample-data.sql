use jupjup;

insert into user (id, name, provider_key, email)
values (1, 'name1', 'provider1', 'user_email1'),
       (2, 'name2', 'provider2', 'user_email2'),
       (3, 'name3', 'provider3', 'user_email3')
;

insert into giveaway (title, giver_id, status, description)
values ('title1', 1, 'PENDING', 'description'),
       ('title2', 2, 'PENDING', 'descriptiondescriptiondescription'),
       ('제목3', 2, 'PENDING', '나눔 상세'),
       ('제목4', 1, 'RESERVED', '진행중인 나눔'),
       ('제목5', 1, 'COMPLETED', '완료된 나눔')
;

insert into room (id, giveaway_id, created_at)
values (1, 2, NOW())
;

insert into user_chat_room (id, created_at, room_id, user_id)
VALUES (1, NOW(), 1, 2), # giver
       (2, NOW(), 1, 1) # receiver
;

insert into chat (created_at, id, room_id, user_id, content)
values (NOW(), 4, 7, 1, '메시지1'),
       (NOW(), 5, 7, 37, '메시지2'),
       (NOW(), 6, 7, 37, '메시지3')
;