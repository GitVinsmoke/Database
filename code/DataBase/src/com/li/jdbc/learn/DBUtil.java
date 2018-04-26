package com.li.jdbc.learn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库工具 
 * @author Administrator
 *
 */
public class DBUtil {

	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/hotelmanager?useSSL=true";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "112358";
	
	/**
	 * 获取数据库链接
	 * @return
	 */
	public static Connection getConnection(){
		Connection conn = null;
		
		try {
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 关闭数据库
	 * @param conn
	 */
	public static void close(Connection conn){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("数据库关闭异常：" + e);
			}
		}
	}
	
	/**
	 * 
	 * 关闭Statement（也可以传进子类PreparedStatememt）
	 * @param stat
	 */
	public static void close(Statement stat){
		if(stat != null){
			try {
				stat.close();
			} catch (SQLException e) {
				System.out.println("Statement关闭异常：" + e);
			}
		}
	}
	
	/**
	 * 关闭ResultSet
	 * @param stat
	 */
	public static void close(ResultSet rs){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("ResultSet关闭异常：" + e);
			}
		}
	}
	
	
}
