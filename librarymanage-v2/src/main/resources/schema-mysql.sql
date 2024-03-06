drop table if exists book.book_inf;
-- 创建book_inf表
create table book.book_inf
(
    book_id int primary key auto_increment,
    book_title varchar(255) not null,
    book_author varchar(255),
    book_price double
);
