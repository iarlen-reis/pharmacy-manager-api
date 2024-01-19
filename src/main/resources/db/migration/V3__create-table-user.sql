CREATE TABLE user (
  id bigint not null auto_increment,
  username varchar(180) not null,
  password varchar(180) not null,

  primary key (id)
);
