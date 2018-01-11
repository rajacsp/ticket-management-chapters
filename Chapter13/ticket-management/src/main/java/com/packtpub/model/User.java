package com.packtpub.model;

public class User {	

	private Integer userid;
	
	private String username;
	
	/*
	 * usertype:
	 * 		1 - general user
	 * 		2 - CSR (Customer Service Representative)
	 * 		3 - admin
	 * 		
	 * 
	 */
	private Integer usertype;
	
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setUsertype(Integer usertype){
		this.usertype = usertype;
	}
	
	public Integer getUsertype(){
		return this.usertype;
	}

	public User(Integer userid, String username, Integer usertype) {
		this.userid = userid;
		this.username = username;
		this.usertype = usertype;
	}
	
	@Override
	public String toString() {
		return "User [userid=" + userid + ", username=" + username + ", usertpye = "+usertype+"]";
	}
}
