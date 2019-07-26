package com.hmh.service;

import com.hmh.common.pojo.EasyUIDataGridResult;
import com.hmh.common.pojo.TaotaoResult;
import com.hmh.pojo.TbItem;
import com.hmh.pojo.TbItemDesc;

public interface ItemService {
	//	根据id取基本信息
	TbItem getItemById(long itemId);

	//	根据id取描述信息
	TbItemDesc getItemDescById(long itemId);

	EasyUIDataGridResult getItemList(int page, int rows);

	public TaotaoResult addItem(TbItem item, String desc) throws Exception;

}
