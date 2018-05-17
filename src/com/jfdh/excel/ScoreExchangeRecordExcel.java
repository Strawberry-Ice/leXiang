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

import com.jfdh.model.ScoreExchangeRecord;
import com.jfdh.util.DateUtil;

public class ScoreExchangeRecordExcel extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String excelName = "积分兑换";  
        // 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开  
        response.setContentType("APPLICATION/OCTET-STREAM");  
        response.setHeader("Content-Disposition", "attachment; filename="  
                + URLEncoder.encode(excelName, "UTF-8")+".xls");
		
		@SuppressWarnings("unchecked")
		List<ScoreExchangeRecord> records=(List<ScoreExchangeRecord>) model.get("records");
		// 创建Excel的工作sheet,对应到一个excel文档的tab  
		HSSFSheet sheet = workbook.createSheet("积分兑换"); 
		// 创建Excel的sheet的一行  
		HSSFRow header = sheet.createRow(0);
		int i=0;
		header.createCell(i++).setCellValue("登记人员");
		header.createCell(i++).setCellValue("礼品名称");
		header.createCell(i++).setCellValue("兑换码");
		header.createCell(i++).setCellValue("所需积分");
		header.createCell(i++).setCellValue("登记日期");
		header.createCell(i++).setCellValue("使用状态");
		header.createCell(i++).setCellValue("使用日期");
		header.createCell(i++).setCellValue("使用用户");
		
		int j=1;
		HSSFRow row;
		for(ScoreExchangeRecord record:records){
			int k=0;
			row = sheet.createRow(j);
			row.createCell(k++).setCellValue(record.getOperator().getNickName());
			row.createCell(k++).setCellValue(record.getScoreShop().getName());
			row.createCell(k++).setCellValue(record.getCoupon());
			row.createCell(k++).setCellValue(record.getScoreShop().getScore());
			row.createCell(k++).setCellValue(DateUtil.format(record.getCreateDate(),"yyyy/MM/dd HH:mm:ss"));
			row.createCell(k++).setCellValue(record.isStatus()?"已使用":"未使用");
			row.createCell(k++).setCellValue(DateUtil.format(record.getVerifyDate(),"yyyy/MM/dd HH:mm:ss"));
			row.createCell(k++).setCellValue(record.getExchanger().getNickName());
			j++;
		}
	
	}

}
