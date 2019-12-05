package com.hanyun.platform.settle.dao;

import com.hanyun.platform.settle.domain.PayTransaction;

import com.hanyun.platform.settle.vo.PayTransactionReq;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayTransactionDao {

    public PayTransaction selectByTransId(String transId);

    /**
     *      通过查询条件获取数据
     * @param payTransactionReq
     * @return
     */
    public List<PayTransaction> selectPayTransactionList(PayTransactionReq payTransactionReq);

    /**
     *    通过查询条件
     * @param payTransactionReq
     * @return
     */
    public int selectPayTransactionCount(PayTransactionReq payTransactionReq);

    /**
     *      通过查询条件获取数据并导出数据
     * @param payTransactionReq
     * @return
     */
   public List<PayTransaction> expSelectPayTransactionList(PayTransactionReq payTransactionReq);


}