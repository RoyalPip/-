package com.company;

import com.mysql.jdbc.Statement;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class LoginListener implements ActionListener{
    private javax.swing.JTextField text_name;
    private javax.swing.JPasswordField text_password;
    private javax.swing.JFrame login;

    //JDBC驱动名
    String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    //数据库URL：这里的tt是数据库名称
    String JDBC_URL = "jdbc:mysql://localhost:3306/tt?useSSL=false&serverTimezone=UTC";
    //        数据库的用户名与密码
    String USER = "root";
    String PASS = "012416";
    //通过DriverManager类获得该连接对象才能访问数据库
    Connection connection = null;
    //        通过Connection获得该结果对象用于执行静态的SQL语句
    Statement statement = null;

    public LoginListener(javax.swing.JFrame login, javax.swing.JTextField text_name, javax.swing.JPasswordField text_password)
    {//获取登录界面、账号密码输入框对象
        this.login=login;
        this.text_name=text_name;
        this.text_password=text_password;
    }

    int i=3;            //3次登录机会

    public void actionPerformed(ActionEvent e)
    {
        Dimension dim2 = new Dimension(100,30);
        Dimension dim3 = new Dimension(300,30);

        //生成新界面
        javax.swing.JFrame login2 = new javax.swing.JFrame();
        login2.setSize(400,200);
        login2.setDefaultCloseOperation(3);
        login2.setLocationRelativeTo(null);
        login2.setFont(new Font("宋体",Font.PLAIN,14));  //宋体，正常风格，14号字体
        //创建组件
        javax.swing.JPanel jp1 = new JPanel();
        javax.swing.JPanel jp2 = new JPanel();
        //打开数据库
        try {
            //注册JDBC驱动
            Class.forName(JDBC_DRIVER);
            //数据库的连接：通过DriverManager类的getConnection方法，传入三个参数：数据库URL、用户名、用户密码，实例化connection对象
            connection = DriverManager.getConnection(JDBC_URL,USER,PASS);
            //实例化statement对象
            statement = (Statement) connection.createStatement();
            String sql = "SELECT name,password FROM aa";
            //执行查询语句
            ResultSet rSet = statement.executeQuery(sql);
            //展开查询到的数据
            while(rSet.next()) {
            //这里getString()方法中的参数对应的是数据库表中的列名
                String get_name = rSet.getString("name");
                String get_password = rSet.getString("password");
            //输出数据
                if(text_name.getText().equals(get_name) && text_password.getText().equals(get_password))
                {
                    JOptionPane.showMessageDialog(null, "登陆成功", "提示", JOptionPane.PLAIN_MESSAGE);
                    login.dispose();
                    MainInterface d = new MainInterface();//mmmmmm
                    d.way1();// 窗体属性
                    d.way2();// 容器，布置组件
                    d.f.setVisible(true);// 窗体 可见
                    break;
                }
            }
            //依次关闭对象
            rSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
