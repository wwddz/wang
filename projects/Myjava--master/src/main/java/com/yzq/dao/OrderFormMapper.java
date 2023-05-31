package com.yzq.dao;

import com.yzq.bean.OrderSellerBean;
import com.yzq.pojo.OrderForm;

import java.util.List;

public interface OrderFormMapper {
  int deleteByPrimaryKey(Integer id);

  int insert(OrderForm record);

  int insertSelective(OrderForm record);

  OrderForm selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(OrderForm record);

  int updateByPrimaryKey(OrderForm record);

  int getCounts(int uid);

  List<OrderForm> selectAll();

  List<OrderForm> selectByUid(int uid, int start);

  List<OrderForm> selectByUidAll(int uid);

  List<OrderSellerBean> selectBySellIdAll(int sellerId);
}