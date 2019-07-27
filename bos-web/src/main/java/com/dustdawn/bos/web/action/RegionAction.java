package com.dustdawn.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dustdawn.bos.entity.Region;
import com.dustdawn.bos.service.IRegionService;
import com.dustdawn.bos.utils.PageBean;
import com.dustdawn.bos.utils.PinYin4jUtils;
import com.dustdawn.bos.web.action.base.BaseAction;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region>{
	//属性驱动接受上传文件
	private File regionFile;
	@Autowired
	private IRegionService regionService;
	/**
	 * 	导入区域
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public String implortXls() throws Exception {
		List<Region> regionList = new ArrayList<Region>();
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(regionFile));
		HSSFSheet sheet = workbook.getSheet("Sheet1");
		//根据名称获得指定sheet对象
		for(Row row : sheet) {
			if(row.getRowNum() == 0) {
				continue;
			}
			String id = row.getCell(0).getStringCellValue();
			String province = row.getCell(1).getStringCellValue();
			String city = row.getCell(2).getStringCellValue();
			String district = row.getCell(3).getStringCellValue();
			String postcode = row.getCell(4).getStringCellValue();
			
			Region region = new Region(id,province,city,district,postcode,null,null,null);
			 
			province = province.substring(0, province.length() - 1);
			city = city.substring(0, city.length() - 1);
			district = district.substring(0, district.length() - 1);
			String info = province + city + district;
			String[] headByString = PinYin4jUtils.getHeadByString(info);
			
			String shortcode = StringUtils.join(headByString);
			String citycode = PinYin4jUtils.hanziToPinyin(city, "");
			
			region.setShortcode(shortcode);
			region.setCitycode(citycode);
			regionList.add(region);
		}
		//批量保存
		regionService.savaBatch(regionList);
		return NONE;
		
	}



	/**
	 * 分页查询
	 * @return
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException {

		regionService.pageQuery(pageBean);
		this.javaToJson(pageBean, new String[] {"currentPage","detachedCriteria","pageSize","subareas"});
		return NONE;
	}
	/**
	 * 	分区添加时jaso返回json数据
	 * @param regionFile
	 */
	private String q;
	public String listajax() {
		List<Region> list = null;
		if(StringUtils.isNotBlank(q)) {
			list = regionService.findListByQ(q);
		}else {
			list = regionService.findAll();
		}
			
		this.javaToJson(list, new String[] {"subareas"});
		return NONE;
	}
	
	
	
	public void setRegionFile(File regionFile) {
		this.regionFile = regionFile;
	}



	public void setQ(String q) {
		this.q = q;
	}
	
}
