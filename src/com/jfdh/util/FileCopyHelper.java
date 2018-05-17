package com.jfdh.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileCopyHelper {
	@Value("#{configProperties['fileUploadDisk']}")
	private String fileUploadDisk;

	@Value("#{configProperties['fileUploadTempPath']}")
	private String fileUploadTempPath;
	@Value("#{configProperties['fileUploadPath']}")
	private String fileUploadPath;
	
	public String copyFile(String path,String logoPath) {
		if (path.startsWith(fileUploadPath)) {
			return path;
		}
		String fileFrom = fileUploadDisk + path;
		String fileTo = fileUploadDisk + fileUploadPath + FilePathHelper.getPath(logoPath)+"/"
				+ path.substring(path.lastIndexOf("/") + 1);
		if (copy(fileFrom, fileTo)) {
			return fileUploadPath + FilePathHelper.getPath(logoPath)+"/"
					+ path.substring(path.lastIndexOf("/") + 1);
		} else {
			return "failed";
		}
	}
	

	private boolean copy(String fileFrom, String fileTo) {
		try {
			File temp=new File(fileTo.substring(0, fileTo.lastIndexOf("/")));
			if(!temp.exists()){
				temp.mkdirs();
			}
			FileInputStream in = new java.io.FileInputStream(fileFrom);
			FileOutputStream out = new FileOutputStream(fileTo);
			byte[] bt = new byte[1024];
			int count;
			while ((count = in.read(bt)) > 0) {
				out.write(bt, 0, count);
			}
			in.close();
			out.close();
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
	}
}
