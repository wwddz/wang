package com.yzq.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderSellerBean implements Serializable {
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
   * 买家
   */
  private String shopUserName;
  /**
   * 买家联系方式
   */
  private String buyerPhone;
  /**
   * 数量
   */
  private Integer num;
  /**
   * 状态
   */
  private Integer status;
  /*  *//**
   * 买家地址
   *//*
  private String readdress;*/
}