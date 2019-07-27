package com.dustdawn.bos.utils;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.dustdawn.bos.entity.User;

public class BOSUtils {
	public static HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}
	public static User getLoginUser() {
		return (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
	}
}
