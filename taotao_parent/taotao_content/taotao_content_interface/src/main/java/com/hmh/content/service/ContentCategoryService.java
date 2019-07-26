package com.hmh.content.service;

import com.hmh.common.pojo.EasyUITreeNode;
import com.hmh.common.pojo.TaotaoResult;

import java.util.List;

public interface ContentCategoryService {
	List<EasyUITreeNode> getContentCatList(long parentId);

	TaotaoResult addContentCategory(long parentId, String name);

	//删除内容分类，注意参数名称要与content-category.jsp页面指定的参数名称一致
	TaotaoResult deleteContentCategory(long id);

	public TaotaoResult updateContentCategory(long id, String name);



}

