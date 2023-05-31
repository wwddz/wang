package com.yzq.service.Impl;

import com.yzq.dao.GoodsCarMapper;
import com.yzq.pojo.GoodsCar;
import com.yzq.service.GoodsCarService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class GoodsCarServiceImpl implements GoodsCarService {
  @Resource
  private GoodsCarMapper goodsCarMapper;

  @Override
  public int deleteByPrimaryKey(Integer id) {
    return this.goodsCarMapper.deleteByPrimaryKey(id);
  }

  @Override
  public int insert(GoodsCar record) {
    return goodsCarMapper.insert(record);
  }

  @Override
  public int insertSelective(GoodsCar record) {
    return goodsCarMapper.insertSelective(record);
  }

  @Override
  public GoodsCar selectByUidAndSid(Integer uid, Integer sid) {
    GoodsCar goodsCar = goodsCarMapper.selectByUidAndSid(uid, sid);
    return goodsCar;
  }

  @Override
  public GoodsCar selectByPrimaryKey(Integer id) {
    return goodsCarMapper.selectByPrimaryKey(id);
  }

  @Override
  public int updateByPrimaryKeySelective(GoodsCar record) {
    return goodsCarMapper.updateByPrimaryKeySelective(record);
  }

  @Override
  public int updateByPrimaryKey(GoodsCar record) {
    return goodsCarMapper.updateByPrimaryKey(record);
  }

  @Override
  public List<GoodsCar> selectByUid(int scid) {
    return goodsCarMapper.selectByUid(scid);
  }
}
