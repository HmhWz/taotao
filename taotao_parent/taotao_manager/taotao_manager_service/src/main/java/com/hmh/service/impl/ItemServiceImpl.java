package com.hmh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hmh.common.pojo.EasyUIDataGridResult;
import com.hmh.common.pojo.TaotaoResult;
import com.hmh.common.utils.IDUtils;
import com.hmh.common.utils.JsonUtils;
import com.hmh.jedis.JedisClient;
import com.hmh.mapper.TbItemDescMapper;
import com.hmh.mapper.TbItemMapper;
import com.hmh.pojo.TbItem;
import com.hmh.pojo.TbItemDesc;
import com.hmh.pojo.TbItemExample;
import com.hmh.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper tbItemMapper;

	@Autowired
	private TbItemDescMapper tbItemDescMapper;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Resource(name = "topicDestination")
	private Topic topicDestination;


	@Autowired
	private JedisClient jedisClient;

	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;

	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;

	@Override
	public TbItem getItemById(long itemId) {
		// 先查询缓存
		try {
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":BASE");
			if (StringUtils.isNotBlank(json)) {
				TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
				return tbItem;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 如果缓存中没有命中，那么查询数据库
		TbItem item = tbItemMapper.selectByPrimaryKey(itemId);
		// 添加到缓存
		try {
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":BASE", JsonUtils.objectToJson(item));
			// 设置过期时间
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":BASE", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}

	@Override
	public TbItemDesc getItemDescById(long itemId) {
		String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + "DESC");
		if (StringUtils.isNotBlank(json)) {
			TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
			return tbItemDesc;
		}
		TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
		json = JsonUtils.objectToJson(tbItemDesc);
		jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + "DESC", json);
		jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + "DESC", REDIS_ITEM_EXPIRE);

		return tbItemDesc;
	}

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		// 分页处理
		PageHelper.startPage(page, rows);
		// 执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = tbItemMapper.selectByExample(example);
		// 创建返回结果对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		// 取总记录数
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public TaotaoResult addItem(TbItem item, String desc) throws Exception{
		// 先生成商品id
		final long itemId = IDUtils.genItemId();
		item.setId(itemId);
		// 商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		// 插入到商品表
		tbItemMapper.insert(item);
		// 商品描述
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		// 插入商品描述表
		tbItemDescMapper.insert(itemDesc);

		// 商品添加完成后发送一个MQ消息
//		jmsTemplate.send(topicDestination, new MessageCreator() {
//			@Override
//			public Message createMessage(Session session) throws JMSException {
//				// 创建一个消息对象
//				// 要在匿名内部类访问局部变量itemId，itemId需要用final修饰
//				TextMessage message = session.createTextMessage(itemId + "");
//				return message;
//			}
//		});

		return TaotaoResult.ok();
	}

}











































