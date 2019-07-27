package com.dustdawn.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dustdawn.bos.dao.IRegionDao;
import com.dustdawn.bos.dao.base.impl.BaseDaoImpl;
import com.dustdawn.bos.entity.Region;

@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements IRegionDao {

	/**
	 * 	根据q参数模糊查询
	 */
	public List<Region> findListByQ(String q) {
		String hql = "FROM Region r WHERE r.shortcode LIKE ? OR r.citycode LIKE ? OR r.province LIKE ? OR r.city LIKE ? OR r.district LIKE ?";
		List<Region> list = (List<Region>) this.getHibernateTemplate().find(hql,"%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%");
		return list;
		
		
	}
	
}
