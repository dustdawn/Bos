package com.dustdawn.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dustdawn.bos.dao.IUserDao;
import com.dustdawn.bos.entity.User;
import com.dustdawn.bos.service.IUserService;
import com.dustdawn.bos.utils.MD5Utils;

@Service
@Transactional
public class UserServiceImpl implements IUserService{
	@Autowired
	private IUserDao userDao;
	/**
	 * 用户登录
	 */
	public User login(User user) {
		String password = MD5Utils.md5(user.getPassword());
		return userDao.findUserByUsernameAndPassword(user.getUsername(),password);
	}
	/**
	 * 根据用户id修改密码
	 */
	public void editPassword(String id, String password) {
		password = MD5Utils.md5(password);
		userDao.executeUpdate("user.editpassword", password,id);
	}

}
