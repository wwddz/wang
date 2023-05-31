package com.yzq.service.Impl;

import com.yzq.dao.UserWantMapper;
import com.yzq.pojo.UserWant;
import com.yzq.service.UserWantService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class UserWantServiceImpl implements UserWantService {
  @Resource
  private UserWantMapper userWantMapper;

  @Override
  public int deleteByPrimaryKey(Integer id) {
    return this.userWantMapper.deleteByPrimaryKey(id);
  }

  @Override
  public int insert(UserWant record) {
    return userWantMapper.insert(record);
  }

  @Override
  public int insertSelective(UserWant record) {
    return userWantMapper.insertSelective(record);
  }

  @Override
  public UserWant selectByPrimaryKey(Integer id) {
    return userWantMapper.selectByPrimaryKey(id);
  }

  @Override
  public int updateByPrimaryKeySelective(UserWant record) {
    return userWantMapper.updateByPrimaryKeySelective(record);
  }

  @Override
  public int updateByPrimaryKey(UserWant record) {
    return userWantMapper.updateByPrimaryKey(record);
  }

  @Override
  public int getCounts(int uid) {
    return userWantMapper.getCounts(uid);
  }

  @Override
  public List<UserWant> selectByUid(int uid, int start) {
    return userWantMapper.selectByUid(uid, start);
  }

  @Override
  public List<UserWant> selectMineByUid(int id) {
    return userWantMapper.selectMineByUid(id);
  }

  @Override
  public List<UserWant> selectAll() {
    return userWantMapper.selectAll();
  }
}
