package com.hmh.content.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hmh.common.pojo.EasyUIDataGridResult;
import com.hmh.common.pojo.TaotaoResult;
import com.hmh.common.utils.JsonUtils;
import com.hmh.content.jedis.JedisClient;
import com.hmh.content.service.ContentService;
import com.hmh.mapper.TbContentMapper;
import com.hmh.pojo.TbContent;
import com.hmh.pojo.TbContentExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper;

	@Autowired
	private JedisClient jedisClient;

	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;

	@Override
	public EasyUIDataGridResult getContentList(long categoryId, int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//执行查询
		TbContentExample example = new TbContentExample();
		TbContentExample.Criteria createCriteria = example.createCriteria();
		createCriteria.andCategoryIdEqualTo(categoryId);
		//获取查询结果
		List<TbContent> list = contentMapper.selectByExample(example);
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		//返回结果
		return result;
	}

	@Override
	public TaotaoResult addContent(TbContent content) {
		// 补全pojo的属性
		content.setCreated(new Date());
		content.setUpdated(new Date());
		// 向内容表中插入数据
		contentMapper.insert(content);

		// 做缓存同步，清除redis中内容分类id对应的缓存信息
		jedisClient.hdel(CONTENT_KEY, content.getCategoryId().toString());

		return TaotaoResult.ok();
	}

}
