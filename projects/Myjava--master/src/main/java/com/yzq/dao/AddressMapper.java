package com.yzq.dao;

import com.yzq.pojo.Address;

import java.util.List;

public interface AddressMapper {

  int deleteByPrimaryKey(Integer id);

  int insert(Address record);

  int insertSelective(Address record);

  List<Address> selectAll();

  Address selectByPrimaryKey(Integer id);

  List<Address> selectByUid(Integer uid);

  int updateByPrimaryKeySelective(Address record);

  int updateByPrimaryKey(Address record);
}
