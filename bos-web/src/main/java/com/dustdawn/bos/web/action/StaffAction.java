package com.dustdawn.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dustdawn.bos.entity.Staff;
import com.dustdawn.bos.service.IstaffService;
import com.dustdawn.bos.utils.PageBean;
import com.dustdawn.bos.web.action.base.BaseAction;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 取派员管理
 * @author DUSTDAWN
 *
 */
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff>{
	@Autowired
	private IstaffService staffService;
	/**
	 * 添加取派员
	 * @return
	 */
	public String add() {
		staffService.save(model);
		return "list";
	}


	
	public String pageQuery() throws IOException {

		staffService.pageQuery(pageBean);
		this.javaToJson(pageBean, new String[] {"currentPage","detachedCriteria","pageSize","decidedzones"});
		return NONE;
	}
	
	
	/**
	 * 	取派员批量删除
	 */
	//属性驱动，接受页面提交批量删除的ids参数
	private String ids;
	
	public String deleteBatch() {
		staffService.deleteBatch(ids);;
		return "list";
	}
	public String edit() {
		Staff staff = staffService.findById(model.getId());
		staff.setName(model.getName());
		staff.setTelephone(model.getTelephone());
		staff.setDecidedzones(model.getDecidedzones());
		staff.setStation(model.getStation());
		staff.setHaspda(model.getHaspda());
		staff.setHaspda("0");
		staffService.update(staff);
		return "list";
	}
	
	/**
	 * 	查询所有未删除的取派员，返回json
	 * @param ids
	 */
	public String listajax() {
		List<Staff> list = staffService.findListNotDelete();
		this.javaToJson(list, new String[]{"decidedzones"});
		return NONE;
	}
	

	public void setIds(String ids) {
		this.ids = ids;
	}
}
