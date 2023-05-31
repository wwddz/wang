package com.yzq.service.Impl;


import com.yzq.dao.AddressMapper;
import com.yzq.pojo.Address;
import com.yzq.service.AddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

  @Resource
  private AddressMapper addressMapper;

  @Override
  public int deleteByPrimaryKey(Integer id) {
    return this.addressMapper.deleteByPrimaryKey(id);
  }

  @Override
  public int insert(Address record) {
    return this.addressMapper.insert(record);
  }

  @Override
  public int insertSelective(Address record) {
    return this.addressMapper.insertSelective(record);
  }

  @Override
  public List<Address> selectAll() {
    return this.addressMapper.selectAll();
  }

  @Override
  public Address selectByPrimaryKey(Integer id) {
    return this.addressMapper.selectByPrimaryKey(id);
  }

  @Override
  public List<Address> selectByUid(Integer uid) {
    return this.addressMapper.selectByUid(uid);
  }

  @Override
  public int updateByPrimaryKeySelective(Address record) {
    return this.addressMapper.updateByPrimaryKeySelective(record);
  }

  @Override
  public int updateByPrimaryKey(Address record) {
    return this.addressMapper.updateByPrimaryKey(record);
  }
}
