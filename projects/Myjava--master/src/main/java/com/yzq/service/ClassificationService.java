package com.yzq.service;

import com.yzq.pojo.Classification;

import java.util.List;


public interface ClassificationService {
  int deleteByPrimaryKey(Integer id);

  int insert(Classification record);

  int insertSelective(Classification record);

  Classification selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(Classification record);

  int updateByPrimaryKey(Classification record);

  List<Classification> selectByAid(int aid);
}
