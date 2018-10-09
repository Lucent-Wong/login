drop table if exists sys_users;
drop table if exists sys_roles;
drop table if exists sys_permissions;
drop table if exists sys_users_roles;
drop table if exists sys_roles_permissions;
drop table if exists sys_user_account;
drop table if exists sys_currency;

create table sys_users (
  id bigint auto_increment,
  username varchar(100),
  password varchar(100),
  salt varchar(100),
  locked bool default false,
  available bool default true,
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  constraint pk_sys_users primary key(id),
) charset=utf8 ENGINE=InnoDB;
create unique index idx_sys_users_username on sys_users(username);

create table sys_roles (
  id bigint auto_increment,
  role varchar(100),
  description varchar(100),
  available bool default true,
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  constraint pk_sys_roles primary key(id)
) charset=utf8 ENGINE=InnoDB;
create unique index idx_sys_roles_role on sys_roles(role);

create table sys_permissions (
  id bigint auto_increment,
  permission varchar(100),
  description varchar(100),
  available bool default true,
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  constraint pk_sys_permissions primary key(id)
) charset=utf8 ENGINE=InnoDB;
create unique index idx_sys_permissions_permission on sys_permissions(permission);

create table sys_users_roles (
  user_id bigint,
  role_id bigint,
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  constraint pk_sys_users_roles primary key(user_id, role_id)
) charset=utf8 ENGINE=InnoDB;

create table sys_roles_permissions (
  role_id bigint,
  permission_id bigint,
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  constraint pk_sys_roles_permissions primary key(role_id, permission_id)
) charset=utf8 ENGINE=InnoDB;

create table sys_user_account (
  user_id bigint,
  deposit bigint default 0,
  eth_balance bigint default 0,
  wallet varchar(2000),
  secret varchar(100),
  wallet_address varchar(500),
  description varchar(100),
  available bool default true,
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  constraint pk_sys_user_account primary key(user_id)
) charset=utf8 ENGINE=InnoDB;

create table sys_currency (
  id bigint auto_increment,
  user_id bigint,
  deposit_change bigint,
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  constraint pk_sys_deposit_history primary key(id)
) charset=utf8 ENGINE=InnoDB;
