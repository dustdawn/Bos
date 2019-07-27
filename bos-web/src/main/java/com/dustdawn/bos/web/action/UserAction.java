package com.dustdawn.bos.web.action;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dustdawn.bos.entity.User;
import com.dustdawn.bos.service.IUserService;
import com.dustdawn.bos.utils.BOSUtils;
import com.dustdawn.bos.web.action.base.BaseAction;

//多例使用对象时产生
@Controller("aaa")
@Scope("prototype")
public class UserAction extends BaseAction<User>{
	//属性驱动，接受页面输入的验证码
	private String checkcode;
	@Autowired
	private IUserService userService;
	//登录
	public String login() {
		String validatecode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		if(StringUtils.isNotBlank(checkcode) && checkcode.equals(validatecode)) {
			User user = userService.login(model);
			if(user!=null) {
				ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
				return "home";
			}else {
				this.addActionError("用户名或密码错误");
				return LOGIN;
			}
				
		}else {
			this.addActionError("输入的验证码错误!");
			return LOGIN;
		}
	}
	
	//注销
	public String logout() {
		ServletActionContext.getRequest().getSession().invalidate();
		return LOGIN;
	}
	//修改密码
	public String editPassword() throws IOException {
		String f = "1";
		User user = BOSUtils.getLoginUser();
		try {
			userService.editPassword(user.getId(),model.getPassword());
		} catch (Exception e) {
			f = "0";
			e.printStackTrace();
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(f);

		return NONE;
	}
	
	public void setCheckcode(String checkcode) {

		this.checkcode = checkcode;
	}
	
}
