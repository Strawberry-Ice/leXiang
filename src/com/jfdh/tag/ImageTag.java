package com.jfdh.tag;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.jfdh.model.Image;
import com.jfdh.repository.ImageRepository;
import com.jfdh.util.ImageUtil;

public class ImageTag extends RequestContextAwareTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String url;
	private String size;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@Override
	protected int doStartTagInternal() throws Exception {
		if(StringUtils.isEmpty(url)){
			return EVAL_PAGE; 
		}
		WebApplicationContext context = getRequestContext()
				.getWebApplicationContext();
		PropertiesFactoryBean config = context
				.getBean(PropertiesFactoryBean.class);
		ImageRepository imageRepository = context
				.getBean(ImageRepository.class);
		Image image = imageRepository.findByUrlAndSize(url, size);
		String returnValue = getFileExtention(url) + "_" + size + "."
				+ getExtention(url);
		if (null == image) {
			String fileUploadDisk = config.getObject().getProperty(
					"fileUploadDisk");
			String sizes[] = size.split("_");
			String targetFile = fileUploadDisk + returnValue;
			ImageUtil.genImage(fileUploadDisk + url, targetFile,
					Integer.parseInt(sizes[0]), Integer.parseInt(sizes[1]));
			image = new Image();
			image.setSize(size);
			image.setUrl(url);
			image.setCreateDate(new Date());
			try {
				imageRepository.save(image);
			} catch (Exception e) {
				Thread.sleep(50);
			}
		}
		JspWriter out = pageContext.getOut(); // 重要
		out.write(((HttpServletRequest) pageContext.getRequest())
				.getContextPath() + returnValue);
		return EVAL_PAGE; // 表示处理完标签后继续执行以下的JSP网页
		// return SKIP_PAGE; //表示不处理接下来的JSP网页
	}

	private String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos + 1);
	}

	private String getFileExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(0, pos);
	}
}
