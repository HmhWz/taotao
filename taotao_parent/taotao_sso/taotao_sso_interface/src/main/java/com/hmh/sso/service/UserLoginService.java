package com.hmh.sso.service;

import com.hmh.common.pojo.TaotaoResult;

public interface UserLoginService {

	TaotaoResult login(String username, String password);
	TaotaoResult getUserByToken(String token);

	TaotaoResult logout(String token);
}
