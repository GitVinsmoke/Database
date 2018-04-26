package com.li.jdbc.learn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCMYSQL {

	// 数据库连接，返回Connection对象
	public Connection getConnection() {
		Connection c = null;
		try {
			// 加载数据库驱动：驱动全类名=包名+类名
			Class.forName("com.mysql.jdbc.Driver"); //会抛出 ClassNotFoundException异常    
			System.out.println("加载数据库驱动成功!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			//写法：c=DriverManager.getConnection(url, user, password);
			//mysql URL写法："jdbc:mysql://主机名或IP地址:端口号/数据库名称"
			//主机地址：本地主机地址为localhost或者写127.0.0.1
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbtest?useSSL=true", "root", "123456");
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
			String sql = "CREATE TABLE Company " + "(ID INT PRIMARY KEY     NOT NULL,"
					+ " NAME           TEXT    NOT NULL, " + " AGE            INT     NOT NULL, "
					+ " ADDRESS        CHAR(50), " + " SALARY         REAL)";
			/*
			 * 执行SQL语句采用的是接口java.sql.Statement的成员方法
			 * 1. int executeUpdate(String sql) throws SQLException
			 * 第一条通常用来执行一些不具有返回结果的SQL语句，如insert，update或delete语句
			 * 
			 * 2. ResultSet executeQuery(String sql) throws SQLException
			 * 第二条通常用来执行只返回一个结果集的SQL语句。该成员方法直接返回结果集对象的引用
			 * 
			 * 3. boolean execute(String sql) throws SQLException
			 * 第三条通常用来执行一些具有单个或多个返回结果集的SQL语句，如select语句。如有结果集返回，则
			 * 返回值为true，否则返回false。
			 */
			//增删改，都可以使用此方法，返回值是影响数据的条数（int型）
			stmt.executeUpdate(sql);
			
			System.out.println("表创建成功!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			//finally表示即便是前面出现异常，仍然要处理的动作
			//出现异常也不要忘记关闭资源
			try {
				// 释放Statement实例占用的数据库和JDBC资源
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
	}

	// 向表Company中插入记录
	public void insert(Connection c) {
		Statement stmt = null;
	    try {
	      c.setAutoCommit(false);
	      System.out.println("打开数据库成功!");
	      stmt = c.createStatement();
	      
	      //插入操作
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

	      
	      
	    //使所有上一次提交/回滚后进行的更改成为持久更改，并释放此Connection对象当前持有的所有的数据库锁
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
	    
	    System.out.println("成功!");
	}
	
	//SELECT操作
	public void select(Connection c){
		Statement stmt = null;
		ResultSet rs=null;
	    try {
	      c.setAutoCommit(false);
	      System.out.println("数据库打开成功");
	      stmt = c.createStatement();
	      //临时表，用以存放数据库查询操作所得的结果集
	      rs = stmt.executeQuery( "SELECT * FROM Company;" );   
	      //使用迭代器取出数据
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
	    System.out.println("操作成功!");
	}
	
	//UPDATE操作
	public void update(Connection c){
		Statement stmt = null;
	    try {
	      c.setAutoCommit(false);
	      System.out.println("数据库打开成功！");
	      stmt = c.createStatement();
	      String sql = "UPDATE Company set SALARY = 25000.00 where ID=1;";
	      stmt.executeUpdate(sql);
	      c.commit();
	
	      //调用SELECT操作
	      select(c);
   
	      stmt.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("更新成功！");
	}
	
	//delete操作
	public void delete(Connection c){
		Statement stmt=null;
		try {
		      c.setAutoCommit(false);
		      System.out.println("数据库打开成功！");
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
		System.out.println("删除成功！");
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
		
		//关闭数据库连接
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			//finally表示捕捉到异常之后需要执行的动作
			//即便是发生了异常，也要考虑到关闭数据库连接
		}
	}
}