package com.zhong.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhong.dao.UsersMapper;
import com.zhong.model.Users;
import com.zhong.service.UsersService;
@Service
public class UsersServiceImpl implements UsersService {
	@Autowired
	private UsersMapper usersmapper;
	
	public int saveUsers(Users user) {
		int num= usersmapper.saveUsers(user);
		//发送给用户注册成功的邮件
		
		
		
		return num;
	}

	public int delUsers(int id) {
		return usersmapper.delUsers(id);
	}

	public int updateUsers(Users user) {
		return usersmapper.updateUsers(user);
	}

	public Users findUserById(int id) {
		return usersmapper.findUserById(id);
	}

	public List<Users> findUserList() {
		return usersmapper.findUserList();
	}

	public Users login(Users user) {
		return usersmapper.login(user);
	}
	
	public Users findUserByCode(String code) {
		return usersmapper.findUserByCode(code);
	}
	
	public Users findUserByName(String username) {
		return usersmapper.findUserByName(username);
	}
}
