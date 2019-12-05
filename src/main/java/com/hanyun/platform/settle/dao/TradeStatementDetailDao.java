/**
 * 
 */
package com.hanyun.platform.settle.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanyun.platform.settle.domain.TradeStatementDetail;
import com.hanyun.platform.settle.vo.TradeStatementDetailReq;

/**
 * 
 * @author liyinglong@hanyun.com
 * @date 2017年11月5日 下午3:17:00
 */
@Repository
public interface TradeStatementDetailDao {
    /**
    * @Description: 通过对账Id查询对账信息 
     */
    public List<TradeStatementDetail> selectStatementDetailList(TradeStatementDetailReq statementDetailReq);
    
    /**
    * @Description: 条数 
    * @return int   
     */
    public Integer countStatementDetail(TradeStatementDetailReq statementDetailReq);

}
