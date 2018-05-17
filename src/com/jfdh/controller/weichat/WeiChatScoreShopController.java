package com.jfdh.controller.weichat;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jfdh.httpmodel.DataRequest;
import com.jfdh.httpmodel.PagingData;
import com.jfdh.httpmodel.WeiChatScoreRecord;
import com.jfdh.model.ScoreExchangeRecord;
import com.jfdh.model.ScorePool;
import com.jfdh.model.ScoreShopItem;
import com.jfdh.model.User;
import com.jfdh.service.ScoreShopService;

@Controller
@RequestMapping("/weichatScoreShop")
public class WeiChatScoreShopController {
	@Autowired
	private ScoreShopService scoreShopService;
	
	@RequestMapping(value = "/listAllScoreItems")
	public String list(
			@RequestParam(defaultValue = "1", value = "page") String page,HttpSession session,
			@RequestParam(defaultValue = "6", value = "rows") String rows, Model model) {
		DataRequest request = new DataRequest();
		request.setPage(StringUtils.isEmpty(page) ? 1 : Integer.valueOf(page));
		request.setRows(StringUtils.isEmpty(rows) ? 6 : Integer.valueOf(rows));

		Page<ScoreShopItem> pagingData = scoreShopService
				.findAllScoreShop4weichat(request);
		if(pagingData.hasNext()){
			model.addAttribute("nextParam","page="+(request.getPage()+1)+"&rows="+request.getRows());
		}
		model.addAttribute("pagingData", pagingData);
		return "/weichat/list_integral_goods";
	}
	
	
	@RequestMapping(value = "/viewScoreItem/{id}")
	public String viewScoreItem(@PathVariable String id, Model model,HttpSession session) {
		ScoreShopItem item = scoreShopService.findById(id);
		model.addAttribute("item", item);
		return "/weichat/exchange_good";
	}
	
	@RequestMapping(value = "/doExchangeItem")
	public String doExchangeItem(HttpSession session,String id,Integer number,RedirectAttributes redirectAttributes) {
		String msg=scoreShopService.doExchangeItem(getUserId(session),id,number);
		redirectAttributes.addFlashAttribute("msg", msg);
		if("success".equals(msg)){
			return "redirect:/weichatScoreShop/listAllScoreItems";
		}else{
			return "redirect:/weichatScoreShop/viewScoreItem/"+id;
		}
	}
	
	
	
	@RequestMapping(value = "/findAllMyScoreRecords")
	public String findAllMyScoreRecords(HttpSession session,
			@RequestParam(defaultValue = "1", value = "page") String page,
			@RequestParam(defaultValue = "6", value = "rows") String rows, Model model) {
		DataRequest request = new DataRequest();
		request.setPage(StringUtils.isEmpty(page) ? 1 : Integer.valueOf(page));
		request.setRows(StringUtils.isEmpty(rows) ? 12 : Integer.valueOf(rows));
		PagingData<WeiChatScoreRecord> pagingData = scoreShopService
				.findAllBalanceRecord4weichat(getUserId(session),request);
		if(pagingData.isHasNext()){
			model.addAttribute("nextParam","page="+(request.getPage()+1)+"&rows="+request.getRows());
		}
		model.addAttribute("pagingData", pagingData);
		return "/weichat/my_integral_record";
	}
	
	@RequestMapping(value = "/findAllMyScoreRecords2")
	public String findAllMyScoreRecords2(Model model) {
		List<ScorePool> pagingData = scoreShopService
				.findAllTOP10Score();
		
		model.addAttribute("pagingData", pagingData);
		return "/weichat/integral_record";
	}
	
	
	@RequestMapping(value = "/findAllMyCoupons")
	public String findAllMyfindAllMyCoupons(HttpSession session,
			@RequestParam(defaultValue = "1", value = "page") String page,
			@RequestParam(defaultValue = "6", value = "rows") String rows, Model model) {
		DataRequest request = new DataRequest();
		request.setPage(StringUtils.isEmpty(page) ? 1 : Integer.valueOf(page));
		request.setRows(StringUtils.isEmpty(rows) ? 12 : Integer.valueOf(rows));
		Page<ScoreExchangeRecord> pagingData = scoreShopService
				.findAllCoupons4weichat(getUserId(session),request);
		if(pagingData.hasNext()){
			model.addAttribute("nextParam","page="+(request.getPage()+1)+"&rows="+request.getRows());
		}
		model.addAttribute("pagingData", pagingData);
		return "/weichat/my_integral_coupons";
	}
	
	
	@RequestMapping(value = "/myRecord/{id}")
	public String myRecord(HttpSession session,@PathVariable String id, Model model) {
		WeiChatScoreRecord record = scoreShopService.findWeiChatScoreRecordById(id,getUserId(session));
		model.addAttribute("record", record);
		return "/weichat/my_score_integral_history";
	}
	
	
	@RequestMapping(value = "/myCoupon/{id}")
	public String myCoupon(@PathVariable String id, Model model) {
		ScoreExchangeRecord record = scoreShopService.findWeiChatScoreRecordById(id);
		model.addAttribute("record", record);
		return "/weichat/my_score_integral_coupon";
	}
	
	private User getUser(HttpSession session){
		return (User) session.getAttribute("USER_FOR_WEICHAT");
	}
	
	private String getUserId(HttpSession session){
		return getUser(session).getId();
	}
	
}
                  