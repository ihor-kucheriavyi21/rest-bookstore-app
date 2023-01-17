CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


insert into publisher values (uuid_generate_v4(), 'Name5', 'Address5');
insert into book(id, author, genre, name, publisher_id) values (uuid_generate_v4(),'author1','genre1','bookName1',(select publisher.id from publisher));
insert into book(id, author, genre, name, publisher_id) values (uuid_generate_v4(),'author2','genre2','bookName2',(select publisher.id from publisher));
insert into book(id, author, genre, name, publisher_id) values (uuid_generate_v4(),'author3','genre3','bookName3',(select publisher.id from publisher));
insert into book(id, author, genre, name, publisher_id) values (uuid_generate_v4(),'author4','genre4','bookName4',(select publisher.id from publisher));
insert into book(id, author, genre, name, publisher_id) values (uuid_generate_v4(),'author5','genre5','bookName5',(select publisher.id from publisher));
insert into book(id, author, genre, name, publisher_id) values (uuid_generate_v4(),'author6','genre6','bookName6',(select publisher.id from publisher));

insert into publisher values (uuid_generate_v4(), 'Name1', 'Address1');
insert into publisher values (uuid_generate_v4(), 'Name2', 'Address2');
insert into publisher values (uuid_generate_v4(), 'Name3', 'Address3');

