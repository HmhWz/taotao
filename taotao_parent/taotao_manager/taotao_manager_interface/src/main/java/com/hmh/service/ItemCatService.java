package com.hmh.service;

import com.hmh.common.pojo.EasyUITreeNode;

import java.util.List;

public interface ItemCatService {
	List<EasyUITreeNode> getItemCatList(long parentId);
}
