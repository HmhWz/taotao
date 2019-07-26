package com.hmh.content.service;

import com.hmh.common.pojo.EasyUIDataGridResult;
import com.hmh.common.pojo.TaotaoResult;
import com.hmh.pojo.TbContent;

import java.util.List;

public interface ContentService {

	TaotaoResult addContent(TbContent content);

	public EasyUIDataGridResult getContentList(long categoryId, int page, int rows);

}
