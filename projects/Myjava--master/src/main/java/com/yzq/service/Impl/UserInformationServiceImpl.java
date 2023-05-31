package com.yzq.service.Impl;

import com.yzq.dao.UserInformationMapper;
import com.yzq.pojo.UserInformation;
import com.yzq.service.UserInformationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("userInformationService")
public class UserInformationServiceImpl implements UserInformationService {
  @Resource
  private UserInformationMapper userInformationMapper;

  @Override
  public int deleteByPrimaryKey(Integer id) {
    return this.userInformationMapper.deleteByPrimaryKey(id);
  }

  @Override
  public int insert(UserInformation record) {
    return this.userInformationMapper.insert(record);
  }

  @Override
  public int insertSelective(UserInformation record) {
    return this.userInformationMapper.insertSelective(record);
  }

  @Override
  public UserInformation selectByPrimaryKey(Integer id) {
    return this.userInformationMapper.selectByPrimaryKey(id);
  }

  @Override
  public int updateByPrimaryKeySelective(UserInformation record) {
    return this.userInformationMapper.updateByPrimaryKeySelective(record);
  }

  @Override
  public int updateByPrimaryKey(UserInformation record) {
    return this.userInformationMapper.updateByPrimaryKey(record);
  }

  @Override
  public int selectIdByPhone(String phone) {
    try {
      return this.userInformationMapper.selectIdByPhone(phone);
    } catch (Exception e) {
      return 0;
    }
  }

  @Override
  public List<UserInformation> getAllForeach(List<Integer> list) {
    return this.userInformationMapper.getAllForeach(list);
  }

  @Override
  public List<UserInformation> selectAll() {
    return this.userInformationMapper.selectAll();
  }
}
