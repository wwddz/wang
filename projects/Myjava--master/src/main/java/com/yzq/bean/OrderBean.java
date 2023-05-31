package com.yzq.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderBean implements Serializable {
  /**
   * 订单id
   */
  private Integer id;
  /**
   * 商品名称
   */
  private String shopName;
  /**
   * 图片
   */
  private String shopImg;
  /**
   * 卖家
   */
  private String shopUserName;
  /**
   * 卖家联系方式
   */
  private String sellerPhone;

  /**
   * 数量
   */
  private Integer num;
  /**
   * 状态
   */
  private Integer status;

}