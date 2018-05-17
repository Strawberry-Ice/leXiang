package com.jfdh.controller.weichat.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

public class DownLoadImageUtil {
	 // 生成图片函数  
    public static void downLoad(String imgUrl,String path,String fileName) {  
        try {  
            // 创建流  
            BufferedInputStream in = new BufferedInputStream(new URL(imgUrl)  
                    .openStream());  
  
            // 生成图片名  
            // 存放地址  
        	File img = new File(path, fileName);
			if (!img.getParentFile().exists()) {
				img.getParentFile().mkdirs();
			}
            // 生成图片  
            BufferedOutputStream out = new BufferedOutputStream(  
                    new FileOutputStream(img));  
            byte[] buf = new byte[2048];  
            int length = in.read(buf);  
            while (length != -1) {  
                out.write(buf, 0, length);  
                length = in.read(buf);  
            }  
            in.close();  
            out.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
    
}
