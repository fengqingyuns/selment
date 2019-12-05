/**
 * 
 */
package com.hanyun.platform.settle.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanyun.platform.settle.dao.StatementDiffDetailDao;
import com.hanyun.platform.settle.dao.TradeStatementDetailDao;
import com.hanyun.platform.settle.domain.StatementDiffDetail;
import com.hanyun.platform.settle.domain.TradeStatementDetail;
import com.hanyun.platform.settle.service.TradeStatementDetailService;
import com.hanyun.platform.settle.vo.TradeStatementDetailReq;
import com.hanyun.platform.settle.vo.StatementDiffReq;

/**
 * 
 * @author liyinglong@hanyun.com
 * @date 2017年11月5日 下午3:14:15
 */
@Service
public class TradeStatementDetailServiceImpl implements TradeStatementDetailService {
    @Resource
    private TradeStatementDetailDao tradeStatementDetailDao;
    @Resource
    private StatementDiffDetailDao statementDiffDetailDao;


    @Override
    public List<TradeStatementDetail> statementDetailList(TradeStatementDetailReq statementDetailReq) {
        List<TradeStatementDetail> statementDetailList = tradeStatementDetailDao.selectStatementDetailList(statementDetailReq);

        return statementDetailList;

    }

    @Override
    public Integer statementCount(TradeStatementDetailReq statementDetailReq) {
        return tradeStatementDetailDao.countStatementDetail(statementDetailReq);
    }

    @Override
    public List<StatementDiffDetail> statementDiffList(StatementDiffReq statementDiffReq) {
        List<StatementDiffDetail> statementDiffDetailList = statementDiffDetailDao.statementDiffDetailList(statementDiffReq);
        return statementDiffDetailList;

    }

    @Override
    public Integer statementDiffCount(StatementDiffReq statementDiffReq) {
        return statementDiffDetailDao.selectCount(statementDiffReq);
    }

}
