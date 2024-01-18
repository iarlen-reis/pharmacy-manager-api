alter table remedy add column active tinyint;
update remedy set active = 1;