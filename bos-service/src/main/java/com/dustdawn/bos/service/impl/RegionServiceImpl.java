package com.dustdawn.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dustdawn.bos.dao.IRegionDao;
import com.dustdawn.bos.entity.Region;
import com.dustdawn.bos.service.IRegionService;
import com.dustdawn.bos.utils.PageBean;
@Service
@Transactional
public class RegionServiceImpl implements IRegionService {
	/**
	 * 	区域数据批量保存
	 */
	@Autowired
	private IRegionDao regionDao;

	public void savaBatch(List<Region> regionList) {
		for(Region region : regionList) {
			regionDao.saveOrUpdate(region);
		}
	}

	public void pageQuery(PageBean pageBean) {
		regionDao.pageQuery(pageBean);
	}
	/**
	 * 	查询所有区域
	 */
	public List<Region> findAll() {
		
		return regionDao.findAll();
	}

	//模糊查询
	public List<Region> findListByQ(String q) {
		return regionDao.findListByQ(q);
	}

}
