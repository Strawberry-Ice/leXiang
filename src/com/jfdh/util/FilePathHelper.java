package com.jfdh.util;

public class FilePathHelper {
	public static String getPath(String path){
		return path+"/"+new FloderHelper().getCurrentMonday();
	}
}
