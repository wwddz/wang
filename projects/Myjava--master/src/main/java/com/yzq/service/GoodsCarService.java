package com.yzq.service;

import com.yzq.pojo.GoodsCar;

import java.util.List;


public interface GoodsCarService {
  int deleteByPrimaryKey(Integer id);

  int insert(GoodsCar record);

  int insertSelective(GoodsCar record);

  GoodsCar selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(GoodsCar record);

  int updateByPrimaryKey(GoodsCar record);

  List<GoodsCar> selectByUid(int uid);
  
  GoodsCar selectByUidAndSid(Integer uid, Integer sid);

}
