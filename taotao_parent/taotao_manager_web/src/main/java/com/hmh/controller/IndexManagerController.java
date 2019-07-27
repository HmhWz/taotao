package com.hmh.controller;

import com.hmh.common.pojo.TaotaoResult;
import com.hmh.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 索引库维护Controller
 * <p>Title: IndexManagerController</p>
 * <p>Desc: </p>
 * <p>Company: com.hmh</p>
 *
 * @version 1.0
 */
@Controller
public class IndexManagerController {
	@Autowired
	private SearchItemService searchItemService;

	@RequestMapping(value = "/index/import", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult indexImport() throws Exception {
		TaotaoResult taotaoResult = searchItemService.importAllItemToIndex();
		return taotaoResult;
	}
}
