package com.dustdawn.bos.service;

import com.dustdawn.bos.entity.Decidedzone;
import com.dustdawn.bos.utils.PageBean;

public interface IDecidedzoneService {

	void save(Decidedzone model, String[] subareaid);

	void pageQuery(PageBean pageBean);

}
