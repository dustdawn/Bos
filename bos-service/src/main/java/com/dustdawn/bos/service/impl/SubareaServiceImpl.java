package com.dustdawn.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dustdawn.bos.dao.ISubraeaDao;
import com.dustdawn.bos.entity.Subarea;
import com.dustdawn.bos.service.ISubareaService;
import com.dustdawn.bos.utils.PageBean;

@Service
@Transactional
public class SubareaServiceImpl implements ISubareaService{
	
	@Autowired
	private ISubraeaDao subareaDao;
	public void save(Subarea model) {
		subareaDao.save(model);
	}
	//分页查询
	public void pageQuery(PageBean pageBean) {
		subareaDao.pageQuery(pageBean);
		
	}
	public List<Subarea> findAll() {
		return subareaDao.findAll();
	}
	
	/**
	 * 	查询所有为关联分区
	 */
	public List<Subarea> findListNotAssociation() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		//过滤天健为分区对象中定区为空
		detachedCriteria.add(Restrictions.isNull("decidedzone"));
		return subareaDao.findByCriteria(detachedCriteria);
	}

}
