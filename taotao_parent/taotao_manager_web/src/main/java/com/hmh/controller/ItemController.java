package com.hmh.controller;

import com.hmh.common.pojo.EasyUIDataGridResult;
import com.hmh.common.pojo.TaotaoResult;
import com.hmh.pojo.TbItem;
import com.hmh.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class ItemController {

	@Autowired
	public ItemService itemService;

	@RequestMapping(value = "/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		return itemService.getItemById(itemId);
	}

	//查询商品页面
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page, Integer rows){
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}

	// 添加商品
	@RequestMapping(value = "/item/save", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult addItem(TbItem item, String desc){
		try{
			TaotaoResult result = itemService.addItem(item, desc);
			return result;
		}catch (Exception e){
			e.printStackTrace();
			return TaotaoResult.build(500,"添加商品失败");
		}
	}
}
