package com.jfdh.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;

import com.jfdh.httpmodel.DataRequest;
import com.jfdh.httpmodel.DataResponse;
import com.jfdh.httpmodel.HttpSearch;
import com.jfdh.httpmodel.PagingData;
import com.jfdh.httpmodel.ScoreTransfer;
import com.jfdh.httpmodel.WeiChatScoreRecord;
import com.jfdh.model.ScoreBalanceRecord;
import com.jfdh.model.ScoreExchangeRecord;
import com.jfdh.model.ScorePool;
import com.jfdh.model.ScoreShopItem;
import com.jfdh.model.ShopItemType;
import com.jfdh.model.User;

public interface ScoreShopService {

	public DataResponse search(DataRequest request);

	public ScoreShopItem save(ScoreShopItem ScoreShopItem);

	public ScoreShopItem findById(String id);

	public boolean update(ScoreShopItem ScoreShopItem);

	public void delete(String ids);

	public List<ShopItemType> findAllConstantItemType();

	public BigDecimal findRatioById(String id);

	public ScoreShopItem addScoreShopItem(ScoreShopItem scoreShopItem);
	
	public ScorePool findAdminScorePool();
	public List<ScorePool> findTop5ByScorePoolGovOrgParent();
	public List<ScorePool> findTop5ByScorePoolGovOrg();
	public List<ScorePool> findTop5ByUsers();

	public boolean transfer(ScoreTransfer scoreTransfer);

	public Object doExchangeValid(String key,User user);

	public List<ScoreExchangeRecord> getAllExchangeRecord();

	public DataResponse searchScoreExchangeList(DataRequest request);

	public List<ScoreExchangeRecord> findAllScoreExchangeList();

	public Page<ScoreShopItem> findAllScoreShop4weichat(DataRequest request);

	public PagingData<WeiChatScoreRecord> findAllBalanceRecord4weichat(String userId,
			DataRequest request);

	public String doExchangeItem(String userId, String id, Integer number);

	public Page<ScoreBalanceRecord> findAllMyScoreRecord(String userId,
			DataRequest request);

	public WeiChatScoreRecord findWeiChatScoreRecordById(String id,
			String userId);

	public Page<ScoreExchangeRecord> findAllCoupons4weichat(
			String userId, DataRequest request);

	public ScoreExchangeRecord findWeiChatScoreRecordById(String id);

	public String application(String code, String id);

	public ScorePool findAdminScorePoolById(String id);

	public List<ScorePool> findTop5ByUsersById(String id);

	public DataResponse search(DataRequest request, HttpSearch httpSearch);

	public DataResponse searchScoreExchangeList(DataRequest request,
			HttpSearch httpSearch);

	public void doBusiness();

	public List<ScorePool> findAllTOP10Score();
}
