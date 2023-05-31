package com.yzq.service.Impl;

import com.yzq.bean.OrderBean;
import com.yzq.bean.OrderSellerBean;
import com.yzq.dao.OrderFormMapper;
import com.yzq.dao.ShopInformationMapper;
import com.yzq.dao.UserInformationMapper;
import com.yzq.pojo.OrderForm;
import com.yzq.pojo.ShopInformation;
import com.yzq.pojo.UserInformation;
import com.yzq.service.OrderFormService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrderFormServiceImpl implements OrderFormService {
  @Resource
  private OrderFormMapper orderFormMapper;
  @Resource
  private ShopInformationMapper shopInformationMapper;
  @Resource
  private UserInformationMapper userInformationMapper;

  @Override
  public int deleteByPrimaryKey(Integer id) {
    return 0;
  }

  @Override
  public int insert(OrderForm record) {
    return orderFormMapper.insert(record);
  }

  @Override
  public int insertSelective(OrderForm record) {
    return orderFormMapper.insertSelective(record);
  }

  @Override
  public OrderForm selectByPrimaryKey(Integer id) {
    return orderFormMapper.selectByPrimaryKey(id);
  }

  @Override
  public int updateByPrimaryKeySelective(OrderForm record) {
    return orderFormMapper.updateByPrimaryKeySelective(record);
  }

  @Override
  public int updateByPrimaryKey(OrderForm record) {
    return orderFormMapper.updateByPrimaryKey(record);
  }

  @Override
  public int getCounts(int uid) {
    return orderFormMapper.getCounts(uid);
  }

  @Override
  public List<OrderForm> selectByUid(int uid, int start) {
    return orderFormMapper.selectByUid(uid, start);
  }

  @Override
  public List<OrderForm> selectAll() {
    return orderFormMapper.selectAll();
  }

  @Override
  public List<OrderBean> selectByUidAll(int uid) {
    List<OrderForm> orderFormList = orderFormMapper.selectByUidAll(uid);
    List<OrderBean> orderBeanList = new ArrayList<>();
    if (orderFormList.size() > 0 && orderFormList != null) {
      for (int i = 0; i < orderFormList.size(); i++) {
        OrderBean orderBean = new OrderBean();
        orderBean.setId(orderFormList.get(i).getId());
        orderBean.setNum(orderFormList.get(i).getQuantity());
        orderBean.setStatus(orderFormList.get(i).getStatus());
        //System.out.println(orderFormList.get(i).getShopId());
        //查询商品名称
        ShopInformation shopInformation = shopInformationMapper.selectByPrimaryKey(orderFormList.get(i).getShopId());
        //System.out.println(shopInformation.getImage());
        if (shopInformation != null) {
          orderBean.setShopName(shopInformation.getName());
          orderBean.setShopImg(shopInformation.getImage());
        }
        //查询卖家
        UserInformation userInformation = userInformationMapper.selectByPrimaryKey(orderFormList.get(i).getUid());
        orderBean.setShopUserName(userInformation.getUsername());
        orderBean.setSellerPhone(userInformation.getPhone());
        orderBeanList.add(orderBean);
      }
    }
    return orderBeanList;
  }

  /**
   * 卖出商品
   *
   * @param sellerId
   * @return
   */
  @Override
  public List<OrderSellerBean> selectBySellAll(int sellerId) {
    List<OrderSellerBean> orderFormList = orderFormMapper.selectBySellIdAll(sellerId);
 /*   Iterator it = orderFormList.iterator();
    while (it.hasNext())
      System.out.println(it.next());*/
    return orderFormList;
  }
}
