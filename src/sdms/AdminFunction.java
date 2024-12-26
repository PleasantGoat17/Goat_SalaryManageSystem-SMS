package sdms;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminFunction implements ActionListener { //财务功能类
    String admin_num;
    String department_id;
    JFrame sarAdminJFrame = new JFrame("员工工资管理系统-----[财务模式]");
    Container dorCon = sarAdminJFrame.getContentPane();
    JPanel pn_function = new JPanel(); //放置各种功能页面的容器
    JLabel lb_topFunction = new JLabel(); //顶部信息栏，当前功能
    JLabel lb_tips = new JLabel(); //提示窗口的内容
    JPanel pn_first = new JPanel(); //选项卡1
    JPanel pn_first_1 = new JPanel(); //选项卡1_1
    JPanel pn_first_2 = new JPanel(); //选项卡1_2

    JPanel pn_second = new JPanel(); //选项卡2
    JPanel pn_second_1 = new JPanel(); //选项卡2_1
    JPanel pn_second_2 = new JPanel(); //选项卡2_2

    JPanel pn_third = new JPanel(); //选项卡3
    JPanel pn_third_1 = new JPanel(); //选项卡2_1
    JPanel pn_third_2 = new JPanel(); //选项卡2_2

    JPanel pn_fourth = new JPanel(); //选项卡4

    int SalaryPaymentDay;
    int rowcount;
    MonthlySalaryDB monthlySalarydb = null;
    DepartmentDB departmentdb = null;
    EmployeeDB employeedb = null;
    TitleDB titledb = null;
    PositionDB positiondb = null;
    AttendanceDB attendancedb = null;
    String rowData[][] = null;

    public AdminFunction(String admin_num) { //整体界面
        this.admin_num = admin_num;
        try {

            String sql = "SELECT * FROM Department WHERE ManagerID = " + admin_num; //SQL语句，查询该财务管理的部门
            PreparedStatement ps = HomePage.connection.prepareStatement(sql); //把操作数据库返回的结果保存到ps中
            ResultSet rs = ps.executeQuery(sql); //ResultSet类，用来存放获取的结果集

            if (rs.next()) //该财务有管理的部门，则获取相应部门号
                department_id = rs.getString("DepartmentID");
            else
                department_id = "";

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sarAdminJFrame.setSize(1300, 800);
        sarAdminJFrame.setLocationRelativeTo(null);
        sarAdminJFrame.setResizable(false);
        sarAdminJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sarAdminJFrame.setLayout(null);

        pn_function.setLayout(null);
        pn_function.setBorder(BorderFactory.createEtchedBorder());
        pn_function.setBounds(250, 30, 1045, 735);

        JPanel pn_topUser = new JPanel(); //顶部信息栏，操作者
        pn_topUser.setBackground(Color.white);
        pn_topUser.setBounds(0, 0, 250, 30);

        JLabel lb_topUser = new JLabel("财务：" + admin_num);
        lb_topUser.setFont(new Font("方正大标宋_GBK", 0, 18));
        lb_topUser.setForeground(Color.blue);
        pn_topUser.add(lb_topUser);


        JPanel pn_topFunction = new JPanel(); //顶部信息栏，当前功能
        pn_topFunction.setBackground(Color.white);
        pn_topFunction.setBounds(250, 0, 1045, 30);
        lb_topFunction.setFont(new Font("方正大标宋_GBK", 0, 20));
        lb_topFunction.setForeground(Color.black);
        pn_topFunction.add(lb_topFunction);

        JPanel pn_menu = new JPanel(); //菜单，进行功能选择
        pn_menu.setBackground(new Color(249, 250, 252));
        pn_menu.setBorder(BorderFactory.createEtchedBorder());
        pn_menu.setLayout(null);
        pn_menu.setBounds(0, 30, 250, 735);

        JButton bt_info = new JButton("个人信息"); //[个人信息]
        bt_info.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_info.setContentAreaFilled(false);
        bt_info.setBounds(0, 50, 249, 50);

        JButton bt_company = new JButton("公司管理"); //[部门管理]
        bt_company.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_company.setContentAreaFilled(false);
        bt_company.setBounds(0, 105, 249, 50);

        JButton bt_department = new JButton("部门管理"); //[部门管理]
        bt_department.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_department.setContentAreaFilled(false);
        bt_department.setBounds(0, 160, 249, 50);

        JButton bt_salary = new JButton("工资调整"); //[工资调整]
        bt_salary.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_salary.setContentAreaFilled(false);
        bt_salary.setBounds(0, 215, 249, 50);

        JButton bt_attendence = new JButton("考勤登记"); //[考勤登记]
        bt_attendence.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_attendence.setContentAreaFilled(false);
        bt_attendence.setBounds(0, 270, 249, 50);

        JButton bt_query = new JButton("工资查询"); //[建议与反馈]
        bt_query.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_query.setContentAreaFilled(false);
        bt_query.setBounds(0, 325, 249, 50);

        JButton bt_password = new JButton("修改密码"); //[修改密码]
        bt_password.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_password.setContentAreaFilled(false);
        bt_password.setBounds(0, 380, 249, 50);

        JButton bt_out = new JButton("退出"); //[退出]
        bt_out.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_out.setContentAreaFilled(false);
        bt_out.setBounds(0, 435, 249, 50);


        bt_info.addActionListener(this);
        bt_company.addActionListener(this);
        bt_department.addActionListener(this);
        bt_salary.addActionListener(this);
        bt_attendence.addActionListener(this);
        bt_query.addActionListener(this);
        bt_password.addActionListener(this);
        bt_out.addActionListener(this);

        pn_menu.add(bt_info);
        pn_menu.add(bt_company);
        pn_menu.add(bt_department);
        pn_menu.add(bt_salary);
        pn_menu.add(bt_attendence);
        pn_menu.add(bt_query);
        pn_menu.add(bt_password);
        pn_menu.add(bt_out);

        JPanel pn_welcome = new JPanel(); //欢迎页
        pn_welcome.setBorder(BorderFactory.createEtchedBorder());
        pn_welcome.setLayout(new BorderLayout());
        pn_welcome.setBounds(0, 0, 1045, 735);

        JLabel lb_welcome = new JLabel("欢迎使用");
        lb_welcome.setFont(new Font("方正大标宋_GBK", 0, 100));
        lb_welcome.setHorizontalAlignment(SwingConstants.CENTER);
        pn_welcome.add(lb_welcome, BorderLayout.CENTER);

        dorCon.add(pn_topUser);
        dorCon.add(pn_topFunction);
        dorCon.add(pn_menu);
        dorCon.add(pn_function);
        pn_function.add(pn_welcome);
        sarAdminJFrame.setVisible(true);
    }

    public static boolean isValidDate(String date) {
        // 定义匹配 YY-MM-DD 格式的正则表达式
        String regex = "^\\d{4}-\\d{2}-\\d{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);

        // 检查是否匹配
        return matcher.matches();
    }

    public void actionPerformed(ActionEvent e) { //依据选择的菜单功能，进行相应功能
        if (e.getActionCommand().equals("退出")) {
            lb_tips.setText("是否退出财务模式？");
            choiceTips("");
        } else {
            pn_function.removeAll();
            sarAdminJFrame.repaint();
            if (e.getActionCommand().equals("个人信息")) {
                lb_topFunction.setText("[个人信息]");
                pn_function.add(info_Finance());
            } else if (e.getActionCommand().equals("公司管理")) {
                lb_topFunction.setText("[公司管理]");
                pn_function.add(departmentManage());
            } else if (e.getActionCommand().equals("部门管理")) {
                lb_topFunction.setText("[部门管理]");
                pn_function.add(employeeManage());
            } else if (e.getActionCommand().equals("工资调整")) {
                lb_topFunction.setText("[工资调整]");
                pn_function.add(Title_and_PositionManage());
            } else if (e.getActionCommand().equals("考勤登记")) {
                lb_topFunction.setText("[考勤登记]");
                pn_function.add(AttendanceManage());
            } else if (e.getActionCommand().equals("工资查询")) {
                lb_topFunction.setText("[工资查询]");
                pn_function.add(querySalary());
            } else if (e.getActionCommand().equals("修改密码")) {
                lb_topFunction.setText("[修改密码]");
                pn_function.add(changePassword());
            }
            sarAdminJFrame.validate();
        }
    }

    public JPanel info_Finance() { //[个人信息]功能(搞定)
        String[] admin_info = new String[6]; //个人（财务）信息
        String[] department_info = {"无", "无", "无", "无"}; //管理的部门信息
        try {
            String sql = "SELECT * FROM EmployeeView WHERE EmployeeID=" + admin_num; //SQL语句，查询财务信息
            PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
            ps = HomePage.connection.prepareStatement(sql); //把操作数据库返回的结果保存到ps中
            ResultSet rs = ps.executeQuery(sql); //ResultSet类，用来存放获取的结果集
            while (rs.next()) {
                admin_info[0] = rs.getString("EmployeeID");
                admin_info[1] = rs.getString("EmployeeName");
                admin_info[2] = rs.getString("DepartmentName");
                admin_info[3] = rs.getString("PositionName");
                admin_info[4] = rs.getString("TitleName");
                admin_info[5] = rs.getString("HireDate");
            }
            sql = "SELECT * FROM DepartmentView WHERE DepartmentID =(SELECT DepartmentID FROM Employee WHERE EmployeeID= " + admin_num + ")"; //SQL语句，查询该财务管理的部门信息
            ps = HomePage.connection.prepareStatement(sql); //把操作数据库返回的结果保存到ps中
            rs = ps.executeQuery(sql); //ResultSet类，用来存放获取的结果集
            if (rs.next()) { //若该财务有管理的部门，则获取相应部门信息
                department_info[0] = rs.getString("DepartmentID");
                department_info[1] = rs.getString("DepartmentName");
                department_info[2] = rs.getString("ManagerName");
                if (rs.getString("ParentDepartmentName") != null) {
                    department_info[3] = rs.getString("ParentDepartmentName");
                }

            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JPanel pn_info = new JPanel();
        pn_info.setLayout(null);
        pn_info.setSize(1045, 735);
        pn_info.setBorder(BorderFactory.createEtchedBorder());
        JLabel lb_infoImage = new JLabel(new ImageIcon("image/user.png"));
        lb_infoImage.setBounds(700, 130, 200, 200);
        JLabel[] lb = new JLabel[10];
        for (int i = 0; i < 10; i++) {
            lb[i] = new JLabel();
            lb[i].setFont(new Font("方正大标宋_GBK", 0, 30));
            lb[i].setBounds(150, 97 + i * 60, 500, 50);
            pn_info.add(lb[i]);
        }
        lb[0].setText("编    号： " + admin_info[0]);
        lb[1].setText("姓    名： " + admin_info[1]);
        lb[2].setText("部门名称： " + admin_info[2]);
        lb[3].setText("职    位： " + admin_info[3]);
        lb[4].setText("职    称： " + admin_info[4]);
        lb[5].setText("入职日期： " + admin_info[5]);

        lb[6].setText("部门编号： " + department_info[0]);
        lb[7].setText("部门名称： " + department_info[1]);
        lb[9].setText("部门经理姓名： " + department_info[2]);
        lb[8].setText("上级部门名称： " + department_info[3]);
        pn_info.add(lb_infoImage);
        return pn_info;
    }

    public JTabbedPane departmentManage() { //部门管理
        JTabbedPane tp_department = new JTabbedPane();
        tp_department.setFont(new Font("方正大标宋_GBK", 0, 25));
        tp_department.setBounds(0, 0, 1045, 735);
        allDepartment();
        tp_department.addTab(" 全部 ", pn_first);
        queryDepartmentInfo_1();
        tp_department.addTab(" 查询 ", pn_second);
        return tp_department;
    }

    public void allDepartment() { //公司的所有部门
        String[] columnNames = {"部门编号", "部门名称", "部门经理名称", "上级部门名称", "操作"}; //表格列名
        String[][] rowData = null; //表格数据
        int count = 0; //表的元组总数
        try { //获取departmentview视图信息
            String sql = "SELECT * FROM DepartmentView"; //SQL语句
            PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
            ps = HomePage.connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY); //把操作数据库返回的结果保存到ps中
            ResultSet rs = ps.executeQuery(sql); //ResultSet类，用来存放获取的结果集
            rs.last();
            count = rs.getRow(); //获取部门元组总数
            if (count == 0) { //若dormitory表无元组
                rowData = new String[1][5];
                for (int i = 0; i < 5; i++)
                    rowData[0][i] = "无";
            } else { //若dormitory表有元组
                rowData = new String[count][5];
                rs.first();
                int i = 0;
                do { //获取该财务管理的部门的所有部门信息
                    rowData[i][0] = rs.getString("DepartmentID"); //部门编号
                    rowData[i][1] = rs.getString("DepartMentName"); //部门名
                    rowData[i][2] = rs.getString("ManagerName"); //部门经理编号
                    if (rs.getString("ParentDepartmentName") != null) {
                        rowData[i][3] = rs.getString("ParentDepartmentName"); //上级部门编号
                    } else {
                        rowData[i][3] = "无"; //总床位数
                    }
                    i++;
                } while (rs.next());
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //界面
        pn_first.setLayout(null);
        pn_first_1.removeAll();
        pn_first_1.setBounds(0, 0, 1045, 695);
        pn_first_1.setLayout(null);
        JPanel pn_top = new JPanel();
        pn_top.setBounds(0, 0, 1045, 50);
        JLabel lb_num;
        if (count == 0)
            lb_num = new JLabel("  无部门！  ");
        else
            lb_num = new JLabel("   公司共有" + count + "条部门信息！  ");
        lb_num.setFont(new Font("方正大标宋_GBK", 0, 25));
        JButton bt_add = new JButton("添加", new ImageIcon("image/add.png"));
        bt_add.setBackground(Color.white.darker());
        bt_add.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_add.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (count == 0)
            bt_add.setEnabled(false);
        pn_top.add(bt_add);
        pn_top.add(lb_num);
        JTable table = new JTable(new MyTableModel(columnNames, rowData, 2));
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(1, 35)); //设置表头高度
        header.setFont(new Font("方正大标宋_GBK", Font.BOLD, 23));
        table.setRowHeight(35); //设置各行高度
        table.setFont(new Font("方正大标宋_GBK", 0, 20));
        table.setBackground(null);
        table.getTableHeader().setReorderingAllowed(false); //不允许移动各列
        JScrollPane scrollPane = new JScrollPane(table); //滚动条
        scrollPane.setBounds(0, 50, 1045, 645);
        pn_first_1.add(pn_top);
        pn_first_1.add(scrollPane);
        if (count != 0) {
            MyEvent e = new MyEvent() { //点击“查看”按钮，查看信息，可修改和删除信息
                public void invoke(ActionEvent e) {
                    MyButton button = (MyButton) e.getSource();
                    pn_first_2.removeAll();
                    visitDepartmentInfo((String) table.getValueAt(button.getRow(), button.getColumn() - 4), 1, null);
                }
            };
            table.getColumnModel().getColumn(4).setCellRenderer(new MyButtonRender("查看")); //设置表格的渲染器
            MyButtonEditor editor = new MyButtonEditor(e, "查看");
            table.getColumnModel().getColumn(4).setCellEditor(editor); //设置表格的编辑器
        }
        bt_add.addActionListener(new ActionListener() { //添加部门信息
            public void actionPerformed(ActionEvent e) {
                pn_first_2.removeAll();
                addDepartmentInfo();
            }
        });
        pn_first.removeAll();
        sarAdminJFrame.repaint();
        pn_first.add(pn_first_1);
        sarAdminJFrame.validate();
    }

    public void visitDepartmentInfo(String department_id, int x, String query_sql) { //查看部门信息，可修改和删除
        if (x == 1) {
            pn_first_2.removeAll();
            pn_first_2.setLayout(null);
            pn_first_2.setBounds(0, 0, 1045, 695);
            pn_first_2.setBorder(BorderFactory.createEtchedBorder());
        } else {
            pn_second_2.removeAll();
            pn_second_2.setLayout(null);
            pn_second_2.setBounds(0, 0, 1045, 695);
            pn_second_2.setBorder(BorderFactory.createEtchedBorder());
        }
        JButton bt_back = new JButton("返回", new ImageIcon("image/back.png"));
        bt_back.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_back.setBounds(1, 10, 92, 25);
        bt_back.setContentAreaFilled(false);
        bt_back.setBorderPainted(false);
        bt_back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JButton bt_modify = new JButton("修改部门信息", new ImageIcon("image/modify.png")), bt_delete = new JButton("删除部门信息", new ImageIcon("image/delete.png")),
                bt_save = new JButton("保存", new ImageIcon("image/save.png")), bt_cancel = new JButton("取消", new ImageIcon("image/cancel.png"));
        bt_modify.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_modify.setBounds(755, 380, 190, 40);
        bt_modify.setContentAreaFilled(false);
        bt_modify.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bt_delete.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_delete.setBounds(755, 430, 190, 40);
        bt_delete.setForeground(Color.red);
        bt_delete.setContentAreaFilled(false);
        bt_delete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bt_save.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_save.setBounds(750, 380, 95, 40);
        bt_save.setContentAreaFilled(false);
        bt_save.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bt_cancel.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_cancel.setBounds(855, 380, 95, 40);
        bt_cancel.setContentAreaFilled(false);
        bt_cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel lb_floorImage = new JLabel(new ImageIcon("image/department.png"));
        lb_floorImage.setBounds(750, 165, 200, 200);
        if (x == 1) {
            pn_first_2.add(bt_back);
            pn_first_2.add(bt_modify);
            pn_first_2.add(bt_delete);
            pn_first_2.add(lb_floorImage);
        } else {
            pn_second_2.add(bt_back);
            pn_second_2.add(bt_modify);
            pn_second_2.add(bt_delete);
            pn_second_2.add(lb_floorImage);
        }
        JLabel[] lb = new JLabel[4];
        for (int i = 0; i < 4; i++) {
            lb[i] = new JLabel();
            lb[i].setFont(new Font("方正大标宋_GBK", 0, 25));
            if (i < 2)
                lb[i].setBounds(260, 150 + i * 55, 130, 50);
            else
                lb[i].setBounds(210, 150 + i * 55, 180, 50);
            if (x == 1)
                pn_first_2.add(lb[i]);
            else
                pn_second_2.add(lb[i]);
        }
        lb[0].setText("部门编号：");
        lb[1].setText("部门名称：");
        lb[2].setText("上级部门名称：");
        lb[3].setText("部门经理编号：");
        JTextField[] tf = new JTextField[4];
        for (int i = 0; i < 4; i++) {
            tf[i] = new JTextField();
            tf[i].setFont(new Font("方正大标宋_GBK", 0, 25));
            tf[i].setBounds(392, 155 + i * 55, 260, 40);
            tf[i].setEditable(false);
            if (x == 1)
                pn_first_2.add(tf[i]);
            else
                pn_second_2.add(tf[i]);
        }
        try {
            String sql = "SELECT * FROM Department WHERE DepartmentID =" + department_id; //SQL语句
            PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
            ps = HomePage.connection.prepareStatement(sql); //把操作数据库返回的结果保存到ps中
            ResultSet rs = ps.executeQuery(sql); //ResultSet类，用来存放获取的结果集
            while (rs.next()) {
                departmentdb = new DepartmentDB(rs.getString("DepartmentID"), rs.getString("Name"), rs.getString("ParentDepartmentID"), rs.getString("ManagerID"));
                tf[0].setText(departmentdb.department_id);
                tf[1].setText(departmentdb.department_name);
                tf[2].setText(departmentdb.parent_department_id);
                tf[3].setText(departmentdb.manager_id);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        bt_back.addMouseListener(new MouseListener() { //返回
            public void mouseEntered(MouseEvent arg0) {
                bt_back.setForeground(Color.blue);
            }

            public void mouseExited(MouseEvent arg0) {
                bt_back.setForeground(null);
            }

            public void mouseClicked(MouseEvent arg0) {
                departmentdb = null;
                if (x == 1)
                    allDepartment();
                else
                    queryDepartmentInfo_2(query_sql);
            }

            public void mousePressed(MouseEvent arg0) {
            }

            public void mouseReleased(MouseEvent arg0) {
            }
        });
        bt_modify.addActionListener(new ActionListener() { //修改部门信息
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 4; i++)
                    tf[i].setEditable(true);
                // tf[0].setEditable(false);
                if (x == 1) {
                    pn_first_2.remove(bt_modify);
                    pn_first_2.remove(bt_delete);
                    sarAdminJFrame.repaint();
                    pn_first_2.add(bt_save);
                    pn_first_2.add(bt_cancel);
                    sarAdminJFrame.validate();
                } else {
                    pn_second_2.remove(bt_modify);
                    pn_second_2.remove(bt_delete);
                    sarAdminJFrame.repaint();
                    pn_second_2.add(bt_save);
                    pn_second_2.add(bt_cancel);
                    sarAdminJFrame.validate();
                }
            }
        });
        bt_delete.addActionListener(new ActionListener() { //删除部门信息
            public void actionPerformed(ActionEvent e) {
                lb_tips.setText("是否确定删除该部门信息？");
                if (x == 1)
                    choiceTips("");
                else
                    choiceTips(query_sql);
            }
        });
        bt_save.addActionListener(new ActionListener() { //保存
            public void actionPerformed(ActionEvent e) {
                String sql; //SQL语句
                PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
                ResultSet rs; //ResultSet类，用来存放获取的结果集
                try {
                    if (tf[0].getText().equals("") || tf[1].getText().equals("") || tf[3].getText().equals("")) {
                        lb_tips.setText("请填写完整！");
                        functionTips();
                    } else { //已填写完整
                        boolean isParent = true;
                        if (tf[2].getText().equals("")) {
                            lb_tips.setText("您编辑的部门无上级部门编号");
                            functionTips();
                            isParent = false;
                        }
                        boolean flag = false;//代表部门编号是否重复
                        //检测部门编号是否存在
                        sql = "SELECT DepartmentID FROM Department WHERE DepartmentID='" + tf[0].getText() + "'";
                        ps = HomePage.connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY);
                        rs = ps.executeQuery(sql);
                        flag = rs.next();
                        rs.first();
                        flag = flag && !(tf[0].getText().equals(departmentdb.department_id));
                        if (flag) { //部门编号重复，进行提示
                            lb_tips.setText("部门编号重复！");
                            functionTips();
                        } else { //输入数据符合要求，进行保存
                            try {
                                sql = "UPDATE Department SET DepartmentID=?,Name=?,ParentDepartmentID=?,ManagerID=? WHERE DepartmentID=" + departmentdb.department_id;
                                ps = HomePage.connection.prepareStatement(sql);
                                ps.setString(1, tf[0].getText()); //SQL语句第一个?值
                                ps.setString(2, tf[1].getText()); //SQL语句第二个?值
                                if (isParent) {
                                    ps.setString(3, tf[2].getText()); //SQL语句第三个?值
                                } else {
                                    ps.setNull(3, Types.NULL);
                                }
                                ps.setString(4, tf[3].getText()); //SQL语句第四个?值
                                ps.executeUpdate(); //更新，执行修改操作
                                rs.close();
                                ps.close();
                                departmentdb = new DepartmentDB(tf[0].getText(), tf[1].getText(), tf[2].getText(), tf[3].getText());
                                for (int i = 0; i < 4; i++)
                                    tf[i].setEditable(false);
                                if (x == 1) {
                                    pn_first_2.remove(bt_save);
                                    pn_first_2.remove(bt_cancel);
                                    sarAdminJFrame.repaint();
                                    pn_first_2.add(bt_modify);
                                    pn_first_2.add(bt_delete);
                                    sarAdminJFrame.validate();
                                } else {
                                    pn_second_2.remove(bt_save);
                                    pn_second_2.remove(bt_cancel);
                                    sarAdminJFrame.repaint();
                                    pn_second_2.add(bt_modify);
                                    pn_second_2.add(bt_delete);
                                    sarAdminJFrame.validate();
                                }
                            } catch (SQLException se) {
                                lb_tips.setText("数据库操作出错！");
                                functionTips();
                                se.printStackTrace();
                            } catch (NumberFormatException ne) {
                                lb_tips.setText("数据转换出错！");
                                functionTips();
                                ne.printStackTrace();
                            }
                        }
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        });
        //取消
        bt_cancel.addActionListener(e -> {
            for (int i = 0; i < 4; i++)
                tf[i].setEditable(false);
            tf[0].setText(departmentdb.department_id);
            tf[1].setText(departmentdb.department_name);
            tf[2].setText(departmentdb.parent_department_id);
            tf[3].setText(departmentdb.manager_id);
            if (x == 1) {
                pn_first_2.remove(bt_save);
                pn_first_2.remove(bt_cancel);
                sarAdminJFrame.repaint();
                pn_first_2.add(bt_modify);
                pn_first_2.add(bt_delete);
                sarAdminJFrame.validate();
            } else {
                pn_second_2.remove(bt_save);
                pn_second_2.remove(bt_cancel);
                sarAdminJFrame.repaint();
                pn_second_2.add(bt_modify);
                pn_second_2.add(bt_delete);
                sarAdminJFrame.validate();
            }
        });
        if (x == 1) {
            pn_first.removeAll();
            sarAdminJFrame.repaint();
            pn_first.add(pn_first_2);
            sarAdminJFrame.validate();
        } else {
            pn_second.removeAll();
            sarAdminJFrame.repaint();
            pn_second.add(pn_second_2);
            sarAdminJFrame.validate();
        }
    }

    public void addDepartmentInfo() { //添加部门信息
        pn_first_2.removeAll();
        pn_first_2.setLayout(null);
        pn_first_2.setBounds(0, 0, 1045, 695);
        pn_first_2.setBorder(BorderFactory.createEtchedBorder());
        JButton bt_back = new JButton("返回", new ImageIcon("image/返回.png"));
        bt_back.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_back.setBounds(1, 10, 92, 25);
        bt_back.setContentAreaFilled(false);
        bt_back.setBorderPainted(false);
        bt_back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JButton bt_confirm = new JButton("确认添加", new ImageIcon("image/confirm.png"));
        bt_confirm.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_confirm.setBounds(447, 520, 150, 50);
        bt_confirm.setContentAreaFilled(false);
        bt_confirm.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel lb_floorImage = new JLabel(new ImageIcon("image/department.png"));
        lb_floorImage.setBounds(750, 205, 200, 200);
        pn_first_2.add(bt_back);
        pn_first_2.add(bt_confirm);
        pn_first_2.add(lb_floorImage);
        JLabel[] lb = new JLabel[6];
        for (int i = 0; i < 6; i++) {
            lb[i] = new JLabel();
            lb[i].setFont(new Font("方正大标宋_GBK", 0, 25));
            if (i < 2)
                lb[i].setBounds(260, 150 + i * 55, 130, 50);
            else
                lb[i].setBounds(210, 150 + i * 55, 180, 50);
            pn_first_2.add(lb[i]);
        }
        lb[0].setText("部门编号：");
        lb[1].setText("部门名称：");
        lb[2].setText("上级部门编号：");
        lb[3].setText("部门经理编号：");

        // lb[4].setBounds(250,150+4*55,150,50);
        JTextField[] tf = new JTextField[4];
        for (int i = 0; i < 4; i++) {
            tf[i] = new JTextField();
            tf[i].setFont(new Font("方正大标宋_GBK", 0, 25));
            tf[i].setBounds(392, 155 + i * 55, 260, 40);
            if (i != 1)
                tf[i].setDocument(new NumLimit()); //限制文本框只能输入数字
            pn_first_2.add(tf[i]);
        }
        bt_back.addMouseListener(new MouseListener() { //返回
            public void mouseEntered(MouseEvent arg0) {
                bt_back.setForeground(Color.blue);
            }

            public void mouseExited(MouseEvent arg0) {
                bt_back.setForeground(null);
            }

            public void mouseClicked(MouseEvent arg0) {
                allDepartment();
            }

            public void mousePressed(MouseEvent arg0) {
            }

            public void mouseReleased(MouseEvent arg0) {
            }
        });
        bt_confirm.addActionListener(new ActionListener() { //确认添加部门信息
            public void actionPerformed(ActionEvent e) {
                if (tf[0].getText().equals("") || tf[1].getText().equals("") || tf[2].getText().equals("") || tf[3].getText().equals("")) {
                    lb_tips.setText("请填写完整！");
                    functionTips();
                } else { //已填写完整
                    boolean isParent = true;
                    if (tf[2].getText().equals("")) {
                        lb_tips.setText("您编辑的部门无上级部门编号");
                        functionTips();
                        isParent = false;
                    }
                    departmentdb = new DepartmentDB(tf[0].getText(), tf[1].getText(), tf[2].getText(), tf[3].getText());
                    String sql; //SQL语句
                    PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
                    ResultSet rs; //ResultSet类，用来存放获取的结果集
                    try {
                        boolean flag;//代表部门编号是否重复
                        //检测部门编号是否存在
                        sql = "SELECT DepartmentID FROM Department WHERE DepartmentID='" + tf[0].getText() + "'";
                        ps = HomePage.connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY);
                        rs = ps.executeQuery(sql);
                        flag = rs.next();
                        rs.first();
                        flag = flag && !(tf[0].getText().equals(departmentdb.department_id));
                        if (flag) { //部门编号重复，进行提示
                            lb_tips.setText("部门编号重复！");
                            functionTips();
                        } else {
                            //输入数据符合要求，进行添加
                            try {
                                sql = "INSERT INTO Department VALUES(?,?,?,?)"; //SQL语句
                                ps = HomePage.connection.prepareStatement(sql); //把操作数据库返回的结果保存到ps中
                                ps.setString(1, departmentdb.department_id); //SQL语句第一个?值
                                ps.setString(2, departmentdb.department_name); //SQL语句第二个?值
                                if (isParent) {
                                    ps.setString(3, departmentdb.parent_department_id); //SQL语句第三个?值
                                } else {
                                    ps.setNull(3, Types.NULL);
                                }
                                ps.setString(4, departmentdb.manager_id); //SQL语句第四个?值
                                ps.executeUpdate();
                                lb_tips.setText("部门添加成功！");
                                functionTips();//更新，执行插入操作

                                departmentdb = null;
                                for (int i = 0; i < 4; i++) {
                                    tf[i].setEditable(false);
                                }
                                pn_first_2.remove(bt_confirm);
                                sarAdminJFrame.repaint();
                                rs.close();
                                ps.close();
                            } catch (SQLException se) {
                                lb_tips.setText("数据库操作出错！");
                                functionTips();
                                se.printStackTrace();
                            }
                        }

                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
                }
            }
        });
        pn_first.removeAll();
        sarAdminJFrame.repaint();
        pn_first.add(pn_first_2);
        sarAdminJFrame.validate();
    }

    public void queryDepartmentInfo_1() { //查询部门信息
        pn_second.setLayout(null);
        pn_second_1.removeAll();
        pn_second_1.setLayout(null);
        pn_second_1.setBounds(0, 0, 1045, 695);
        pn_second_1.setBorder(BorderFactory.createEtchedBorder());
        JButton bt_query = new JButton("查询", new ImageIcon("image/query.png"));
        bt_query.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_query.setBounds(447, 520, 150, 50);
        bt_query.setContentAreaFilled(false);
        bt_query.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel lb_departmentImage = new JLabel(new ImageIcon("image/department.png"));
        lb_departmentImage.setBounds(750, 205, 200, 200);
        pn_second_1.add(bt_query);
        pn_second_1.add(lb_departmentImage);
        JLabel[] lb = new JLabel[4];
        for (int i = 0; i < 4; i++) {
            lb[i] = new JLabel();
            lb[i].setFont(new Font("方正大标宋_GBK", 0, 25));
            if (i < 2)
                lb[i].setBounds(260, 150 + i * 55, 130, 50);
            else
                lb[i].setBounds(210, 150 + i * 55, 180, 50);
            pn_second_1.add(lb[i]);
        }
        lb[0].setText("部门编号：");
        lb[1].setText("部门名称：");
        lb[2].setText("上级部门编号：");
        lb[3].setText("部门经理编号：");

        JTextField[] tf = new JTextField[4];
        for (int i = 0; i < 4; i++) {
            tf[i] = new JTextField();
            tf[i].setFont(new Font("方正大标宋_GBK", 0, 25));
            tf[i].setBounds(392, 155 + i * 55, 260, 40);
            if (i != 1)
                tf[i].setDocument(new NumLimit()); //限制文本框只能输入数字
            pn_second_1.add(tf[i]);
        }
        //查询部门信息
        bt_query.addActionListener(e -> {
            String sql = "SELECT * FROM Department";
            if (tf[0].getText().equals("") && tf[1].getText().equals("") && tf[2].getText().equals("") && tf[3].getText().equals("")) {
                //无查询条件
            } else { //有查询条件
                sql = sql + " WHERE 1=1";
                if (!tf[0].getText().equals(""))
                    sql = sql + " AND DepartmentID='" + tf[0].getText() + "'";
                if (!tf[1].getText().equals(""))
                    sql = sql + " AND Name=" + tf[1].getText();
                if (!tf[2].getText().equals(""))
                    sql = sql + " AND ParentDepartmentID=" + tf[2].getText();
                if (!tf[3].getText().equals(""))
                    sql = sql + " AND ManagerID=" + tf[3].getText();
            }
            queryDepartmentInfo_2(sql);
        });
        pn_second.removeAll();
        sarAdminJFrame.repaint();
        pn_second.add(pn_second_1);
        sarAdminJFrame.validate();
    }

    public void queryDepartmentInfo_2(String sql) { //查询部门信息结果
        String[] columnNames = {"部门编号", "部门名称", "上级部门编号", "部门经理编号", "操作"}; //表格列名
        String[][] rowData = null; //表格数据
        int count = 0; //查询到的部门个数
        try {
            PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
            ps = HomePage.connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY); //把操作数据库返回的结果保存到ps中
            ResultSet rs = ps.executeQuery(sql); //ResultSet类，用来存放获取的结果集
            rs.last();
            count = rs.getRow(); //获取查询到的部门个数
            if (count == 0) { //若无符合条件的部门
                rowData = new String[1][4];
                for (int i = 0; i < 4; i++)
                    rowData[0][i] = "无";
            } else { //若有符合条件的部门
                rowData = new String[count][4];
                rs.first();
                int i = 0;
                do { //获取符合条件的部门信息
                    rowData[i][0] = rs.getString("DepartmentID"); //部门号
                    rowData[i][1] = rs.getString("Name"); //部门名称
                    rowData[i][2] = rs.getString("ParentDepartmentID"); //上级部门号
                    rowData[i][3] = rs.getString("ManagerID"); //部门经理号
                    i++;
                } while (rs.next());
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //界面
        pn_second_1.removeAll();
        pn_second_1.setBounds(0, 0, 1045, 695);
        pn_second_1.setLayout(null);
        JPanel pn_top = new JPanel();
        pn_top.setBounds(0, 0, 1045, 50);
        JLabel lb_num = new JLabel(" 共查询到" + count + "条部门信息！ ");
        lb_num.setFont(new Font("方正大标宋_GBK", 0, 25));
        JButton bt_back = new JButton("返回", new ImageIcon("image/back.png"));
        bt_back.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_back.setContentAreaFilled(false);
        bt_back.setBorderPainted(false);
        bt_back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pn_top.add(bt_back);
        pn_top.add(lb_num);
        JTable table = new JTable(new MyTableModel(columnNames, rowData, 2));
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(1, 35)); //设置表头高度
        header.setFont(new Font("方正大标宋_GBK", Font.BOLD, 23));
        table.setRowHeight(35); //设置各行高度
        table.setFont(new Font("方正大标宋_GBK", 0, 20));
        table.setBackground(null);
        table.getTableHeader().setReorderingAllowed(false); //不允许移动各列
        JScrollPane scrollPane = new JScrollPane(table); //滚动条
        scrollPane.setBounds(0, 50, 1045, 645);
        pn_second_1.add(pn_top);
        pn_second_1.add(scrollPane);
        if (count != 0) {
            MyEvent e = new MyEvent() { //点击“查看”按钮，查看信息，可修改和删除信息
                public void invoke(ActionEvent e) {
                    MyButton button = (MyButton) e.getSource();
                    visitDepartmentInfo((String) table.getValueAt(button.getRow(), button.getColumn() - 6), 2, sql);
                }
            };
            table.getColumnModel().getColumn(6).setCellRenderer(new MyButtonRender("查看")); //设置表格的渲染器
            MyButtonEditor editor = new MyButtonEditor(e, "查看");
            table.getColumnModel().getColumn(6).setCellEditor(editor); //设置表格的编辑器
        }
        bt_back.addMouseListener(new MouseListener() { //返回
            public void mouseEntered(MouseEvent arg0) {
                bt_back.setForeground(Color.blue);
            }

            public void mouseExited(MouseEvent arg0) {
                bt_back.setForeground(null);
            }

            public void mouseClicked(MouseEvent arg0) {
                queryDepartmentInfo_1();
            }

            public void mousePressed(MouseEvent arg0) {
            }

            public void mouseReleased(MouseEvent arg0) {
            }
        });
        pn_second.removeAll();
        sarAdminJFrame.repaint();
        pn_second.add(pn_second_1);
        sarAdminJFrame.validate();
    }

    public JTabbedPane employeeManage() { //员工管理
        JTabbedPane tp_employee = new JTabbedPane();
        tp_employee.setFont(new Font("方正大标宋_GBK", 0, 25));
        tp_employee.setBounds(0, 0, 1045, 735);
        allEmployee();
        tp_employee.addTab(" 全部 ", pn_first);
        queryEmployeeInfo_1();
        tp_employee.addTab(" 查询 ", pn_second);
        return tp_employee;
    }

    public void allEmployee() { //该财务管理的部门的所有员工
        String[] columnNames = {"编号", "姓名", "部门", "职位", "职称", "入职日期", "操作"}; //表格列名
        String[][] rowData = null; //表格数据
        int count = 0; //表的元组总数
        try { //获取EmployeeView视图信息
            String sql = "SELECT * FROM EmployeeView WHERE DepartmentName=(SELECT Name FROM Department WHERE DepartmentID = " + department_id + ")"; //SQL语句
            PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
            ps = HomePage.connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY); //把操作数据库返回的结果保存到ps中
            ResultSet rs = ps.executeQuery(sql); //ResultSet类，用来存放获取的结果集
            rs.last();
            count = rs.getRow(); //获取员工元组总数
            if (count == 0) { //若EmployeeView视图无元组
                rowData = new String[1][7];
                for (int i = 0; i < 6; i++)
                    rowData[0][i] = "无";
            } else { //若adminview_student视图有元组
                rowData = new String[count][7];
                rs.first();
                int i = 0;
                do { //获取该财务管理的部门的所有学生信息
                    rowData[i][0] = rs.getString("EmployeeID"); // 编号
                    rowData[i][1] = rs.getString("EmployeeName"); //姓名
                    rowData[i][2] = rs.getString("DepartmentName"); //部门
                    rowData[i][3] = rs.getString("PositionName"); //职位
                    rowData[i][4] = rs.getString("TitleName"); //职称
                    rowData[i][5] = rs.getString("HireDate"); //入职日期
                    i++;
                } while (rs.next());
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //界面
        pn_first.setLayout(null);
        pn_first_1.removeAll();
        pn_first_1.setBounds(0, 0, 1045, 695);
        pn_first_1.setLayout(null);
        JPanel pn_top = new JPanel();
        pn_top.setBounds(0, 0, 1045, 50);
        JLabel lb_num;
        if (department_id.equals(""))
            lb_num = new JLabel("  您无管理的部门！  ");
        else
            lb_num = new JLabel("  编号为 " + department_id + "的部门共有" + count + "条员工信息！  ");
        lb_num.setFont(new Font("方正大标宋_GBK", 0, 25));
        JButton bt_add = new JButton("添加", new ImageIcon("image/add.png")), bt_export = new JButton("导出", new ImageIcon("image/out.png"));

        bt_add.setBackground(Color.white.darker());
        bt_add.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_add.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (department_id.equals(""))
            bt_add.setEnabled(false);
        bt_export.setBackground(Color.green.darker());
        bt_export.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_export.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pn_top.add(bt_add);
        pn_top.add(lb_num);
        pn_top.add(bt_export);

        JTable table = new JTable(new MyTableModel(columnNames, rowData, 4));

        JTableHeader header = table.getTableHeader();

        header.setPreferredSize(new Dimension(1, 35)); //设置表头高度
        header.setFont(new Font("方正大标宋_GBK", Font.BOLD, 23));

        table.setRowHeight(35); //设置各行高度
        table.setFont(new Font("方正大标宋_GBK", 0, 20));
        table.setBackground(null);
        table.getTableHeader().setReorderingAllowed(false); //不允许移动各列

        JScrollPane scrollPane = new JScrollPane(table); //滚动条
        scrollPane.setBounds(0, 50, 1045, 645);
        pn_first_1.add(pn_top);
        pn_first_1.add(scrollPane);

        if (count != 0) {
            MyEvent e = new MyEvent() { //点击“查看”按钮，查看信息，可修改和删除信息
                public void invoke(ActionEvent e) {
                    MyButton button = (MyButton) e.getSource();
                    pn_first_2.removeAll();
                    visitEmployeeInfo((String) table.getValueAt(button.getRow(), button.getColumn() - 6), 1, null);
                }
            };
            table.getColumnModel().getColumn(6).setCellRenderer(new MyButtonRender("查看")); //设置表格的渲染器
            MyButtonEditor editor = new MyButtonEditor(e, "查看");
            table.getColumnModel().getColumn(6).setCellEditor(editor); //设置表格的编辑器
        }

        bt_add.addActionListener(new ActionListener() { //添加学生信息
            public void actionPerformed(ActionEvent e) {
                pn_first_2.removeAll();
                addEmployeeInfo();
            }
        });
        bt_export.addActionListener(new ActionListener() { //将表格导出成Excel文件
            public void actionPerformed(ActionEvent e) {
                FileDialog fd = new FileDialog(sarAdminJFrame, "请设置导出位置和文件名！", FileDialog.SAVE);
                fd.setVisible(true);
                String file = fd.getDirectory() + fd.getFile() + ".xls";
                if (fd.getFile() != null)
                    JTableToExcel.export(new File(file), table);
            }
        });
        pn_first.removeAll();
        sarAdminJFrame.repaint();
        pn_first.add(pn_first_1);
        sarAdminJFrame.validate();
    }

    public void visitEmployeeInfo(String employee_id, int x, String query_sql) { //查看部门信息，可修改和删除
        if (x == 1) {
            pn_first_2.removeAll();
            pn_first_2.setLayout(null);
            pn_first_2.setBounds(0, 0, 1045, 695);
            pn_first_2.setBorder(BorderFactory.createEtchedBorder());
        } else {
            pn_second_2.removeAll();
            pn_second_2.setLayout(null);
            pn_second_2.setBounds(0, 0, 1045, 695);
            pn_second_2.setBorder(BorderFactory.createEtchedBorder());
        }

        JButton bt_back = new JButton("返回", new ImageIcon("image/back.png"));
        bt_back.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_back.setBounds(1, 10, 92, 25);
        bt_back.setContentAreaFilled(false);
        bt_back.setBorderPainted(false);
        bt_back.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton bt_modify = new JButton("修改员工信息", new ImageIcon("image/modify.png")), bt_delete = new JButton("删除员工信息", new ImageIcon("image/delete.png")),
                bt_save = new JButton("保存", new ImageIcon("image/save.png")), bt_cancel = new JButton("取消", new ImageIcon("image/cancel.png"));
        bt_modify.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_modify.setBounds(755, 380, 190, 40);
        bt_modify.setContentAreaFilled(false);
        bt_modify.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bt_delete.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_delete.setBounds(755, 430, 190, 40);
        bt_delete.setForeground(Color.red);
        bt_delete.setContentAreaFilled(false);
        bt_delete.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bt_save.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_save.setBounds(750, 380, 95, 40);
        bt_save.setContentAreaFilled(false);
        bt_save.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bt_cancel.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_cancel.setBounds(855, 380, 95, 40);
        bt_cancel.setContentAreaFilled(false);
        bt_cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lb_infoImage = new JLabel(new ImageIcon("image/user.png"));
        lb_infoImage.setBounds(750, 165, 200, 200);
        if (x == 1) {
            pn_first_2.add(bt_back);
            pn_first_2.add(bt_modify);
            pn_first_2.add(bt_delete);
            pn_first_2.add(lb_infoImage);
        } else {
            pn_second_2.add(bt_back);
            pn_second_2.add(bt_modify);
            pn_second_2.add(bt_delete);
            pn_second_2.add(lb_infoImage);
        }
        JLabel[] lb = new JLabel[7];
        for (int i = 0; i < 7; i++) {
            lb[i] = new JLabel();
            lb[i].setFont(new Font("方正大标宋_GBK", 0, 25));
            lb[i].setBounds(260, 65 + i * 55, 130, 50);
            if (x == 1)
                pn_first_2.add(lb[i]);
            else
                pn_second_2.add(lb[i]);
        }

        lb[0].setText("编    号：");
        lb[1].setText("姓    名：");
        lb[2].setText("职称编号：");
        lb[3].setText("职位编号：");
        lb[4].setText("部门编号：");
        lb[5].setText("入职日期：");
        lb[6].setText("基本工资：");
        lb[4].setFont(new Font("方正大标宋_GBK", 1, 25));

        JTextField[] tf = new JTextField[7];
        for (int i = 0; i < 7; i++) {
            tf[i] = new JTextField();
            tf[i].setFont(new Font("方正大标宋_GBK", 0, 25));
            tf[i].setBounds(392, 70 + i * 55, 260, 40);
            tf[i].setEditable(false);
            if (x == 1)
                pn_first_2.add(tf[i]);
            else
                pn_second_2.add(tf[i]);
        }
        try {
            String sql = "SELECT * FROM Employee WHERE EmployeeID='" + employee_id + "'"; //SQL语句
            PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
            ps = HomePage.connection.prepareStatement(sql); //把操作数据库返回的结果保存到ps中
            ResultSet rs = ps.executeQuery(sql); //ResultSet类，用来存放获取的结果集
            while (rs.next()) {
                employeedb = new EmployeeDB(rs.getString("EmployeeID"), rs.getString("Name"), rs.getString("TitleID"), rs.getString("DepartmentID"), rs.getString("PositionID"), rs.getString("HireDate"), rs.getString("BasicSalary"));
                tf[0].setText(employeedb.employee_id);
                tf[1].setText(employeedb.name);
                tf[2].setText(employeedb.title_id);
                tf[3].setText(employeedb.position_id);
                tf[4].setText(employeedb.department_id);
                tf[5].setText(employeedb.hire_date);
                tf[6].setText(employeedb.basic_salary);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        bt_back.addMouseListener(new MouseListener() { //返回
            public void mouseEntered(MouseEvent arg0) {
                bt_back.setForeground(Color.blue);
            }

            public void mouseExited(MouseEvent arg0) {
                bt_back.setForeground(null);
            }

            public void mouseClicked(MouseEvent arg0) {
                employeedb = null;
                if (x == 1)
                    allEmployee();
                else
                    queryEmployeeInfo_2(query_sql);
            }

            public void mousePressed(MouseEvent arg0) {
            }

            public void mouseReleased(MouseEvent arg0) {
            }
        });
        bt_modify.addActionListener(new ActionListener() { //修改员工信息
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 8; i++)
                    if (i != 7)
                        tf[i].setEditable(true);
                if (x == 1) {
                    pn_first_2.remove(bt_modify);
                    pn_first_2.remove(bt_delete);
                    sarAdminJFrame.repaint();
                    pn_first_2.add(bt_save);
                    pn_first_2.add(bt_cancel);
                    sarAdminJFrame.validate();
                } else {
                    pn_second_2.remove(bt_modify);
                    pn_second_2.remove(bt_delete);
                    sarAdminJFrame.repaint();
                    pn_second_2.add(bt_save);
                    pn_second_2.add(bt_cancel);
                    sarAdminJFrame.validate();
                }
            }
        });
        bt_delete.addActionListener(new ActionListener() { //删除学生信息
            public void actionPerformed(ActionEvent e) {
                lb_tips.setText("是否确定删除该员工信息？");
                if (x == 1)
                    choiceTips("");
                else
                    choiceTips(query_sql);
            }
        });
        bt_save.addActionListener(new ActionListener() { //保存
            public void actionPerformed(ActionEvent e) {
                String sql; //SQL语句
                PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
                ResultSet rs; //ResultSet类，用来存放获取的结果集
                try {
                    if (tf[0].getText().equals("") || tf[1].getText().equals("") || tf[2].getText().equals("") || tf[3].getText().equals("") || tf[4].getText().equals("") || tf[5].getText().equals("") || tf[6].getText().equals("")) {
                        lb_tips.setText("请填写完整！");
                        functionTips();
                    } else { //已填写完整
                        boolean flag = false; //代表员工编号是否重复
                        //检测员工编号是否重复
                        sql = "SELECT EmployeeID FROM Employee WHERE EmployeeID='" + tf[0].getText() + "'";
                        ps = HomePage.connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY);
                        rs = ps.executeQuery(sql);
                        flag = rs.next();
                        rs.first();
                        flag = flag && !(tf[0].getText().equals(employeedb.employee_id));
                        if (flag) { //员工编号重复，进行提示
                            lb_tips.setText("员工编号重复！");
                            functionTips();
                        } else { //输入数据符合要求，进行保存
                            try {
                                sql = "UPDATE Employee SET EmployeeID=?,Name=?,TitleID=?,PositionID=?,DepartmentID=?,HireDate=?,BasicSalary=? WHERE EmployeeID='" + employeedb.employee_id + "'";
                                ps = HomePage.connection.prepareStatement(sql);
                                ps.setInt(1, Integer.parseInt(tf[0].getText())); //SQL语句第一个?值
                                ps.setString(2, tf[1].getText()); //SQL语句第二个?值
                                ps.setInt(3, Integer.parseInt(tf[2].getText())); //SQL语句第三个?值
                                ps.setInt(4, Integer.parseInt(tf[3].getText())); //SQL语句第四个?值
                                ps.setInt(5, Integer.parseInt(tf[4].getText())); //SQL语句第五个?值

                                try {
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    ps.setDate(6, new java.sql.Date((dateFormat.parse(tf[5].getText())).getTime())); //SQL语句第六个?值
                                } catch (ParseException pe) {
                                    lb_tips.setText("数据转换出错！");
                                    functionTips();
                                    pe.printStackTrace();
                                }

                                ps.setBigDecimal(7, BigDecimal.valueOf(Double.parseDouble(tf[6].getText()))); //SQL语句第七个?值


                                ps.executeUpdate(); //更新，执行修改操作
                                rs.close();
                                ps.close();
                                employeedb = new EmployeeDB(tf[0].getText(), tf[1].getText(), tf[2].getText(), tf[3].getText(), tf[4].getText(), tf[5].getText(), tf[6].getText());
                                // studnet=new Student(tf[0].getText(),tf[1].getText(),tf[2].getText(),tf[3].getText(),tf[4].getText(),tf[5].getText(),tf[6].getText(),tf[7].getText(),tf[8].getText(),tf[9].getText());
                                for (int i = 0; i < 7; i++)
                                    tf[i].setEditable(false);
                                if (x == 1) {
                                    pn_first_2.remove(bt_save);
                                    pn_first_2.remove(bt_cancel);
                                    sarAdminJFrame.repaint();
                                    pn_first_2.add(bt_modify);
                                    pn_first_2.add(bt_delete);
                                    sarAdminJFrame.validate();
                                } else {
                                    pn_second_2.remove(bt_save);
                                    pn_second_2.remove(bt_cancel);
                                    sarAdminJFrame.repaint();
                                    pn_second_2.add(bt_modify);
                                    pn_second_2.add(bt_delete);
                                    sarAdminJFrame.validate();
                                }
                            } catch (SQLException se) {
                                lb_tips.setText("数据库操作出错！");
                                functionTips();
                                se.printStackTrace();
                            } catch (NumberFormatException ne) {
                                lb_tips.setText("数据转换出错！");
                                functionTips();
                                ne.printStackTrace();
                            }
                        }
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        });
        bt_cancel.addActionListener(new ActionListener() { //取消
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 7; i++)
                    tf[i].setEditable(false);
                tf[0].setText(employeedb.employee_id);
                tf[1].setText(employeedb.name);
                tf[2].setText(employeedb.title_id);
                tf[3].setText(employeedb.position_id);
                tf[4].setText(employeedb.department_id);
                tf[5].setText(employeedb.hire_date);
                tf[6].setText(employeedb.basic_salary);
                if (x == 1) {
                    pn_first_2.remove(bt_save);
                    pn_first_2.remove(bt_cancel);
                    sarAdminJFrame.repaint();
                    pn_first_2.add(bt_modify);
                    pn_first_2.add(bt_delete);
                    sarAdminJFrame.validate();
                } else {
                    pn_second_2.remove(bt_save);
                    pn_second_2.remove(bt_cancel);
                    sarAdminJFrame.repaint();
                    pn_second_2.add(bt_modify);
                    pn_second_2.add(bt_delete);
                    sarAdminJFrame.validate();
                }
            }
        });
        if (x == 1) {
            pn_first.removeAll();
            sarAdminJFrame.repaint();
            pn_first.add(pn_first_2);
            sarAdminJFrame.validate();
        } else {
            pn_second.removeAll();
            sarAdminJFrame.repaint();
            pn_second.add(pn_second_2);
            sarAdminJFrame.validate();
        }
    }

    public void addEmployeeInfo() { //添加员工信息
        pn_first_2.removeAll();
        pn_first_2.setLayout(null);
        pn_first_2.setBounds(0, 0, 1045, 695);
        pn_first_2.setBorder(BorderFactory.createEtchedBorder());
        JButton bt_back = new JButton("返回", new ImageIcon("image/back.png"));
        bt_back.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_back.setBounds(1, 10, 92, 25);
        bt_back.setContentAreaFilled(false);
        bt_back.setBorderPainted(false);
        bt_back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JButton bt_confirm = new JButton("确认添加", new ImageIcon("image/confirm.png"));
        bt_confirm.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_confirm.setBounds(447, 625, 150, 50);
        bt_confirm.setContentAreaFilled(false);
        bt_confirm.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton bt_time = new JButton("获取当前时间", new ImageIcon("image/time.png"));
        bt_time.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_time.setBounds(765, 415, 170, 40);
        bt_time.setContentAreaFilled(false);
        bt_time.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lb_infoImage = new JLabel(new ImageIcon("image/user.png"));
        lb_infoImage.setBounds(750, 205, 200, 200);

        pn_first_2.add(bt_back);
        pn_first_2.add(bt_confirm);
        pn_first_2.add(lb_infoImage);
        pn_first_2.add(bt_time);

        JLabel[] lb = new JLabel[7];
        for (int i = 0; i < 7; i++) {
            lb[i] = new JLabel();
            lb[i].setFont(new Font("方正大标宋_GBK", 0, 25));
            lb[i].setBounds(260, 65 + i * 55, 130, 50);
            pn_first_2.add(lb[i]);
        }
        lb[0].setText("编    号：");
        lb[1].setText("姓    名：");
        lb[2].setText("职称编号：");
        lb[3].setText("职位编号：");
        lb[4].setText("部门编号：");
        lb[5].setText("入职日期：");
        lb[6].setText("基本工资：");
        JTextField[] tf = new JTextField[7];
        for (int i = 0; i < 7; i++) {
            tf[i] = new JTextField();
            tf[i].setFont(new Font("方正大标宋_GBK", 0, 25));
            tf[i].setBounds(392, 70 + i * 55, 260, 40);
            if (i == 0 || i == 2 || i == 3 || i == 4)
                tf[i].setDocument(new NumLimit()); //限制文本框只能输入数字
            pn_first_2.add(tf[i]);
        }
        tf[4].setText(department_id);
        tf[4].setEditable(false);

        bt_back.addMouseListener(new MouseListener() { //返回
            public void mouseEntered(MouseEvent arg0) {
                bt_back.setForeground(Color.blue);
            }

            public void mouseExited(MouseEvent arg0) {
                bt_back.setForeground(null);
            }

            public void mouseClicked(MouseEvent arg0) {
                allEmployee();
            }

            public void mousePressed(MouseEvent arg0) {
            }

            public void mouseReleased(MouseEvent arg0) {
            }
        });
        bt_time.addActionListener(new ActionListener() { //获取当前时间
            public void actionPerformed(ActionEvent e) {
                Date date = new Date();
                SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
                tf[5].setText(timeFormat.format(date));
            }
        });
        bt_confirm.addActionListener(new ActionListener() { //确认添加学生信息
            public void actionPerformed(ActionEvent e) {
                String sex = null;
                if (tf[0].getText().equals("") || tf[1].getText().equals("") || tf[2].getText().equals("") || tf[3].getText().equals("") || tf[4].getText().equals("") || tf[5].getText().equals("") || tf[6].getText().equals("")) {
                    lb_tips.setText("请填写完整！");
                    functionTips();
                } else { //已填写完整

                    employeedb = new EmployeeDB(tf[0].getText(), tf[1].getText(), tf[2].getText(), sex, tf[3].getText(), tf[4].getText(), tf[5].getText());
                    String sql; //SQL语句
                    PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
                    ResultSet rs; //ResultSet类，用来存放获取的结果集
                    try {
                        //检测员工编号是否存在
                        sql = "SELECT EmployeeID FROM Employee WHERE EmployeeID='" + tf[0].getText() + "'";
                        ps = HomePage.connection.prepareStatement(sql);
                        rs = ps.executeQuery(sql);
                        if (rs.next()) { //编号存在，则进行提示
                            lb_tips.setText("编号已经存在！");
                            functionTips();
                        } else { //输入数据符合要求，进行添加
                            //检测职称编号是否存在
                            sql = "SELECT TitleID FROM Title WHERE TitleID='" + tf[2].getText() + "'";
                            ps = HomePage.connection.prepareStatement(sql);
                            rs = ps.executeQuery(sql);
                            if (!rs.next()) { //职称编号不存在，进行提示
                                lb_tips.setText("职称编号不存在！");
                                functionTips();
                            } else {
                                sql = "SELECT DepartmentID FROM Department WHERE DepartmentID='" + tf[4].getText() + "'";
                                ps = HomePage.connection.prepareStatement(sql);
                                rs = ps.executeQuery(sql);
                                if (!rs.next()) { //职称编号不存在，进行提示
                                    lb_tips.setText("职称编号不存在！");
                                    functionTips();
                                } else {
                                    try {
                                        sql = "INSERT INTO Employee VALUES(?,?,?,?,?,?,?)"; //SQL语句
                                        ps = HomePage.connection.prepareStatement(sql); //把操作数据库返回的结果保存到ps中

                                        ps.setInt(1, Integer.parseInt(tf[0].getText())); //SQL语句第一个?值
                                        ps.setString(2, tf[1].getText()); //SQL语句第二个?值
                                        ps.setInt(3, Integer.parseInt(tf[2].getText())); //SQL语句第三个?值
                                        ps.setInt(4, Integer.parseInt(tf[3].getText())); //SQL语句第四个?值
                                        ps.setInt(5, Integer.parseInt(tf[4].getText())); //SQL语句第五个?值

                                        try {
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                            ps.setDate(6, new java.sql.Date((dateFormat.parse(tf[5].getText())).getTime())); //SQL语句第六个?值
                                        } catch (ParseException pe) {
                                            lb_tips.setText("数据转换出错！");
                                            functionTips();
                                            pe.printStackTrace();
                                        }

                                        ps.setBigDecimal(7, BigDecimal.valueOf(Double.parseDouble(tf[6].getText()))); //SQL语句第七个?值

                                        ps.executeUpdate(); //更新，执行插入操作
                                        lb_tips.setText("员工添加成功！");
                                        functionTips();
                                        employeedb = null;
                                        for (int i = 0; i < 7; i++) {
                                            tf[i].setEditable(false);
                                        }
                                        pn_first_2.remove(bt_confirm);
                                        sarAdminJFrame.repaint();
                                        rs.close();
                                        ps.close();
                                    } catch (SQLException se) {
                                        lb_tips.setText("数据库操作出错！");
                                        functionTips();
                                        se.printStackTrace();
                                    }
                                }
                            }
                        }
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
                }
            }
        });
        pn_first.removeAll();
        sarAdminJFrame.repaint();
        pn_first.add(pn_first_2);
        sarAdminJFrame.validate();
    }

    public void queryEmployeeInfo_1() { //查询员工信息
        pn_second.setLayout(null);
        pn_second_1.removeAll();
        pn_second_1.setLayout(null);
        pn_second_1.setBounds(0, 0, 1045, 695);
        pn_second_1.setBorder(BorderFactory.createEtchedBorder());

        JButton bt_query = new JButton("查询", new ImageIcon("image/query.png"));
        bt_query.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_query.setBounds(447, 625, 150, 50);
        bt_query.setContentAreaFilled(false);
        bt_query.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lb_infoImage = new JLabel(new ImageIcon("image/user.png"));
        lb_infoImage.setBounds(750, 205, 200, 200);
        pn_second_1.add(bt_query);
        pn_second_1.add(lb_infoImage);

        JLabel[] lb = new JLabel[5];
        for (int i = 0; i < 5; i++) {
            lb[i] = new JLabel();
            lb[i].setFont(new Font("方正大标宋_GBK", 0, 25));
            lb[i].setBounds(260, 65 + i * 55, 130, 50);
            pn_second_1.add(lb[i]);
        }
        lb[0].setText("编    号：");
        lb[1].setText("姓    名：");
        lb[2].setText("职    称：");
        lb[3].setText("职    位：");
        lb[4].setText("入职日期：");

        JTextField[] tf = new JTextField[5];
        for (int i = 0; i < 5; i++) {
            tf[i] = new JTextField();
            tf[i].setFont(new Font("方正大标宋_GBK", 0, 25));
            tf[i].setBounds(392, 70 + i * 55, 260, 40);
            if (i == 0)
                tf[i].setDocument(new NumLimit()); //限制文本框只能输入数字
            pn_second_1.add(tf[i]);
        }
        //查询学生信息
        bt_query.addActionListener(e -> {
            String sql = "SELECT * FROM EmployeeView WHERE DepartmentName=(SELECT Name FROM Department WHERE DepartmentID = " + department_id + ") AND 1=1";
            if (tf[0].getText().equals("") && tf[1].getText().equals("") && tf[2].getText().equals("") && tf[3].getText().equals("") && tf[4].getText().equals("")) {
                //无查询条件
            } else { //有查询条件
                if (!tf[0].getText().equals(""))
                    sql = sql + " AND EmployeeID='" + tf[0].getText() + "'";
                if (!tf[1].getText().equals(""))
                    sql = sql + " AND EmployeeName='" + tf[1].getText() + "'";
                if (!tf[2].getText().equals(""))
                    sql = sql + " AND TitleName='" + tf[2].getText() + "'";
                if (!tf[3].getText().equals(""))
                    sql = sql + " AND PositionName='" + tf[3].getText() + "'";
                // if(!tf[4].getText().equals(""))
                // 	sql=sql+" AND DepartMentName="+tf[4].getText();
                if (!tf[4].getText().equals(""))
                    sql = sql + " AND HireDate=" + tf[4].getText();
            }
            queryEmployeeInfo_2(sql);
        });
        pn_second.removeAll();
        sarAdminJFrame.repaint();
        pn_second.add(pn_second_1);
        sarAdminJFrame.validate();
    }

    public void queryEmployeeInfo_2(String sql) { //查询员工信息结果

        String[] columnNames = {"编号", "姓名", "部门", "职位", "职称", "入职日期", "操作"}; //表格列名
        String[][] rowData = null; //表格数据
        int count = 0; //查询到的员工个数
        try {
            PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
            ps = HomePage.connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY); //把操作数据库返回的结果保存到ps中
            ResultSet rs = ps.executeQuery(sql); //ResultSet类，用来存放获取的结果集
            rs.last();
            count = rs.getRow(); //获取查询到的员工个数
            if (count == 0) { //若无符合条件的员工
                rowData = new String[1][7];
                for (int i = 0; i < 6; i++)
                    rowData[0][i] = "无";
            } else { //若有符合条件的员工
                rowData = new String[count][7];
                rs.first();
                int i = 0;
                do { //获取符合条件的员工信息
                    rowData[i][0] = rs.getString("EmployeeID"); // 编号
                    rowData[i][1] = rs.getString("EmployeeName"); //姓名
                    rowData[i][2] = rs.getString("DepartmentName"); //部门
                    rowData[i][3] = rs.getString("PositionName"); //职位
                    rowData[i][4] = rs.getString("TitleName"); //职称
                    rowData[i][5] = rs.getString("HireDate"); //入职日期
                    i++;
                } while (rs.next());
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //界面
        pn_second_1.removeAll();
        pn_second_1.setBounds(0, 0, 1045, 695);
        pn_second_1.setLayout(null);

        JPanel pn_top = new JPanel();
        pn_top.setBounds(0, 0, 1045, 50);

        JLabel lb_num = new JLabel(" 共查询到" + count + "条员工信息！ ");
        lb_num.setFont(new Font("方正大标宋_GBK", 0, 25));

        JButton bt_back = new JButton("返回", new ImageIcon("image/back.png"));
        bt_back.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_back.setContentAreaFilled(false);
        bt_back.setBorderPainted(false);
        bt_back.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pn_top.add(bt_back);
        pn_top.add(lb_num);


        JTable table = new JTable(new MyTableModel(columnNames, rowData, 4));

        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(1, 35)); //设置表头高度
        header.setFont(new Font("方正大标宋_GBK", Font.BOLD, 23));
        table.setRowHeight(35); //设置各行高度
        table.setFont(new Font("方正大标宋_GBK", 0, 20));
        table.setBackground(null);
        table.getTableHeader().setReorderingAllowed(false); //不允许移动各列

        JScrollPane scrollPane = new JScrollPane(table); //滚动条
        scrollPane.setBounds(0, 50, 1045, 645);
        pn_second_1.add(pn_top);
        pn_second_1.add(scrollPane);
        if (count != 0) {
            MyEvent e = new MyEvent() { //点击“查看”按钮，查看信息，可修改和删除信息
                public void invoke(ActionEvent e) {
                    MyButton button = (MyButton) e.getSource();
                    visitEmployeeInfo((String) table.getValueAt(button.getRow(), button.getColumn() - 6), 2, sql);
                }
            };
            table.getColumnModel().getColumn(6).setCellRenderer(new MyButtonRender("查看")); //设置表格的渲染器
            MyButtonEditor editor = new MyButtonEditor(e, "查看");
            table.getColumnModel().getColumn(6).setCellEditor(editor); //设置表格的编辑器
        }
        bt_back.addMouseListener(new MouseListener() { //返回
            public void mouseEntered(MouseEvent arg0) {
                bt_back.setForeground(Color.blue);
            }

            public void mouseExited(MouseEvent arg0) {
                bt_back.setForeground(null);
            }

            public void mouseClicked(MouseEvent arg0) {
                queryEmployeeInfo_1();
            }

            public void mousePressed(MouseEvent arg0) {
            }

            public void mouseReleased(MouseEvent arg0) {
            }
        });
        pn_second.removeAll();
        sarAdminJFrame.repaint();
        pn_second.add(pn_second_1);
        sarAdminJFrame.validate();
    }

    public JTabbedPane Title_and_PositionManage() { //工资调整
        JTabbedPane tp_title_position = new JTabbedPane();
        tp_title_position.setFont(new Font("方正大标宋_GBK", 0, 25));
        tp_title_position.setBounds(0, 0, 1045, 735);
        allTitle();
        tp_title_position.addTab(" 职称 ", pn_first);
        allPosition();
        tp_title_position.addTab(" 职位 ", pn_second);
        changeTenureSalary();
        tp_title_position.addTab(" 工龄工资 ", pn_third);
        changeSalaryPayment();
        tp_title_position.addTab(" 工资发放日", pn_fourth);

        return tp_title_position;
    }

    public void allPosition() { //该财务管理的部门的所有职位
        String[] columnNames = {"职位编号", "职位名称", "职位工资", "操作"}; //表格列名
        String[][] rowData = null; //表格数据
        int count = 0; //表的元组总数
        try { //获取Position表信息
            String sql = "SELECT * FROM Position"; //SQL语句
            PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
            ps = HomePage.connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY); //把操作数据库返回的结果保存到ps中
            ResultSet rs = ps.executeQuery(sql); //ResultSet类，用来存放获取的结果集
            rs.last();
            count = rs.getRow(); //获取职位元组总数
            if (count == 0) { //若Position表无元组
                rowData = new String[1][4];
                for (int i = 0; i < 3; i++)
                    rowData[0][i] = "无";
            } else { //若Position表有元组
                rowData = new String[count][4];
                rs.first();
                int i = 0;
                do { //获取所有职位信息
                    rowData[i][0] = rs.getString("PositionID"); //学号
                    rowData[i][1] = rs.getString("Name"); //姓名
                    rowData[i][2] = rs.getString("Salary"); //楼号
                    i++;
                } while (rs.next());
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //界面
        pn_second.setLayout(null);
        pn_second_1.removeAll();
        pn_second_1.setBounds(0, 0, 1045, 695);
        pn_second_1.setLayout(null);
        JPanel pn_top = new JPanel();
        pn_top.setBounds(0, 0, 1045, 50);
        JLabel lb_num;
        lb_num = new JLabel("  公司共有" + count + "条职位信息！  ");
        lb_num.setFont(new Font("方正大标宋_GBK", 0, 25));
        JButton bt_add = new JButton("添加", new ImageIcon("image/add.png")), bt_export = new JButton("导出", new ImageIcon("image/out.png"));
        bt_add.setBackground(Color.white.darker());
        bt_add.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_add.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bt_export.setBackground(Color.green.darker());
        bt_export.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_export.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pn_top.add(bt_add);
        pn_top.add(lb_num);
        pn_top.add(bt_export);
        JTable table = new JTable(new MyTableModel(columnNames, rowData, 5));
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(1, 35)); //设置表头高度
        header.setFont(new Font("方正大标宋_GBK", Font.BOLD, 23));
        table.setRowHeight(35); //设置各行高度
        table.setFont(new Font("方正大标宋_GBK", 0, 20));
        table.setBackground(null);
        table.getTableHeader().setReorderingAllowed(false); //不允许移动各列
        JScrollPane scrollPane = new JScrollPane(table); //滚动条
        scrollPane.setBounds(0, 50, 1045, 645);
        pn_second_1.add(pn_top);
        pn_second_1.add(scrollPane);
        if (count != 0) {
            MyEvent e = new MyEvent() { //点击“查看”按钮，查看信息，可修改和删除信息
                public void invoke(ActionEvent e) {
                    MyButton button = (MyButton) e.getSource();
                    pn_second_2.removeAll();
                    visitPositionInfo((String) table.getValueAt(button.getRow(), button.getColumn() - 3), 1, null);
                }
            };
            table.getColumnModel().getColumn(3).setCellRenderer(new MyButtonRender("查看")); //设置表格的渲染器
            MyButtonEditor editor = new MyButtonEditor(e, "查看");
            table.getColumnModel().getColumn(3).setCellEditor(editor); //设置表格的编辑器
        }
        bt_add.addActionListener(new ActionListener() { //添加职位信息
            public void actionPerformed(ActionEvent e) {
                pn_second_2.removeAll();
                // addStayInfo();
            }
        });
        bt_export.addActionListener(new ActionListener() { //将表格导出成Excel文件
            public void actionPerformed(ActionEvent e) {
                FileDialog fd = new FileDialog(sarAdminJFrame, "请设置导出位置和文件名！", FileDialog.SAVE);
                fd.setVisible(true);
                String file = fd.getDirectory() + fd.getFile() + ".xls";
                if (fd.getFile() != null)
                    JTableToExcel.export(new File(file), table);
            }
        });
        pn_second.removeAll();
        sarAdminJFrame.repaint();
        pn_second.add(pn_second_1);
        sarAdminJFrame.validate();
    }

    public void allTitle() { //该公司的所有职称
        String[] columnNames = {"职称编号", "职称名称", "职称工资", "操作"}; //表格列名
        String[][] rowData = null; //表格数据
        int count = 0; //表的元组总数
        try { //获取Title表信息
            String sql = "SELECT * FROM Title"; //SQL语句
            PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
            ps = HomePage.connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY); //把操作数据库返回的结果保存到ps中
            ResultSet rs = ps.executeQuery(sql); //ResultSet类，用来存放获取的结果集
            rs.last();
            count = rs.getRow(); //获取职称元组总数
            if (count == 0) { //若Title视图无元组
                rowData = new String[1][3];
                for (int i = 0; i < 3; i++)
                    rowData[0][i] = "无";
            } else { //若Title表有元组
                rowData = new String[count][4];
                rs.first();
                int i = 0;
                do { //获取所有职称信息
                    rowData[i][0] = rs.getString("TitleID"); //职称号
                    rowData[i][1] = rs.getString("Name"); //名称
                    rowData[i][2] = rs.getString("Salary"); //职称工资
                    i++;
                } while (rs.next());
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //界面
        pn_first.setLayout(null);
        pn_first_1.removeAll();
        pn_first_1.setBounds(0, 0, 1045, 695);
        pn_first_1.setLayout(null);
        JPanel pn_top = new JPanel();
        pn_top.setBounds(0, 0, 1045, 50);
        JLabel lb_num;
        lb_num = new JLabel("  公司共有" + count + "条职称信息！  ");
        lb_num.setFont(new Font("方正大标宋_GBK", 0, 25));
        JButton bt_add = new JButton("添加", new ImageIcon("image/add.png"));
        bt_add.setBackground(Color.white.darker());
        bt_add.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_add.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pn_top.add(bt_add);
        pn_top.add(lb_num);
        JTable table = new JTable(new MyTableModel(columnNames, rowData, 5));
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(1, 35)); //设置表头高度
        header.setFont(new Font("方正大标宋_GBK", Font.BOLD, 23));
        table.setRowHeight(35); //设置各行高度
        table.setFont(new Font("方正大标宋_GBK", 0, 20));
        table.setBackground(null);
        table.getTableHeader().setReorderingAllowed(false); //不允许移动各列
        JScrollPane scrollPane = new JScrollPane(table); //滚动条
        scrollPane.setBounds(0, 50, 1045, 645);
        pn_first_1.add(pn_top);
        pn_first_1.add(scrollPane);
        if (count != 0) {
            MyEvent e = new MyEvent() { //点击“查看”按钮，查看信息，可修改和删除信息
                public void invoke(ActionEvent e) {
                    MyButton button = (MyButton) e.getSource();
                    pn_first_2.removeAll();
                    visitTitleInfo((String) table.getValueAt(button.getRow(), button.getColumn() - 3), 1, null);
                }
            };
            table.getColumnModel().getColumn(3).setCellRenderer(new MyButtonRender("查看")); //设置表格的渲染器
            MyButtonEditor editor = new MyButtonEditor(e, "查看");
            table.getColumnModel().getColumn(3).setCellEditor(editor); //设置表格的编辑器
        }
        bt_add.addActionListener(new ActionListener() { //添加职位信息
            public void actionPerformed(ActionEvent e) {
                pn_first_2.removeAll();
                // addStayInfo();
            }
        });
        pn_first.removeAll();
        sarAdminJFrame.repaint();
        pn_first.add(pn_first_1);
        sarAdminJFrame.validate();
    }

    public void visitTitleInfo(String title_id, int x, String query_sql) { //查看职称信息，可修改和删除
        pn_first_2.removeAll();
        pn_first_2.setLayout(null);
        pn_first_2.setBounds(0, 0, 1045, 695);
        pn_first_2.setBorder(BorderFactory.createEtchedBorder());

        JButton bt_back = new JButton("返回", new ImageIcon("image/back.png"));
        bt_back.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_back.setBounds(1, 10, 92, 25);
        bt_back.setContentAreaFilled(false);
        bt_back.setBorderPainted(false);
        bt_back.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton bt_modify = new JButton("修改职称信息", new ImageIcon("image/modify.png")), bt_delete = new JButton("删除员工信息", new ImageIcon("image/delete.png")),
                bt_save = new JButton("保存", new ImageIcon("image/save.png")), bt_cancel = new JButton("取消", new ImageIcon("image/cancel.png"));
        bt_modify.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_modify.setBounds(755, 380, 190, 40);
        bt_modify.setContentAreaFilled(false);
        bt_modify.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bt_delete.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_delete.setBounds(755, 430, 190, 40);
        bt_delete.setForeground(Color.red);
        bt_delete.setContentAreaFilled(false);
        bt_delete.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bt_save.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_save.setBounds(750, 380, 95, 40);
        bt_save.setContentAreaFilled(false);
        bt_save.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bt_cancel.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_cancel.setBounds(855, 380, 95, 40);
        bt_cancel.setContentAreaFilled(false);
        bt_cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lb_infoImage = new JLabel(new ImageIcon("image/user.png"));
        lb_infoImage.setBounds(750, 165, 200, 200);
        pn_first_2.add(bt_back);
        pn_first_2.add(bt_modify);
        pn_first_2.add(bt_delete);
        pn_first_2.add(lb_infoImage);
        JLabel[] lb = new JLabel[3];
        for (int i = 0; i < 3; i++) {
            lb[i] = new JLabel();
            lb[i].setFont(new Font("方正大标宋_GBK", 0, 25));
            lb[i].setBounds(260, 65 + i * 55, 130, 50);
            pn_first_2.add(lb[i]);
        }

        lb[0].setText("职称编号：");
        lb[1].setText("职称名称：");
        lb[2].setText("职称工资：");

        JTextField[] tf = new JTextField[3];
        for (int i = 0; i < 3; i++) {
            tf[i] = new JTextField();
            tf[i].setFont(new Font("方正大标宋_GBK", 0, 25));
            tf[i].setBounds(392, 70 + i * 55, 260, 40);
            tf[i].setEditable(false);
            pn_first_2.add(tf[i]);
        }
        try {
            String sql = "SELECT * FROM Title WHERE TitleID='" + title_id + "'"; //SQL语句
            PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
            ps = HomePage.connection.prepareStatement(sql); //把操作数据库返回的结果保存到ps中
            ResultSet rs = ps.executeQuery(sql); //ResultSet类，用来存放获取的结果集
            while (rs.next()) {
                titledb = new TitleDB(rs.getString("TitleID"), rs.getString("Name"), rs.getString("Salary"));
                tf[0].setText(titledb.title_id);
                tf[1].setText(titledb.title_name);
                tf[2].setText(titledb.title_salary);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        bt_back.addMouseListener(new MouseListener() { //返回
            public void mouseEntered(MouseEvent arg0) {
                bt_back.setForeground(Color.blue);
            }

            public void mouseExited(MouseEvent arg0) {
                bt_back.setForeground(null);
            }

            public void mouseClicked(MouseEvent arg0) {
                titledb = null;
                allTitle();
            }

            public void mousePressed(MouseEvent arg0) {
            }

            public void mouseReleased(MouseEvent arg0) {
            }
        });
        bt_modify.addActionListener(new ActionListener() { //修改职称信息
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 3; i++)
                    tf[i].setEditable(true);
                pn_first_2.remove(bt_modify);
                pn_first_2.remove(bt_delete);
                sarAdminJFrame.repaint();
                pn_first_2.add(bt_save);
                pn_first_2.add(bt_cancel);
                sarAdminJFrame.validate();
            }
        });
        bt_delete.addActionListener(new ActionListener() { //删除职称信息
            public void actionPerformed(ActionEvent e) {
                // lb_tips.setText("是否确定删除该员工信息？");
                // if(x==1)
                // 	choiceTips("");
                // else
                // 	choiceTips(query_sql);
            }
        });
        bt_save.addActionListener(new ActionListener() { //保存
            public void actionPerformed(ActionEvent e) {
                String sql; //SQL语句
                PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
                ResultSet rs; //ResultSet类，用来存放获取的结果集
                try {
                    if (tf[0].getText().equals("") || tf[1].getText().equals("") || tf[2].getText().equals("")) {
                        lb_tips.setText("请填写完整！");
                        functionTips();
                    } else { //已填写完整
                        boolean flag = false; //代表职称编号是否重复
                        //检测职称编号是否重复
                        sql = "SELECT TitleID FROM Title WHERE TitleID='" + tf[0].getText() + "'";
                        ps = HomePage.connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY);
                        rs = ps.executeQuery(sql);
                        flag = rs.next();
                        rs.first();
                        flag = flag && !(tf[0].getText().equals(titledb.title_id));
                        if (flag) { //职称编号重复，进行提示
                            lb_tips.setText("职称编号重复！");
                            functionTips();
                            flag = false;
                        } else { //输入数据符合要求，进行保存
                            try {
                                sql = "UPDATE Title SET TitleID=?,Name=?,Salary=? WHERE TitleID='" + titledb.title_id + "'";
                                ps = HomePage.connection.prepareStatement(sql);
                                ps.setInt(1, Integer.parseInt(tf[0].getText())); //SQL语句第一个?值
                                ps.setString(2, tf[1].getText()); //SQL语句第二个?值
                                ps.setBigDecimal(3, BigDecimal.valueOf(Double.parseDouble(tf[2].getText()))); //SQL语句第三个?值

                                ps.executeUpdate(); //更新，执行修改操作
                                rs.close();
                                ps.close();
                                for (int i = 0; i < 3; i++)
                                    tf[i].setEditable(false);
                                pn_first_2.remove(bt_save);
                                pn_first_2.remove(bt_cancel);
                                sarAdminJFrame.repaint();
                                pn_first_2.add(bt_modify);
                                pn_first_2.add(bt_delete);
                                sarAdminJFrame.validate();

                            } catch (SQLException se) {
                                lb_tips.setText("数据库操作出错！");
                                functionTips();
                                se.printStackTrace();
                            } catch (NumberFormatException ne) {
                                lb_tips.setText("数据转换出错！");
                                functionTips();
                                ne.printStackTrace();
                            }
                        }
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        });
        bt_cancel.addActionListener(new ActionListener() { //取消
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 3; i++)
                    tf[i].setEditable(false);
                tf[0].setText(titledb.title_id);
                tf[1].setText(titledb.title_name);
                tf[2].setText(titledb.title_salary);
                pn_first_2.remove(bt_save);
                pn_first_2.remove(bt_cancel);
                sarAdminJFrame.repaint();
                pn_first_2.add(bt_modify);
                pn_first_2.add(bt_delete);
            }
        });
        pn_first.removeAll();
        sarAdminJFrame.repaint();
        pn_first.add(pn_first_2);
        sarAdminJFrame.validate();
    }

    public void visitPositionInfo(String position_id, int x, String query_sql) { //查看职位信息，可修改和删除
        pn_second_2.removeAll();
        pn_second_2.setLayout(null);
        pn_second_2.setBounds(0, 0, 1045, 695);
        pn_second_2.setBorder(BorderFactory.createEtchedBorder());

        JButton bt_back = new JButton("返回", new ImageIcon("image/back.png"));
        bt_back.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_back.setBounds(1, 10, 92, 25);
        bt_back.setContentAreaFilled(false);
        bt_back.setBorderPainted(false);
        bt_back.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton bt_modify = new JButton("修改职位信息", new ImageIcon("image/modify.png")), bt_delete = new JButton("删除职位信息", new ImageIcon("image/delete.png")),
                bt_save = new JButton("保存", new ImageIcon("image/save.png")), bt_cancel = new JButton("取消", new ImageIcon("image/cancel.png"));
        bt_modify.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_modify.setBounds(755, 380, 190, 40);
        bt_modify.setContentAreaFilled(false);
        bt_modify.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bt_delete.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_delete.setBounds(755, 430, 190, 40);
        bt_delete.setForeground(Color.red);
        bt_delete.setContentAreaFilled(false);
        bt_delete.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bt_save.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_save.setBounds(750, 380, 95, 40);
        bt_save.setContentAreaFilled(false);
        bt_save.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bt_cancel.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_cancel.setBounds(855, 380, 95, 40);
        bt_cancel.setContentAreaFilled(false);
        bt_cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lb_infoImage = new JLabel(new ImageIcon("image/user.png"));
        lb_infoImage.setBounds(750, 165, 200, 200);

        pn_second_2.add(bt_back);
        pn_second_2.add(bt_modify);
        pn_second_2.add(bt_delete);
        pn_second_2.add(lb_infoImage);

        JLabel[] lb = new JLabel[3];
        for (int i = 0; i < 3; i++) {
            lb[i] = new JLabel();
            lb[i].setFont(new Font("方正大标宋_GBK", 0, 25));
            lb[i].setBounds(260, 65 + i * 55, 130, 50);
            pn_second_2.add(lb[i]);
        }

        lb[0].setText("职位编号：");
        lb[1].setText("职位名称：");
        lb[2].setText("职位工资：");

        JTextField[] tf = new JTextField[3];
        for (int i = 0; i < 3; i++) {
            tf[i] = new JTextField();
            tf[i].setFont(new Font("方正大标宋_GBK", 0, 25));
            tf[i].setBounds(392, 70 + i * 55, 260, 40);
            tf[i].setEditable(false);
            pn_second_2.add(tf[i]);
        }
        try {
            String sql = "SELECT * FROM Position WHERE PositionID='" + position_id + "'"; //SQL语句
            PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
            ps = HomePage.connection.prepareStatement(sql); //把操作数据库返回的结果保存到ps中
            ResultSet rs = ps.executeQuery(sql); //ResultSet类，用来存放获取的结果集
            while (rs.next()) {
                positiondb = new PositionDB(rs.getString("PositionID"), rs.getString("Name"), rs.getString("Salary"));
                tf[0].setText(positiondb.position_id);
                tf[1].setText(positiondb.position_name);
                tf[2].setText(positiondb.position_salary);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        bt_back.addMouseListener(new MouseListener() { //返回
            public void mouseEntered(MouseEvent arg0) {
                bt_back.setForeground(Color.blue);
            }

            public void mouseExited(MouseEvent arg0) {
                bt_back.setForeground(null);
            }

            public void mouseClicked(MouseEvent arg0) {
                positiondb = null;
                allPosition();
            }

            public void mousePressed(MouseEvent arg0) {
            }

            public void mouseReleased(MouseEvent arg0) {
            }
        });
        bt_modify.addActionListener(new ActionListener() { //修改职称信息
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 3; i++)
                    tf[i].setEditable(true);
                pn_second_2.remove(bt_modify);
                pn_second_2.remove(bt_delete);
                sarAdminJFrame.repaint();
                pn_second_2.add(bt_save);
                pn_second_2.add(bt_cancel);
                sarAdminJFrame.validate();
            }
        });
        bt_delete.addActionListener(new ActionListener() { //删除职称信息
            public void actionPerformed(ActionEvent e) {
                // lb_tips.setText("是否确定删除该员工信息？");
                // if(x==1)
                // 	choiceTips("");
                // else
                // 	choiceTips(query_sql);
            }
        });
        bt_save.addActionListener(new ActionListener() { //保存
            public void actionPerformed(ActionEvent e) {
                String sql; //SQL语句
                PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
                ResultSet rs; //ResultSet类，用来存放获取的结果集
                try {
                    if (tf[0].getText().equals("") || tf[1].getText().equals("") || tf[2].getText().equals("")) {
                        lb_tips.setText("请填写完整！");
                        functionTips();
                    } else { //已填写完整
                        boolean flag = false; //代表职位编号是否重复
                        //检测职位编号是否重复
                        sql = "SELECT PositionID FROM Position WHERE PositionID='" + tf[0].getText() + "'";
                        ps = HomePage.connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY);
                        rs = ps.executeQuery(sql);
                        flag = rs.next();
                        rs.first();
                        flag = flag && !(tf[0].getText().equals(positiondb.position_id));
                        if (flag) { //职称编号重复，进行提示
                            lb_tips.setText("职称编号重复！");
                            functionTips();
                            flag = false;
                        } else { //输入数据符合要求，进行保存
                            try {
                                sql = "UPDATE Position SET PositionID=?,Name=?,Salary=? WHERE PositionID='" + positiondb.position_id + "'";
                                ps = HomePage.connection.prepareStatement(sql);
                                ps.setInt(1, Integer.parseInt(tf[0].getText())); //SQL语句第一个?值
                                ps.setString(2, tf[1].getText()); //SQL语句第二个?值
                                ps.setBigDecimal(3, BigDecimal.valueOf(Double.parseDouble(tf[2].getText()))); //SQL语句第三个?值

                                ps.executeUpdate(); //更新，执行修改操作
                                rs.close();
                                ps.close();
                                for (int i = 0; i < 3; i++)
                                    tf[i].setEditable(false);
                                pn_second_2.remove(bt_save);
                                pn_second_2.remove(bt_cancel);
                                sarAdminJFrame.repaint();
                                pn_second_2.add(bt_modify);
                                pn_second_2.add(bt_delete);
                                sarAdminJFrame.validate();

                            } catch (SQLException se) {
                                lb_tips.setText("数据库操作出错！");
                                functionTips();
                                se.printStackTrace();
                            } catch (NumberFormatException ne) {
                                lb_tips.setText("数据转换出错！");
                                functionTips();
                                ne.printStackTrace();
                            }
                        }
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        });
        bt_cancel.addActionListener(new ActionListener() { //取消
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 3; i++)
                    tf[i].setEditable(false);
                tf[0].setText(titledb.title_id);
                tf[1].setText(titledb.title_name);
                tf[2].setText(titledb.title_salary);
                pn_second_2.remove(bt_save);
                pn_second_2.remove(bt_cancel);
                sarAdminJFrame.repaint();
                pn_second_2.add(bt_modify);
                pn_second_2.add(bt_delete);
            }
        });
        pn_second.removeAll();
        sarAdminJFrame.repaint();
        pn_second.add(pn_second_2);
        sarAdminJFrame.validate();
    }

    public void changeSalaryPayment() { //[修改工资发放日]功能
        pn_fourth.removeAll();
        pn_fourth.setLayout(null);
        pn_fourth.setBounds(0, 0, 1045, 695);
        pn_fourth.setBorder(BorderFactory.createEtchedBorder());


        JLabel lb_sar1 = new JLabel("工资发放日期：每月");
        lb_sar1.setFont(new Font("方正大标宋_GBK", 0, 25));
        lb_sar1.setBounds(290, 200, 250, 50);

        JLabel lb_sar2 = new JLabel("日");
        lb_sar2.setFont(new Font("方正大标宋_GBK", 0, 25));
        lb_sar2.setBounds(580, 200, 50, 50);

        JLabel lb_sar3 = new JLabel();
        lb_sar3.setFont(new Font("方正大标宋_GBK", 1, 25));
        lb_sar3.setBounds(530, 200, 50, 50);

        JTextField tf_day = new JTextField();
        tf_day.setFont(new Font("方正大标宋_GBK", 0, 25));
        tf_day.setBounds(520, 200, 50, 50);
        tf_day.setEditable(false);
        tf_day.setDocument(new NumLimit());

        JButton bt_modify = new JButton("修改工资发放日", new ImageIcon("image/modify.png")),
                bt_save = new JButton("保存", new ImageIcon("image/save.png")), bt_cancel = new JButton("取消", new ImageIcon("image/cancel.png"));

        bt_modify.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_modify.setBounds(400, 380, 190, 40);
        bt_modify.setContentAreaFilled(false);
        bt_modify.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bt_cancel.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_cancel.setBounds(380, 380, 95, 40);
        bt_cancel.setContentAreaFilled(false);
        bt_cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bt_save.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_save.setBounds(500, 380, 95, 40);
        bt_save.setContentAreaFilled(false);
        bt_save.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pn_fourth.add(lb_sar1);
        pn_fourth.add(lb_sar2);
        pn_fourth.add(lb_sar3);
        pn_fourth.add(bt_modify);

        try {
            String sql = "SELECT ConfigValue FROM Config WHERE ConfigKey = 'SalaryPaymentDay'"; //SQL语句
            PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
            ps = HomePage.connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY); //把操作数据库返回的结果保存到ps中
            ResultSet rs = ps.executeQuery(sql); //ResultSet类，用来存放获取的结果集
            if (rs.next()) {
                SalaryPaymentDay = Integer.parseInt(rs.getString("ConfigValue"));
                lb_sar3.setText(rs.getString("ConfigValue"));
            } else {
                lb_tips.setText("读取工作发放日出错！");
                functionTips();
            }
            rs.close();
            ps.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        bt_modify.addActionListener(new ActionListener() { //修改工资发放日信息
            public void actionPerformed(ActionEvent e) {
                pn_fourth.add(tf_day);
                tf_day.setEditable(true);
                pn_fourth.remove(bt_modify);
                pn_fourth.remove(lb_sar3);
                sarAdminJFrame.repaint();
                pn_fourth.add(bt_save);
                pn_fourth.add(bt_cancel);
                sarAdminJFrame.validate();
            }
        });
        bt_save.addActionListener(new ActionListener() { //保存
            public void actionPerformed(ActionEvent e) {
                String sql; //SQL语句
                PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
                if (tf_day.getText().equals("")) {
                    lb_tips.setText("请填写完整！");
                    functionTips();
                } else { //已填写完整
                    SalaryPaymentDay = Integer.parseInt(tf_day.getText());
                    if (SalaryPaymentDay < 1 || SalaryPaymentDay > 28) { //日期非法，进行提示
                        lb_tips.setText("非法的日期！");
                        functionTips();
                    } else { //输入数据符合要求，进行保存
                        try {
                            sql = "UPDATE Config SET ConfigValue=? WHERE ConfigKey='SalaryPaymentDay'";
                            ps = HomePage.connection.prepareStatement(sql);
                            ps.setString(1, tf_day.getText()); //SQL语句第一个?值

                            ps.executeUpdate(); //更新，执行修改操作
                            ps.close();

                            pn_fourth.remove(bt_save);
                            pn_fourth.remove(bt_cancel);
                            pn_fourth.remove(tf_day);
                            lb_sar3.setText(tf_day.getText());
                            sarAdminJFrame.repaint();
                            pn_fourth.add(bt_modify);
                            pn_fourth.add(lb_sar3);
                            sarAdminJFrame.validate();

                        } catch (SQLException se) {
                            lb_tips.setText("数据库操作出错！");
                            functionTips();
                            se.printStackTrace();
                        } catch (NumberFormatException ne) {
                            lb_tips.setText("数据转换出错！");
                            functionTips();
                            ne.printStackTrace();
                        }
                    }
                }
            }
        });
        bt_cancel.addActionListener(new ActionListener() { //取消
            public void actionPerformed(ActionEvent e) {
                tf_day.setText("");
                pn_fourth.remove(bt_save);
                pn_fourth.remove(bt_cancel);
                pn_fourth.remove(tf_day);
                sarAdminJFrame.repaint();
                pn_fourth.add(bt_modify);
                pn_fourth.add(lb_sar3);
            }
        });

        sarAdminJFrame.repaint();
        sarAdminJFrame.validate();
    }

    public void changeTenureSalary() { //[修改工龄工资]功能
        pn_third.removeAll();
        pn_third.setLayout(null);
        pn_third.setBounds(0, 0, 1045, 695);
        pn_third.setBorder(BorderFactory.createEtchedBorder());


        JLabel lb_tenure_title = new JLabel("工龄工资");
        lb_tenure_title.setFont(new Font("方正大标宋_GBK", 0, 30));
        lb_tenure_title.setBounds(430, 200, 120, 50);

        JLabel lb_tenure_label = new JLabel("每年       元，逐年递增");
        lb_tenure_label.setFont(new Font("方正大标宋_GBK", 0, 25));
        lb_tenure_label.setBounds(380, 270, 300, 50);

        JLabel lb_tenure_label_cover = new JLabel();
        lb_tenure_label_cover.setFont(new Font("方正大标宋_GBK", 1, 25));
        lb_tenure_label_cover.setBounds(435, 270, 50, 50);

        JTextField tf_tenure_sar = new JTextField();
        tf_tenure_sar.setFont(new Font("方正大标宋_GBK", 0, 25));
        tf_tenure_sar.setBounds(435, 270, 50, 50);
        tf_tenure_sar.setEditable(false);
        tf_tenure_sar.setDocument(new NumLimit());

        JButton bt_modify_tenure_sar = new JButton("修改工龄工资", new ImageIcon("image/modify.png")),
                bt_save = new JButton("保存", new ImageIcon("image/save.png")), bt_cancel = new JButton("取消", new ImageIcon("image/cancel.png"));

        bt_modify_tenure_sar.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_modify_tenure_sar.setBounds(400, 380, 190, 40);
        bt_modify_tenure_sar.setContentAreaFilled(false);
        bt_modify_tenure_sar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bt_cancel.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_cancel.setBounds(380, 380, 95, 40);
        bt_cancel.setContentAreaFilled(false);
        bt_cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bt_save.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_save.setBounds(500, 380, 95, 40);
        bt_save.setContentAreaFilled(false);
        bt_save.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pn_third.add(lb_tenure_title);
        pn_third.add(lb_tenure_label);
        pn_third.add(lb_tenure_label_cover);
        pn_third.add(bt_modify_tenure_sar);

        try {
            String sql = "SELECT ConfigValue FROM Config WHERE ConfigKey = 'TenureSalary'"; //SQL语句
            PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
            ps = HomePage.connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY); //把操作数据库返回的结果保存到ps中
            ResultSet rs = ps.executeQuery(sql); //ResultSet类，用来存放获取的结果集
            if (rs.next()) {
                lb_tenure_label_cover.setText(rs.getString("ConfigValue"));
            } else {
                lb_tips.setText("读取工龄工资出错！");
                functionTips();
            }
            rs.close();
            ps.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        bt_modify_tenure_sar.addActionListener(new ActionListener() { //修改工资发放日信息
            public void actionPerformed(ActionEvent e) {
                pn_third.add(tf_tenure_sar);
                tf_tenure_sar.setEditable(true);
                pn_third.remove(bt_modify_tenure_sar);
                pn_third.remove(lb_tenure_label_cover);
                sarAdminJFrame.repaint();
                pn_third.add(bt_save);
                pn_third.add(bt_cancel);
                sarAdminJFrame.validate();
            }
        });
        bt_save.addActionListener(new ActionListener() { //保存
            public void actionPerformed(ActionEvent e) {
                String sql; //SQL语句
                PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
                if (tf_tenure_sar.getText().equals("")) {
                    lb_tips.setText("请填写完整！");
                    functionTips();
                } else { //已填写完整
                    try {
                        sql = "UPDATE Config SET ConfigValue=? WHERE ConfigKey='SalaryPaymentDay'";
                        ps = HomePage.connection.prepareStatement(sql);
                        ps.setBigDecimal(1, BigDecimal.valueOf(Double.parseDouble(tf_tenure_sar.getText())));

                        ps.executeUpdate(); //更新，执行修改操作
                        ps.close();

                        pn_third.remove(bt_save);
                        pn_third.remove(bt_cancel);
                        pn_third.remove(tf_tenure_sar);
                        lb_tenure_label_cover.setText(tf_tenure_sar.getText());
                        sarAdminJFrame.repaint();
                        pn_third.add(bt_modify_tenure_sar);
                        pn_third.add(lb_tenure_label_cover);
                        sarAdminJFrame.validate();

                    } catch (SQLException se) {
                        lb_tips.setText("数据库操作出错！");
                        functionTips();
                        se.printStackTrace();
                    } catch (NumberFormatException ne) {
                        lb_tips.setText("数据转换出错！");
                        functionTips();
                        ne.printStackTrace();
                    }
                }

            }
        });
        bt_cancel.addActionListener(new ActionListener() { //取消
            public void actionPerformed(ActionEvent e) {
                tf_tenure_sar.setText("");
                pn_third.remove(bt_save);
                pn_third.remove(bt_cancel);
                pn_third.remove(tf_tenure_sar);
                sarAdminJFrame.repaint();
                pn_third.add(bt_modify_tenure_sar);
                pn_third.add(lb_tenure_label_cover);
            }
        });

        sarAdminJFrame.repaint();
        sarAdminJFrame.validate();
    }

    public JTabbedPane AttendanceManage() { //考勤登记
        JTabbedPane tp_inout = new JTabbedPane();
        tp_inout.setFont(new Font("方正大标宋_GBK", 0, 25));
        tp_inout.setBounds(0, 0, 1045, 735);
        allAttendance();
        tp_inout.addTab(" 全部 ", pn_first);
        // queryInoutInfo_1();
        tp_inout.addTab(" 查询 ", pn_second);
        return tp_inout;
    }

    public void allAttendance() { //该财务管理的部门的所有晚归信息
        String[] columnNames = {"考勤编号", "员工编号", "日期", "类型", "操作"}; //表格列名
        String[][] rowData = null; //表格数据
        int count = 0; //表的元组总数
        try { //获取student_inout视图信息
            String sql = "SELECT * FROM Attendance"; //SQL语句
            PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
            ps = HomePage.connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY); //把操作数据库返回的结果保存到ps中
            ResultSet rs = ps.executeQuery(sql); //ResultSet类，用来存放获取的结果集
            rs.last();
            count = rs.getRow(); //获取晚归信息元组总数
            if (count == 0) { //若student_inout视图无元组
                rowData = new String[1][5];
                for (int i = 0; i < 4; i++)
                    rowData[0][i] = "无";
            } else { //若student_inout视图有元组
                rowData = new String[count][5];
                rs.first();
                int i = 0;
                do { //获取该财务管理的部门的所有晚归信息
                    rowData[i][0] = rs.getString("AttendanceID"); //学号
                    rowData[i][1] = rs.getString("EmployeeID"); //姓名
                    rowData[i][2] = rs.getString("Date"); //部门
                    rowData[i][3] = rs.getString("Type"); //时间
                    i++;
                } while (rs.next());
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //界面
        pn_first.setLayout(null);
        pn_first_1.removeAll();
        pn_first_1.setBounds(0, 0, 1045, 695);
        pn_first_1.setLayout(null);
        JPanel pn_top = new JPanel();
        pn_top.setBounds(0, 0, 1045, 50);
        JLabel lb_num;
        lb_num = new JLabel("  公司共有" + count + "条考勤登记信息！  ");
        lb_num.setFont(new Font("方正大标宋_GBK", 0, 25));
        JButton bt_add = new JButton("登记", new ImageIcon("image/add.png")), bt_export = new JButton("导出", new ImageIcon("image/out.png"));
        bt_add.setBackground(Color.white.darker());
        bt_add.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_add.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bt_export.setBackground(Color.green.darker());
        bt_export.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_export.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pn_top.add(bt_add);
        pn_top.add(lb_num);
        pn_top.add(bt_export);

        JTable table = new JTable(new MyTableModel(columnNames, rowData, 6));
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(1, 35)); //设置表头高度
        header.setFont(new Font("方正大标宋_GBK", Font.BOLD, 23));
        table.setRowHeight(35); //设置各行高度
        table.setFont(new Font("方正大标宋_GBK", 0, 20));
        table.setBackground(null);
        table.getTableHeader().setReorderingAllowed(false); //不允许移动各列
        JScrollPane scrollPane = new JScrollPane(table); //滚动条
        scrollPane.setBounds(0, 50, 1045, 645);
        pn_first_1.add(pn_top);
        pn_first_1.add(scrollPane);
        if (count != 0) {
            MyEvent e = new MyEvent() { //点击“删除”按钮，可删除晚归信息
                public void invoke(ActionEvent e) {
                    MyButton button = (MyButton) e.getSource();
                    String sql = "DELETE FROM Attendance WHERE AttendanceID='" + (String) table.getValueAt(button.getRow(), button.getColumn() - 4) + "'";
                    lb_tips.setText("是否确定删除该考勤登记信息？");
                    choiceTips(sql);
                    allAttendance();
                }
            };
            table.getColumnModel().getColumn(4).setCellRenderer(new MyButtonRender("删除")); //设置表格的渲染器
            MyButtonEditor editor = new MyButtonEditor(e, "删除");
            table.getColumnModel().getColumn(4).setCellEditor(editor); //设置表格的编辑器
        }
        bt_export.addActionListener(new ActionListener() { //将表格导出成Excel文件
            public void actionPerformed(ActionEvent e) {
                FileDialog fd = new FileDialog(sarAdminJFrame, "请设置导出位置和文件名！", FileDialog.SAVE);
                fd.setVisible(true);
                String file = fd.getDirectory() + fd.getFile() + ".xls";
                if (fd.getFile() != null)
                    JTableToExcel.export(new File(file), table);
            }
        });
        bt_add.addActionListener(new ActionListener() { //添加晚归信息
            public void actionPerformed(ActionEvent e) {
                pn_first_2.removeAll();
                addAttendanceInfo();
            }
        });
        pn_first.removeAll();
        sarAdminJFrame.repaint();
        pn_first.add(pn_first_1);
        sarAdminJFrame.validate();
    }

    public void addAttendanceInfo() { //添加考勤信息
        pn_first_2.removeAll();
        pn_first_2.setLayout(null);
        pn_first_2.setBounds(0, 0, 1045, 695);
        pn_first_2.setBorder(BorderFactory.createEtchedBorder());
        JButton bt_back = new JButton("返回", new ImageIcon("image/back.png"));
        bt_back.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_back.setBounds(1, 10, 92, 25);
        bt_back.setContentAreaFilled(false);
        bt_back.setBorderPainted(false);
        bt_back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JButton bt_confirm = new JButton("确认添加", new ImageIcon("image/confirm.png"));
        bt_confirm.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_confirm.setBounds(447, 520, 150, 50);
        bt_confirm.setContentAreaFilled(false);
        bt_confirm.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JButton bt_time = new JButton("获取当前时间", new ImageIcon("image/time.png"));
        bt_time.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_time.setBounds(765, 415, 170, 40);
        bt_time.setContentAreaFilled(false);
        bt_time.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel lb_infoImage = new JLabel(new ImageIcon("image/user.png"));
        lb_infoImage.setBounds(750, 205, 200, 200);

        String[] attendance_type = {"--------请选择--------", "请假", "旷工", "迟到早退"};
        JComboBox<String> cb_attendance_type = new JComboBox<String>(attendance_type);

        cb_attendance_type.setFont(new Font("方正大标宋_GBK", 0, 20));
        cb_attendance_type.setBounds(392, 360, 260, 40);

        pn_first_2.add(bt_back);
        pn_first_2.add(bt_confirm);
        pn_first_2.add(bt_time);
        pn_first_2.add(lb_infoImage);
        pn_first_2.add(cb_attendance_type);

        JLabel[] lb = new JLabel[5];
        for (int i = 0; i < 4; i++) {
            lb[i] = new JLabel();
            lb[i].setFont(new Font("方正大标宋_GBK", 0, 25));
            lb[i].setBounds(280, 190 + i * 55, 130, 50);
            pn_first_2.add(lb[i]);
        }
        lb[0].setText("考勤编号：");
        lb[1].setText("员工编号：");
        lb[2].setText("日   期：");
        lb[3].setText("考勤类型：");
        JTextField[] tf = new JTextField[3];

        for (int i = 0; i < 3; i++) {
            tf[i] = new JTextField();
            tf[i].setFont(new Font("方正大标宋_GBK", 0, 25));
            tf[i].setBounds(392, 195 + i * 55, 260, 40);
            if (i == 0 || i == 1)
                tf[i].setDocument(new NumLimit()); //限制文本框只能输入数字
            pn_first_2.add(tf[i]);
        }
        bt_back.addMouseListener(new MouseListener() { //返回
            public void mouseEntered(MouseEvent arg0) {
                bt_back.setForeground(Color.blue);
            }

            public void mouseExited(MouseEvent arg0) {
                bt_back.setForeground(null);
            }

            public void mouseClicked(MouseEvent arg0) {
                allAttendance();
            }

            public void mousePressed(MouseEvent arg0) {
            }

            public void mouseReleased(MouseEvent arg0) {
            }
        });
        bt_time.addActionListener(new ActionListener() { //获取当前时间
            public void actionPerformed(ActionEvent e) {
                Date date = new Date();
                SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
                tf[2].setText(timeFormat.format(date));
            }
        });
        bt_confirm.addActionListener(new ActionListener() { //确认添加考勤信息
            public void actionPerformed(ActionEvent e) {
                if (tf[0].getText().equals("") || tf[1].getText().equals("") || tf[2].getText().equals("") || cb_attendance_type.getSelectedItem().toString().equals("--------请选择--------")) {
                    lb_tips.setText("请填写完整！");
                    functionTips();
                } else { //已填写完整
                    String sql; //SQL语句
                    PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
                    ResultSet rs; //ResultSet类，用来存放获取的结果集
                    try {
                        //检测考勤编号是否存在
                        sql = "SELECT AttendanceID FROM Attendance WHERE AttendanceID='" + tf[0].getText() + "'";
                        ps = HomePage.connection.prepareStatement(sql);
                        rs = ps.executeQuery(sql);
                        if (rs.next()) { //考勤编号存在，进行提示
                            lb_tips.setText("考勤编号不存在！");
                            functionTips();
                        } else {
                            try {

                                attendancedb = new AttendanceDB(tf[0].getText(), tf[1].getText(), tf[2].getText(), cb_attendance_type.getSelectedItem().toString());
                                //添加登记信息
                                sql = "INSERT INTO Attendance VALUES(?,?,?,?)"; //SQL语句
                                ps = HomePage.connection.prepareStatement(sql); //把操作数据库返回的结果保存到ps中
                                ps.setString(1, attendancedb.attendance_id); //SQL语句第一个?值
                                ps.setString(2, attendancedb.employee_id); //SQL语句第二个?值
                                try {
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    ps.setDate(3, new java.sql.Date((dateFormat.parse(attendancedb.attendance_date)).getTime())); //SQL语句第三个?值
                                } catch (ParseException pe) {
                                    lb_tips.setText("数据转换出错！");
                                    functionTips();
                                    pe.printStackTrace();
                                }
                                ps.setString(4, attendancedb.attendance_type); //SQL语句第四个?值
                                ps.executeUpdate(); //更新，执行插入操作
                                lb_tips.setText("考勤登记成功！");
                                functionTips();
                                for (int i = 0; i < 3; i++) {
                                    tf[i].setEditable(false);
                                }
                                cb_attendance_type.setEnabled(false);
                                tf[0].setText(attendancedb.attendance_id);
                                tf[1].setText(attendancedb.employee_id);
                                tf[2].setText(attendancedb.attendance_date);
                                attendancedb = null;
                                pn_first_2.remove(bt_time);
                                pn_first_2.remove(bt_confirm);
                                sarAdminJFrame.repaint();
                                rs.close();
                                ps.close();
                            } catch (SQLException se) {
                                lb_tips.setText("数据库操作出错！");
                                functionTips();
                                se.printStackTrace();
                            }
                        }
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
                }
            }
        });
        pn_first.removeAll();
        sarAdminJFrame.repaint();
        pn_first.add(pn_first_2);
        sarAdminJFrame.validate();
    }

    public JTabbedPane querySalary() { //工资查询
        JTabbedPane tp_salary = new JTabbedPane();
        tp_salary.setFont(new Font("方正大标宋_GBK", 0, 25));
        tp_salary.setBounds(0, 0, 1045, 735);
        allSalary();
        tp_salary.addTab(" 全部 ", pn_first);
        allSalaryHistory();
        tp_salary.addTab(" 工资发放记录 ", pn_second);
        querySalaryInfo_1();
        tp_salary.addTab(" 历史工资查询 ", pn_third);
        return tp_salary;
    }

    public void allSalary() { //该财务管理的部门的所有工资发放
        String[] columnNames = {"员工编号", "员工姓名", "月份", "基本工资", "职位工资", "职称工资", "工龄工资", "缺勤扣除", "全勤奖励", "总工资"}; //表格列名
        rowData = null; //表格数据
        int count = 0; //表的元组总数
        try { //获取MonthlySalaryView视图信息
            String sql = "SELECT * FROM MonthlySalaryView"; //SQL语句
            PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
            ps = HomePage.connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY); //把操作数据库返回的结果保存到ps中
            ResultSet rs = ps.executeQuery(sql); //ResultSet类，用来存放获取的结果集
            rs.last();
            count = rs.getRow(); //获取工资信息元组总数
            rowcount = count;
            if (count == 0) { //若MonthlySalaryView视图无元组
                rowData = new String[1][10];
                for (int i = 0; i < 9; i++)
                    rowData[0][i] = "无";
            } else { //若MonthlySalaryView视图有元组
                rowData = new String[count][10];
                rs.first();
                int i = 0;
                do { //获取该公司的部门的所有工资信息
                    rowData[i][0] = rs.getString("EmployeeID"); //工资编号
                    rowData[i][1] = rs.getString("EmployeeName"); //员工编号
                    rowData[i][2] = rs.getString("Month"); //月份
                    rowData[i][3] = rs.getString("BasicSalary"); //基本工资
                    rowData[i][4] = rs.getString("PositionSalary"); //职位工资
                    rowData[i][5] = rs.getString("TitleSalary"); //职称工资
                    rowData[i][6] = rs.getString("TenureSalary"); //工龄工资
                    rowData[i][7] = rs.getString("AttendanceDeduction"); //缺勤扣除
                    rowData[i][8] = rs.getString("FullAttendanceBonus"); //全勤奖励
                    rowData[i][9] = rs.getString("TotalSalary"); //全勤奖励
                    i++;
                } while (rs.next());
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //界面
        pn_first.setLayout(null);
        pn_first_1.removeAll();
        pn_first_1.setBounds(0, 0, 1045, 695);
        pn_first_1.setLayout(null);

        JPanel pn_top = new JPanel();
        pn_top.setBounds(0, 0, 1045, 50);

        JLabel lb_num;
        lb_num = new JLabel("  公司共有" + count + "条工资信息！  ");
        lb_num.setFont(new Font("方正大标宋_GBK", 0, 25));
        pn_top.add(lb_num);

        JTable table = new JTable(new MyTableModel(columnNames, rowData, 7));
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(1, 35)); //设置表头高度
        header.setFont(new Font("方正大标宋_GBK", Font.BOLD, 20));

        table.setRowHeight(25); //设置各行高度
        table.setFont(new Font("方正大标宋_GBK", 0, 15));
        table.setBackground(null);
        table.getTableHeader().setReorderingAllowed(false); //不允许移动各列

        JScrollPane scrollPane = new JScrollPane(table); //滚动条
        scrollPane.setBounds(0, 50, 1045, 645);

        JButton bt_renew = new JButton("更新", new ImageIcon("image/renew.png"));
        bt_renew.setBackground(Color.white.darker());
        bt_renew.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_renew.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton bt_export = new JButton("导出", new ImageIcon("image/out.png"));
        bt_export.setBackground(Color.green.darker());
        bt_export.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_export.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pn_top.add(bt_export);
        pn_top.add(bt_renew);
        pn_first_1.add(pn_top);
        pn_first_1.add(scrollPane);

        bt_export.addActionListener(new ActionListener() { //将表格导出成Excel文件
            public void actionPerformed(ActionEvent e) {
                FileDialog fd = new FileDialog(sarAdminJFrame, "请设置导出位置和文件名！", FileDialog.SAVE);
                fd.setVisible(true);
                String file = fd.getDirectory() + fd.getFile() + ".xls";
                if (fd.getFile() != null)
                    JTableToExcel.export(new File(file), table);
            }
        });
        bt_renew.addActionListener(new ActionListener() { //计算并更新当月工资
            public void actionPerformed(ActionEvent e) {
                allSalary();
                pn_first_1.removeAll();
                try {
                    String sql = "SELECT ConfigValue FROM Config WHERE ConfigKey = 'SalaryPaymentDay'"; //SQL语句
                    PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
                    ps = HomePage.connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY); //把操作数据库返回的结果保存到ps中
                    ResultSet rs = ps.executeQuery(sql); //ResultSet类，用来存放获取的结果集
                    if (rs.next()) {
                        SalaryPaymentDay = Integer.parseInt(rs.getString("ConfigValue"));
                    } else {
                        lb_tips.setText("读取工作发放日出错！");
                        functionTips();
                    }
                    rs.close();
                    ps.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
                for (int i = 0; i < rowcount; i++) {
                    monthlySalarydb = new MonthlySalaryDB(rowData[i][0], rowData[i][2], rowData[i][3], rowData[i][4], rowData[i][5], rowData[i][6], rowData[i][7], rowData[i][8], rowData[i][9]);
                    try {
                        String sql; //SQL语句
                        PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
                        // ResultSet rs; //ResultSet类，用来存放获取的结果集
                        sql = "INSERT INTO MonthlySalary (EmployeeID, Month, BasicSalary, PositionSalary, TitleSalary, TenureSalary, AttendanceDeduction, FullAttendanceBonus, TotalSalary) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        ps = HomePage.connection.prepareStatement(sql);
                        ps.setString(1, monthlySalarydb.employee_id);
                        ps.setString(2, monthlySalarydb.month + "-" + String.format("%02d", SalaryPaymentDay));
                        ps.setString(3, monthlySalarydb.basic_salary);
                        ps.setString(4, monthlySalarydb.position_salary);
                        ps.setString(5, monthlySalarydb.title_salary);
                        ps.setString(6, monthlySalarydb.tenure_salary);
                        ps.setString(7, monthlySalarydb.attendance_deduction);
                        ps.setString(8, monthlySalarydb.full_attendance_bonus);
                        ps.setString(9, monthlySalarydb.total_salary);
                        ps.executeUpdate();

                        monthlySalarydb = null;
                    } catch (SQLException se) {
                        lb_tips.setText("数据库操作出错！");
                        functionTips();
                        se.printStackTrace();
                    }
                }
                lb_tips.setText("发放当月工资成功！");
                functionTips();
                allSalaryHistory();
                allSalary();
            }
        });
        pn_first.removeAll();
        sarAdminJFrame.repaint();
        pn_first.add(pn_first_1);
        sarAdminJFrame.validate();
    }

    public void allSalaryHistory() { //该财务管理的部门的所有工资历史记录
        String[] columnNames = {"工资编号", "员工编号", "发放时间", "基本工资", "职位工资", "职称工资", "工龄工资", "缺勤扣除", "全勤奖励", "总工资", "操作"}; //表格列名
        rowData = null; //表格数据
        int count = 0; //表的元组总数
        try { //获取MonthlySalaryView视图信息
            String sql = "SELECT * FROM MonthlySalary"; //SQL语句
            PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
            ps = HomePage.connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY); //把操作数据库返回的结果保存到ps中
            ResultSet rs = ps.executeQuery(sql); //ResultSet类，用来存放获取的结果集
            rs.last();
            count = rs.getRow(); //获取工资信息元组总数
            rowcount = count;
            if (count == 0) { //若MonthlySalaryView视图无元组
                rowData = new String[1][11];
                for (int i = 0; i < 10; i++)
                    rowData[0][i] = "无";
            } else { //若MonthlySalaryView视图有元组
                rowData = new String[count][11];
                rs.first();
                int i = 0;
                do { //获取该公司的部门的所有工资信息
                    rowData[i][0] = rs.getString("SalaryID"); //工资编号
                    rowData[i][1] = rs.getString("EmployeeID"); //员工编号
                    rowData[i][2] = rs.getString("Month"); //月份
                    rowData[i][3] = rs.getString("BasicSalary"); //基本工资
                    rowData[i][4] = rs.getString("PositionSalary"); //职位工资
                    rowData[i][5] = rs.getString("TitleSalary"); //职称工资
                    rowData[i][6] = rs.getString("TenureSalary"); //工龄工资
                    rowData[i][7] = rs.getString("AttendanceDeduction"); //缺勤扣除
                    rowData[i][8] = rs.getString("FullAttendanceBonus"); //全勤奖励
                    rowData[i][9] = rs.getString("TotalSalary"); //全勤奖励
                    i++;
                } while (rs.next());
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //界面
        pn_second.setLayout(null);
        pn_second_1.removeAll();
        pn_second_1.setBounds(0, 0, 1045, 695);
        pn_second_1.setLayout(null);

        JPanel pn_top = new JPanel();
        pn_top.setBounds(0, 0, 1045, 50);

        JLabel lb_num;
        lb_num = new JLabel("  公司共有" + count + "条工资发放信息！  ");
        lb_num.setFont(new Font("方正大标宋_GBK", 0, 25));
        pn_top.add(lb_num);

        JTable table = new JTable(new MyTableModel(columnNames, rowData, 7));
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(1, 35)); //设置表头高度
        header.setFont(new Font("方正大标宋_GBK", Font.BOLD, 20));

        table.setRowHeight(25); //设置各行高度
        table.setFont(new Font("方正大标宋_GBK", 0, 15));
        table.setBackground(null);
        table.getTableHeader().setReorderingAllowed(false); //不允许移动各列

        JScrollPane scrollPane = new JScrollPane(table); //滚动条
        scrollPane.setBounds(0, 50, 1045, 645);

        JButton bt_export = new JButton("导出", new ImageIcon("image/out.png"));
        bt_export.setBackground(Color.green.darker());
        bt_export.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_export.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pn_top.add(bt_export);
        pn_second_1.add(pn_top);
        pn_second_1.add(scrollPane);


        if (count != 0) {
            MyEvent e = new MyEvent() { //点击“查看”按钮，查看信息，可修改和删除信息
                public void invoke(ActionEvent e) {
                    MyButton button = (MyButton) e.getSource();
                    int row = button.getRow();
                    pn_second_2.removeAll();
                    visitSalaryInfo((String) table.getValueAt(row, button.getColumn() - 10), 2, null);
                }
            };
            table.getColumnModel().getColumn(10).setCellRenderer(new MyButtonRender("查看")); //设置表格的渲染器
            MyButtonEditor editor = new MyButtonEditor(e, "查看");
            table.getColumnModel().getColumn(10).setCellEditor(editor); //设置表格的编辑器
        }
        bt_export.addActionListener(new ActionListener() { //将表格导出成Excel文件
            public void actionPerformed(ActionEvent e) {
                FileDialog fd = new FileDialog(sarAdminJFrame, "请设置导出位置和文件名！", FileDialog.SAVE);
                fd.setVisible(true);
                String file = fd.getDirectory() + fd.getFile() + ".xls";
                if (fd.getFile() != null)
                    JTableToExcel.export(new File(file), table);
            }
        });
        pn_second.removeAll();
        sarAdminJFrame.repaint();
        pn_second.add(pn_second_1);
        sarAdminJFrame.validate();
    }

    public void visitSalaryInfo(String salary_id, int x, String query_sql) { //查看工资信息，可删除
        if (x == 2) {
            pn_second_2.removeAll();
            pn_second_2.setLayout(null);
            pn_second_2.setBounds(0, 0, 1045, 695);
            pn_second_2.setBorder(BorderFactory.createEtchedBorder());
        } else {
            pn_third_2.removeAll();
            pn_third_2.setLayout(null);
            pn_third_2.setBounds(0, 0, 1045, 695);
            pn_third_2.setBorder(BorderFactory.createEtchedBorder());
        }
        JButton bt_back = new JButton("返回", new ImageIcon("image/back.png"));
        bt_back.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_back.setBounds(1, 10, 92, 25);
        bt_back.setContentAreaFilled(false);
        bt_back.setBorderPainted(false);
        bt_back.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton bt_delete = new JButton("删除工资记录", new ImageIcon("image/delete.png"));
        bt_delete.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_delete.setBounds(755, 360, 190, 40);
        bt_delete.setForeground(Color.red);
        bt_delete.setContentAreaFilled(false);
        bt_delete.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lb_infoImage = new JLabel(new ImageIcon("image/department.png"));
        lb_infoImage.setBounds(750, 135, 200, 200);
        if (x == 2) {
            pn_second_2.add(bt_back);
            pn_second_2.add(bt_delete);
            pn_second_2.add(lb_infoImage);
        } else {
            pn_third_2.add(bt_back);
            pn_third_2.add(bt_delete);
            pn_third_2.add(lb_infoImage);
        }
        JLabel[] lb = new JLabel[10];
        for (int i = 0; i < 10; i++) {
            lb[i] = new JLabel();
            lb[i].setFont(new Font("方正大标宋_GBK", 0, 25));
            lb[i].setBounds(140, 95 + i * 55, 130, 50);
            if (x == 2)
                pn_second_2.add(lb[i]);
            else
                pn_third_2.add(lb[i]);
        }

        lb[0].setText("工资标号：");
        lb[1].setText("员工编号：");
        lb[2].setText("发放日期：");
        lb[3].setText("基本工资：");
        lb[4].setText("职位工资：");
        lb[5].setText("职称工资：");
        lb[6].setText("工龄工资：");
        lb[7].setText("考勤扣除：");
        lb[8].setText("全勤奖励：");
        lb[9].setText("总 工 资：");

        JTextField[] tf = new JTextField[10];
        for (int i = 0; i < 10; i++) {
            tf[i] = new JTextField();
            tf[i].setFont(new Font("方正大标宋_GBK", 0, 25));
            tf[i].setBounds(272, 100 + i * 55, 250, 40);
            tf[i].setEditable(false);
            if (x == 2)
                pn_second_2.add(tf[i]);
            else
                pn_third_2.add(tf[i]);
        }

        try {
            String sql = "SELECT * FROM MonthlySalary WHERE SalaryID='" + salary_id + "'"; //SQL语句
            PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
            ps = HomePage.connection.prepareStatement(sql); //把操作数据库返回的结果保存到ps中
            ResultSet rs = ps.executeQuery(sql); //ResultSet类，用来存放获取的结果集
            while (rs.next()) {
                monthlySalarydb = new MonthlySalaryDB(rs.getString("SalaryID"), rs.getString("EmployeeID"), rs.getString("Month"), rs.getString("BasicSalary"), rs.getString("PositionSalary"), rs.getString("TitleSalary"), rs.getString("TenureSalary"), rs.getString("AttendanceDeduction"), rs.getString("FullAttendanceBonus"), rs.getString("TotalSalary"));
                tf[0].setText(monthlySalarydb.salary_id);
                tf[1].setText(monthlySalarydb.employee_id);
                tf[2].setText(monthlySalarydb.month);
                tf[3].setText(monthlySalarydb.basic_salary);
                tf[4].setText(monthlySalarydb.position_salary);
                tf[5].setText(monthlySalarydb.title_salary);
                tf[6].setText(monthlySalarydb.tenure_salary);
                tf[7].setText(monthlySalarydb.attendance_deduction);
                tf[8].setText(monthlySalarydb.full_attendance_bonus);
                tf[9].setText(monthlySalarydb.total_salary);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        bt_back.addMouseListener(new MouseListener() { //返回
            public void mouseEntered(MouseEvent arg0) {
                bt_back.setForeground(Color.blue);
            }

            public void mouseExited(MouseEvent arg0) {
                bt_back.setForeground(null);
            }

            public void mouseClicked(MouseEvent arg0) {
                monthlySalarydb = null;
                if (x == 2)
                    allSalaryHistory();
                else
                    querySalaryInfo_2(query_sql);
            }

            public void mousePressed(MouseEvent arg0) {
            }

            public void mouseReleased(MouseEvent arg0) {
            }
        });
        bt_delete.addActionListener(new ActionListener() { //删除工资信息
            public void actionPerformed(ActionEvent e) {
                lb_tips.setText("是否确定删除该工资信息？");
                if (x == 2)
                    choiceTips("");
                else
                    choiceTips(query_sql);
            }
        });
        if (x == 2) {
            pn_second.removeAll();
            sarAdminJFrame.repaint();
            pn_second.add(pn_second_2);
            sarAdminJFrame.validate();
        } else {
            pn_third.removeAll();
            sarAdminJFrame.repaint();
            pn_third.add(pn_third_2);
            sarAdminJFrame.validate();
        }
    }

    public void querySalaryInfo_1() { //查询工资发放记录信息
        // rowData = null;
        pn_third.setLayout(null);
        pn_third_1.removeAll();
        pn_third_1.setLayout(null);
        pn_third_1.setBounds(0, 0, 1045, 695);
        pn_third_1.setBorder(BorderFactory.createEtchedBorder());
        JButton bt_query = new JButton("查询", new ImageIcon("image/query.png"));
        bt_query.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_query.setBounds(447, 520, 150, 50);
        bt_query.setContentAreaFilled(false);
        bt_query.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel lb_infoImage = new JLabel(new ImageIcon("image/department.png"));
        lb_infoImage.setBounds(750, 205, 200, 200);
        pn_third_1.add(bt_query);
        pn_third_1.add(lb_infoImage);
        JLabel[] lb = new JLabel[2];
        for (int i = 0; i < 2; i++) {
            lb[i] = new JLabel();
            lb[i].setFont(new Font("方正大标宋_GBK", 0, 25));
            lb[i].setBounds(260, 150 + i * 55, 130, 50);
            pn_third_1.add(lb[i]);
        }
        lb[0].setText("员工编号：");
        lb[1].setText("月    份：");
        JTextField[] tf = new JTextField[6];
        for (int i = 0; i < 2; i++) {
            tf[i] = new JTextField();
            tf[i].setFont(new Font("方正大标宋_GBK", 0, 25));
            tf[i].setBounds(392, 155 + i * 55, 260, 40);
            if (i == 0)
                tf[i].setDocument(new NumLimit()); //限制文本框只能输入数字
            pn_third_1.add(tf[i]);
        }
        bt_query.addActionListener(new ActionListener() { //查询工资发放信息
            public void actionPerformed(ActionEvent e) {
                String sql = "SELECT * FROM MonthlySalary";
                if (tf[0].getText().equals("") && tf[1].getText().equals("")) {
                    //无查询条件
                    querySalaryInfo_2(sql);
                } else if (!isValidDate(tf[1].getText())) {
                    lb_tips.setText("请填写正确的日期！");
                    functionTips();
                } else { //有查询条件

                    sql = sql + " WHERE 1=1";
                    if (!tf[0].getText().equals(""))
                        sql = sql + " AND EmployeeID='" + tf[0].getText() + "'";
                    if (!tf[1].getText().equals(""))
                        sql = sql + " AND Month='" + tf[1].getText() + "'";

                    querySalaryInfo_2(sql);
                }

            }
        });
        pn_third.removeAll();
        sarAdminJFrame.repaint();
        pn_third.add(pn_third_1);
        sarAdminJFrame.validate();
    }

    public void querySalaryInfo_2(String sql) { //查询工资发放信息结果
        String[] columnNames = {"工资编号", "员工编号", "发放时间", "基本工资", "职位工资", "职称工资", "工龄工资", "考勤扣除", "全勤奖励", "总工资", "操作"}; //表格列名
        // rowData=null; //表格数据
        int count = 0; //查询到的工资发放信息条数
        try {
            PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
            ps = HomePage.connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY); //把操作数据库返回的结果保存到ps中
            ResultSet rs = ps.executeQuery(sql); //ResultSet类，用来存放获取的结果集
            rs.last();
            count = rs.getRow(); //获取查询到的工资发放信息条数
            if (count == 0) { //若无符合条件的工资发放信息
                rowData = new String[1][11];
                for (int i = 0; i < 10; i++)
                    rowData[0][i] = "无";
            } else { //若有符合条件的工资发放信息
                rowData = new String[count][11];
                rs.first();
                int i = 0;
                do { //获取符合条件的工资信息
                    rowData[i][0] = rs.getString("SalaryID"); //工资编号
                    rowData[i][1] = rs.getString("EmployeeID"); //员工编号
                    rowData[i][2] = rs.getString("Month"); //月份-工资发放时间
                    rowData[i][3] = rs.getString("BasicSalary"); //基本工资
                    rowData[i][4] = rs.getString("PositionSalary"); //职位工资
                    rowData[i][5] = rs.getString("TitleSalary"); //职称工资
                    rowData[i][6] = rs.getString("TenureSalary"); //工龄工资
                    rowData[i][7] = "<html><font color='red'>" + rs.getString("AttendanceDeduction") + "</font></html>"; //考勤扣除
                    rowData[i][8] = rs.getString("FullAttendanceBonus"); //全勤奖励
                    rowData[i][9] = rs.getString("TotalSalary"); //总工资
                    i++;
                } while (rs.next());
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //界面
        pn_third_1.removeAll();
        pn_third_1.setBounds(0, 0, 1045, 695);
        pn_third_1.setLayout(null);
        JPanel pn_top = new JPanel();
        pn_top.setBounds(0, 0, 1045, 50);
        JLabel lb_num = new JLabel(" 共查询到" + count + "条工资发放信息！ ");
        lb_num.setFont(new Font("方正大标宋_GBK", 0, 25));
        JButton bt_back = new JButton("返回", new ImageIcon("image/back.png"));
        bt_back.setFont(new Font("方正大标宋_GBK", 0, 17));
        bt_back.setContentAreaFilled(false);
        bt_back.setBorderPainted(false);
        bt_back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pn_top.add(bt_back);
        pn_top.add(lb_num);
        JTable table = new JTable(new MyTableModel(columnNames, rowData, 7));
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(1, 35)); //设置表头高度
        header.setFont(new Font("方正大标宋_GBK", Font.BOLD, 17));

        table.setRowHeight(25); //设置各行高度
        table.setFont(new Font("方正大标宋_GBK", 0, 15));
        table.setBackground(null);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table); //滚动条
        scrollPane.setBounds(0, 50, 1045, 645);
        pn_third_1.add(pn_top);
        pn_third_1.add(scrollPane);
        if (count != 0) {
            MyEvent e = new MyEvent() { //点击“查看”按钮，查看信息，可修改和删除信息
                public void invoke(ActionEvent e) {
                    MyButton button = (MyButton) e.getSource();
                    int row = button.getRow();
                    pn_second_2.removeAll();
                    visitSalaryInfo((String) table.getValueAt(row, button.getColumn() - 10), 3, null);
                }
            };
            table.getColumnModel().getColumn(10).setCellRenderer(new MyButtonRender("查看")); //设置表格的渲染器
            MyButtonEditor editor = new MyButtonEditor(e, "查看");
            table.getColumnModel().getColumn(10).setCellEditor(editor); //设置表格的编辑器
        }
        bt_back.addMouseListener(new MouseListener() { //返回
            public void mouseEntered(MouseEvent arg0) {
                bt_back.setForeground(Color.blue);
            }

            public void mouseExited(MouseEvent arg0) {
                bt_back.setForeground(null);
            }

            public void mouseClicked(MouseEvent arg0) {
                querySalaryInfo_1();
            }

            public void mousePressed(MouseEvent arg0) {
            }

            public void mouseReleased(MouseEvent arg0) {
            }
        });
        pn_third.removeAll();
        sarAdminJFrame.repaint();
        pn_third.add(pn_third_1);
        sarAdminJFrame.validate();
    }

    public JPanel changePassword() { //[修改密码]功能
        JPanel pn_changePassword = new JPanel();
        pn_changePassword.setLayout(null);
        pn_changePassword.setSize(1045, 735);
        pn_changePassword.setBorder(BorderFactory.createEtchedBorder());
        JLabel lb_old = new JLabel("旧密码："), lb_new1 = new JLabel("新密码："), lb_new2 = new JLabel("确认密码："), lb = new JLabel("（密码不超过10位）");
        JPasswordField pf_old = new JPasswordField(), pf_new1 = new JPasswordField(), pf_new2 = new JPasswordField();
        lb_old.setFont(new Font("方正大标宋_GBK", 0, 25));
        lb_old.setBounds(300, 200, 200, 50);
        lb_new1.setFont(new Font("方正大标宋_GBK", 0, 25));
        lb_new1.setBounds(300, 260, 200, 50);
        lb_new2.setFont(new Font("方正大标宋_GBK", 0, 25));
        lb_new2.setBounds(275, 320, 225, 50);
        lb.setFont(new Font("方正大标宋_GBK", 0, 18));
        lb.setBounds(650, 260, 200, 50);
        pf_old.setFont(new Font(null, 0, 30));
        pf_old.setBounds(400, 205, 250, 40);
        pf_new1.setFont(new Font(null, 0, 30));
        pf_new1.setBounds(400, 265, 250, 40);
        pf_new2.setFont(new Font(null, 0, 30));
        pf_new2.setBounds(400, 325, 250, 40);
        JLabel lb_image = new JLabel(new ImageIcon("image/change_pwd.png"));
        lb_image.setBounds(447, 20, 150, 150);
        pn_changePassword.add(lb_old);
        pn_changePassword.add(lb_new1);
        pn_changePassword.add(lb_new2);
        pn_changePassword.add(lb);
        pn_changePassword.add(pf_old);
        pn_changePassword.add(pf_new1);
        pn_changePassword.add(pf_new2);
        pn_changePassword.add(lb_image);
        JButton bt_confirm = new JButton("确认"), bt_reset = new JButton("重置");
        bt_confirm.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_confirm.setBounds(405, 410, 100, 50);
        bt_confirm.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bt_reset.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_reset.setBounds(540, 410, 100, 50);
        bt_reset.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pn_changePassword.add(bt_confirm);
        pn_changePassword.add(bt_reset);
        bt_reset.addActionListener(new ActionListener() { //重置，将密码框内容清空
            public void actionPerformed(ActionEvent e) {
                pf_old.setText("");
                pf_new1.setText("");
                pf_new2.setText("");
            }
        });
        bt_confirm.addActionListener(new ActionListener() { //确认
            public void actionPerformed(ActionEvent e) {
                String password = null;
                if (!((!String.valueOf(pf_old.getPassword()).equals("")) //若未填写完整，则进行提示
                        && (!String.valueOf(pf_new1.getPassword()).equals(""))
                        && (!String.valueOf(pf_new2.getPassword()).equals("")))) {
                    lb_tips.setText("请填写完整！");
                    functionTips();
                } else { //若填写完整，则进行密码判断
                    try { //获取原密码
                        String sql = "SELECT password FROM admin WHERE admin_num=" + admin_num; //SQL语句
                        PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
                        ps = HomePage.connection.prepareStatement(sql); //把操作数据库返回的结果保存到ps中
                        ResultSet rs = ps.executeQuery(sql); //ResultSet类，用来存放获取的结果集
                        while (rs.next()) { //遍历结果集
                            password = rs.getString("password");
                        }
                        rs.close();
                        ps.close();
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
                    if (!String.valueOf(pf_old.getPassword()).equals(password)) { //若输入的旧密码错误，则进行提示
                        lb_tips.setText("旧密码错误！");
                        functionTips();
                    }
                    //若输入的旧密码正确，将判断两次新密码是否相同
                    else if (!String.valueOf(pf_new1.getPassword()).equals(String.valueOf(pf_new2.getPassword()))) { //若两次新密码不相同，则进行提示
                        lb_tips.setText("两次新密码不同！");
                        functionTips();
                    }
                    //若输入的两次新密码相同，将判断密码是否不超过20位
                    else if (String.valueOf(pf_new1.getPassword()).length() > 20) { //若输入的密码超过10位，则进行提示
                        lb_tips.setText("密码超过10位！");
                        functionTips();
                    } else { //符合改密条件，进行改密
                        try {
                            String sql = "UPDATE admin set password=? WHERE admin_num=?"; //SQL语句
                            PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
                            ps = HomePage.connection.prepareStatement(sql); //把操作数据库返回的结果保存到ps中
                            ps.setString(1, String.valueOf(pf_new1.getPassword())); //SQL语句第一个?值
                            ps.setString(2, admin_num); //SQL语句第二个?值
                            ps.executeUpdate(); //更新，执行修改操作
                            ps.close();
                        } catch (SQLException se) {
                            se.printStackTrace();
                        }
                        pf_old.setText("");
                        pf_new1.setText("");
                        pf_new2.setText("");
                        lb_tips.setText("密码修改成功！");
                        functionTips();
                    }
                }
            }
        });
        return pn_changePassword;
    }

    public void functionTips() { //操作时弹出的提示信息窗口
        JDialog tips = new JDialog(sarAdminJFrame, "  提示", true);
        JPanel pn_tips = new JPanel();
        JButton bt_tips = new JButton("确定");
        tips.setSize(500, 200);
        tips.setLocationRelativeTo(null);
        tips.setResizable(false);
        tips.setLayout(null);
        pn_tips.setBounds(0, 30, 500, 70);
        lb_tips.setFont(new Font("方正大标宋_GBK", 0, 25));
        bt_tips.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_tips.setBounds(200, 100, 100, 50);
        bt_tips.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pn_tips.add(lb_tips);
        tips.add(pn_tips);
        tips.add(bt_tips);
        bt_tips.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tips.dispose();
            }
        });
        tips.setVisible(true);
    }

    public void choiceTips(String str) { //操作时弹出的提示选择窗口
        JDialog choiceTips = new JDialog(sarAdminJFrame, "  提示", true);
        JPanel pn_tips = new JPanel();
        JButton bt_yes = new JButton("是(Y)");
        JButton bt_no = new JButton("否(N)");
        choiceTips.setSize(500, 200);
        choiceTips.setLocationRelativeTo(null);
        choiceTips.setResizable(false);
        choiceTips.setLayout(null);
        pn_tips.setBounds(0, 30, 500, 70);
        lb_tips.setFont(new Font("方正大标宋_GBK", 0, 25));
        bt_yes.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_yes.setBounds(135, 100, 100, 50);
        bt_yes.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bt_no.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_no.setBounds(260, 100, 100, 50);
        bt_no.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pn_tips.add(lb_tips);
        choiceTips.add(pn_tips);
        choiceTips.add(bt_yes);
        choiceTips.add(bt_no);
        bt_yes.addActionListener(new ActionListener() { //选择“是”，进行相应操作
            public void actionPerformed(ActionEvent e) {
                choiceTips.dispose();
                yesOperation(str);
            }
        });
        bt_no.addActionListener(new ActionListener() { //选择“否”，关闭提示选择窗口
            public void actionPerformed(ActionEvent e) {
                choiceTips.dispose();
            }
        });
        bt_yes.addKeyListener(new KeyListener() { //“是”按钮的快捷键“Y”，“否”按钮的快捷键“N”
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_Y) {
                    choiceTips.dispose();
                    yesOperation(str);
                }
                if (e.getKeyCode() == KeyEvent.VK_N) {
                    choiceTips.dispose();
                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });
        bt_no.addKeyListener(new KeyListener() { //“是”按钮的快捷键“Y”，“否”按钮的快捷键“N”
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_Y) {
                    choiceTips.dispose();
                    yesOperation(str);
                }
                if (e.getKeyCode() == KeyEvent.VK_N) {
                    choiceTips.dispose();
                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });
        choiceTips.setVisible(true);
    }

    public void yesOperation(String str) { //选择“是”之后，根据提示内容，选择要进行的操作
        if (lb_tips.getText().equals("是否退出财务模式？")) { //确定退出财务模式，返回到欢迎界面
            try {
                HomePage.connection.close(); //关闭数据库连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
            sarAdminJFrame.dispose();
            HomePage.con.removeAll();
            HomePage.mainJFrame.repaint();
            HomePage.welcomePage();
            HomePage.mainJFrame.validate();
        } else if (lb_tips.getText().equals("是否确定删除该部门信息？")) { //确定删除部门，进行删除操作
            try {
                String sql = "DELETE FROM Department WHERE DepartMentID='" + departmentdb.department_id + "'"; //SQL语句
                PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
                ps = HomePage.connection.prepareStatement(sql); //把操作数据库返回的结果保存到ps中
                ps.executeUpdate(); //更新，执行删除操作
                lb_tips.setText("部门删除成功！");
                functionTips();
                ps.close();
                departmentdb = null;
                if (str.equals(""))
                    allDepartment();
                else
                    queryDepartmentInfo_2(str);
            } catch (SQLException e) {
                lb_tips.setText("数据库操作出错！");
                functionTips();
                e.printStackTrace();
            }
        } else if (lb_tips.getText().equals("是否确定删除该员工信息？")) { //确定删除学生，进行删除操作
            try {
                String sql = "DELETE FROM Employee WHERE EmployeeID='" + employeedb.employee_id + "'"; //SQL语句
                PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
                ps = HomePage.connection.prepareStatement(sql); //把操作数据库返回的结果保存到ps中
                ps.executeUpdate(); //更新，执行删除操作
                lb_tips.setText("员工删除成功！");
                functionTips();
                ps.close();
                employeedb = null;
                if (str.equals(""))
                    allEmployee();
                else
                    queryEmployeeInfo_2(str);
            } catch (SQLException e) {
                lb_tips.setText("数据库操作出错！");
                functionTips();
                e.printStackTrace();
            }
        } else if (lb_tips.getText().equals("是否确定删除该工资信息？")) { //确定删除工资信息，进行删除操作
            try {
                //删除工资信息
                String sql = "DELETE FROM MonthlySalary WHERE SalaryID='" + monthlySalarydb.salary_id + "'"; //SQL语句
                PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
                ps = HomePage.connection.prepareStatement(sql); //把操作数据库返回的结果保存到ps中
                ps.executeUpdate(); //更新，执行删除操作
                lb_tips.setText("工资信息删除成功！");
                functionTips();
                ps.close();
                monthlySalarydb = null;
                if (str.equals(""))
                    allSalaryHistory();
                else
                    querySalaryInfo_2(str);
            } catch (SQLException e) {
                lb_tips.setText("数据库操作出错！");
                functionTips();
                e.printStackTrace();
            }
        } else if (lb_tips.getText().equals("是否确定删除该考勤登记信息？")) { //确定删除晚归信息，进行删除操作
            try {
                PreparedStatement ps; //创建PreparedStatement类对象ps，用来执行SQL语句
                ps = HomePage.connection.prepareStatement(str); //把操作数据库返回的结果保存到ps中
                ps.executeUpdate(); //更新，执行删除操作
                lb_tips.setText("考勤登记信息删除成功！");
                functionTips();
                ps.close();
            } catch (SQLException e) {
                lb_tips.setText("数据库操作出错！");
                functionTips();
                e.printStackTrace();
            }
        }
    }
}