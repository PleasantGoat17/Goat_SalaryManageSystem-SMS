/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2024/6/24 17:19:27                           */
/*==============================================================*/
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS Attendance;

DROP TABLE IF EXISTS Department;

DROP TABLE IF EXISTS Employee;

DROP TABLE IF EXISTS EmployeeTitleHistory;

DROP TABLE IF EXISTS MonthlySalary;

DROP TABLE IF EXISTS NewTenureSalary;

DROP TABLE IF EXISTS Position;

DROP TABLE IF EXISTS SalaryAdjustment;

DROP TABLE IF EXISTS SalaryCategory;

DROP TABLE IF EXISTS TenureSalary;

DROP TABLE IF EXISTS Title;

DROP TABLE IF EXISTS admin;

DROP TABLE IF EXISTS user;

DROP TABLE IF EXISTS Config;

DROP VIEW IF EXISTS EmployeeView;

DROP VIEW IF EXISTS DepartmentView;

DROP VIEW IF EXISTS MonthlySalaryView;
/*==============================================================*/
/* TABLE: TITLE                                                 */
/*==============================================================*/
CREATE TABLE Title
(
   TitleID              INT NOT NULL AUTO_INCREMENT,
   Name                 VARCHAR(50),
   Salary               DECIMAL(10,2),
   PRIMARY KEY (TitleID)
);

ALTER TABLE Title COMMENT 'TitleID (职称编号, INT, PK)
Name (职称名称, VARCHAR(50))
Salary (职称工资, DECIMAL(10, 2))';

/*==============================================================*/
/* TABLE: ATTENDANCE                                            */
/*==============================================================*/
CREATE TABLE Attendance
(
   AttendanceID         INT NOT NULL AUTO_INCREMENT,
   EmployeeID           INT,
   Date                 DATE,
   Type                 VARCHAR(50),
   PRIMARY KEY (AttendanceID)
);

ALTER TABLE Attendance COMMENT 'AttendanceID (考勤编号, INT, PK)
EmployeeID (员工编号, INT, FK)
Date (日期, DATE)
Type (考勤类型, NVARCHAR(50))';

/*==============================================================*/
/* TABLE: DEPARTMENT                                            */
/*==============================================================*/
CREATE TABLE Department
(
   DepartmentID         INT NOT NULL AUTO_INCREMENT,
   Name                 VARCHAR(50),
   ParentDepartmentID   INT,
   ManagerID            INT,
   PRIMARY KEY (DepartmentID)
);

ALTER TABLE Department COMMENT 'DepartmentID (部门编号, INT, PK)
Name (部门名称, VARCHAR(50))
ParentDepartmentID (上级部门编号, INT, FK, 自引用)
ManagerID (部门经理编号, INT, FK)';

/*==============================================================*/
/* TABLE: EMPLOYEE                                              */
/*==============================================================*/
CREATE TABLE Employee
(
   EmployeeID           INT NOT NULL AUTO_INCREMENT,
   Name                 VARCHAR(50),
   TitleID              INT,
   PositionID           INT,
   DepartmentID         INT,
   HireDate             DATE NOT NULL,
   BasicSalary          DECIMAL(10,2),
   PRIMARY KEY (EmployeeID)
);

ALTER TABLE Employee COMMENT 'EmployeeID (员工编号, INT, PK)
Name (姓名, VARCHAR(50))
TitleID (职称编号, INT, FK)
DepartmentID (部门编号, INT, FK)
PositionID (职位编号, INT, FK)
HireDate (入职日期, DATE)
BasicSalary (基本工资, DECIMAL(10, 2))';

/*==============================================================*/
/* TABLE: EMPLOYEETITLEHISTORY                                  */
/*==============================================================*/
CREATE TABLE EmployeeTitleHistory
(
   EmployeeID           INT NOT NULL,
   TitleID              INT NOT NULL,
   StartDate            DATE NOT NULL,
   EndDate              DATE,
   PRIMARY KEY (EmployeeID, TitleID, StartDate)
);

ALTER TABLE EmployeeTitleHistory COMMENT 'EmployeeID (员工编号, INT, PK, FK)
TitleID (职称编号, INT, PK, FK)
StartDate (开始日期, DATE, PK)
EndDate (结束日期, DATE)';

/*==============================================================*/
/* TABLE: MONTHLYSALARY                                         */
/*==============================================================*/
CREATE TABLE MonthlySalary
(
   SalaryID             INT NOT NULL AUTO_INCREMENT,
   EmployeeID           INT,
   Month                DATE,
   BasicSalary          DECIMAL(10,2),
   PositionSalary       DECIMAL(10,2),
   TitleSalary          DECIMAL(10,2),
   TenureSalary         DECIMAL(10,2),
   AttendanceDeduction  DECIMAL(10,2),
   FullAttendanceBonus  DECIMAL(10,2),
   TotalSalary          DECIMAL(10,2),
   PRIMARY KEY (SalaryID)
);

ALTER TABLE MonthlySalary COMMENT 'SalaryID (工资编号, INT)
EmployeeID (员工编号, INT)
EmployeeName (员工姓名, VARCHAR(50))
Month (月份, DATE)
BasicSalary (基本工资, DECIMAL(10, 2))
PositionSalary (职位工资, DECIMAL(10, 2))
TitleSalary (职称工资, DECIMAL(10, 2))
TenureSalary (工龄工资, DECIMAL(10, 2))
AttendanceDeduction (考勤扣除, DECIMAL(10, 2))
FullAttendanceBonus (全勤奖励, DECIMAL(10, 2))
TotalSalary (总工资, DECIMAL(10, 2))';

/*==============================================================*/
/* TABLE: NEWTENURESALARY                                       */
/*==============================================================*/
CREATE TABLE NewTenureSalary
(
   EmployeeID           INT NOT NULL,
   NewTenure            INT,
   EffectiveDate        DATE,
   EndDate              DATE,
   PRIMARY KEY (EmployeeID)
);

ALTER TABLE NewTenureSalary COMMENT 'EmployeeID (员工编号, INT, PK, FK)
NewTenure (新工龄, INT)
EffectiveDate (生效日期, DATE)
EndDate (结束日期, DATE)';

/*==============================================================*/
/* TABLE: POSITION                                              */
/*==============================================================*/
CREATE TABLE Position
(
   PositionID           INT NOT NULL AUTO_INCREMENT,
   Name                 VARCHAR(50),
   Salary               DECIMAL(10,2),
   PRIMARY KEY (PositionID)
);

ALTER TABLE Position COMMENT 'PositionID (职位编号, INT, PK)
Name (职位名称, VARCHAR(50))
Salary (职位工资, DECIMAL(10, 2))';

/*==============================================================*/
/* TABLE: SALARYADJUSTMENT                                      */
/*==============================================================*/
CREATE TABLE SalaryAdjustment
(
   AdjustmentID         INT NOT NULL AUTO_INCREMENT,
   CategoryID           INT,
   EmployeeID           INT,
   Amount               DECIMAL(10,2),
   Month                DATE,
   PRIMARY KEY (AdjustmentID)
);

ALTER TABLE SalaryAdjustment COMMENT 'AdjustmentID (调整编号, INT, PK)
EmployeeID (员工编号, INT, FK)
CategoryID (类别编号, INT, FK)
Amount (金额, DECIMAL(10, 2))
Month (月份, DATE)';

/*==============================================================*/
/* TABLE: SALARYCATEGORY                                        */
/*==============================================================*/
CREATE TABLE SalaryCategory
(
   CategoryID           INT NOT NULL AUTO_INCREMENT,
   Name                 VARCHAR(50),
   IsManual             BOOL,
   PRIMARY KEY (CategoryID)
);

ALTER TABLE SalaryCategory COMMENT 'CategoryID (类别编号, INT, PK)
Name (类别名称, VARCHAR(50))
IsManual (是否手动调整, BOOLEAN)';

/*==============================================================*/
/* TABLE: TENURESALARY                                          */
/*==============================================================*/
CREATE TABLE TenureSalary
(
   EmployeeID           INT NOT NULL,
   Tenure               INT,
   Salary               DECIMAL(10,2),
   PRIMARY KEY (EmployeeID)
);

ALTER TABLE TenureSalary COMMENT 'EmployeeID (员工编号, INT, PK, FK)
Tenure (工龄, INT)
Salary (工龄工资, DECIMAL(10, 2))';


/*==============================================================*/
/* TABLE: ADMIN                                                 */
/*==============================================================*/
CREATE TABLE admin
(
   admin_num            CHAR(10) NOT NULL,
   password             CHAR(10),
   PRIMARY KEY (admin_num)
);

/*==============================================================*/
/* TABLE: USER                                                  */
/*==============================================================*/
CREATE TABLE user
(
   user_num             CHAR(10) NOT NULL,
   password             CHAR(10),
   PRIMARY KEY (user_num)
);

CREATE TABLE Config (
    ConfigKey VARCHAR(50) PRIMARY KEY,
    ConfigValue VARCHAR(50)
);


-- 建立部门视图
CREATE VIEW DepartmentView AS
SELECT 
    d.DepartmentID,
    d.Name AS DepartmentName,
    e.Name AS ManagerName,
    pd.Name AS ParentDepartmentName
FROM 
    Department d
LEFT JOIN 
    Employee e ON d.ManagerID = e.EmployeeID
LEFT JOIN 
    Department pd ON d.ParentDepartmentID = pd.DepartmentID;


-- 建立员工视图
CREATE VIEW EmployeeView AS
SELECT 
    e.EmployeeID,
    e.Name AS EmployeeName,
    d.Name AS DepartmentName,
    p.Name AS PositionName,
    t.Name AS TitleName,
    e.HireDate
FROM 
    Employee e
LEFT JOIN 
    Department d ON e.DepartmentID = d.DepartmentID
LEFT JOIN 
    Position p ON e.PositionID = p.PositionID
LEFT JOIN 
    Title t ON e.TitleID = t.TitleID;

-- 建立月度工资视图

-- 建立月度工资视图
CREATE OR REPLACE VIEW MonthlySalaryView AS
SELECT 
    e.EmployeeID,
    e.Name AS EmployeeName,
    DATE_FORMAT(CURDATE(), '%Y-%m') AS Month,
    COALESCE(e.BasicSalary, 0) AS BasicSalary,
    COALESCE(p.Salary, 0) AS PositionSalary,
    COALESCE(t.Salary, 0) AS TitleSalary,
    COALESCE(ts.Salary, 0) AS TenureSalary,
    -- 计算考勤扣除
    COALESCE((SELECT 
        SUM(
            CASE 
                WHEN a.Type = '旷工' THEN 300
                WHEN a.Type = '迟到早退' THEN 50
                WHEN a.Type = '请假' THEN 100
                ELSE 0
            END
        )
     FROM Attendance a
     WHERE a.EmployeeID = e.EmployeeID
     AND DATE_FORMAT(a.Date, '%Y-%m') = DATE_FORMAT(CURDATE(), '%Y-%m')), 0) AS AttendanceDeduction,
    -- 计算全勤奖励
    CASE 
        WHEN (SELECT COUNT(*) FROM Attendance a 
              WHERE a.EmployeeID = e.EmployeeID
              AND DATE_FORMAT(a.Date, '%Y-%m') = DATE_FORMAT(CURDATE(), '%Y-%m')) = 0 THEN 300
        ELSE 0
    END AS FullAttendanceBonus,
    -- 计算总工资
    COALESCE(e.BasicSalary, 0) + COALESCE(p.Salary, 0) + COALESCE(t.Salary, 0) + COALESCE(ts.Salary, 0) - 
    COALESCE((SELECT 
        SUM(
            CASE 
                WHEN a.Type = '旷工' THEN 300
                WHEN a.Type = '迟到早退' THEN 50
                WHEN a.Type = '请假' THEN 100
                ELSE 0
            END
        )
     FROM Attendance a
     WHERE a.EmployeeID = e.EmployeeID
     AND DATE_FORMAT(a.Date, '%Y-%m') = DATE_FORMAT(CURDATE(), '%Y-%m')), 0) +
    CASE 
        WHEN (SELECT COUNT(*) FROM Attendance a 
              WHERE a.EmployeeID = e.EmployeeID
              AND DATE_FORMAT(a.Date, '%Y-%m') = DATE_FORMAT(CURDATE(), '%Y-%m')) = 0 THEN 300
        ELSE 0
    END AS TotalSalary
FROM 
    Employee e
LEFT JOIN 
    Position p ON e.PositionID = p.PositionID
LEFT JOIN 
    Title t ON e.TitleID = t.TitleID
LEFT JOIN 
    TenureSalary ts ON e.EmployeeID = ts.EmployeeID;



-- 建立外键约束

SET FOREIGN_KEY_CHECKS = 1;

ALTER TABLE Attendance ADD CONSTRAINT FK_Reference_10 FOREIGN KEY (EmployeeID)
      REFERENCES Employee (EmployeeID) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE Department ADD CONSTRAINT FK_Reference_8 FOREIGN KEY (ParentDepartmentID)
      REFERENCES Department (DepartmentID) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE Employee ADD CONSTRAINT FK_Reference_3 FOREIGN KEY (TitleID)
      REFERENCES Title (TitleID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE Employee ADD CONSTRAINT FK_Reference_6 FOREIGN KEY (PositionID)
      REFERENCES Position (PositionID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE EmployeeTitleHistory ADD CONSTRAINT FK_Reference_14 FOREIGN KEY (EmployeeID)
      REFERENCES Employee (EmployeeID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE EmployeeTitleHistory ADD CONSTRAINT FK_Reference_15 FOREIGN KEY (TitleID)
      REFERENCES Title (TitleID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE MonthlySalary ADD CONSTRAINT FK_Reference_11 FOREIGN KEY (EmployeeID)
      REFERENCES Employee (EmployeeID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE NewTenureSalary ADD CONSTRAINT FK_Reference_13 FOREIGN KEY (EmployeeID)
      REFERENCES Employee (EmployeeID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE SalaryAdjustment ADD CONSTRAINT FK_Reference_17 FOREIGN KEY (EmployeeID)
      REFERENCES Employee (EmployeeID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE SalaryAdjustment ADD CONSTRAINT FK_Reference_18 FOREIGN KEY (CategoryID)
      REFERENCES SalaryCategory (CategoryID) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE TenureSalary ADD CONSTRAINT FK_Reference_12 FOREIGN KEY (EmployeeID)
      REFERENCES Employee (EmployeeID) ON DELETE RESTRICT ON UPDATE RESTRICT;

DROP PROCEDURE IF EXISTS CalculateAttendance;

DELIMITER //

-- 存储过程: 计算考勤扣除和全勤奖励
CREATE PROCEDURE CalculateAttendance(IN employeeID INT, IN attendanceMonth DATE, OUT deduction DECIMAL(10,2), OUT bonus DECIMAL(10,2))
BEGIN
    DECLARE recordType VARCHAR(50);
    DECLARE done INT DEFAULT 0;
    DECLARE cur CURSOR FOR 
        SELECT Type FROM Attendance 
        WHERE EmployeeID = employeeID AND DATE_FORMAT(Date, '%Y-%m') = DATE_FORMAT(attendanceMonth, '%Y-%m');
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
    
    SET deduction = 0;
    SET bonus = 300;
    
    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO recordType;
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        SET bonus = 0;
        IF recordType = '旷工' THEN
            SET deduction = deduction + 300;
        ELSEIF recordType = '迟到早退' THEN
            SET deduction = deduction + 50;
        ELSEIF recordType = '请假' THEN
            SET deduction = deduction + 100;
        END IF;
    END LOOP;
    CLOSE cur;
END //

DROP TRIGGER IF EXISTS trg_update_monthly_salary_after_insert;

-- 插入触发器: 在考勤记录插入时更新月度工资表
CREATE TRIGGER trg_update_monthly_salary_after_insert
AFTER INSERT ON Attendance
FOR EACH ROW
BEGIN
    DECLARE attendanceMonth DATE;
    DECLARE deduction DECIMAL(10,2);
    DECLARE bonus DECIMAL(10,2);
    
    -- 获取考勤记录的月份
    SET attendanceMonth = DATE_FORMAT(NEW.Date, '%Y-%m-01');
    
    -- 调用存储过程计算考勤扣除和全勤奖励
    CALL CalculateAttendance(NEW.EmployeeID, attendanceMonth, deduction, bonus);
    
    -- 更新月度工资表
    UPDATE MonthlySalary 
    SET AttendanceDeduction = deduction, FullAttendanceBonus = bonus 
    WHERE EmployeeID = NEW.EmployeeID AND DATE_FORMAT(Month, '%Y-%m') = DATE_FORMAT(attendanceMonth, '%Y-%m');
END //

-- 删除触发器: 在考勤记录删除时更新月度工资表
CREATE TRIGGER trg_update_monthly_salary_after_delete
AFTER DELETE ON Attendance
FOR EACH ROW
BEGIN
    DECLARE attendanceMonth DATE;
    DECLARE deduction DECIMAL(10,2);
    DECLARE bonus DECIMAL(10,2);
    
    -- 获取考勤记录的月份
    SET attendanceMonth = DATE_FORMAT(OLD.Date, '%Y-%m-01');
    
    -- 调用存储过程计算考勤扣除和全勤奖励
    CALL CalculateAttendance(OLD.EmployeeID, attendanceMonth, deduction, bonus);
    
    -- 更新月度工资表
    UPDATE MonthlySalary 
    SET AttendanceDeduction = deduction, FullAttendanceBonus = bonus 
    WHERE EmployeeID = OLD.EmployeeID AND DATE_FORMAT(Month, '%Y-%m') = DATE_FORMAT(attendanceMonth, '%Y-%m');
END //

DELIMITER ;


DROP PROCEDURE IF EXISTS GetEmployeeBasicSalary;

DELIMITER //

CREATE PROCEDURE GetEmployeeBasicSalary(IN eID INT, OUT basicSalary DECIMAL(10,2))
BEGIN
    DECLARE bS DECIMAL(10,2);

    SELECT BasicSalary INTO bS
    FROM Employee
    WHERE EmployeeID = eID;

    SET basicSalary = bS;

END //

DELIMITER ;




DROP TRIGGER IF EXISTS after_employee_insert_add_user;
-- 自动生成账号密码

DELIMITER //  
CREATE TRIGGER after_employee_insert_add_user
AFTER INSERT ON Employee  
FOR EACH ROW  
BEGIN  
    DECLARE username VARCHAR(10);  
    DECLARE pwd VARCHAR(10);  
  
    -- 设置新插入的员工的账户名和密码（账户名为员工编号）  
    SET username = NEW.EmployeeID;  
    SET pwd = NEW.EmployeeID; -- 密码初始化为账户名 
  
    -- 在admin表中插入新的账户和密码  
    INSERT INTO user (user_num, password) VALUES (username, pwd);  
END;  
//  
DELIMITER ;


DROP TRIGGER IF EXISTS after_department_insert_add_admin;
-- 自动生成管理员账号密码


DELIMITER //  
CREATE TRIGGER after_department_insert_add_admin
AFTER INSERT ON Department  
FOR EACH ROW  
BEGIN  
    DECLARE manager_id VARCHAR(10);  
    DECLARE pwd VARCHAR(10);
  
    
    SET manager_id = NEW.ManagerID;
    SET pwd = NEW.ManagerID;-- 密码初始化为账户名
  
    IF manager_id IS NOT NULL THEN  
        -- 在super_admin表中插入新记录，使用员工的名字作为初始用户名和密码  
        INSERT INTO admin (admin_num, password) VALUES (manager_id, pwd);
    END IF;  
END;  
//  
DELIMITER ;

DROP PROCEDURE IF EXISTS ExportMonthlySalary;


DELIMITER //

CREATE PROCEDURE ExportMonthlySalary()
BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE empID INT;
    DECLARE empName VARCHAR(50);
    DECLARE bS DECIMAL(10,2);
    DECLARE pS DECIMAL(10,2);
    DECLARE tS DECIMAL(10,2);
    DECLARE teS DECIMAL(10,2);
    DECLARE attendanceDeduction DECIMAL(10,2);
    DECLARE fullAttendanceBonus DECIMAL(10,2);
    DECLARE totalSalary DECIMAL(10,2);

    -- 定义游标
    DECLARE cur CURSOR FOR
    SELECT 
        EmployeeID,
        EmployeeName,
        BasicSalary,
        PositionSalary,
        TitleSalary,
        TenureSalary,
        AttendanceDeduction,
        FullAttendanceBonus,
        TotalSalary
    FROM 
        MonthlySalaryView;

    -- 定义游标结束处理程序
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    -- 打开游标
    OPEN cur;

    -- 读取游标数据
    read_loop: LOOP
        FETCH cur INTO empID, empName, bS, pS, tS, teS, attendanceDeduction, fullAttendanceBonus, totalSalary;
        IF done THEN
            LEAVE read_loop;
        END IF;

        -- 插入数据到 MonthlySalary 表
        INSERT INTO MonthlySalary (EmployeeID, Month, BasicSalary, PositionSalary, TitleSalary, TenureSalary, AttendanceDeduction, FullAttendanceBonus, TotalSalary)
        VALUES (empID, DATE_FORMAT(CURDATE(), '%Y-%m-01'), basicSalary, positionSalary, titleSalary, tenureSalary, attendanceDeduction, fullAttendanceBonus, totalSalary);
    END LOOP;

    -- 关闭游标
    CLOSE cur;
END //

DELIMITER ;


DROP EVENT IF EXISTS SalaryPaymentEvent;

DELIMITER //

-- 创建事件调度器
CREATE EVENT SalaryPaymentEvent
ON SCHEDULE EVERY 1 DAY
STARTS '1999-01-01 00:00:00'
DO
BEGIN
    DECLARE salaryPaymentDay INT;
    DECLARE currentDay INT;
    
    -- 获取工资发放日
    SELECT ConfigValue INTO salaryPaymentDay FROM Config WHERE ConfigKey = 'SalaryPaymentDay';

    SELECT ConfigValue INTO currentDay FROM Config WHERE ConfigKey = 'CurrentTime';
    
    -- 检查今天是否是工资发放日
    IF currentDay = salaryPaymentDay THEN
        -- 调用生成工资存储过程
        CALL ExportMonthlySalary();
    END IF;
END //

DELIMITER ;

DROP PROCEDURE IF EXISTS InsertCurrentTimestamp;

DELIMITER //  
CREATE PROCEDURE InsertCurrentTimestamp()  
BEGIN  
    UPDATE Config SET ConfigValue = DAY(CURDATE()) WHERE ConfigKey = 'CurrentTime';
END //  
DELIMITER ;

DROP PROCEDURE IF EXISTS UpdateTenure;

DELIMITER //

CREATE PROCEDURE UpdateTenure()
BEGIN
    DECLARE emp_id INT;
    DECLARE hire_date DATE;
    DECLARE tenure_years INT;
    DECLARE done INT DEFAULT 0;


    -- 遍历员工表，计算工龄并插入 TenureSalary 表
    DECLARE cur CURSOR FOR
        SELECT EmployeeID, HireDate FROM Employee;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO emp_id, hire_date;
        IF done THEN
            LEAVE read_loop;
        END IF;

        -- 计算工龄（以年为单位）
        SET tenure_years = TIMESTAMPDIFF(YEAR, hire_date, CURDATE());

        -- 更新 TenureSalary 表中的工龄信息
        UPDATE TenureSalary 
        SET Tenure = tenure_years
        WHERE EmployeeID = emp_id;

    END LOOP;

    CLOSE cur;
END //

DELIMITER ;

DROP TRIGGER IF EXISTS after_employee_insert_calculate_tenure;

DELIMITER //

-- 在 Employee 表插入时自动计算工龄并更新 TenureSalary 表
CREATE TRIGGER after_employee_insert_calculate_tenure
AFTER INSERT ON Employee
FOR EACH ROW
BEGIN
    DECLARE currentTenure INT;
    DECLARE tenureSalary DECIMAL(10, 2);

    -- 计算工龄（月数）
    SET currentTenure = TIMESTAMPDIFF(MONTH, NEW.HireDate, CURDATE());

    -- 假设每年有12个月，工龄以年为单位计算
    SET currentTenure = FLOOR(currentTenure / 12);

    -- 根据计算的工龄查找或计算相应的工龄工资
    -- 这里假设有一个表 `TenureSalaryScale` 存储工龄与工资的对应关系

    SET tenureSalary = 0;

    SET tenureSalary = tenureSalary + currentTenure * 300;

    -- 插入或更新 TenureSalary 表
    INSERT INTO TenureSalary (EmployeeID, Tenure, Salary)
    VALUES (NEW.EmployeeID, currentTenure, tenureSalary)
    ON DUPLICATE KEY UPDATE Tenure = currentTenure, Salary = tenureSalary;
END //

DELIMITER ;

CREATE EVENT IF NOT EXISTS daily_update_tenure_salary
    ON SCHEDULE
    EVERY 1 DAY
    STARTS CURRENT_TIMESTAMP
    COMMENT 'Daily update of tenure and salary'
    DO
    BEGIN
        CALL UpdateTenureAndSalary();
    END;




