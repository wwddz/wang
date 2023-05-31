package com.yzq.dao;

import com.yzq.pojo.UserPassword;

public interface UserPasswordMapper {
  int deleteByPrimaryKey(Integer id);

  int deleteByUid(Integer uid);

  int insert(UserPassword record);

  int insertSelective(UserPassword record);

  UserPassword selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(UserPassword record);

  int updateByPrimaryKey(UserPassword record);

  UserPassword selectByUid(Integer uid);
}