package com.hmh.service.impl;

import com.hmh.common.pojo.EasyUITreeNode;
import com.hmh.mapper.TbItemCatMapper;
import com.hmh.pojo.TbItemCat;
import com.hmh.pojo.TbItemCatExample;
import com.hmh.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public List<EasyUITreeNode> getItemCatList(long parentId) {
		//创建查询条件
		TbItemCatExample example = new TbItemCatExample();
		TbItemCatExample.Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//根据条件查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		List<EasyUITreeNode> resultList = new ArrayList<>();
		//把列表转换成treeNodelist
		for (TbItemCat tbItemCat : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent() ? "closed" : "open");
			resultList.add(node);
		}
		//返回结果
		return resultList;
	}
}
