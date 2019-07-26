package com.hmh.mapper;

import com.hmh.pojo.TbItemParamItem;

public interface TbItemParamItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TbItemParamItem record);

    int insertSelective(TbItemParamItem record);

    TbItemParamItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbItemParamItem record);

    int updateByPrimaryKeyWithBLOBs(TbItemParamItem record);

    int updateByPrimaryKey(TbItemParamItem record);
}