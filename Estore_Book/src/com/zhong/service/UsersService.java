package com.zhong.service;

import java.util.List;

import com.zhong.model.Users;

public interface UsersService {

	public int saveUsers(Users user);
	public int delUsers(int id);
	public int updateUsers(Users user);
	public Users findUserById(int id);
	public List<Users> findUserList();
	public Users login(Users user);
	public Users findUserByCode(String code);
	public Users findUserByName(String username);
}
