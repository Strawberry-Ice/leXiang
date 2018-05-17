package com.jfdh.excel;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.jfdh.httpmodel.HttpApplication;
import com.jfdh.httpmodel.HttpFieldValue;
import com.jfdh.model.FieldsType;

public class ApplicationExcel extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		
		String excelName = "报名数据.xls";  
        // 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开  
        response.setContentType("APPLICATION/OCTET-STREAM");  
        response.setHeader("Content-Disposition", "attachment; filename="  
                + URLEncoder.encode(excelName, "UTF-8"));
		
		List<FieldsType> typeList=(List<FieldsType>) model.get("typeList");
		List<HttpApplication> applicationList=(List<HttpApplication>) model.get("applicationList");
		// 创建Excel的工作sheet,对应到一个excel文档的tab  
	    HSSFSheet sheet = workbook.createSheet("sheet1"); 
	    // 创建Excel的sheet的一行  
	    HSSFRow header = sheet.createRow(0);  
	    header.createCell(0).setCellValue("活动名称");
	    header.createCell(1).setCellValue("报名日期");
	    header.createCell(2).setCellValue("状态");
	    int i=3;
	    for(FieldsType fieldsType:typeList){
	    	header.createCell(i).setCellValue(fieldsType.getName());
	    	i++;
	    }
	    int j=1;
	    HSSFRow row;
		for(HttpApplication application:applicationList){
			row = sheet.createRow(j);
			row.createCell(0).setCellValue(application.getActivityName());
			row.createCell(1).setCellValue(application.getApplyDate());
			row.createCell(2).setCellValue(application.getStatus().toString());
			List<HttpFieldValue> values=application.getValues();
			int l=3;
			for(FieldsType fieldsType:typeList){
				for(HttpFieldValue fieldValue:values){
					if(fieldValue.getTypeName().equalsIgnoreCase(fieldsType.getName())){
						row.createCell(l).setCellValue(fieldValue.getCode());
					}
				}
				l++;
		    }
			j++;
		}
	}

}
