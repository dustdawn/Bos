package com.dustdawn.bos.service;

import com.dustdawn.bos.entity.User;

public interface IUserService {

	User login(User model);

	void editPassword(String id, String password);

}
