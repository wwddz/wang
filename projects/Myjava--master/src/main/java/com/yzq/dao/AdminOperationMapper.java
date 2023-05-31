package com.yzq.dao;

import com.yzq.pojo.AdminOperation;

public interface AdminOperationMapper {
  int deleteByPrimaryKey(Integer id);

  int insert(AdminOperation record);

  int insertSelective(AdminOperation record);

  AdminOperation selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(AdminOperation record);

  int updateByPrimaryKey(AdminOperation record);
}