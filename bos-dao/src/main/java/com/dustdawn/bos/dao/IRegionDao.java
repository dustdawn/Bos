package com.dustdawn.bos.dao;

import java.util.List;

import com.dustdawn.bos.dao.base.IBaseDao;
import com.dustdawn.bos.entity.Region;

public interface IRegionDao extends IBaseDao<Region>{

	List<Region> findListByQ(String q);

}
