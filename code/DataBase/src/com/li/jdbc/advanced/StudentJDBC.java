package com.li.jdbc.advanced;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.li.pojo.Student;

/*对数据库的CRUD操作*/

public class StudentJDBC {

	/* 插入 */
	public void save(Student stu) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		/* 通过连接工厂获取连接对象 */

		try {
			conn = ConnectionFactory.getConnection();
			
			/*设置事务隔离级别*/
			conn.setTransactionIsolation(8);
			/*获取事务隔离级别*/
			System.out.println("事务隔离级别："+conn.getTransactionIsolation());
			
			/* 设置手动提交事务 */
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
			
			/* 提交事务 */
			conn.commit();
			
			System.out.println("成功插入记录：" + rows + "条");
		} catch (SQLException e) {
			e.printStackTrace();
			/* 如果操作出现问题，将回滚事务 */
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

	/* 删除 */
	public void delete(Integer id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		/* 通过连接工厂获取连接对象 */
		conn = ConnectionFactory.getConnection();

		try {
			String insertSQL = "delete from student where id=?";
			pstmt = conn.prepareStatement(insertSQL);
			pstmt.setInt(1, id);
			int rows = pstmt.executeUpdate();
			System.out.println("成功删除记录：" + rows + "条");
		} catch (SQLException e) {
			e.printStackTrace();
			/* 如果操作出现问题，将事务回滚 */
			try {
				conn.rollback();
			} catch (SQLException e1) {
			}
		} finally {
			DBUtils.close(conn);
			DBUtils.close(pstmt);
		}
	}

	/* 更新 */
	/* 查找单个 */
	/* 查找所有，返回List集合，List<Stuent> */

}
