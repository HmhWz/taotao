package com.hmh.search.service;

import com.hmh.common.pojo.SearchResult;

public interface SearchService {

	SearchResult search(String queryString, int pages, int rows) throws Exception;
}
