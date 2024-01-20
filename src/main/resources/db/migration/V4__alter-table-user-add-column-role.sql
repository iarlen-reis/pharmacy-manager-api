alter table user add column role text;
update user set role = "USER";