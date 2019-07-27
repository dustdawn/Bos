package com.dustdawn.bos.service;

import java.util.List;

import com.dustdawn.bos.entity.Region;
import com.dustdawn.bos.utils.PageBean;

public interface IRegionService {
	
	//批量保存区域
	void savaBatch(List<Region> regionList);
	//分页查询
	void pageQuery(PageBean pageBean);
	//分区
	List<Region> findAll();
	List<Region> findListByQ(String q);
	
}
