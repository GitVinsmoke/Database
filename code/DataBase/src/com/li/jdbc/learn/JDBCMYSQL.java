package com.li.jdbc.learn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCMYSQL {

	// ���ݿ����ӣ�����Connection����
	public Connection getConnection() {
		Connection c = null;
		try {
			// �������ݿ�����������ȫ����=����+����
			Class.forName("com.mysql.jdbc.Driver"); //���׳� ClassNotFoundException�쳣    
			System.out.println("�������ݿ������ɹ�!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			//д����c=DriverManager.getConnection(url, user, password);
			//mysql URLд����"jdbc:mysql://��������IP��ַ:�˿ں�/���ݿ�����"
			//������ַ������������ַΪlocalhost����д127.0.0.1
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbtest?useSSL=true", "root", "123456");
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
			String sql = "CREATE TABLE Company " + "(ID INT PRIMARY KEY     NOT NULL,"
					+ " NAME           TEXT    NOT NULL, " + " AGE            INT     NOT NULL, "
					+ " ADDRESS        CHAR(50), " + " SALARY         REAL)";
			/*
			 * ִ��SQL�����õ��ǽӿ�java.sql.Statement�ĳ�Ա����
			 * 1. int executeUpdate(String sql) throws SQLException
			 * ��һ��ͨ������ִ��һЩ�����з��ؽ����SQL��䣬��insert��update��delete���
			 * 
			 * 2. ResultSet executeQuery(String sql) throws SQLException
			 * �ڶ���ͨ������ִ��ֻ����һ���������SQL��䡣�ó�Ա����ֱ�ӷ��ؽ�������������
			 * 
			 * 3. boolean execute(String sql) throws SQLException
			 * ������ͨ������ִ��һЩ���е����������ؽ������SQL��䣬��select��䡣���н�������أ���
			 * ����ֵΪtrue�����򷵻�false��
			 */
			//��ɾ�ģ�������ʹ�ô˷���������ֵ��Ӱ�����ݵ�������int�ͣ�
			stmt.executeUpdate(sql);
			
			System.out.println("�����ɹ�!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			//finally��ʾ������ǰ������쳣����ȻҪ����Ķ���
			//�����쳣Ҳ��Ҫ���ǹر���Դ
			try {
				// �ͷ�Statementʵ��ռ�õ����ݿ��JDBC��Դ
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
	}

	// ���Company�в����¼
	public void insert(Connection c) {
		Statement stmt = null;
	    try {
	      c.setAutoCommit(false);
	      System.out.println("�����ݿ�ɹ�!");
	      stmt = c.createStatement();
	      
	      //�������
	      String sql = "INSERT INTO Company (ID,NAME,AGE,ADDRESS,SALARY) " +
	                   "VALUES (1, 'Paul', 32, 'California', 20000.00 );"; 
	      stmt.executeUpdate(sql);

	      sql = "INSERT INTO Company (ID,NAME,AGE,ADDRESS,SALARY) " +
	            "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );"; 
	      stmt.executeUpdate(sql);

	      sql = "INSERT INTO Company (ID,NAME,AGE,ADDRESS,SALARY) " +
	            "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );"; 
	      stmt.executeUpdate(sql);

	      sql = "INSERT INTO Company (ID,NAME,AGE,ADDRESS,SALARY) " +
	            "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );"; 
	      stmt.executeUpdate(sql);

	      
	      
	    //ʹ������һ���ύ/�ع�����еĸ��ĳ�Ϊ�־ø��ģ����ͷŴ�Connection����ǰ���е����е����ݿ���
	      c.commit();                          
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    } finally{
	    	try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
	    
	    System.out.println("�ɹ�!");
	}
	
	//SELECT����
	public void select(Connection c){
		Statement stmt = null;
		ResultSet rs=null;
	    try {
	      c.setAutoCommit(false);
	      System.out.println("���ݿ�򿪳ɹ�");
	      stmt = c.createStatement();
	      //��ʱ�����Դ�����ݿ��ѯ�������õĽ����
	      rs = stmt.executeQuery( "SELECT * FROM Company;" );   
	      //ʹ�õ�����ȡ������
	      while ( rs.next() ) {
	         int id = rs.getInt("id");
	         String  name = rs.getString("name");
	         int age  = rs.getInt("age");
	         String  address = rs.getString("address");
	         float salary = rs.getFloat("salary");
	         
	         System.out.println( "ID = " + id );
	         System.out.println( "NAME = " + name );
	         System.out.println( "AGE = " + age );
	         System.out.println( "ADDRESS = " + address );
	         System.out.println( "SALARY = " + salary );
	         System.out.println();
	      }
	      
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    } finally{
		    try {
		    	rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
	    System.out.println("�����ɹ�!");
	}
	
	//UPDATE����
	public void update(Connection c){
		Statement stmt = null;
	    try {
	      c.setAutoCommit(false);
	      System.out.println("���ݿ�򿪳ɹ���");
	      stmt = c.createStatement();
	      String sql = "UPDATE Company set SALARY = 25000.00 where ID=1;";
	      stmt.executeUpdate(sql);
	      c.commit();
	
	      //����SELECT����
	      select(c);
   
	      stmt.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("���³ɹ���");
	}
	
	//delete����
	public void delete(Connection c){
		Statement stmt=null;
		try {
		      c.setAutoCommit(false);
		      System.out.println("���ݿ�򿪳ɹ���");
		      stmt = c.createStatement();
		      String sql = "DELETE from Company where ID=2;";
		      stmt.executeUpdate(sql);
		      c.commit();
		      select(c);
		      stmt.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		System.out.println("ɾ���ɹ���");
	}

	public static void main(String args[]) {

		JDBCMYSQL DB = new JDBCMYSQL();
		Connection c = null;

		c = DB.getConnection();
		
		//DB.creatTable(c);
		//DB.insert(c);
		//DB.select(c);
		//DB.update(c);
		DB.delete(c);
		
		//�ر����ݿ�����
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			//finally��ʾ��׽���쳣֮����Ҫִ�еĶ���
			//�����Ƿ������쳣��ҲҪ���ǵ��ر����ݿ�����
		}
	}
}