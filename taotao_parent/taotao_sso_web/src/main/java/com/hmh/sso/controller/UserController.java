package com.hmh.sso.controller;

import com.hmh.common.pojo.TaotaoResult;
import com.hmh.common.utils.CookieUtils;
import com.hmh.common.utils.JsonUtils;
import com.hmh.pojo.TbUser;
import com.hmh.sso.service.UserLoginService;
import com.hmh.sso.service.UserRegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {
	@Autowired
	private UserRegisterService userRegisterService;

	@Autowired
	private UserLoginService userLoginService;

	@Value("${COOKIE_TOKEN_KEY}")
	private String COOKIE_TOKEN_KEY;

	//	/user/check/hmh/1
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public TaotaoResult userCheck(@PathVariable("param") String param, @PathVariable("type") Integer type) {
		TaotaoResult result = userRegisterService.checkUserInfo(param, type);
		return result;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/user/register")
	@ResponseBody
	public TaotaoResult registerUser(TbUser user) {
		return userRegisterService.createUser(user);
	}

	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult userLogin(String username, String password,
								  HttpServletRequest request, HttpServletResponse response) {

		TaotaoResult result = userLoginService.login(username, password);
		if (result.getStatus() == 200) {
			String token = result.getData().toString();
			CookieUtils.setCookie(request, response, COOKIE_TOKEN_KEY, token);
		}

		return result;
	}

	//传统支持jsonp的方案
/*	@RequestMapping(value = "/user/token/{token}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getUserByToken(@PathVariable("token") String token, String callback) {
		TaotaoResult taotaoResult = userLoginService.getUserByToken(token);
		if(StringUtils.isNotBlank(callback)){
			String jsonResult = callback + "(" + JsonUtils.objectToJson(taotaoResult) + ")";
			return jsonResult;
		}
		return JsonUtils.objectToJson(taotaoResult);
	}*/

	// 方法二，从4.1以上版本才可以使用
	@RequestMapping(value="/user/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback) {
		TaotaoResult result = userLoginService.getUserByToken(token);
		if (StringUtils.isNotBlank(callback)) {
			// 设置要包装的数据
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			// 设置回调方法
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
		return result;
	}

	@RequestMapping("/user/logout/{token}")
	@ResponseBody
	public TaotaoResult logout(@PathVariable String token) {
		return userLoginService.logout(token);
	}
}
