package com.model;

public class StudentDataModel {
	
	int id;
	String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return "StudentData [id=" + id + ", name=" + name + "]";
	}
	public void setName(String name) {
		this.name = name;
	}

}
