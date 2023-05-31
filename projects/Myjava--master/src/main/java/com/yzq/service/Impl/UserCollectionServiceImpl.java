package com.yzq.service.Impl;

import com.yzq.dao.UserCollectionMapper;
import com.yzq.pojo.UserCollection;
import com.yzq.service.UserCollectionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class UserCollectionServiceImpl implements UserCollectionService {
  @Resource
  private UserCollectionMapper userCollectionMapper;

  @Override
  public int deleteByPrimaryKey(Integer id) {
    return this.userCollectionMapper.deleteByPrimaryKey(id);
  }

  @Override
  public int insert(UserCollection record) {
    return userCollectionMapper.insert(record);
  }

  @Override
  public int insertSelective(UserCollection record) {
    return userCollectionMapper.insertSelective(record);
  }

  @Override
  public UserCollection selectByPrimaryKey(Integer id) {
    return userCollectionMapper.selectByPrimaryKey(id);
  }

  @Override
  public int updateByPrimaryKeySelective(UserCollection record) {
    return userCollectionMapper.updateByPrimaryKeySelective(record);
  }

  @Override
  public int updateByPrimaryKey(UserCollection record) {
    return userCollectionMapper.updateByPrimaryKey(record);
  }

  @Override
  public int getCounts(int uid) {
    return userCollectionMapper.getCounts(uid);
  }

  @Override
  public List<UserCollection> selectByUid(int uid, int start) {
    return userCollectionMapper.selectByUid(uid, start);
  }
}
