package sdms;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserFunction implements ActionListener { //财务功能类
    String user_num;
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

    String rowData[][] = null;

    public UserFunction(String user_num) { //整体界面
        this.user_num = user_num;
        try {

            String sql = "SELECT * FROM Department WHERE ManagerID = " + user_num; //SQL语句，查询该财务管理的部门
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

        JLabel lb_topUser = new JLabel("财务：" + user_num);
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

        JButton bt_query = new JButton("工资查询"); //[建议与反馈]
        bt_query.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_query.setContentAreaFilled(false);
        bt_query.setBounds(0, 105, 249, 50);

        JButton bt_password = new JButton("修改密码"); //[修改密码]
        bt_password.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_password.setContentAreaFilled(false);
        bt_password.setBounds(0, 160, 249, 50);

        JButton bt_out = new JButton("退出"); //[退出]
        bt_out.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_out.setContentAreaFilled(false);
        bt_out.setBounds(0, 215, 249, 50);


        bt_info.addActionListener(this);
        bt_query.addActionListener(this);
        bt_password.addActionListener(this);
        bt_out.addActionListener(this);

        pn_menu.add(bt_info);
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
            String sql = "SELECT * FROM EmployeeView WHERE EmployeeID=" + user_num; //SQL语句，查询财务信息
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
            sql = "SELECT * FROM DepartmentView WHERE DepartmentID =(SELECT DepartmentID FROM Employee WHERE EmployeeID= " + user_num + ")"; //SQL语句，查询该财务管理的部门信息
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


        JButton bt_export = new JButton("导出", new ImageIcon("image/out.png"));
        bt_export.setBackground(Color.green.darker());
        bt_export.setFont(new Font("方正大标宋_GBK", 0, 20));
        bt_export.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pn_top.add(bt_export);
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

        JLabel lb_infoImage = new JLabel(new ImageIcon("image/department.png"));
        lb_infoImage.setBounds(750, 135, 200, 200);
        if (x == 2) {
            pn_second_2.add(bt_back);
            pn_second_2.add(lb_infoImage);
        } else {
            pn_third_2.add(bt_back);
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
                        String sql = "SELECT password FROM admin WHERE admin_num=" + user_num; //SQL语句
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
                            ps.setString(2, user_num); //SQL语句第二个?值
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