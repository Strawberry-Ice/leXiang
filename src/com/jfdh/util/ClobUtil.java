/**
 * 
 */
package com.jfdh.util;

import java.io.Reader;
import java.sql.Clob;

/**
 * @author 
 *
 */
public class ClobUtil {

	/*********************
	 * 
	 * 将clob类型转换成String工具方法
	 * 
	 * 
	 * @param clob 要转换的clob类型数据
	 * @return 转换的结果String
	 */
	public static String clob2String(Clob clob) {

		if (clob == null) {
			return "";
		}

		StringBuffer sb = new StringBuffer(10240);
		Reader clobStream = null;

		try {
			clobStream = clob.getCharacterStream();
			char[] b = new char[10240];
			int i = 0;
			while ((i = clobStream.read(b)) != -1){
				sb.append(b, 0, i);
			}
		} catch (Exception ex) {
			sb = null;
		} finally {
			try {
				if (clobStream != null)
					clobStream.close();
			} catch (Exception e) {
			}
		}
		if (sb == null)
			return "";
		else
			return sb.toString();
	}
	
	/*********************
	 * 
	 * 将String类型转换成clob工具方法
	 * 
	 * 
	 * @param string 要转换的clob类型数据
	 * @return 转换的结果clob
	 * @throws RuntimeException 
	 */
	public static Clob string2Clob(String str){
		if(str == null){
			str = "";
		}
		try{
			Clob c = new javax.sql.rowset.serial.SerialClob(str.toCharArray());
			return c;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public static String getString(String dateString,String content,String fileName){
		String msg="";
		if(content==null){
			msg=dateString+"&nbsp;删除附件&nbsp;<b>"+fileName+"</b>";
        }else{
        	msg=content+"<br>"+"&nbsp;&nbsp;&nbsp;&nbsp;"+dateString+"&nbsp;删除附件&nbsp;<b>"+fileName+"</b>";
        }
		return msg;
	}
}
