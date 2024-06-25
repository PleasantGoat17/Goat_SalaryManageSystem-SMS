/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2024/6/24 17:19:27                           */
/*==============================================================*/


drop table if exists Attendance;

drop table if exists Department;

drop table if exists Employee;

drop table if exists EmployeeTitleHistory;

drop table if exists MonthlySalary;

drop table if exists NewTenureSalary;

drop table if exists Position;

drop table if exists SalaryAdjustment;

drop table if exists SalaryCategory;

drop table if exists TenureSalary;

drop table if exists Title;

drop table if exists admin;

drop table if exists user;

/*==============================================================*/
/* Table: Attendance                                            */
/*==============================================================*/
create table Attendance
(
   AttendanceID         int not null,
   EmployeeID           int,
   Date                 date,
   Type                 varchar(50),
   primary key (AttendanceID)
);

alter table Attendance comment 'Attendance (考勤表)

AttendanceID (考勤编号, INT, PK)
                               ';

/*==============================================================*/
/* Table: Department                                            */
/*==============================================================*/
create table Department
(
   DepartmentID         int not null,
   Name                 varchar(50),
   ParentDepartmentID   int,
   EmployeeID           int,
   ManagerID            int,
   primary key (DepartmentID)
);

alter table Department comment 'Department (部门)

DepartmentID (部门编号, PK)
N';

/*==============================================================*/
/* Table: Employee                                              */
/*==============================================================*/
create table Employee
(
   EmployeeID           int not null,
   Name                 varchar(50),
   TitleID              int,
   PositionID           int,
   DepartmentID         int,
   HireDate             date not null,
   BasicSalary          decimal(10,2),
   primary key (EmployeeID)
);

alter table Employee comment 'Employee (员工表)

EmployeeID (员工编号, INT, PK)
                             -&#&';

/*==============================================================*/
/* Table: EmployeeTitleHistory                                  */
/*==============================================================*/
create table EmployeeTitleHistory
(
   EmployeeID           int not null,
   TitleID              int not null,
   StartDate            date not null,
   EndDate              date,
   primary key (EmployeeID, TitleID, StartDate)
);

alter table EmployeeTitleHistory comment 'EmployeeTitleHistory (员工职称历史表)

EmployeeID (员工编号';

/*==============================================================*/
/* Table: MonthlySalary                                         */
/*==============================================================*/
create table MonthlySalary
(
   SalaryID             int not null,
   EmployeeID           int,
   Month                date,
   BasicSalary          decimal(10,2),
   PositionSalary       decimal(10,2),
   TitleSalary          decimal(10,2),
   TenureSalary         decimal(10,2),
   AttendanceDeduction  decimal(10,2),
   FullAttendanceBonus  decimal(10,2),
   TotalSalary          decimal(10,2),
   primary key (SalaryID)
);

alter table MonthlySalary comment 'MonthlySalary (月度工资表)

SalaryID (工资编号, INT, PK)
';

/*==============================================================*/
/* Table: NewTenureSalary                                       */
/*==============================================================*/
create table NewTenureSalary
(
   EmployeeID           int not null,
   NewTenure            int,
   EffectiveDate        date,
   EndDate              date,
   primary key (EmployeeID)
);

alter table NewTenureSalary comment 'NewTenureSalary (新工龄工资表)

EmployeeID (员工编号, INT,';

/*==============================================================*/
/* Table: Position                                              */
/*==============================================================*/
create table Position
(
   PositionID           int not null,
   Name                 varchar(50),
   Salary               decimal(10,2),
   primary key (PositionID)
);

alter table Position comment 'Position (职位表)

PositionID (职位编号, INT, PK)
                             -&#&';

/*==============================================================*/
/* Table: SalaryAdjustment                                      */
/*==============================================================*/
create table SalaryAdjustment
(
   AdjustmentID         int not null,
   CategoryID           int,
   EmployeeID           int,
   Amount               decimal(10,2),
   Month                date,
   primary key (AdjustmentID)
);

alter table SalaryAdjustment comment 'SalaryAdjustment (工资调整表)

AdjustmentID (调整编号, IN';

/*==============================================================*/
/* Table: SalaryCategory                                        */
/*==============================================================*/
create table SalaryCategory
(
   CategoryID           int not null,
   EmployeeID           int,
   Name                 varchar(50),
   IsManual             bool,
   primary key (CategoryID)
);

alter table SalaryCategory comment 'SalaryCategory (工资类别表)

CategoryID (类别编号, INT, P';

/*==============================================================*/
/* Table: TenureSalary                                          */
/*==============================================================*/
create table TenureSalary
(
   EmployeeID           int not null,
   Tenure               int,
   Salary               decimal(10,2),
   primary key (EmployeeID)
);

alter table TenureSalary comment 'TenureSalary (工龄工资表)

EmployeeID (员工编号, INT, PK,';

/*==============================================================*/
/* Table: Title                                                 */
/*==============================================================*/
create table Title
(
   TitleID              int not null,
   Name                 varchar(50),
   Salary               decimal(10,2),
   primary key (TitleID)
);

alter table Title comment 'Position (职位表)

PositionID (职位编号, INT, PK)
                          -&#&';

/*==============================================================*/
/* Table: admin                                                 */
/*==============================================================*/
create table admin
(
   admin_num            char(10) not null,
   password             char(10),
   primary key (admin_num)
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   user_num             char(10) not null,
   password             char(10),
   primary key (user_num)
);

alter table Attendance add constraint FK_Reference_10 foreign key (EmployeeID)
      references Employee (EmployeeID) on delete restrict on update restrict;

alter table Department add constraint FK_Reference_7 foreign key (EmployeeID)
      references Employee (EmployeeID) on delete restrict on update restrict;

alter table Department add constraint FK_Reference_8 foreign key (ParentDepartmentID)
      references Department (DepartmentID) on delete restrict on update restrict;

alter table Department add constraint FK_Reference_9 foreign key (ManagerID)
      references Employee (EmployeeID) on delete restrict on update restrict;

alter table Employee add constraint FK_Reference_3 foreign key (TitleID)
      references Title (TitleID) on delete restrict on update restrict;

alter table Employee add constraint FK_Reference_6 foreign key (PositionID)
      references Position (PositionID) on delete restrict on update restrict;

alter table EmployeeTitleHistory add constraint FK_Reference_14 foreign key (EmployeeID)
      references Employee (EmployeeID) on delete restrict on update restrict;

alter table EmployeeTitleHistory add constraint FK_Reference_15 foreign key (TitleID)
      references Title (TitleID) on delete restrict on update restrict;

alter table MonthlySalary add constraint FK_Reference_11 foreign key (EmployeeID)
      references Employee (EmployeeID) on delete restrict on update restrict;

alter table NewTenureSalary add constraint FK_Reference_13 foreign key (EmployeeID)
      references Employee (EmployeeID) on delete restrict on update restrict;

alter table SalaryAdjustment add constraint FK_Reference_17 foreign key (EmployeeID)
      references Employee (EmployeeID) on delete restrict on update restrict;

alter table SalaryAdjustment add constraint FK_Reference_18 foreign key (CategoryID)
      references SalaryCategory (CategoryID) on delete restrict on update restrict;

alter table SalaryCategory add constraint FK_Reference_16 foreign key (EmployeeID)
      references Employee (EmployeeID) on delete restrict on update restrict;

alter table TenureSalary add constraint FK_Reference_12 foreign key (EmployeeID)
      references Employee (EmployeeID) on delete restrict on update restrict;

