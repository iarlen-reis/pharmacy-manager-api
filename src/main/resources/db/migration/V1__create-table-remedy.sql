CREATE TABLE remedy (
  id bigint not null auto_increment,
  name varchar(180) not null,
  via varchar(180) not null,
  batch varchar(180) not null,
  amount int(20) not null,
  validity varchar(180) not null,
  laboratory varchar(180) not null,

  primary key (id)
);
