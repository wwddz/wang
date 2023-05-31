package com.yzq.service;

import com.yzq.pojo.ShopPicture;

import java.util.List;


public interface ShopPictureService {
  int deleteByPrimaryKey(Integer id);

  int insert(ShopPicture record);

  int insertSelective(ShopPicture record);

  ShopPicture selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(ShopPicture record);

  int updateByPrimaryKey(ShopPicture record);

  ShopPicture selectBySidOnlyOne(Integer sid);

  List<ShopPicture> selectBySid(Integer sid);
}
