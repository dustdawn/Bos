package com.dustdawn.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dustdawn.bos.dao.IUserDao;
import com.dustdawn.bos.dao.base.impl.BaseDaoImpl;
import com.dustdawn.bos.entity.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao{
	/**
	 * 根据用户名密码名称查询用户
	 */
	public User findUserByUsernameAndPassword(String username, String password) {
		String hql = "FROM User u where u.username = ? And u.password = ?";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, username,password);
		if(list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
		
	}

}
