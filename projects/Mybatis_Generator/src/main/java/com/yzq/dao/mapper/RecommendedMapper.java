package com.yzq.dao.mapper;

import com.yzq.dao.pojo.Recommended;
import com.yzq.dao.pojo.RecommendedExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RecommendedMapper {
    long countByExample(RecommendedExample example);

    int deleteByExample(RecommendedExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Recommended record);

    int insertSelective(Recommended record);

    List<Recommended> selectByExample(RecommendedExample example);

    Recommended selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Recommended record, @Param("example") RecommendedExample example);

    int updateByExample(@Param("record") Recommended record, @Param("example") RecommendedExample example);

    int updateByPrimaryKeySelective(Recommended record);

    int updateByPrimaryKey(Recommended record);
}