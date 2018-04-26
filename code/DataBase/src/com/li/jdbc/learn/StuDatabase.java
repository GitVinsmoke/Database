package com.li.jdbc.learn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StuDatabase {

	public Connection getConnection() {
		Connection c = null;
		try {
			Class.forName("com.mysql.jdbc.Driver"); // 会抛出异常
			System.out.println("加载数据库驱动成功！");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbtest?useSSL=true", "root", "112358");
			System.out.println("数据库连接成功！");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return c;
	}

	// SELECT操作
	public void select(Connection c) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			c.setAutoCommit(false);
			System.out.println("数据库打开成功");
			stmt = c.createStatement();
			// 临时表，用以存放数据库查询操作所得的结果集
			rs = stmt.executeQuery("SELECT * FROM students;");
			// 使用迭代器取出数据
			while (rs.next()) {
				int ID = rs.getInt("ID");
				String Name = rs.getString("Name");
				int Score = rs.getInt("Score");
				String Password = rs.getString("Password");

				System.out.println("ID = " + ID);
				System.out.println("Name = " + Name);
				System.out.println("Score = " + Score);
				System.out.println("Password = " + Password);
				System.out.println();
			}

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("操作成功!");
	}

	public static void main(String[] args) {
		StuDatabase sd = new StuDatabase();
		Connection c = null;
		c = sd.getConnection();

		sd.select(c);

		// 关闭数据库连接
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// finally表示捕捉到异常之后需要执行的动作
			// 即便是发生了异常，也要考虑到关闭数据库连接

		}
	}

}
