package com.hmh.search.listener;

import com.hmh.common.pojo.SearchItem;
import com.hmh.search.mapper.SearchItemMapper;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ItemAddListener implements MessageListener {

	@Autowired
	private SearchItemMapper searchItemMapper;
	@Autowired
	private SolrServer solrServer;

	@Override
	public void onMessage(Message message) {
		// 从消息中取商品id
		try {
			TextMessage textMessage = (TextMessage) message;
			String strItemId = textMessage.getText();
			// 转换成Long
			Long itemId = new Long(strItemId);
			//根据商品id查询商品详情，这里需要注意的是消息发送方法
			//有可能还没有提交事务，因此这里是有可能取不到商品信息
			//的，为了避免这种情况出现，我们最好等待事务提交，这里
			//我采用3次尝试的方法，每尝试一次休眠一秒
			SearchItem searchItem = null;
			for (int i = 0; i < 3; i++) {
				try {
					searchItem = searchItemMapper.getItemById(itemId);
					//如果获取到了商品信息，那就退出循环。
					if (searchItem != null) {
						break;
					}
					Thread.sleep(1000);//休眠一秒
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// 把商品信息添加到索引库
			SolrInputDocument document = new SolrInputDocument();
			// 为文档添加域
			document.addField("id", searchItem.getId());
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			document.addField("item_desc", searchItem.getItem_desc());
			// 向索引库中添加文档。
			solrServer.add(document);
			// 提交
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}