package sdms;

import java.util.*;
import java.text.*;

//各种信息类
public class Info {
	
}

class Floor { //宿舍楼
	String floor_num; //楼号
	int layer_amount; //层数
	int room_amount; //房间数
	String category; //类别
	String sex; //居住时间
	String admin_num; //宿管编号
	public Floor(String floor_num, String layer_amount, String room_amount, String category, String sex, String admin_num) {
		this.floor_num=floor_num;
		this.layer_amount=Integer.parseInt(layer_amount);
		this.room_amount=Integer.parseInt(room_amount);
		this.category=category;
		this.sex=sex;
		this.admin_num=admin_num;
	}
}

class Dormitory { //宿舍
	String floor_num; //楼号
	int layer; //楼层
	int room_num; //宿舍号
	int bed_total; //总床位数
	int bed_surplus; //剩余床位数
	int price; //住宿费
	public Dormitory(String floor_num, String layer, String room_num, String bed_total, String bed_surplus, String price) {
		this.floor_num=floor_num;
		this.layer=Integer.parseInt(layer);
		this.room_num=Integer.parseInt(room_num);
		this.bed_total=Integer.parseInt(bed_total);
		this.bed_surplus=Integer.parseInt(bed_surplus);
		this.price=Integer.parseInt(price);
	}
}

class DepartmentDB { //宿舍
	String department_id; //楼号
	String department_name; //楼层
	String parent_department_id;
	String manager_id;

	public DepartmentDB(String department_id, String department_name, String parent_department_id, String manager_id) {
		this.department_id = department_id;
		this.department_name = department_name;
		this.parent_department_id = parent_department_id;
		this.manager_id = manager_id;
	}
}

class TitleDB{
	String title_id;
	String title_name;
	String title_salary;

	public TitleDB(String title_id, String title_name, String title_salary) {
		this.title_id = title_id;
		this.title_name = title_name;
		this.title_salary = title_salary;
	}
}

class PositionDB {
	String position_id;
	String position_name;
	String position_salary;

	public PositionDB(String position_id, String position_name, String position_salary) {
		this.position_id = position_id;
		this.position_name = position_name;
		this.position_salary = position_salary;
	}
}

class AttendanceDB {
	String attendance_id;
	String employee_id;
	String attendance_date;
	String attendance_type;

	public AttendanceDB(String attendance_id, String employee_id, String attendance_date, String attendance_type) {
		this.attendance_id = attendance_id;
		this.employee_id = employee_id;
		this.attendance_date = attendance_date;
		this.attendance_type = attendance_type;
	}
}

class MonthlySalaryDB {
	String salary_id;
	String employee_id;
	String month;
	String basic_salary;
	String position_salary;
	String title_salary;
	String tenure_salary;
	String attendance_deduction;
	String full_attendance_bonus;
	String total_salary;

	public MonthlySalaryDB(String employee_id, String month, String basic_salary, String position_salary, String title_salary, String tenure_salary, String attendance_deduction, String full_attendance_bonus, String total_salary) {
		this.salary_id = null;
		this.employee_id = employee_id;
		this.month = month;
		this.basic_salary = basic_salary;
		this.position_salary = position_salary;
		this.title_salary = title_salary;
		this.tenure_salary = tenure_salary;
		this.attendance_deduction = attendance_deduction;
		this.full_attendance_bonus = full_attendance_bonus;
		this.total_salary = total_salary;
	}

	public MonthlySalaryDB(String salary_id, String employee_id, String month, String basic_salary, String position_salary, String title_salary, String tenure_salary, String attendance_deduction, String full_attendance_bonus, String total_salary) {
		this.salary_id = salary_id;
		this.employee_id = employee_id;
		this.month = month;
		this.basic_salary = basic_salary;
		this.position_salary = position_salary;
		this.title_salary = title_salary;
		this.tenure_salary = tenure_salary;
		this.attendance_deduction = attendance_deduction;
		this.full_attendance_bonus = full_attendance_bonus;
		this.total_salary = total_salary;
	}
}

class Admin { //宿管
	String admin_num; //编号
	String password; //密码
	String name; //姓名
	String sex; //性别
	String phone; //手机
	public Admin(String admin_num, String password, String name, String sex, String phone) {
		this.admin_num=admin_num;
		this.password=password;
		this.name=name;
		this.sex=sex;
		this.phone=phone;
	}
}

class Student { //学生
	String stu_num; //学号
	String password; //密码
	String name; //姓名
	String sex; //性别
	int birth; //出生日期
	int grade; //年级
	String faculty; //院系
	String clas; //班级
	String phone; //手机号
	String yes_no; //是否入住
	public Student(String stu_num, String password, String name, String sex, String birth, String grade, String faculty, String clas, String phone, String yes_no) {
		this.stu_num=stu_num;
		this.password=password;
		this.name=name;
		this.sex=sex;
		this.birth=Integer.parseInt(birth);
		this.grade=Integer.parseInt(grade);
		this.faculty=faculty;
		this.clas=clas;
		this.phone=phone;
		this.yes_no=yes_no;
	}
}

class Employee { //员工
	String employee_id;
	String employee_name;
	String department_name;
	String position_name;
	String title_name;
	String hire_date;

	public Employee(String employee_id, String employee_name, String department_name, String position_name, String title_name, String hire_date) {
		this.employee_id = employee_id;
		this.employee_name = employee_name;
		this.department_name = department_name;
		this.position_name = position_name;
		this.title_name = title_name;
		this.hire_date = hire_date;
	}
}

class EmployeeDB { //员工
	String employee_id;
	String name;
	String title_id;
	String department_id;
	String position_id;
	String hire_date;
	String basic_salary;

	public EmployeeDB(String employee_id, String name, String title_id, String department_id, String position_id, String hire_date, String basic_salary) {
		this.employee_id = employee_id;
		this.name = name;
		this.title_id = title_id;
		this.department_id = department_id;
		this.position_id = position_id;
		this.hire_date = hire_date;
		this.basic_salary = basic_salary;
	}
}

class Stay { //住宿
	String stu_num; //学号
	String name; //姓名
	String floor_num; //楼号
	int layer; //楼层
	int room_num; //宿舍号
	Date time; //入住时间
	public Stay(String stu_num, String name, String floor_num, String layer, String room_num, String time) {
		SimpleDateFormat timeFormat=new SimpleDateFormat("yyyy-MM-dd");
		this.stu_num=stu_num;
		this.name=name;
		this.floor_num=floor_num;
		this.layer=Integer.parseInt(layer);
		this.room_num=Integer.parseInt(room_num);
		try {
			this.time=timeFormat.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}

class InOut { //晚归
	String stu_num; //学号
	String name; //姓名
	String floor_num; //楼号
	Date time; //时间
	public InOut(String stu_num, String name, String floor_num,String time) {
		SimpleDateFormat timeFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.stu_num=stu_num;
		this.name=name;
		this.floor_num=floor_num;
		try {
			this.time=timeFormat.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}

class Repair { //报修
	String stu_num; //学号
	String name; //姓名
	String floor_num; //楼号
	int layer; //楼层
	int room_num; //宿舍号
	String info; //报修详细
	String yes_no; //是否处理
	public Repair(String stu_num, String name, String floor_num, String layer, String room_num, String info, String yes_no) {
		this.stu_num=stu_num;
		this.name=name;
		this.floor_num=floor_num;
		this.layer=Integer.parseInt(layer);
		this.room_num=Integer.parseInt(room_num);
		this.info=info;
		this.yes_no=yes_no;
	}
}

class Advice { //建议与反馈
	String stu_num; //学号
	String name; //姓名
	String info; //详细信息
	public Advice(String stu_num, String name, String info) {
		this.stu_num=stu_num;
		this.name=name;
		this.info=info;
	}
}

class Stu { //学生，用于分配宿舍的信息
	String stu_num; //学号
	int grade; //年级
	String faculty; //院系
	String clas; //班级
	public Stu(String stu_num, String grade, String faculty, String clas) {
		this.stu_num=stu_num;
		this.grade=Integer.parseInt(grade);
		this.faculty=faculty;
		this.clas=clas;
	}
}

class Dor { //宿舍，用于分配宿舍的信息
	String floor_num; //楼号
	int layer; //楼层
	int room_num; //宿舍号
	int bed_surplus; //剩余床位数
	public Dor(String floor_num, String layer, String room_num, String bed_surplus) {
		this.floor_num=floor_num;
		this.layer=Integer.parseInt(layer);
		this.room_num=Integer.parseInt(room_num);
		this.bed_surplus=Integer.parseInt(bed_surplus);
	}
}