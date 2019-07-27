package com.hmh.search.mapper;

import com.hmh.common.pojo.SearchItem;

import java.util.List;

public interface SearchItemMapper {
	//需要导入到索引库的所有数据
	List<SearchItem> getItemList();

	//查询商品详情
	SearchItem getItemById(long itemId);
}
