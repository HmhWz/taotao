package com.hmh.search.mapper;

import com.hmh.common.pojo.SearchItem;

import java.util.List;

public interface ItemMapper {
	List<SearchItem> getItemList();
	SearchItem getItemById(long itemId);
}
