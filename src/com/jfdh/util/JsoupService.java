package com.jfdh.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;
@Service("jsoupService")
public class JsoupService {
	
	
	@Value("#{configProperties['fileUploadDisk']}")
	private String fileUploadDisk;

	@Value("#{configProperties['logoPath']}")
	private String logoPath;
	@Value("#{configProperties['fileUploadPath']}")
	private String fileUploadPath;
	
	public String parse(String content,String contextPath){
		Document doc = Jsoup.parse(content);
		Elements elements=doc.getElementsByTag("img");
		for(int i=0;i<elements.size();i++){
			Element element=elements.get(i);
			String img=element.attr("src");
			if(StringUtil.isNotNull(img)){
				String url=parseImg(img);
				element.attr("src",contextPath+url);
			}
			
		}
		return doc.html();
	}
	
	public String parseImg(String img){
		String[] strs=img.split(Constants.CONSTANTS_COMMA);
		String type=(strs[0].split(Constants.CONSTANTS_SPRIT)[1]).split(";")[0];
		return Test(strs[1],type);
	}
	

//	private String base64StringToImage(String base64String,String type){    
//        try {   
//            BASE64Decoder decoder = new BASE64Decoder();
//        	String fileName = System.currentTimeMillis()+"."+ type;
//            byte[] bytes1 = decoder.decodeBuffer(base64String);                  
//            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);    
//            BufferedImage bi1 =ImageIO.read(bais);    
//            File targetFile = new File(FilePathHelper.getPath(fileUploadDisk + fileUploadPath
//					+ logoPath), fileName);   
//            if (!targetFile.getParentFile().exists()) {
//    			targetFile.getParentFile().mkdirs();
//    		}
//            ImageIO.write(bi1, "jpg", targetFile);//不管输出什么格式图片，此处不需改动    
//            return FilePathHelper.getPath(fileUploadPath + logoPath)+"/"+fileName;
//            
//        } catch (IOException e) {    
//            e.printStackTrace();    
//        }  
//        return null;
//    } 
	
	private String Test(String base64String,String type){    
		try {   
            BASE64Decoder decoder = new BASE64Decoder();
        	String fileName = System.currentTimeMillis()+"."+ type;
            File targetFile = new File(FilePathHelper.getPath(fileUploadDisk + fileUploadPath
					+ logoPath), fileName);   
            if (!targetFile.getParentFile().exists()) {
    			targetFile.getParentFile().mkdirs();
    		}
            
            FileOutputStream write = new FileOutputStream(targetFile);
            byte[] decoderBytes = decoder.decodeBuffer(base64String);
            write.write(decoderBytes);
            
            return FilePathHelper.getPath(fileUploadPath + logoPath)+"/"+fileName;
            
        } catch (IOException e) {    
            e.printStackTrace();    
        }  
        return null;
    } 
	
}
