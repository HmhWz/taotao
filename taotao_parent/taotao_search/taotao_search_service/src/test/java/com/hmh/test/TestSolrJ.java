package com.hmh.test;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TestSolrJ {

	@Test
	public void testSolrJAddDocument() throws IOException, SolrServerException {
		SolrServer solrServer = new HttpSolrServer("http://119.29.83.167:8080/solr4/collection1");
		SolrInputDocument document = new SolrInputDocument();

		document.addField("id", "test001");
		document.addField("item_title", "海尔空调");
		document.addField("item_sell_point", "送一个电暖宝");
		document.addField("item_price", 10000);
		document.addField("item_image", "http://123.jpg");
		document.addField("item_category_name", "电器");
		document.addField("item_desc", "这是一款最新的空调，实惠，质量好");

		solrServer.add(document);
		solrServer.commit();

	}

	@Test
	public void testSolrJDeleteDocument() throws Exception {
		// 创建一个SolrServer对象，即HttpSolrServer对象，需要指定solr服务的url
		SolrServer solrServer = new HttpSolrServer("http://119.29.83.167:8080/solr4/collection1");
		// 通过id来删除文档
		solrServer.deleteById("test001");
		// 提交
		solrServer.commit();
	}

	@Test
	public void deleteDocumentByQuery() throws Exception {
		// 创建一个SolrServer对象，即HttpSolrServer对象，需要指定solr服务的url
		SolrServer solrServer = new HttpSolrServer("http://119.29.83.167:8080/solr4/collection1");
		// 通过价格来删除文档
		solrServer.deleteByQuery("item_price:10000");
		// 提交
		solrServer.commit();
	}

	@Test
	public void queryDocument() throws Exception {
		// 创建一个SolrServer对象，即HttpSolrServer对象，需要指定solr服务的url
		SolrServer solrServer = new HttpSolrServer("http://119.29.83.167:8080/solr4/collection1");
		SolrQuery query = new SolrQuery();
		query.setQuery("id:test001");
		QueryResponse response = solrServer.query(query);
		SolrDocumentList list = response.getResults();
		for (SolrDocument document : list) {
			String id = document.getFieldValue("id").toString();
			String title = document.getFieldValue("item_title").toString();
			System.out.println(id);
			System.out.println(title);
		}
	}

	@Test
	public void testQueryDocument() throws Exception {
		SolrServer solrServer = new HttpSolrServer("http://119.29.83.167:8080/solr4");
		// 创建一个SolrQuery对象
		SolrQuery query = new SolrQuery();
		query.setQuery("手机");
		// 设置自己的分页条件
		query.setStart(0); // 从0开始
		query.setRows(3); // 每页取10条记录
		// 设置默认搜索域
		query.set("df", "item_keywords");
		query.setHighlight(true); // 开启高亮显示
		query.addHighlightField("item_title"); // 添加高亮显示的域
		query.setHighlightSimplePre("<em>"); // 设置高亮显示的前缀
		query.setHighlightSimplePost("</em>"); // 设置高亮显示的后缀
		// 执行查询
		QueryResponse response = solrServer.query(query);
		// 取查询结果
		SolrDocumentList solrDocumentList = response.getResults();
		// 查询结果总记录数
		System.out.println("查询结果总记录数：" + solrDocumentList.getNumFound());
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			String itemName = null;
			// 取高亮显示
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			if (list != null && list.size() > 0) {
				itemName = list.get(0); // 将高亮后的结果取出来
			} else {
				itemName = (String) solrDocument.get("item_title");
			}
			System.out.println(itemName);
			System.out.println(solrDocument.get("item_sell_point"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_image"));
			System.out.println(solrDocument.get("item_category_name"));
			System.out.println("--------------------------------------------------------------");
		}
	}

}
