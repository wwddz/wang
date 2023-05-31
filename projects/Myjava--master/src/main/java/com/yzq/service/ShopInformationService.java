package com.yzq.service;

import com.yzq.pojo.ShopInformation;

import java.util.List;
import java.util.Map;


public interface ShopInformationService {
  int deleteByPrimaryKey(Integer id);

  int insert(ShopInformation record);

  int insertSelective(ShopInformation record);

  ShopInformation selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(ShopInformation record);

  int updateByPrimaryKey(ShopInformation record);

  List<ShopInformation> selectTen(Map map);

  List<ShopInformation> selectOffShelf(int uid, int start);

  int getCountsOffShelf(int uid);

  int getCounts();

  int selectIdByImage(String image);

  List<ShopInformation> selectByName(String name);

  List<ShopInformation> selectBySort(int sort);

  List<ShopInformation> selectUserReleaseByUid(int uid);

  List<ShopInformation> selectAll();
}
