drop database if exists triends;

create database if not exists triends;

use triends;

CREATE TABLE if not exists member
(
    user_id VARCHAR(255) PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    user_pass VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(20)
);

CREATE TABLE if not exists board
(
    no INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    content VARCHAR(2000),
    user_id VARCHAR(255),
    hit INT NULL DEFAULT 0,
    register_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(user_id) references member(user_id) on delete cascade
);

CREATE TABLE if not exists teaminfo
(
	no INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

insert into member 
values('ssafy','싸피','ssafy','ssafy@ssafy.com','010-0000-0000');

insert into board (title,content,user_id)
values('제목1','내용1','ssafy');

-- insert into comment(board_no,user_id,content)
-- values (1,'ssafy','댓글1');

select * from member;
