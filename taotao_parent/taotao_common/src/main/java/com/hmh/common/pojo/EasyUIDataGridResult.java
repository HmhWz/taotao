package com.hmh.common.pojo;

import java.io.Serializable;
import java.util.List;

public class EasyUIDataGridResult implements Serializable {
	private Long total;
	//由于rows集合可能是各种不同的对象，因此我们便不再使用泛型，直接用List表示类型，它可以装任意类型的对象。
	private List rows;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}
}
