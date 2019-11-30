/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/12/1 0:42:04                            */
/*==============================================================*/

/*==============================================================*/
/* Table: CA2_APPLICATION                                       */
/*==============================================================*/
create table CA2_APPLICATION
(
   ID                   int not null auto_increment,
   SERVER_ID            int,
   PROJECT_ID           int,
   NAME                 nvarchar(64),
   TYPE                 smallint,
   REMARK               nvarchar(256),
   BASE_DIR             varchar(255),
   SOURCE_DIR           varchar(255),
   BACKUP_DIR           varchar(255),
   SCRIPT_START         varchar(512),
   SCRIPT_STOP          varchar(512),
   SCRIPT_CHECK         varchar(255),
   ADMIN_ID             int,
   primary key (ID)
);

/*==============================================================*/
/* Table: CA2_APPLICATION_BACKUP                                */
/*==============================================================*/
create table CA2_APPLICATION_BACKUP
(
   ID                   int not null auto_increment,
   APPLICATION_ID       int,
   FILE_PATH            varchar(256),
   SHA256               varchar(512),
   CREATE_TIME          datetime,
   primary key (ID)
);

/*==============================================================*/
/* Table: CA2_APPLICATION_UPDATE_APPLY                          */
/*==============================================================*/
create table CA2_APPLICATION_UPDATE_APPLY
(
   ID                   int not null auto_increment,
   PROJECT_ID           int,
   PROJECT_PACKAGE_ID   int,
   APPLICATION_ID       int,
   APPLICATION_FILE_SHA256 varchar(512),
   CREATE_USER_ID       int,
   CREATE_TIME          datetime,
   STATUS               tinyint,
   AUDIT_USER_ID        int,
   AUDIT_TIME           datetime,
   AUDIT_RESULT         varchar(256),
   primary key (ID)
);

/*==============================================================*/
/* Table: CA2_APPLICATION_UPDATE_LOG                            */
/*==============================================================*/
create table CA2_APPLICATION_UPDATE_LOG
(
   ID                   int not null auto_increment,
   PROJECT_ID           int,
   SERVER_ID            int,
   APPLICATION_ID       int,
   APPLY_ID             int,
   USER_ID              int,
   TYPE                 tinyint comment '0更新 1回滚',
   CREATE_TIME          datetime,
   DONE_TIME            datetime,
   STATUS               tinyint,
   BEFORE_SHA256        varchar(512),
   AFTER_SHA256         varchar(512),
   LOG                  mediumtext,
   primary key (ID)
);

/*==============================================================*/
/* Table: CA2_ASSEMBLY_LINE                                     */
/*==============================================================*/
create table CA2_ASSEMBLY_LINE
(
   ID                   int not null auto_increment,
   SOURCE_CODE_ID       int,
   BRANCHES             varchar(256),
   NAME                 varchar(64),
   REMARK               varchar(256),
   CONFIG               text,
   ADMIN_ID             int,
   AUTO_TRIGGER         boolean,
   TRIGGER_CRON         varchar(64),
   CREATE_TIME          datetime,
   UPDATE_TIME          datetime,
   LAST_RUN_TIME        datetime,
   primary key (ID)
);

/*==============================================================*/
/* Table: CA2_ASSEMBLY_LINE_LOG                                 */
/*==============================================================*/
create table CA2_ASSEMBLY_LINE_LOG
(
   ID                   int not null auto_increment,
   ASSEMBLY_LINE_ID     int,
   PROJECT_ID           int,
   BRANCH               varchar(64),
   COMMIT_ID            varchar(64),
   CONFIG               text,
   ADMIN_ID             int,
   CREATE_TIME          datetime(3),
   START_TIME           datetime(3),
   END_TIME             datetime(3),
   STATUS               tinyint,
   primary key (ID)
);

/*==============================================================*/
/* Table: CA2_ASSEMBLY_LINE_TASK_LOG                            */
/*==============================================================*/
create table CA2_ASSEMBLY_LINE_TASK_LOG
(
   ID                   int not null auto_increment,
   PRODUCT_ID           int,
   ASSEMBLY_LINE_LOG_ID int,
   LIFE_CYCLE           int,
   TASK_INDEX           int,
   CONTENT              mediumtext,
   STATUS               smallint default 0,
   START_TIME           datetime,
   END_TIME             datetime,
   primary key (ID)
);

/*==============================================================*/
/* Table: CA2_FILE_LIST_SHA                                     */
/*==============================================================*/
create table CA2_FILE_LIST_SHA
(
   SHA256               varchar(512) not null,
   FILE_LIST            mediumtext,
   CREATE_TIME          datetime,
   primary key (SHA256)
);

/*==============================================================*/
/* Table: CA2_HOOK_LOG                                          */
/*==============================================================*/
create table CA2_HOOK_LOG
(
   ID                   int not null auto_increment,
   SOURCE               varchar(32),
   DELIVERY             varchar(64),
   REQUEST_HEADER       varchar(1024),
   REQUEST_BODY         text,
   RESPONSE             varchar(1024),
   HANDLE_STATUS        tinyint,
   HANDLE_RESULT        varchar(64),
   EVENT                varchar(32),
   PROJECT_ID           int,
   CREATE_TIME          datetime default CURRENT_TIMESTAMP,
   primary key (ID)
);

/*==============================================================*/
/* Table: CA2_PROJECT                                           */
/*==============================================================*/
create table CA2_PROJECT
(
   ID                   int not null auto_increment,
   TYPE                 tinyint comment '0: java',
   NAME                 varchar(64),
   REMARK               varchar(256),
   VCS_TYPE             tinyint comment '0: git',
   VCS_URL              varchar(256),
   VCS_USER_NAME        varchar(32),
   VCS_PASS_WORD        varchar(32),
   COMPILE_TYPE         tinyint comment '0:maven, 1:gradle',
   CREATE_TIME          datetime,
   USER_ID              int,
   STATUS               tinyint,
   primary key (ID),
   unique key AK_Key_URL (VCS_URL)
);

/*==============================================================*/
/* Table: CA2_PROJECT_BRANCH                                    */
/*==============================================================*/
create table CA2_PROJECT_BRANCH
(
   ID                   int not null auto_increment,
   PROJECT_ID           int,
   BRANCH_NAME          varchar(64),
   LAST_COMMIT_ID       varchar(64),
   LAST_COMMIT_TIME     datetime,
   LAST_COMMIT_USER     varchar(64),
   COMMIT_LOG           mediumtext,
   UPDATE_TIME          datetime,
   AUTO_TYPE            tinyint comment '0无操作  1每次变化自动编译  2每次变化自动install',
   primary key (ID)
);

/*==============================================================*/
/* Table: CA2_PROJECT_PACKAGE                                   */
/*==============================================================*/
create table CA2_PROJECT_PACKAGE
(
   ID                   int not null auto_increment,
   PROJECT_ID           int,
   TYPE                 tinyint not null default 0 comment '1全量更新 2增量更新',
   BRANCH               varchar(64),
   COMMIT_ID            varchar(64),
   CREATE_TIME          datetime,
   FILE_PATH            varchar(128),
   SUFFIX               varchar(16),
   SHA256               varchar(512),
   VERSION              varchar(16),
   REMARK               varchar(256),
   USER_ID              int,
   primary key (ID)
);

/*==============================================================*/
/* Table: CA2_SERVER                                            */
/*==============================================================*/
create table CA2_SERVER
(
   ID                   int not null auto_increment,
   TYPE                 tinyint comment '服务器类型 0: Linux',
   NAME                 varchar(64) comment '服务器名称',
   INSIDE_IP            varchar(32) comment '内网IP',
   OUTSIDE_IP           varchar(32) comment '外网IP',
   SSH_HOST             varchar(32) comment 'SSH地址',
   SSH_PORT             smallint comment 'SSH端口',
   SSH_USER             varchar(16) comment 'SSH用户(请尽量不要用ROOT)',
   SSH_PWD              varchar(32) comment 'SSH密码',
   REMARK               national varchar(256) comment '备注',
   ENVIRONMENT          tinyint comment '环境 0:测试环境  1:正式环境',
   STATUS               tinyint comment '状态 0:未激活  1:激活',
   primary key (ID)
);

/*==============================================================*/
/* Table: CA2_USER                                              */
/*==============================================================*/
create table CA2_USER
(
   ID                   int not null auto_increment,
   USER_NAME            varchar(32),
   PASS_WORD            varchar(32),
   REAL_NAME            varchar(32),
   LEVEL                tinyint,
   STATUS               tinyint,
   UPDATE_TIME          datetime,
   MOBILE               varchar(16),
   EMAIL                varchar(64),
   primary key (ID),
   unique key AK_Key_USER_NAME (USER_NAME)
);

