package com.li.jdbc.advanced;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.li.pojo.Student;

/*�����ݿ��CRUD����*/

public class StudentJDBC {

	/* ���� */
	public void save(Student stu) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		/* ͨ�����ӹ�����ȡ���Ӷ��� */

		try {
			conn = ConnectionFactory.getConnection();
			
			/*����������뼶��*/
			conn.setTransactionIsolation(8);
			/*��ȡ������뼶��*/
			System.out.println("������뼶��"+conn.getTransactionIsolation());
			
			/* �����ֶ��ύ���� */
			conn.setAutoCommit(false);
			String insertSQL = "insert into student values(?,?,?,?,?)";
			pstmt = conn.prepareStatement(insertSQL);
			
			pstmt.setInt(1, stu.getId());
			pstmt.setString(2, stu.getName());
			pstmt.setString(3, stu.getAddress());
			pstmt.setString(4, stu.getGender());
			pstmt.setInt(5, stu.getAge());
			pstmt.executeUpdate();

			int rows = pstmt.executeUpdate();
			
			/* �ύ���� */
			conn.commit();
			
			System.out.println("�ɹ������¼��" + rows + "��");
		} catch (SQLException e) {
			e.printStackTrace();
			/* ��������������⣬���ع����� */
			try {
				conn.rollback();
			} catch (SQLException e1) {
			}
		} finally {
			DBUtils.close(conn);
			DBUtils.close(pstmt);
			DBUtils.close(rs);
		}
	}

	/* ɾ�� */
	public void delete(Integer id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		/* ͨ�����ӹ�����ȡ���Ӷ��� */
		conn = ConnectionFactory.getConnection();

		try {
			String insertSQL = "delete from student where id=?";
			pstmt = conn.prepareStatement(insertSQL);
			pstmt.setInt(1, id);
			int rows = pstmt.executeUpdate();
			System.out.println("�ɹ�ɾ����¼��" + rows + "��");
		} catch (SQLException e) {
			e.printStackTrace();
			/* ��������������⣬������ع� */
			try {
				conn.rollback();
			} catch (SQLException e1) {
			}
		} finally {
			DBUtils.close(conn);
			DBUtils.close(pstmt);
		}
	}

	/* ���� */
	/* ���ҵ��� */
	/* �������У�����List���ϣ�List<Stuent> */

}
