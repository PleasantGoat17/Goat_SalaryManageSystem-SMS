package com.goat;

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HomePage { //主页类
	static JFrame mainJFrame=new JFrame("员工工资管理系统");
	static Container con=mainJFrame.getContentPane();
	static boolean flag; //是否为管理员
	static Connection connection=null;
	
	public static void main(String[] args) {
		mainJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainJFrame.setResizable(false);
		con.setLayout(null);
		welcomePage();
	}
	
	public static void welcomePage() { //欢迎界面
		mainJFrame.setSize(550,400);
		mainJFrame.setLocationRelativeTo(null);
		JPanel pn=new JPanel();
		pn.setSize(550,400);
		pn.setLayout(null);
		JLabel lb1=new JLabel("欢迎使用"),lb2=new JLabel("员工工资管理系统");
		JButton bt1=new JButton("个人登录"),bt2=new JButton("财务登录");
		lb1.setFont(new Font("微软雅黑",0,35));
		lb1.setBounds(200,30,150,100);
		lb2.setFont(new Font("微软雅黑",0,35));
		lb2.setBounds(128,100,300,80);
		bt1.setFont(new Font("微软雅黑",0,22));
		bt1.setBounds(70,210,170,70);
		bt1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		bt2.setFont(new Font("微软雅黑",0,22));
		bt2.setBounds(310,210,170,70);
		bt2.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		pn.add(lb1);
		pn.add(lb2);
		pn.add(bt1);
		pn.add(bt2);
		
		con.add(pn);
		mainJFrame.setVisible(true);
		bt1.addActionListener(new ActionListener() { //进入员工登录界面
			public void actionPerformed(ActionEvent e) {
				flag=false;
				con.remove(pn);
				mainJFrame.repaint();
				loginPage();
				mainJFrame.validate();
			}
		});
		bt2.addActionListener(new ActionListener() { //进入管理员登录界面
			public void actionPerformed(ActionEvent e) {
				flag=true;
				con.remove(pn);
				mainJFrame.repaint();
				loginPage();
				mainJFrame.validate();
			}
		});
	}
	
	public static void loginPage() { //登录界面
		mainJFrame.setSize(550,400);
		mainJFrame.setLocationRelativeTo(null);
		JPanel pn=new JPanel();
		pn.setSize(550,400);
		pn.setLayout(null);
		JButton bt1=new JButton("返回",new ImageIcon("image/back.png")),bt2=new JButton("登  录");
		JTextField tf=new JTextField();
		JPasswordField pf=new JPasswordField();
		JLabel lb1,lb2=new JLabel("账号："),lb3=new JLabel("密码：");
		if(flag) {
			lb1=new JLabel("财务登录");
			lb1.setFont(new Font("微软雅黑",0,35));
			lb1.setBounds(185,30,180,100);
		}
		else {
			lb1=new JLabel("个人登录");
			lb1.setFont(new Font("微软雅黑",0,35));
			lb1.setBounds(200,30,150,100);
		}
		lb2.setFont(new Font("微软雅黑",0,25));
		lb2.setBounds(100,100,80,100);
		lb3.setFont(new Font("微软雅黑",0,25));
		lb3.setBounds(100,150,80,100);
		tf.setFont(new Font("微软雅黑",0,25));
		tf.setBounds(170,130,230,40);
		pf.setFont(new Font(null,0,25));
		pf.setBounds(170,180,230,40);
		bt2.setFont(new Font("微软雅黑",0,25));
		bt2.setBounds(200,250,150,60);
		bt2.setCursor(new Cursor(Cursor.HAND_CURSOR));
		bt1.setFont(new Font("微软雅黑",0,17));
		bt1.setBounds(1,10,92,25);
		bt1.setContentAreaFilled(false);
		bt1.setBorderPainted(false);
		bt1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pn.add(lb1);
		pn.add(lb2);
		pn.add(lb3);
		pn.add(tf);
		pn.add(pf);
		pn.add(bt1);
		pn.add(bt2);
		con.add(pn);
		bt1.addMouseListener(new MouseListener() { //返回到欢迎界面
			public void mouseEntered(MouseEvent arg0) {
				bt1.setForeground(Color.blue);
			}
			public void mouseExited(MouseEvent arg0) {
				bt1.setForeground(null);
			}
			public void mouseClicked(MouseEvent arg0) {
				con.remove(pn);
				mainJFrame.repaint();
				welcomePage();
				mainJFrame.validate();
			}
			public void mousePressed(MouseEvent arg0){}
			public void mouseReleased(MouseEvent arg0){}
		});
		bt2.addActionListener(new ActionListener() { //登录，连接数据库获取账号密码并检验，正确则进入功能界面
			public void actionPerformed(ActionEvent e) {
				databaseConnection(); //连接MySQL数据库
				if(flag) //进行管理员账号密码检验
					new Login().adminLogin(tf.getText(),String.valueOf(pf.getPassword()));
				else //进行员工账号密码检验
					new Login().userLogin(tf.getText(),String.valueOf(pf.getPassword()));;
			}
		});
	}
	
	public static void databaseConnection() { //连接MySQL数据库
		final String JDBC_DRIVER="com.mysql.cj.jdbc.Driver"; //JDBC驱动名
	    final String DB_URL= "jdbc:mysql://localhost:3306/gongzi?useSSL=false"; //数据库URL
	    final String USER="root"; //数据库用户名
	    final String PASS="root"; //数据库密码
	    try {
	    	Class.forName(JDBC_DRIVER); //加载JDBC驱动
	    	connection=DriverManager.getConnection(DB_URL,USER,PASS); //连接数据库
	    }catch(SQLException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
//		System.out.println("成功登录啦");
	}
}