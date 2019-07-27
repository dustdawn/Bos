package com.dustdawn.bos.service;

import java.util.List;

import com.dustdawn.bos.entity.Subarea;
import com.dustdawn.bos.utils.PageBean;

public interface ISubareaService {

	void save(Subarea model);

	void pageQuery(PageBean pageBean);

	List<Subarea> findAll();

	List<Subarea> findListNotAssociation();

}
