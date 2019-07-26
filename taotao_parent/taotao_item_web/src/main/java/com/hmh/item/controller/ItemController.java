package com.hmh.item.controller;

import com.hmh.item.pojo.Item;
import com.hmh.pojo.TbItem;
import com.hmh.pojo.TbItemDesc;
import com.hmh.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

	@RequestMapping("/item/{itemId}")
	public String showItemInfo(@PathVariable("itemId") Long itemId, Model model){
		TbItem tbItem = itemService.getItemById(itemId);
		Item item = new Item(tbItem);
		TbItemDesc tbItemDesc = itemService.getItemDescById(itemId);

		model.addAttribute("item", item);
		model.addAttribute("itemDesc", tbItemDesc);

		return "item";
	}

}
