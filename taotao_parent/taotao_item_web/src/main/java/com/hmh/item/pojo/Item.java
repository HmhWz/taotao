package com.hmh.item.pojo;

import com.hmh.pojo.TbItem;

public class Item extends TbItem {

	public Item() {
	}

	public Item(TbItem tbItem) {
		this.setBarcode(tbItem.getBarcode());
		this.setCid(tbItem.getCid());
		this.setCreated(tbItem.getCreated());
		this.setId(tbItem.getId());
		this.setImage(tbItem.getImage());
		this.setNum(tbItem.getNum());
		this.setPrice(tbItem.getPrice());
		this.setSellPoint(tbItem.getSellPoint());
		this.setStatus(tbItem.getStatus());
		this.setTitle(tbItem.getTitle());
		this.setUpdated(tbItem.getUpdated());
	}

	public String[] getImages() {
		String image = this.getImage();
		if (image != null && !"".equals(image)) {
			return image.split(",");
		}
		return null;
	}
}