package com.hmh.sso.service;

import com.hmh.common.pojo.TaotaoResult;
import com.hmh.pojo.TbUser;

public interface UserRegisterService {
	TaotaoResult checkUserInfo(String param, int type);
	TaotaoResult createUser(TbUser user);
}
