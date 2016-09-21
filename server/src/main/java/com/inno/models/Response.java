package com.inno.models;

public class Response {
	String message;
	String username;
	
	public Response(){
		this.message="";
		this.username="";
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String toString() {
		return message + " to " + username + "<CR>";
	}
}
