package com.li.jdbc.advanced;

import com.li.pojo.Student;

public class StudentJDBCTest {
	
	public static void main(String[] args) {
		StudentJDBC sj=new StudentJDBC();
		Student stu=new Student(1, "Ð¡Ã÷", "US", "ÄÐ", 20);
		sj.save(stu);
	}
}
