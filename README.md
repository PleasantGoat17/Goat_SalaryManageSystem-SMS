# 二、要求
1.	在对数据库理论及知识理解的基础上，完成相关的算法与程序，重点是针对具体的实际问题选择并设计合适的数据库表加以应用。
2.	按数据库设计的各阶段完成整个课程设计：

    1）	需求分析：根据选定的题目（可以是“七、”中提供的项目，也可以自拟定），描述项目的需求。
    2）	概念结构设计：根据需求分析的内容进行概念结构的设计，可以借助powerDesigner或workBench等辅助工具进行设计，得到E-R实体联系图、概要设计报告（使用powerDesigner设计的可以直接生成），并给出必要的数据流图。
    该阶段强烈建议使用辅助设计工具，提高生产效率。
    3）	逻辑结构设计：把E-R图转化为MySQL支持的数据库表（使用powerDesigner或workBench设计的可以直接生成），并分析主键、外键是否合理设置，候选码是否都设置了惟一约束或惟一索引（二选一）来保证码的完整性。该阶段同步给出系统的功能模块结构图以及主要的程序流程图。
    该阶段还需考虑设计必要的冗余数据列和冗余数据表，用来提升一些复杂查询、统计查询的效率。
    该阶段还需考虑生产业务数据不允许物理删除只能逻辑删除，以实现数据痕迹保留。
    4）	物理结构设计：进一步分析数据表的属性列哪些经常出现在where查询条件中、哪些经常出现在连接条件中、哪些列经常成对出现在where条件中等，通过分析设计必要的合适的索引（含组合索引）提升查询效率。对于多属性组合主键，可以增加自增ID列作为主键，而把实际的多属性组合主键通过惟一索引设为组合候选码。
    因为课程设计时间短，本阶段不考虑分库分区分表等存储方式的设计。
    5）	数据库实施：选择一种编程工具（VC++、.NET、Java、Python等）编写代码实现设计的数据库系统，或者选择直接在数据库端编写存储过程实现数据库表的增删改并给出测试调用SQL代码。无论选择哪种实现方式，对于系统的基础数据要求全部使用SQL代码进行批量数据插入删除来实施。
    该阶段要求设计必要的视图或存储过程来实现外模式，提高数据独立性；设计必要的触发器实现用户自定义完整性以及冗余数据和冗余表的维护；设计必要的存储过程实现全部统计报表的输出。
    其中：使用编程工具编码实现的需要实现各类单据和各种报表的打印功能，数据库端实现的能通过存储过程实现数据展示即可。
    6）	数据库运行和维护：通过程序界面运行系统进行生产数据的维护以及各类报表的查询打印，或数据库端的测试SQL代码进行生产数据的维护以及报表的输出。
    该阶段要求根据项目实际情况，设计一些数据列属性（数据类型、长度等）改变、自定义完整性约束要求改变等案例，并在数据库端完成这些改变而不需要去修改业务逻辑代码来完成运维，充分体会数据独立性在运维阶段的独特优势。
3.	根据“2”中各阶段的设计完成规范化的课程设计说明书的编写；
4.	在选题时，一人一题，除了“七、”中提供的项目外，同学们也可根据自己现实工作或生活的实际需要和能力，自选课程设计题目，要求难易适中，业务情况容易了解，涉及其他专业的"专业性"不要太强。
5.	部分能力强的同学在完成课程设计的基础上，可考虑通过redis内存数据库来实现登录、基础数据的缓存，以应对未来高并发、大数据量情况下的可用性。

# 三、应交文档资料

1. 课程设计说明书纸质文档，主要包括以下内容：
    1) 课程设计的题目；
    2）数据流图描述的系统功能需求结果；
    3）系统的总功能和各子模块的功能；
    4) 程序流程图及主要算法简述；
    5) E-R实体联系图和数据库表结构的详细情况；
    6) 数据库设计中建立的视图、触发器、存储过程的代码；
    7) 课程设计的总结，主要包括以下内容：
（1）课程设计中遇到的主要问题和解决方法；
（2）创新和得意之处；
（3）课程设计中存在的不足，需进一步改进的设想；
（4）课程设计的感想和心得体会。

以上内容均填写在《课程设计说明书》上，要求干净整洁，符合课程设计的要求和规范（学校教务处或者分院网站下载）。
《课程设计说明书》要求在课程设计结束一周内以“学号-姓名-数据库系统课程设计说明书”命名压缩打包再按班级以“xxx班-数据库系统课程设计说明书”命名压缩打包提交给各自的带队老师。 

### 数据表结构设计

1. **Employee** (员工表)
   - **字段**:
     - `EmployeeID` (员工编号, INT, PK)
     - `Name` (姓名, VARCHAR(50))
     - `TitleID` (职称编号, INT, FK)
     - `DepartmentID` (部门编号, INT, FK)
     - `PositionID` (职位编号, INT, FK)
     - `HireDate` (入职日期, DATE)
     - `BasicSalary` (基本工资, DECIMAL(10, 2))
   - **依赖表**:
     - `Department` (部门表)
     - `Position` (职位表)
     - `Title` (职称表)

2. **Department** (部门表)
   - **字段**:
     - `DepartmentID` (部门编号, INT, PK)
     - `Name` (部门名称, VARCHAR(50))
     - `ParentDepartmentID` (上级部门编号, INT, FK, 自引用)
     - `ManagerID` (部门经理编号, INT, FK)
   - **依赖表**:
     - 自引用 (`ParentDepartmentID` 引用 `DepartmentID`)
     - `Employee` (员工表)（`ManagerID`）

3. **Position** (职位表)
   - **字段**:
     - `PositionID` (职位编号, INT, PK)
     - `Name` (职位名称, VARCHAR(50))
     - `Salary` (职位工资, DECIMAL(10, 2))
   - **依赖表**:
     - 无

4. **Title** (职称表)
   - **字段**:
     - `TitleID` (职称编号, INT, PK)
     - `Name` (职称名称, VARCHAR(50))
     - `Salary` (职称工资, DECIMAL(10, 2))
   - **依赖表**:
     - 无

5. **SalaryCategory** (工资类别表)
   - **字段**:
     - `CategoryID` (类别编号, INT, PK)
     - `Name` (类别名称, VARCHAR(50))
     - `IsManual` (是否手动调整, BOOLEAN)
   - **依赖表**:
     - 无

6. **Attendance** (考勤表)
   - **字段**:
     - `AttendanceID` (考勤编号, INT, PK)
     - `EmployeeID` (员工编号, INT, FK)
     - `Date` (日期, DATE)
     - `Type` (考勤类型, NVARCHAR(50))
   - **依赖表**:
     - `Employee` (员工表)

7. **MonthlySalary** (月度工资表)
   - **字段**:
     - `SalaryID` (工资编号, INT, PK)
     - `EmployeeID` (员工编号, INT, FK)
     - Month (月份, DATE)
     - BasicSalary (基本工资, DECIMAL(10, 2))
     - PositionSalary (职位工资, DECIMAL(10, 2))
     - TitleSalary (职称工资, DECIMAL(10, 2))
     - TenureSalary (工龄工资, DECIMAL(10, 2))
     - AttendanceDeduction (考勤扣除, DECIMAL(10, 2))
     - FullAttendanceBonus (全勤奖励, DECIMAL(10, 2))
     - TotalSalary (总工资, DECIMAL(10, 2))
   - **依赖表**:
     - Employee (员工表)

8. **TenureSalary** (工龄工资表)
   - **字段**:
     - EmployeeID (员工编号, INT, PK, FK)
     - Tenure (工龄, INT)
     - Salary (工龄工资, DECIMAL(10, 2))
   - **依赖表**:
     - Employee (员工表)

9. **NewTenureSalary** (新工龄工资表)
   - **字段**:
     - EmployeeID (员工编号, INT, PK, FK)
     - NewTenure (新工龄, INT)
     - EffectiveDate (生效日期, DATE)
     - EndDate (结束日期, DATE)
   - **依赖表**:
     - Employee (员工表)

10. **SalaryAdjustment** (工资调整表)
    - **字段**:
      - AdjustmentID (调整编号, INT, PK)
      - EmployeeID (员工编号, INT, FK)
      - CategoryID (类别编号, INT, FK)
      - Amount (金额, DECIMAL(10, 2))
      - Month (月份, DATE)
    - **依赖表**:
      - Employee (员工表)
      - SalaryCategory (工资类别表)

11. **EmployeeTitleHistory** (员工职称历史表)
    - **字段**:
      - EmployeeID (员工编号, INT, PK, FK)
      - TitleID (职称编号, INT, PK, FK)
      - StartDate (开始日期, DATE, PK)
      - EndDate (结束日期, DATE)
    - **依赖表**:
      - Employee (员工表)
      - Title (职称表)

### 视图设计

1. **DepartmentView** (部门视图)
   - **字段**:
     - DepartmentID (部门编号, INT)
     - Name (部门名称, VARCHAR(50))
     - ManagerName (部门经理姓名, VARCHAR(50))
   - **依赖表**:
     - Department (部门表)
     - Employee (员工表)

2. **EmployeeView** (员工视图)
   - **字段**:
     - `EmployeeID` (员工编号, INT)
     - `Name` (姓名, VARCHAR(50))
     - `DepartmentName` (部门名称, VARCHAR(50))
     - `PositionName` (职位名称, VARCHAR(50))
     - `TitleName` (职称名称, VARCHAR(50))
     - `HireDate` (入职日期, DATE)
   - **依赖表**:
     - `Employee` (员工表)
     - `Department` (部门表)
     - `Position` (职位表)
     - `Title` (职称表)
     - `EmployeeTitleHistory` (员工职称历史表)

3. **AttendanceView** (考勤视图)
   - **字段**:
     - `AttendanceID` (考勤编号, INT)
     - `EmployeeID` (员工编号, INT)
     - `EmployeeName` (员工姓名, VARCHAR(50))
     - `Date` (日期, DATE)
     - `Type` (考勤类型, NVARCHAR(50))
   - **依赖表**:
     - `Attendance` (考勤表)
     - `Employee` (员工表)

4. **MonthlySalaryView** (月度工资视图)
   - **字段**:
     - `SalaryID` (工资编号, INT)
     - `EmployeeID` (员工编号, INT)
     - `EmployeeName` (员工姓名, VARCHAR(50))
     - `Month` (月份, DATE)
     - `BasicSalary` (基本工资, DECIMAL(10, 2))
     - `PositionSalary` (职位工资, DECIMAL(10, 2))
     - `TitleSalary` (职称工资, DECIMAL(10, 2))
     - `TenureSalary` (工龄工资, DECIMAL(10, 2))
     - `AttendanceDeduction` (考勤扣除, DECIMAL(10, 2))
     - `FullAttendanceBonus` (全勤奖励, DECIMAL(10, 2))
     - `TotalSalary` (总工资, DECIMAL(10, 2))
   - **依赖表**:
     - `MonthlySalary` (月度工资表)
     - `Employee` (员工表)

### 触发器设计

1. **AttendanceTrigger** (考勤触发器)
   - **描述**: 用于在考勤数据修改时实时更新缺勤工资和全勤奖励。
   - **依赖表**:
     - `Attendance` (考勤表)
     - `MonthlySalary` (月度工资表)

### 定时任务设计

1. **MonthlySalaryCalculationJob** (月度工资计算任务)
   - **描述**: 用于在工资发放日的凌晨一点计算新的月度工资发放表。
   - **依赖表**:
     - Employee (员工表)
     - Position (职位表)
     - Title (职称表)
     - TenureSalary (工龄工资表)
     - NewTenureSalary (新工龄工资表)
     - Attendance (考勤表)
     - SalaryAdjustment (工资调整表)
     - MonthlySalary (月度工资表)

