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
			System.out.println("�������ݿ������ɹ�!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			c = DriverManager.getConnection("jdbc:mysql:" + "//localhost:3306/xtit?useSSL=true", "root", "112358");
			System.out.println("���ݿ����ӳɹ�!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	// �����ݿ��д���һ����Company
	public void creatTable(Connection c) {

		System.out.println("�����ݿ�ɹ�!");
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			String sql = "CREATE TABLE test " + "(ID INT PRIMARY KEY     NOT NULL,"
					+ " NAME           TEXT    NOT NULL, " + " AGE            INT     NOT NULL, "
					+ " ADDRESS        CHAR(50), " + " SALARY         REAL)";

			// ��ɾ�ģ�������ʹ�ô˷���������ֵ��Ӱ�����ݵ�������int�ͣ�
			stmt.execute(sql);

			System.out.println("�����ɹ�!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// finally��ʾ������ǰ������쳣����ȻҪ����Ķ���
			// �����쳣Ҳ��Ҫ���ǹر���Դ
			try {
				// �ͷ�Statementʵ��ռ�õ����ݿ��JDBC��Դ
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
			System.out.println("�����ݿ�ɹ�!");
			stmt = c.createStatement();

			// �������
			String sql = "INSERT INTO Students (ID,Name,Score,Password) "
					+ "VALUES (2014, 'Poul', 79, password('123456'));";
			stmt.executeUpdate(sql);
			// ʹ������һ���ύ/�ع�����еĸ��ĳ�Ϊ�־ø��ģ����ͷŴ�Connection����ǰ���е����е����ݿ���
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

		System.out.println("��¼�����ɹ�!");
	}

	/**
	 * SQLע��
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
				System.out.println("��¼�ɹ���");
			} else {
				System.out.println("�û������������");
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
	 * SQL���Ԥ����
	 */
	public void prepareStatement(Connection c) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "select * from stu_info where password=? and stuName=?";
		try {
			ps = c.prepareStatement(sql);
			/*
			 * ��ռλ����ֵ�� ռλ��SQL�д������±���1��ʼ���ε��� Ԥ����Ὣ�����е�������Ž���ת�壬����Ԥ��SQLע��
			 */

			ps.setString(1, "1234 or '1'='1'");
			ps.setString(2, "Lucy or '1'='1'");
			
			//ps.setString(1, "123456");
			//ps.setString(2, "Lucy");
			//��������Գɹ�����
			
			System.out.println("------>" + sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println("��¼�ɹ���");
			} else {
				System.out.println("�û������������");
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
			System.out.println("���ݿ�򿪳ɹ�");
			stmt = c.createStatement();
			// ��ʱ�����Դ�����ݿ��ѯ�������õĽ����
			rs = stmt.executeQuery("SELECT * FROM Students where ID=2014 ;");
			// ʹ�õ�����ȡ������
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
		System.out.println("�����ɹ�!");
	}

	public void update(Connection c) {
		Statement stmt = null;
		try {
			c.setAutoCommit(false);
			System.out.println("���ݿ�򿪳ɹ���");
			stmt = c.createStatement();
			String sql = "UPDATE Students set Password = 112358 where ID=2017;";
			stmt.executeUpdate(sql);
			c.commit();

			// ����SELECT����
			select(c);

			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("���³ɹ���");
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
			System.out.println("�ر����ӣ�");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
