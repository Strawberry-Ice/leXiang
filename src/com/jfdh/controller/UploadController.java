package com.jfdh.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jfdh.httpmodel.UploadResult;
import com.jfdh.util.FilePathHelper;
@Controller
public class UploadController {
	@Value("#{configProperties['fileUploadTempPath']}")
    private String pathTemp;
	
	@Value("#{configProperties['fileUploadDisk']}")
	private String disk;
	@RequestMapping(value = "/upload")
	@ResponseBody
	public UploadResult upload(
			@RequestParam(value = "avatar", required = false) MultipartFile file,HttpServletRequest request) {

		String path = disk+pathTemp;
		String temp=file.getOriginalFilename();
		String fileName = System.currentTimeMillis()+temp.substring(temp.lastIndexOf("."));
		// String fileName = new Date().getTime()+".jpg";
		File targetFile = new File(FilePathHelper.getPath(path), fileName);
		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}

		UploadResult ur=new UploadResult();
		// 保存
		try {
			//pathTemp=FilePathHelper.getPath(pathTemp);
			file.transferTo(targetFile);
			ur.setStatus("OK");
			ur.setMessage("头像上传成功！！");
			ur.setLogo(FilePathHelper.getPath(pathTemp)+"/"+fileName);
			ur.setUrl(request.getContextPath()+FilePathHelper.getPath(pathTemp)+"/"+fileName);
			return ur;
		} catch (Exception e) {
			ur.setStatus("ERR");
			ur.setMessage("头像上传失败！！");
			return ur;
		}

		
	}
}
