package com.dustdawn.bos.service;

import java.util.List;

import com.dustdawn.bos.entity.Staff;
import com.dustdawn.bos.utils.PageBean;

public interface IstaffService {

	public void save(Staff model);

	public void pageQuery(PageBean pageBean);
	
	//批量删除
	public void deleteBatch(String ids);
	
	//根据id查询
	public Staff findById(String id);
	
	//根据id修改
	public void update(Staff staff);

	public List<Staff> findListNotDelete();

}
