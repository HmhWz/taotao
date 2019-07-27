package com.hmh.content.service.impl;

import com.alibaba.fastjson.JSON;
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
	public List<TbContent> getContentListByCid(long cid) {

		//首先查询缓存，如果缓存中存在的话，就直接将结果返回给前台展示，查询缓存不能影响业务流程
		try {
			String json = jedisClient.hget(CONTENT_KEY, cid + "");
			//如果从缓存中查到了结果
			if (StringUtils.isNotBlank(json)) {
				//将json串转化为List<TbContent>
				List<TbContent> list = JSON.parseArray(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbContentExample example = new TbContentExample();
		TbContentExample.Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		List<TbContent> list = contentMapper.selectByExample(example);
		//添加缓存，不能影响业务流程
		try {
			String json = JSON.toJSONString(list);
			jedisClient.hset(CONTENT_KEY, cid + "", json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//返回结果
		return list;
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

	@Override
	public TaotaoResult updateContent(TbContent content) {
		// 填充属性
		content.setUpdated(new Date());
		//更新内容
		contentMapper.updateByPrimaryKey(content);
		//返回结果
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteContent(String ids) {
		String[] idList = ids.split(",");
		for (String id : idList) {
			//删除内容
			contentMapper.deleteByPrimaryKey(Long.valueOf(id));
		}
		//返回结果
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult getContent(long id) {
		TbContent content = contentMapper.selectByPrimaryKey(id);
		return TaotaoResult.ok(content);
	}

}
