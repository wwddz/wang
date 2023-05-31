package com.yzq.dao;

import com.yzq.pojo.UserState;

public interface UserStateMapper {
  int deleteByPrimaryKey(Integer id);

  int insert(UserState record);

  int insertSelective(UserState record);

  UserState selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(UserState record);

  int updateByPrimaryKey(UserState record);

  UserState selectByUid(int uid);

}