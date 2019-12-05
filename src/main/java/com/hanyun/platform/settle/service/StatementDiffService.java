package com.hanyun.platform.settle.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.platform.settle.domain.StatementDiff;
import com.hanyun.platform.settle.domain.StatementDiffDetail;
import com.hanyun.platform.settle.vo.StatementDiffReq;

public interface StatementDiffService {

    /**
     * @param statementDiffReq
     * @return StatementDiff
     * @throws
     * @Title: getStatementDiffDetailById
     * @Description: 通过Id查询对账差异
     */
    public StatementDiffDetail getStatementDiffDetailByTransId(StatementDiffReq statementDiffReq);

    /**
     * 新增对账差异提报
     *
     * @param statementDiff
     */
    public HttpResponse addStatementDiffSubmit(StatementDiff statementDiff);

    /**
     * 修改对账差异提报
     *
     * @param statementDiff
     */
    public void updateStatementDiffSubmit(StatementDiff statementDiff);
}
