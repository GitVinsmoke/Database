package com.li.jdbc.learn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {

	public Connection getConnection() {
		Connection c = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("加载数据库驱动成功!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			c = DriverManager.getConnection("jdbc:mysql:" + "//localhost:3306/xtit?useSSL=true", "root", "112358");
			System.out.println("数据库连接成功!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	// 在数据库中创建一个表Company
	public void creatTable(Connection c) {

		System.out.println("打开数据库成功!");
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			String sql = "CREATE TABLE test " + "(ID INT PRIMARY KEY     NOT NULL,"
					+ " NAME           TEXT    NOT NULL, " + " AGE            INT     NOT NULL, "
					+ " ADDRESS        CHAR(50), " + " SALARY         REAL)";

			// 增删改，都可以使用此方法，返回值是影响数据的条数（int型）
			stmt.execute(sql);

			System.out.println("表创建成功!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// finally表示即便是前面出现异常，仍然要处理的动作
			// 出现异常也不要忘记关闭资源
			try {
				// 释放Statement实例占用的数据库和JDBC资源
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void insert(Connection c) {
		Statement stmt = null;
		try {
			c.setAutoCommit(false);
			System.out.println("打开数据库成功!");
			stmt = c.createStatement();

			// 插入操作
			String sql = "INSERT INTO Students (ID,Name,Score,Password) "
					+ "VALUES (2014, 'Poul', 79, password('123456'));";
			stmt.executeUpdate(sql);
			// 使所有上一次提交/回滚后进行的更改成为持久更改，并释放此Connection对象当前持有的所有的数据库锁
			c.commit();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		System.out.println("记录创建成功!");
	}

	/**
	 * SQL注入
	 * 
	 * @param c
	 */
	public void sqlOsmosis(Connection c) {
		Statement st = null;
		ResultSet rs = null;
		String name="LeeH' or '1'='1";
		String password="156' or '1'='1";

		String sql = "select * from stu_info where stuName ='" + name + "' and password = '" + password + "'";
		System.out.println("------>" + sql);
		try {
			st = c.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				System.out.println("登录成功！");
			} else {
				System.out.println("用户名或密码错误！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * SQL语句预处理
	 */
	public void prepareStatement(Connection c) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "select * from stu_info where password=? and stuName=?";
		try {
			ps = c.prepareStatement(sql);
			/*
			 * 将占位符赋值： 占位在SQL中从左到右下标以1开始依次递增 预处理会将参数中的特殊符号进行转义，可以预防SQL注入
			 */

			ps.setString(1, "1234 or '1'='1'");
			ps.setString(2, "Lucy or '1'='1'");
			
			//ps.setString(1, "123456");
			//ps.setString(2, "Lucy");
			//这样便可以成功查找
			
			System.out.println("------>" + sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println("登录成功！");
			} else {
				System.out.println("用户名或密码错误！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void select(Connection c) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			c.setAutoCommit(false);
			System.out.println("数据库打开成功");
			stmt = c.createStatement();
			// 临时表，用以存放数据库查询操作所得的结果集
			rs = stmt.executeQuery("SELECT * FROM Students where ID=2014 ;");
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

	public void update(Connection c) {
		Statement stmt = null;
		try {
			c.setAutoCommit(false);
			System.out.println("数据库打开成功！");
			stmt = c.createStatement();
			String sql = "UPDATE Students set Password = 112358 where ID=2017;";
			stmt.executeUpdate(sql);
			c.commit();

			// 调用SELECT操作
			select(c);

			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("更新成功！");
	}

	public static void main(String[] args) {

		Test test = new Test();
		Connection c = null;

		c = test.getConnection();
		// test.creatTable(c);
		// test.insert(c);
		// test.update(c);
		// test.select(c);
		test.sqlOsmosis(c);
		test.prepareStatement(c);

		try {
			c.close();
			System.out.println("关闭连接！");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
