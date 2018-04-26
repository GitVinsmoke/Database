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
			Class.forName("com.mysql.jdbc.Driver"); // ���׳��쳣
			System.out.println("�������ݿ������ɹ���");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbtest?useSSL=true", "root", "112358");
			System.out.println("���ݿ����ӳɹ���");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return c;
	}

	// SELECT����
	public void select(Connection c) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			c.setAutoCommit(false);
			System.out.println("���ݿ�򿪳ɹ�");
			stmt = c.createStatement();
			// ��ʱ�����Դ�����ݿ��ѯ�������õĽ����
			rs = stmt.executeQuery("SELECT * FROM students;");
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
		System.out.println("�����ɹ�!");
	}

	public static void main(String[] args) {
		StuDatabase sd = new StuDatabase();
		Connection c = null;
		c = sd.getConnection();

		sd.select(c);

		// �ر����ݿ�����
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// finally��ʾ��׽���쳣֮����Ҫִ�еĶ���
			// �����Ƿ������쳣��ҲҪ���ǵ��ر����ݿ�����

		}
	}

}
