package com.dustdawn.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dustdawn.bos.dao.IDecidedzoneDao;
import com.dustdawn.bos.dao.ISubraeaDao;
import com.dustdawn.bos.entity.Decidedzone;
import com.dustdawn.bos.entity.Subarea;
import com.dustdawn.bos.service.IDecidedzoneService;
import com.dustdawn.bos.utils.PageBean;

@Service
@Transactional
public class DecidedzoneServiceImpl implements IDecidedzoneService{

	@Autowired
	private IDecidedzoneDao decidedzoneDao;
	@Autowired
	private ISubraeaDao subareaDao;
	/**
	 * 	添加定区，同时关联分区
	 */
	public void save(Decidedzone model, String[] subareaid) {
		decidedzoneDao.save(model);
		for (String id : subareaid) {
			Subarea subarea = subareaDao.findById(id);
			//model.getSubareas().add(subarea);一方定区放弃维护外键权利
			subarea.setDecidedzone(model);//维护外键
		}
	}
	public void pageQuery(PageBean pageBean) {
		decidedzoneDao.pageQuery(pageBean);
	}

}
