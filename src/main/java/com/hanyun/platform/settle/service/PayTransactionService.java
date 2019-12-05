package com.hanyun.platform.settle.service;

import com.hanyun.platform.settle.domain.PayTransaction;
import com.hanyun.platform.settle.domain.PayTransactionExcel;
import com.hanyun.platform.settle.vo.PayTransactionReq;
import com.hanyun.platform.settle.vo.base.PageResData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
* @Description: 查询交易明细
* @author wangjie@hanyun.com
* @date 2017年5月4日 上午11:42:37
*/
public interface PayTransactionService {
	/**
	 *      通过查询条件获取数据
	 * @param payTransactionReq
	 * @return
	 */
	public PageResData selectPayTransactionList(PayTransactionReq payTransactionReq);


	/**
	 * 通过查询条件获取数据并导出数据
	 * @param payTransactionReq
	 */
    public void expPayTransactionList(HttpServletResponse response, HttpServletRequest request, PayTransactionReq payTransactionReq) throws IOException;
}
