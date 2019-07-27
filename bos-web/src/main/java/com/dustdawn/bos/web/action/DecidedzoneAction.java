package com.dustdawn.bos.web.action;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dustdawn.bos.entity.Decidedzone;
import com.dustdawn.bos.service.IDecidedzoneService;
import com.dustdawn.bos.web.action.base.BaseAction;

/**
 * 	定区管理
 * @author DUSTDAWN
 *
 */
@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone>{
	//属性驱动,接受多个参数
	private String[] subareaid;
	@Autowired
	private IDecidedzoneService decidedzoneService;
	public String add() {
		decidedzoneService.save(model,subareaid);
		return "list";
	}
	public String pageQuery() {
		decidedzoneService.pageQuery(pageBean);
		this.javaToJson(pageBean, new String[] {"currentPage","detachedCriteria","pageSize","subareas","decidedzones"});
		return NONE;
		
	}
	
	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}
}
