SET FOREIGN_KEY_CHECKS = 0;
-- 插入基础数据
INSERT INTO Title (TitleID, Name, Salary) VALUES
(1, '高级', 3000.00),
(2, '中级', 2000.00),
(3, '初级', 1000.00);

INSERT INTO Position (PositionID, Name, Salary) VALUES
(1, '经理', 8000.00),
(2, '高级工程师', 6000.00),
(3, '初级工程师', 4000.00),
(4, '实习生', 2000.00);

-- 插入部门数据
INSERT INTO Department (DepartmentID, Name, ParentDepartmentID, ManagerID) VALUES
(1, '人事', NULL, 101),
(2, '财务', NULL, 102),
(3, '工程', NULL, 103),
(4, '市场', NULL, 104),
(5, '人事调研部', 1, 105);

-- 插入员工数据
INSERT INTO Employee (EmployeeID, Name, TitleID, PositionID, DepartmentID, HireDate, BasicSalary) VALUES
(101, '叶苏炜的爸爸', 1, 1, 1, '2020-01-15', 10000.00),
(102, '柯洁', 2, 2, 2, '2019-03-20', 7000.00),
(103, '战鹰', 3, 3, 3, '2021-05-10', 5000.00),
(104, '孙笑川', 1, 4, 4, '2022-07-22', 3000.00),
(105, '叶劲延', 3, 4, 1, '2022-01-01', 100.00),
(106, '朱文卓', 2, 1, 1, '2024-01-01', 1000.00),
(107, '刘致嘉', 1, 1, 1, '2024-02-01', 1000000.00);

-- 插入其他数据
INSERT INTO SalaryCategory (CategoryID, Name, IsManual) VALUES
(1, 'Overtime', TRUE),
(2, 'Bonus', TRUE),
(3, 'Deduction', TRUE);

INSERT INTO Attendance (AttendanceID, EmployeeID, Date, Type) VALUES

(1, 101, '2024-06-01', '旷工'),
(2, 102, '2024-06-01', '迟到早退'),
(3, 103, '2024-06-01', '请假');


-- INSERT INTO MonthlySalary (SalaryID, EmployeeID, Month, BasicSalary, PositionSalary, TitleSalary, TenureSalary, AttendanceDeduction, FullAttendanceBonus, TotalSalary) VALUES
-- (1, 101, '2023-06-01', 10000.00, 8000.00, 3000.00, 1000.00, 0.00, 500.00, 22500.00),
-- (2, 102, '2023-06-01', 7000.00, 6000.00, 2000.00, 800.00, 200.00, 500.00, 16100.00),
-- (3, 103, '2023-06-01', 5000.00, 4000.00, 1000.00, 600.00, 100.00, 500.00, 13600.00),
-- (4, 104, '2023-06-01', 3000.00, 2000.00, 3000.00, 0.00, 0.00, 500.00, 8500.00);

-- INSERT INTO TenureSalary (EmployeeID, Tenure, Salary) VALUES
-- (101, 3, 1000.00),
-- (102, 4, 800.00),
-- (103, 2, 600.00),
-- (104, 1, 0.00);

INSERT INTO NewTenureSalary (EmployeeID, NewTenure, EffectiveDate, EndDate) VALUES
(101, 3, '2023-01-01', NULL),
(102, 4, '2023-01-01', NULL),
(103, 2, '2023-01-01', NULL),
(104, 1, '2023-01-01', NULL);

INSERT INTO SalaryAdjustment (AdjustmentID, EmployeeID, CategoryID, Amount, Month) VALUES
(1, 101, 1, 200.00, '2023-06-01'),
(2, 102, 2, 500.00, '2023-06-01'),
(3, 103, 3, -100.00, '2023-06-01'),
(4, 104, 1, 300.00, '2023-06-01');

INSERT INTO EmployeeTitleHistory (EmployeeID, TitleID, StartDate, EndDate) VALUES
(101, 1, '2020-01-15', NULL),
(102, 2, '2019-03-20', NULL),
(103, 3, '2021-05-10', NULL),
(104, 1, '2022-07-22', NULL);

INSERT INTO Config (ConfigKey, ConfigValue) VALUES ('SalaryPaymentDay', 25);
INSERT INTO Config (ConfigKey, ConfigValue) VALUES ('TenureSalary', 300);
INSERT INTO Config (ConfigKey, ConfigValue) VALUES ('CurrentTime', DAY(CURDATE()));

SET FOREIGN_KEY_CHECKS = 1;