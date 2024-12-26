# 工资管理系统使用说明

作者：Lau_Zega | PleasantGoat17

## 系统简介

系统概述：通过对人事管理管理部门中的岗位、职称、工资、员工、部门、工资类别等相关内容进行分析，完成具有人员管理、工资管理、部门管理等相关功能的小型数据库管理应用系统，系统需要具备增减工资中应发、应扣类别的灵活性，以适应将来需求的变化。
基本要求 ：
需求如下：
- 一个员工属于某个部门，一个部门有多个员工，每个部门有部门经理；
- 各个部门之上有总部，总部设置总经理、副总经理；每个部门有下属的分部门，各个分部门也设定分部经理；总部、部门、分部形成了多级管理；
- 一个员工有一个职称，一个职称可以有多个员工，员工在不同时期具有的不同职称需要保留供查询；
- 集团每个月都会产生一份工资表，保留备查以及可能的分析统计。
- 每个职称有职称工资，属于工资类别的一种；员工当月工资发放之前改变了职称，当月工资按新的职称计算职称工资；员工当月工资发放之后改变了职称，下月工资按新的职称计算职称工资；
- 除了总经理、副总经理、部门经理、分部经理之外，还可设置项目经理、产品经理、测试经理、研发工程师、项目助理等岗位，岗位工资属于工资类别的一种；员工当月工资发放之前改变了岗位，当月工资按新的岗位计算岗位工资；员工当月工资发放之后改变了岗位，下月工资按新的岗位计算岗位工资；
- 员工在公司服务期间，每年/每月300元工龄工资；比如：满1年每月300元工龄工资、满2年每月600元工龄工资、依次递进；工龄的计算以入职时间的年月为依据，比如：2022-01-10入职，2023-02月开始每月有300元的工龄工资，2024-02月开始每月有600元的工龄工资；
- 员工请假1天扣100元，迟到早退1次扣50元，旷工1天扣300元，满勤奖励300元，系统要根据考勤情况计算缺勤工资、全勤奖励；
- 除了岗位工资、职称工资、工龄工资、缺勤工资、全勤奖励之外，工资类别还可以设计基本工资、项目补贴、个税扣除等；
- 每月工资设定发放日，上月发放日之后到本月发放日之前，员工的各工资类别可以获取的工资可以手动修改的（比如项目补贴、个税扣除、缺勤扣除等）用户可以修改，系统计算的（比如岗位工资、职称工程、基本工资、工龄工资）只能通过“计算”功能进行计算，“计算”功能会把用户手工输入和系统计算的全部一起计算后生成当月工资；
- 工资发放日之后不能修改发放日之前的工资；在新的发放日的凌晨一点设定定时任务，计算新的月度的工资发放表初始数据；
- 输出各类统计报表。使用编程工具编码实现的建议所有的报表数据用存储过程实现数据获取，降低网络传输量，利用集合运算的优势提升查询速度，提高数据独立性。
1. 完成岗位、职称、员工、部门、工资类别等基础数据的维护（增删改查），并进行数据准备。注意其中部门是多级部门。其中员工和部门数据的获取要求通过视图实现，要求员工信息视图直接输出部门名称，部门信息视图直接输出部门经理姓名。
2. 完成考勤表的维护（增删改查）。
3. 完成需要手动录入的员工各工资类别的月度工资（基本工资、项目补贴、个税扣除）的维护（增删改查），其中基本工资一般和上月一样，特殊情况才需要修改。
4. 工龄工资要求在数据库端完成全部的计算并保存在单独的一张表。
5. 缺勤工资要求利用触发器、数据表在手动增删改考勤数据时实时动态更新。
6. 设计存储过程完成“计算”功能，生成当月工资发放表；工资发放表生成后，可以按员工、部门进行查询、打印。
7. 完成定时任务的设计。
8. 按年度计算各工资类别的合计金额以及分别占年度工资发放总额的比率，并输出报表。
9. 按部门计算月度最高、最低、平均工资、年度最高、最低、平均工资并输出；按员工计算年度平均工资并输出
10. 设定2类角色：个人、财务，个人只能查看自己的工资，财务可以查看全部员工的工资，另外：总经理、副总经理、部门经理、分部经理虽然是个人角色，但可查看各自部门员工（含个人）的工资。个人查看工资要求用存储过程实现，且要求如果个人职务是分部经理及以上，看到的是含个人的本部门全部员工的工资，其中个人的工资置顶。
11. 运维需求变更案例：由于全球的经济形势下滑，公司调整了工龄工资的计算方法为：1年工龄仍为300元/月，但从第2年开始，每年只增加100元/月，即2年工龄工资400元/月，3年工龄工资500元/月。并要求原来的计算方法保留且正常计算，在公司恢复经济活力后，会恢复原来的计算方法，按新的计算方法少发的工龄工资将适度补发。提示：登记新的计算方法生效日期、结束日期，新增一张表保存按新方法计算出来的工龄工资，“计算”存储过程取工龄工资时判断发放月度是否在新方法的有效期内，在有效期内则取新表的工龄工资，否则取旧表的数据。新方法结束日期为空取当前时间的日期。通过这样处理，修改新方法的生效日期结束日期就可以随时在新旧方法间进行切换，而无需修改任何应用程序代码，实现了比较高的数据独立性。

本题目所需的知识点：E-R实体联系图；数据库表设计；数据库表维护；视图；触发器；存储过程；数据独立性；定时任务等。

## 使用步骤

### 1.初始化

在IDEA中打开项目，点击`构建`构建项目。

运行数据库，修改`HomePage`中的`databaseConnection()`方法。填入数据库URL和数据库用户名，密码。

运行数据库转储文件`lib/gongzi.sql`创建表。

### 2.运行

运行主类 `HomePage.java`（实际已经添加到配置中，如果使用idea的话）

注册的用户初始密码都为 `123456`

### 感想

早期作品，还不会使用Maven来管理依赖，`awt`和`swing`还是有局限性的，打算利用`JavaFX`来制作更高效，更美观的图形化界面。