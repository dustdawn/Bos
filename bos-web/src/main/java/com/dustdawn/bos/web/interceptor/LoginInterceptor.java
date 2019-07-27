package com.dustdawn.bos.web.interceptor;

import org.apache.struts2.ServletActionContext;

import com.dustdawn.bos.entity.User;
import com.dustdawn.bos.utils.BOSUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
/**
 * 自定义的拦截器，实现用户未登录跳转到登录页面
 * @author DUSTDAWN
 * 继承struts2框架的MethodFIlterInterceptor,可选择拦截对象
 */
public class LoginInterceptor extends MethodFilterInterceptor{

	protected String doIntercept(ActionInvocation invocation) throws Exception {
		User user = BOSUtils.getLoginUser();
		if(user==null) {
			return "login";
		}
		return invocation.invoke();
	}
	
	
}
