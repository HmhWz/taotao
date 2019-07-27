package com.hmh.search.service.impl;

import com.hmh.common.pojo.SearchItem;
import com.hmh.common.pojo.TaotaoResult;
import com.hmh.search.mapper.SearchItemMapper;
import com.hmh.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 导入商品数据到索引库
 * <p>Title: SearchItemServiceImpl</p>
 * <p>Description:</p>
 * <p>Company: com.hmh</p>
 *
 * @version 1.0
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

	@Autowired
	private SolrServer solrServer;

	@Autowired
	private SearchItemMapper searchItemMapper;


	@Override
	public TaotaoResult importAllItemToIndex() {
		//1、先查询所有商品数据
		try {
			List<SearchItem> itemList = searchItemMapper.getItemList();
			//2、遍历商品数据添加到索引库
			for (SearchItem searchItem : itemList) {
				//创建文档对象
				SolrInputDocument document = new SolrInputDocument();
				document.setField("id", searchItem.getId());
				document.setField("item_title", searchItem.getTitle());
				document.setField("item_sell_point", searchItem.getSell_point());
				document.setField("item_price", searchItem.getPrice());
				document.setField("item_image", searchItem.getImage());
				document.setField("item_category_name", searchItem.getCategory_name());
				document.setField("item_desc", searchItem.getItem_desc());
				solrServer.add(document);
			}
			solrServer.commit();
			return TaotaoResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, "导入数据失败！");
		}
	}

}


