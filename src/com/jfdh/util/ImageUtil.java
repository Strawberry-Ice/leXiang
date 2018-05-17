package com.jfdh.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {

	// 参数分别对应了员图片的地址，目的图片的地址，裁剪的坐标和缩放的坐标
	public static void genImage(final String srcImageFile,
			String savedImagePath, final int finalWidth, final int finalHeight) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			// 根据ComminsMutilpartFile得到文件输入流，BufferedImage读取这个输入流得到bufferedImage实例
			fis = new FileInputStream(srcImageFile);
			fos = new FileOutputStream(savedImagePath);
			BufferedImage bufferedImage = ImageIO.read(fis);
			Image image = bufferedImage.getScaledInstance(finalWidth,
					finalHeight, BufferedImage.TYPE_INT_RGB);// 钓鱼getScaledInstance得到一个一个60*60的Image对象
			BufferedImage bufferedImage1 = new BufferedImage(finalWidth,
					finalHeight, BufferedImage.TYPE_INT_RGB);
			bufferedImage1.getGraphics().drawImage(image, 0, 0, null);
			ImageIO.write(bufferedImage1, getExtention(srcImageFile), fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != fos) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

	/**
	 * 功能：提取文件名的后缀
	 * 
	 * @param fileName
	 * @return
	 */
	private static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos + 1);
	}

}