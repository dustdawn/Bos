package com.dustdawn.bos.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dustdawn.bos.dao.IStaffDao;
import com.dustdawn.bos.entity.Staff;
import com.dustdawn.bos.service.IstaffService;
import com.dustdawn.bos.utils.PageBean;

@Service
@Transactional
public class StaffServiceImpl implements IstaffService{
	@Autowired
	private IStaffDao staffDao;
	public void save(Staff model) {
		staffDao.save(model);
	}
	
	public void pageQuery(PageBean pageBean) {
		staffDao.pageQuery(pageBean);
	}

	public void deleteBatch(String ids) {
		if(StringUtils.isNotBlank(ids)) {
			String[] staffIds = ids.split(",");
			for(String id : staffIds) {
				staffDao.executeUpdate("staff.delete",id);
			}
		}
		
	}

	public Staff findById(String id) {
		
		return staffDao.findById(id);
	}

	public void update(Staff staff) {
		staffDao.update(staff);
	}

	public List<Staff> findListNotDelete() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
		detachedCriteria.add(Restrictions.eq("deltag", "0"));
		
		return staffDao.findByCriteria(detachedCriteria);
	}

}
