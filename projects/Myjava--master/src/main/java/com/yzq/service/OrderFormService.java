package com.yzq.service;

import com.yzq.bean.OrderBean;
import com.yzq.bean.OrderSellerBean;
import com.yzq.pojo.OrderForm;

import java.util.List;


public interface OrderFormService {

  int deleteByPrimaryKey(Integer id);

  int insert(OrderForm record);

  int insertSelective(OrderForm record);

  OrderForm selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(OrderForm record);

  int updateByPrimaryKey(OrderForm record);

  int getCounts(int uid);

  List<OrderForm> selectByUid(int uid, int start);

  List<OrderBean> selectByUidAll(int uid);

  List<OrderForm> selectAll();

  List<OrderSellerBean> selectBySellAll(int uid);
}
