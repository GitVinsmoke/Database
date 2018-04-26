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

	/* 静态代码块，加载时只被执行一次 */
	static {
		/* Properties存储的是键值对的map */
		Properties props = new Properties();
		InputStream is = ConnectionFactory.class.getResourceAsStream("jdbcinfo.properties");
		
		try {
			/*读取流的信息，加载到类里面*/
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
	
	/*作为测试的main方法*/
	public static void main(String[] args) {
		System.out.println(ConnectionFactory.getConnection());
	}
}
