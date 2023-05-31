package com.yzq.dao;

import com.yzq.pojo.GoodsCar;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsCarMapper {
  int deleteByPrimaryKey(Integer id);

  int insert(GoodsCar record);

  int insertSelective(GoodsCar record);

  GoodsCar selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(GoodsCar record);

  int updateByPrimaryKey(GoodsCar record);

  List<GoodsCar> selectByUid(int uid);

  GoodsCar selectByUidAndSid(@Param("uid") Integer uid, @Param("sid") Integer sid);

}