package com.hmh.mapper;

import com.hmh.pojo.TbUser;
import com.hmh.pojo.TbUserExample;

import java.util.List;

public interface TbUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TbUser record);

    int insertSelective(TbUser record);

    TbUser selectByPrimaryKey(Long id);

    List<TbUser> selectByExample(TbUserExample example);

    int updateByPrimaryKeySelective(TbUser record);

    int updateByPrimaryKey(TbUser record);
}