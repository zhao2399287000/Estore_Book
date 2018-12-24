package com.zhong.model;

public class Users {
	private Integer id;
	private String username;
	private String password;
	private String nickname;
	private String email;
	private String role;
	private Integer state;
	private String activecode;
	private String updatetime;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setId(Integer id) {
		this.id =id;
	}
	public Integer getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getActivecode() {
		return activecode;
	}
	public void setActivecode(String acticecode) {
		this.activecode = acticecode;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public Users() {
		super();
	}
	public Users(Integer id, String username, String password, String nickname, String email, String role,
			Integer state, String activecode, String updatetime) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.email = email;
		this.role = role;
		this.state = state;
		this.activecode = activecode;
		this.updatetime = updatetime;
	}
	
	
}
