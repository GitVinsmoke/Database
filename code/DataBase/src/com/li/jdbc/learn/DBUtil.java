package com.li.jdbc.learn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ���ݿ⹤�� 
 * @author Administrator
 *
 */
public class DBUtil {

	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/hotelmanager?useSSL=true";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "112358";
	
	/**
	 * ��ȡ���ݿ�����
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
	 * �ر����ݿ�
	 * @param conn
	 */
	public static void close(Connection conn){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("���ݿ�ر��쳣��" + e);
			}
		}
	}
	
	/**
	 * 
	 * �ر�Statement��Ҳ���Դ�������PreparedStatememt��
	 * @param stat
	 */
	public static void close(Statement stat){
		if(stat != null){
			try {
				stat.close();
			} catch (SQLException e) {
				System.out.println("Statement�ر��쳣��" + e);
			}
		}
	}
	
	/**
	 * �ر�ResultSet
	 * @param stat
	 */
	public static void close(ResultSet rs){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("ResultSet�ر��쳣��" + e);
			}
		}
	}
	
	
}
