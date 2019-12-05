/**
 * 
 */
package com.hanyun.platform.settle.service;

import java.util.List;

import com.hanyun.platform.settle.domain.StatementDiffDetail;
import com.hanyun.platform.settle.domain.TradeStatementDetail;
import com.hanyun.platform.settle.vo.TradeStatementDetailReq;
import com.hanyun.platform.settle.vo.StatementDiffReq;

/**
 * 
 * @author liyinglong@hanyun.com
 * @date 2017年11月5日 下午2:59:32
 */
public interface TradeStatementDetailService {
    
    public List<TradeStatementDetail> statementDetailList(TradeStatementDetailReq statementDetailReq);

    public Integer statementCount(TradeStatementDetailReq statementDetailReq);

    public List<StatementDiffDetail> statementDiffList(StatementDiffReq statementDiffReq);

    public Integer statementDiffCount(StatementDiffReq statementDiffReq);

}
