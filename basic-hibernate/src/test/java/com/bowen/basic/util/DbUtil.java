package com.bowen.basic.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbUtil {

    public static Connection getConnection() throws SQLException{
        Connection con = null ;
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms_test?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8", "root", "123456");
        return con;
    }

    public static void close(Connection con){
        try{
            if (con != null) con.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void close(PreparedStatement ps){
        try{
            if(ps != null) ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
