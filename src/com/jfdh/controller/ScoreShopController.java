package com.jfdh.controller;

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jfdh.excel.ScoreExchangeRecordExcel;
import com.jfdh.httpmodel.BackendUserDtail;
import com.jfdh.httpmodel.DataRequest;
import com.jfdh.httpmodel.DataResponse;
import com.jfdh.httpmodel.HttpSearch;
import com.jfdh.httpmodel.Json;
import com.jfdh.httpmodel.ScoreTransfer;
import com.jfdh.model.GovOrg;
import com.jfdh.model.Org;
import com.jfdh.model.ScoreExchangeRecord;
import com.jfdh.model.ScorePool;
import com.jfdh.model.ScoreShopItem;
import com.jfdh.model.ShopItemType;
import com.jfdh.model.User;
import com.jfdh.repository.OrgRepository;
import com.jfdh.repository.UserRepository;
import com.jfdh.service.ScoreShopService;
import com.jfdh.service.UserService;
import com.jfdh.util.JsoupService;
import com.jfdh.util.StringUtil;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.model.JSONValue;
import com.sdicons.json.parser.JSONParser;

@Controller
@RequestMapping("/score/shop")
public class ScoreShopController {
	
	@Autowired
	private JsoupService jsoupService;
	@Autowired
	private ScoreShopService scoreShopService;

	@Autowired
	private UserService userService;

	@Autowired
	private OrgRepository orgRepository;
	
	@Autowired
	private UserRepository userRepository;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list() {
		return "shop_item.list";
	}

	@RequestMapping(value = "/findScoreTop5", method = RequestMethod.GET)
	public String findScoreTop5(Model model) {
		
		@SuppressWarnings("unchecked")
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext()
			    .getAuthentication().getAuthorities();;
		for(GrantedAuthority authority:authorities){
			if(authority.getAuthority().equals("ROLE_Role_sys_admin")){
				ScorePool adminScorePool = scoreShopService.findAdminScorePool();
				List<ScorePool> govOrgs = scoreShopService
						.findTop5ByScorePoolGovOrgParent();
				List<ScorePool> communities = scoreShopService
						.findTop5ByScorePoolGovOrg();
				List<ScorePool> users = scoreShopService.findTop5ByUsers();
				model.addAttribute("adminScorePool", adminScorePool);
				model.addAttribute("govOrgs", govOrgs);
				model.addAttribute("communities", communities);
				model.addAttribute("users", users);
				return "score.admin.list";
			}
		}
		BackendUserDtail userDetails = (BackendUserDtail) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
		ScorePool adminScorePool = scoreShopService.findAdminScorePoolById(userDetails.getId());
		List<ScorePool> users = scoreShopService.findTop5ByUsersById(userDetails.getId());
		model.addAttribute("adminScorePool", adminScorePool);
		model.addAttribute("users", users);
		return "score.user.list";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {
		ScoreShopItem scoreShopItem = new ScoreShopItem();
		List<ShopItemType> shopItemTypes = scoreShopService
				.findAllConstantItemType();
		Iterable<Org> orgs = orgRepository.findAll();
		model.addAttribute("orgs", orgs);
		model.addAttribute("scoreShopItem", scoreShopItem);
		model.addAttribute("shopItemTypes", shopItemTypes);
		return "shop_item.create";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(String id, Model model) {
		ScoreShopItem scoreShopItem = scoreShopService.findById(id);
		List<ShopItemType> shopItemTypes = scoreShopService
				.findAllConstantItemType();
		Iterable<Org> orgs = orgRepository.findAll();
		model.addAttribute("orgs", orgs);
		model.addAttribute("scoreShopItem", scoreShopItem);
		model.addAttribute("shopItemTypes", shopItemTypes);
		return "shop_item.edit";
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(String id, Model model) {
		ScoreShopItem scoreShopItem = scoreShopService.findById(id);
		List<ShopItemType> shopItemTypes = scoreShopService
				.findAllConstantItemType();
		Iterable<Org> orgs = orgRepository.findAll();
		model.addAttribute("orgs", orgs);
		model.addAttribute("scoreShopItem", scoreShopItem);
		model.addAttribute("shopItemTypes", shopItemTypes);
		return "shop_item.view";
	}


	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@Valid ScoreShopItem scoreShopItem, BindingResult result,
			Model model,HttpServletRequest request) {
		if (null == scoreShopItem.getOrg()
				|| StringUtils.isEmpty(scoreShopItem.getOrg().getId())) {
			result.addError(new FieldError("scoreShopItem", "org.id",
					"请选择礼品来源！！"));
		}
		if (result.hasErrors()) {
			List<ShopItemType> shopItemTypes = scoreShopService
					.findAllConstantItemType();
			Iterable<Org> orgs = orgRepository.findAll();
			model.addAttribute("orgs", orgs);
			model.addAttribute("shopItemTypes", shopItemTypes);
			return "shop_item.create";
		}
		if(StringUtils.isNotEmpty(scoreShopItem.getDesc())){
			scoreShopItem.setDesc(jsoupService.parse(scoreShopItem.getDesc(), request.getContextPath()));
		}
		scoreShopService.addScoreShopItem(scoreShopItem);
		return "redirect:/score/shop/list";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@Valid ScoreShopItem scoreShopItem,
			BindingResult result, Model model,HttpServletRequest request) {
		if (null == scoreShopItem.getOrg()
				|| StringUtils.isEmpty(scoreShopItem.getOrg().getId())) {
			result.addError(new FieldError("scoreShopItem", "org.id",
					"请选择礼品来源！！"));
		}
		if (result.hasErrors()) {
			List<ShopItemType> shopItemTypes = scoreShopService
					.findAllConstantItemType();
			Iterable<Org> orgs = orgRepository.findAll();
			model.addAttribute("orgs", orgs);
			model.addAttribute("shopItemTypes", shopItemTypes);
			return "shop_item.edit";
		}
		if(StringUtils.isNotEmpty(scoreShopItem.getDesc())){
			scoreShopItem.setDesc(jsoupService.parse(scoreShopItem.getDesc(), request.getContextPath()));
		}
		if(!scoreShopService.update(scoreShopItem)){
			result.addError(new FieldError("scoreShopItem", "num",
					"前台有人兑换过，您输入的数量太小！！"));
			List<ShopItemType> shopItemTypes = scoreShopService
					.findAllConstantItemType();
			Iterable<Org> orgs = orgRepository.findAll();
			model.addAttribute("orgs", orgs);
			model.addAttribute("shopItemTypes", shopItemTypes);
			return "shop_item.edit";
		}
		return "redirect:/score/shop/list";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public Json delete(@RequestParam(value = "ids") String ids) {
		Json j = new Json();
		try {
			scoreShopService.delete(ids);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			j.setSuccess(false);
		}
		return j;
	}

	@RequestMapping(value = "/getRatio", method = RequestMethod.GET)
	@ResponseBody
	public BigDecimal getRatio(String id) {
		BigDecimal ratio = scoreShopService.findRatioById(id);
		return ratio;
	}

	@RequestMapping(value = "/listData")
	@ResponseBody
	public DataResponse list(
			@RequestParam(defaultValue = "1", value = "page") String page,
			@RequestParam(defaultValue = "20", value = "rows") String rows,
			@RequestParam("sidx") String sidx,
			@RequestParam("sord") String sord,
			@RequestParam("_search") boolean search,
			@RequestParam(required = false, value = "searchField") String searchField,
			@RequestParam(required = false, value = "searchOper") String searchOper,
			@RequestParam(required = false, value = "searchString") String searchString,
			@RequestParam(required = false, value = "filters") String filters) {
		try {
			HttpSearch httpSearch=null;
        	if(StringUtil.isNotNull(filters)){
				 filters = new String(filters.getBytes("iso8859-1"),"UTF-8");
				 JSONParser parser1 = new JSONParser(new StringReader(filters)); 
				 JSONValue jsonValue1 = parser1.nextValue();
				 httpSearch=(com.jfdh.httpmodel.HttpSearch) JSONMapper.toJava(jsonValue1, HttpSearch.class);
			 }
			DataRequest request = new DataRequest();
			request.setPage(StringUtils.isEmpty(page) ? 1 : Integer
					.valueOf(page));
			request.setRows(StringUtils.isEmpty(rows) ? 20 : Integer
					.valueOf(rows));
			request.setSidx(sidx);
			request.setSord(sord);
			request.setSearch(search);
			return scoreShopService.search(request,httpSearch);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/transfer", method = RequestMethod.GET)
	public String transfer(Model model) {
		ScoreTransfer scoreTransfer = new ScoreTransfer();
		List<GovOrg> govOrgs = userService.findAllGovOrgsAndChildren();
		model.addAttribute("govOrgs", govOrgs);
		model.addAttribute("scoreTransfer", scoreTransfer);
		return "score.transfer";
	}

	@RequestMapping(value = "/doTransfer", method = RequestMethod.POST)
	public String doTransfer(@Valid ScoreTransfer scoreTransfer,
			BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		if (null == scoreTransfer.getOrgIds()
				|| scoreTransfer.getOrgIds().length == 0) {
			result.addError(new FieldError("scoreTransfer", "orgIds",
					"请至少选择一个街道（社工委）或者社区！！"));
		}
		if (result.hasErrors()) {
			List<GovOrg> govOrgs = userService.findAllGovOrgsAndChildren();
			model.addAttribute("govOrgs", govOrgs);
			return "score.transfer";
		}
		if (scoreShopService.transfer(scoreTransfer)) {
			List<GovOrg> govOrgs = userService.findAllGovOrgsAndChildren();
			redirectAttributes.addFlashAttribute("govOrgs", govOrgs);
			redirectAttributes.addFlashAttribute("msg", "划拨成功！！");
			return "redirect:/score/shop/transfer";
		} else {
			result.addError(new FieldError("scoreTransfer", "score", "积分不足！！"));
			List<GovOrg> govOrgs = userService.findAllGovOrgsAndChildren();
			model.addAttribute("govOrgs", govOrgs);
			return "score.transfer";
		}
	}

	@RequestMapping(value = "/exchange/list", method = RequestMethod.GET)
	public String exchangeList() {
		return "exchange.list";
	}

	@RequestMapping(value = "/exchange/valid", method = RequestMethod.POST)
	public String exchangeValid(String key,
			RedirectAttributes redirectAttributes) {
		if (StringUtils.isEmpty(key)) {
			redirectAttributes.addFlashAttribute("msg", "验证码不能为空！！");
		} else {
			BackendUserDtail userDetails = (BackendUserDtail) SecurityContextHolder.getContext()
				    .getAuthentication()
				    .getPrincipal();
			User user=userRepository.findOne(userDetails.getId());
			Object o=scoreShopService.doExchangeValid(key,user);
			if(o instanceof String){
				redirectAttributes.addFlashAttribute("msg",o);
			}else{
				redirectAttributes.addFlashAttribute("msg","兑换成功！！");
				redirectAttributes.addFlashAttribute("record",o);
			}
		}
		return "redirect:/score/shop/exchange/list";
	}

	@RequestMapping(value = "/score_exchange/list", method = RequestMethod.GET)
	public String scoreExchangeList() {
		return "score.exchange.list";
	}

	@RequestMapping(value = "/score_exchange/listData")
	@ResponseBody
	public DataResponse scoreExchangeList(
			@RequestParam(defaultValue = "1", value = "page") String page,
			@RequestParam(defaultValue = "20", value = "rows") String rows,
			@RequestParam("sidx") String sidx,
			@RequestParam("sord") String sord,
			@RequestParam("_search") boolean search,
			@RequestParam(required = false, value = "searchField") String searchField,
			@RequestParam(required = false, value = "searchOper") String searchOper,
			@RequestParam(required = false, value = "searchString") String searchString,
			@RequestParam(required = false, value = "filters") String filters) {
		try {
			
			HttpSearch httpSearch=null;
        	if(StringUtil.isNotNull(filters)){
				 filters = new String(filters.getBytes("iso8859-1"),"UTF-8");
				 JSONParser parser1 = new JSONParser(new StringReader(filters)); 
				 JSONValue jsonValue1 = parser1.nextValue();
				 httpSearch=(com.jfdh.httpmodel.HttpSearch) JSONMapper.toJava(jsonValue1, HttpSearch.class);
			 }
			DataRequest request = new DataRequest();
			request.setPage(StringUtils.isEmpty(page) ? 1 : Integer
					.valueOf(page));
			request.setRows(StringUtils.isEmpty(rows) ? 20 : Integer
					.valueOf(rows));
			request.setSidx(sidx);
			request.setSord(sord);
			request.setSearch(search);
			return scoreShopService.searchScoreExchangeList(request,httpSearch);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/scoreExchangeListExcel", method = RequestMethod.GET)
	public ModelAndView scoreExchangeListExcel() {
		ModelAndView mav = new ModelAndView(new ScoreExchangeRecordExcel());
		List<ScoreExchangeRecord> records = scoreShopService
				.findAllScoreExchangeList();
		mav.addObject("records", records);
		return mav;
	}

}
