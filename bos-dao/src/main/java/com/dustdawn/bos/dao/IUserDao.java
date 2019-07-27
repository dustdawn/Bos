package com.dustdawn.bos.dao;

import com.dustdawn.bos.dao.base.IBaseDao;
import com.dustdawn.bos.entity.User;

public interface IUserDao extends IBaseDao<User>{

	User findUserByUsernameAndPassword(String username, String password);

}
