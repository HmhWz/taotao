package com.hmh.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class FreemarkerTest {

	@Test
	public void testFreemarkerFirst() throws Exception {
		Configuration configuration = new Configuration(Configuration.getVersion());
		configuration.setDirectoryForTemplateLoading(new File("D:\\DevProjects\\IdeaProjects\\taotao\\taotao_parent\\taotao_item_web\\src\\main\\resources\\ftl"));
		configuration.setDefaultEncoding("utf-8");
		Template template = configuration.getTemplate("first.htm");

		Map data = new HashMap();

		data.put("hello", "hello freemarker!!");
		data.put("title", "Hello Freemarker!!!");
		data.put("stu", new Student(1, "小明", 18, "温都水城"));


		List<Student> stuList = new ArrayList<>();
		stuList.add(new Student(1, "小明1", 18, "温都水城"));
		stuList.add(new Student(2, "小明2", 17, "温都水城"));
		stuList.add(new Student(3, "小明3", 16, "温都水城"));
		stuList.add(new Student(4, "小明4", 15, "温都水城"));
		stuList.add(new Student(5, "小明5", 14, "温都水城"));
		stuList.add(new Student(6, "小明6", 13, "温都水城"));
		stuList.add(new Student(7, "小明7", 12, "温都水城"));
		data.put("stuList", stuList);
		data.put("date", new Date());

		FileWriter writer = new FileWriter(new File("D:\\DevProjects\\IdeaProjects\\taotao\\taotao_parent\\taotao_item_web\\src\\main\\resources\\ftl\\first.html"));
		template.process(data, writer);
		writer.close();
	}
}
