package com.li.jdbc.advanced;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import com.mysql.jdbc.ConnectionGroupManager;

public class ConnectionFactory {
	private static String DRIVER;
	private static String URL;
	private static String USER;
	private static String PASSWORD;

	/* ��̬����飬����ʱֻ��ִ��һ�� */
	static {
		/* Properties�洢���Ǽ�ֵ�Ե�map */
		Properties props = new Properties();
		InputStream is = ConnectionFactory.class.getResourceAsStream("jdbcinfo.properties");
		
		try {
			/*��ȡ������Ϣ�����ص�������*/
			props.load(is);
			DRIVER=props.getProperty("mysql.driver");
			URL=props.getProperty("mysql.url");
			USER=props.getProperty("mysql.user");
			PASSWORD=props.getProperty("mysql.password");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		Connection conn=null;
		try {
			Class.forName(DRIVER);
			conn=DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
		}
		return conn;
	}
	
	/*��Ϊ���Ե�main����*/
	public static void main(String[] args) {
		System.out.println(ConnectionFactory.getConnection());
	}
}
