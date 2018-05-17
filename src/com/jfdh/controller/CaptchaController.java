package com.jfdh.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

/**
 * 防止Captcha机器人登�?
 * @author
 *
 */
@Controller
@RequestMapping("/captchaController")
public class CaptchaController extends BaseController{
	
	@Autowired
	private Producer captchaProducer = null;

	@RequestMapping(value="/{image}",method=RequestMethod.GET)
	public String getKaptchaImage(@PathVariable String image,HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		
		response.setDateHeader("Expires", 0);
		
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		
		// return a jpeg
		response.setContentType("image/jpeg");
		
		// create the text for the image
		String capText = captchaProducer.createText();
		
		// store the text in the session
		session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
		
		// create the image with the text
		BufferedImage bi = captchaProducer.createImage(capText);
		ServletOutputStream out=null;
		try {
			out = response.getOutputStream();
			ImageIO.write(bi, "jpg", out);
			out.flush();
		} catch (IOException e1) {
		
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		
		return null;
	}

}