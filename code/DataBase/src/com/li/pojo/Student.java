package com.li.pojo;

import java.io.Serializable;

/*ʵ����*/

public class Student implements Serializable {

	private static final long serialVersionUID = -2751895717151662361L;
	
	/*��ʹ�û����������ͣ��Է�ȡ������ֵ����Ϊnull*/
	private Integer id; 
	private String name;
	private String address;
	private String gender;
	private Integer age;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Student() {
		
	}
	
	public Student(Integer id, String name, String address, String gender, Integer age) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.gender = gender;
		this.age = age;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", address=" + address + ", gender=" + gender + ", age=" + age
				+ "]";
	}
}
