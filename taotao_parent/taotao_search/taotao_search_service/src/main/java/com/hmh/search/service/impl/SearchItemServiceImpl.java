package com.hmh.search.service.impl;

import com.hmh.common.pojo.SearchItem;
import com.hmh.common.pojo.TaotaoResult;
import com.hmh.search.mapper.ItemMapper;
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
 * @version 1.0
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

	@Autowired
	private SolrServer solrServer;

	@Autowired
	private ItemMapper itemMapper;

	@Override
	public TaotaoResult importAllItemToIndex() throws Exception {
		List<SearchItem> list = itemMapper.getItemList();

		for(SearchItem searchItem : list){
			SolrInputDocument document = new SolrInputDocument();

			document.addField("id", searchItem.getId());
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			document.addField("item_desc", searchItem.getItem_desc());

			solrServer.add(document);
		}
		//提交
		solrServer.commit();

		// 返回TaotaoResult，当你纠结返回值是什么的时候，你就可以使用TaotaoResult。
		return TaotaoResult.ok();
	}
}


