package com.dustdawn.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dustdawn.bos.entity.Region;
import com.dustdawn.bos.entity.Subarea;
import com.dustdawn.bos.service.ISubareaService;
import com.dustdawn.bos.utils.FileUtils;
import com.dustdawn.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea>{
	/**
	 * 	保存添加分区
	 */
	@Autowired
	private ISubareaService subareaService;
	public String add() {
		subareaService.save(model);
		return "list";
	}
	
	public String pageQuery() {
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		String addresskey = model.getAddresskey();
		if(StringUtils.isNotBlank(addresskey)) {
			//过滤条件,根据地址关键字查询
			detachedCriteria.add(Restrictions.like("addresskey", "%"+addresskey+"%"));
		}
		Region region = model.getRegion();
		if(region!=null) {
			String city = region.getCity();
			String district = region.getDistrict();
			String province = region.getProvince();
			//关联查询
			detachedCriteria.createAlias("region", "r");
			if(StringUtils.isNotBlank(city)) {
				detachedCriteria.add(Restrictions.like("r.city", "%"+city+"%"));
			}
			if(StringUtils.isNotBlank(district)) {
				detachedCriteria.add(Restrictions.like("r.district", "%"+district+"%"));
			}
			if(StringUtils.isNotBlank(province)) {
				detachedCriteria.add(Restrictions.like("r.province", "%"+province+"%"));
			}
			
		}
		subareaService.pageQuery(pageBean);
		this.javaToJson(pageBean, new String[] {"currentPage","detachedCriteria","pageSize","decidedzone","subareas"});
		return NONE;
	}
	//分区导出
	public String exportXls() throws IOException {
		List<Subarea> list = subareaService.findAll();
		
		
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建标签页
		HSSFSheet sheet = workbook.createSheet("分区数据");
		//创建标题命令行
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("分区编号");
		headRow.createCell(1).setCellValue("开始标号");
		headRow.createCell(2).setCellValue("结束编号");
		headRow.createCell(3).setCellValue("位置信息");
		headRow.createCell(4).setCellValue("省市区");
		
		for(Subarea subarea : list) {
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum()+1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getStartnum());
			dataRow.createCell(2).setCellValue(subarea.getEndnum());
			dataRow.createCell(3).setCellValue(subarea.getPosition());
			dataRow.createCell(4).setCellValue(subarea.getRegion().getName());
		}
		
		
		String filename = "分区数据.xls";
		//解析文件类型
		String contentType = ServletActionContext.getServletContext().getMimeType(filename);
		
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
		
		ServletActionContext.getResponse().setContentType(contentType);
		
		//获取客户端浏览器类型
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		//转码文件名
		filename = FileUtils.encodeDownloadFilename(filename, agent);
		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename="+filename);
		
		workbook.write(out);
		return NONE;
	}
	/**
	 * 	定区 的分区ajax
	 */
	public String listajax() {
		List<Subarea> list = subareaService.findListNotAssociation();
		this.javaToJson(list, new String[] {"decidedzone","region"});
		return NONE;
	}


}
