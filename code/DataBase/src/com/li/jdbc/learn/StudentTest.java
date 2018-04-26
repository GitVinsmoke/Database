package com.li.jdbc.learn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentTest {

	public  Connection getConnection(){
		Connection c=null;
		
		try {
			//加载数据库驱动
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			c=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/xtit?useSSL=true", "root", "112358");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return c;
	}
	
	public void select(Connection c){
		Statement stmt=null;
		ResultSet rs=null;
		
		try {
			c.setAutoCommit(false);
			stmt=c.createStatement();
			rs = stmt.executeQuery( "SELECT * FROM Student;" );
			
			while(rs.next()){
				String stuNo=rs.getString("stuNo");
				String stuName=rs.getString("stuName");
				int score=rs.getInt("score");
				 System.out.println( "NO = " + stuNo );
		         System.out.println( "NAME = " + stuName );
		         System.out.println( "SCORE = " + score );
		         System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				stmt.close();
				rs.close();
			} catch (Exception e2) {
			}
		}
	}
	
	public static void main(String[] args) {
		StudentTest st=new StudentTest();
		
		st.select(st.getConnection());
	}

}
