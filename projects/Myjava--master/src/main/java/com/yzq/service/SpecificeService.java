package com.yzq.service;

import com.yzq.pojo.Specific;

import java.util.List;


public interface SpecificeService {
  int deleteByPrimaryKey(Integer id);

  int insert(Specific record);

  int insertSelective(Specific record);

  Specific selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(Specific record);

  int updateByPrimaryKey(Specific record);

  List<Specific> selectByCid(int cid);
}
