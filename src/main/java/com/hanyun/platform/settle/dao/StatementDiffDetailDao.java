/**   
* @Title: SettlementDetailDao.java 
* @Package com.hanyun.platform.settle.dao 
* @author A18ccms A18ccms_gmail_com   
* @date 2016年7月26日 上午9:55:48 
* @version V1.0   
*/
package com.hanyun.platform.settle.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanyun.platform.settle.domain.StatementDiffDetail;
import com.hanyun.platform.settle.vo.StatementDiffReq;

/**
 * @author jack
 *
 */
@Repository
public interface StatementDiffDetailDao {

    public StatementDiffDetail selectStatementDiffDetailByTransId(StatementDiffReq statementDiffReq);
    
    public List<StatementDiffDetail> statementDiffDetailList(StatementDiffReq statementDiffReq);
    

    public int selectCount(StatementDiffReq statementDiffReq);
       
    public List<StatementDiffDetail> statementDiffDetailListM(StatementDiffReq statementDiffReq);
    

    public int selectCountM(StatementDiffReq statementDiffReq); 
    
    public List<StatementDiffDetail> expStatementDiffListResultList(StatementDiffReq statementDiffReq);
    

}
