package com.jfdh.test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jfdh.controller.weichat.message.Article;
import com.jfdh.controller.weichat.message.ImageMessage;
import com.jfdh.controller.weichat.message.InputMessage;
import com.jfdh.controller.weichat.message.OutputMessage;
import com.jfdh.controller.weichat.util.XStreamFactory;
import com.jfdh.model.Resource;
import com.jfdh.repository.ResourceRepository;
import com.thoughtworks.xstream.XStream;

public class Test {

	public static void main1(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-test.xml");
		ResourceRepository resourceRepository=context.getBean(ResourceRepository.class);
		Resource parent=new Resource();
		parent.setId(UUID.randomUUID().toString());
		parent.setName("积分商城");
		parent.setResUrl("#");
		parent.setResKey("/score/shop");
		parent.setLogo("menu-icon fa fa-tag");
		parent.setLevel(1);
		resourceRepository.save(parent);
		
		Resource child=new Resource();
		child.setId(UUID.randomUUID().toString());
		child.setName("积分商城");
		child.setResUrl("/score/shop/list");
		child.setResKey("/score/shop/list");
		child.setLogo("menu-icon fa fa-caret-right");
		child.setLevel(1);
		child.setParent(parent);
		resourceRepository.save(child);
		
		child=new Resource();
		child.setId(UUID.randomUUID().toString());
		child.setName("积分兑换记录");
		child.setResUrl("/score/shop/list");
		child.setResKey("/score/shop/list");
		child.setLogo("menu-icon fa fa-caret-right");
		child.setLevel(1);
		child.setParent(parent);
		resourceRepository.save(child);
		
		child=new Resource();
		child.setId(UUID.randomUUID().toString());
		child.setName("兑换品领取");
		child.setResUrl("/score/shop/list");
		child.setResKey("/score/shop/list");
		child.setLogo("menu-icon fa fa-caret-right");
		child.setLevel(1);
		child.setParent(parent);
		resourceRepository.save(child);
		
		child=new Resource();
		child.setId(UUID.randomUUID().toString());
		child.setName("积分划拨");
		child.setResUrl("/score/shop/list");
		child.setResKey("/score/shop/list");
		child.setLogo("menu-icon fa fa-caret-right");
		child.setLevel(1);
		child.setParent(parent);
		resourceRepository.save(child);
		
		
	}
	
	public static void main2(String[] args) {
		XStream xs = XStreamFactory.createXstream();
//		xs.processAnnotations(Article.class);
		xs.processAnnotations(OutputMessage.class);
		OutputMessage outputMsg = new OutputMessage();
		outputMsg.setFromUserName("1111111");
		outputMsg.setToUserName("2222222");
		outputMsg.setCreateTime(3333333l);
		outputMsg.setMsgType("44444444");
		List<Article> articles=new ArrayList<Article>();
		Article a=new Article();
		a.setPicUrl("url");
		a.setDescription("des");
		a.setTitle("title");
		a.setUrl("url");
		articles.add(a);
		Article b=new Article();
		b.setPicUrl("url");
		b.setDescription("des");
		b.setTitle("title");
		b.setUrl("url");
		articles.add(b);
		outputMsg.setArticles(articles);
		outputMsg.setArticleCount(2);
		System.out.println("xml转换：/n" + xs.toXML(outputMsg));
	}
	public static void main(String[] args) {
		 Integer f1 = 100, f2 = 100, f3 = 150, f4 = 150;  
         
	        System.out.println(f1 == f2);  
	        System.out.println(f3 == f4);  
	}
}
