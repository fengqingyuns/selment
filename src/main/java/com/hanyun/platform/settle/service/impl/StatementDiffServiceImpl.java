package com.hanyun.platform.settle.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.platform.settle.consts.BizResCode;
import com.hanyun.platform.settle.consts.SettlementConsts;
import com.hanyun.platform.settle.dao.PayTransactionDao;
import com.hanyun.platform.settle.dao.StatementDiffDao;
import com.hanyun.platform.settle.dao.StatementDiffDetailDao;
import com.hanyun.platform.settle.domain.PayTransaction;
import com.hanyun.platform.settle.domain.StatementDiff;
import com.hanyun.platform.settle.domain.StatementDiffDetail;
import com.hanyun.platform.settle.service.StatementDiffService;
import com.hanyun.platform.settle.util.BizResUtil;
import com.hanyun.platform.settle.vo.StatementDiffReq;

@Service
public class StatementDiffServiceImpl implements StatementDiffService {
	private static Logger LOGGER = LoggerFactory.getLogger(StatementDiffServiceImpl.class);
	@Resource
	private StatementDiffDao statementDiffDao;
	@Resource
	private StatementDiffDetailDao statementDiffDetailDao;
	@Resource
	private PayTransactionDao payTransactionDao;

	/**
	 * 根据ID查询对账差异明细
	 */
	@Override
	public StatementDiffDetail getStatementDiffDetailByTransId(StatementDiffReq statementDiffReq) {

		return statementDiffDetailDao.selectStatementDiffDetailByTransId(statementDiffReq);
	}

	/**
	 * 新增对账差异提报
	 */
	@Override
	public HttpResponse addStatementDiffSubmit(StatementDiff statementDiff) {
		HttpResponse httpResponse = null;
		// 差异类型不能为空
		if (statementDiff.getDiffType() == null) {
		    LOGGER.error("差异类型为空!");
		    return BizResUtil.fail(BizResCode.PARAMERROR);
		} 
		try {
		    PayTransaction trans = payTransactionDao.selectByTransId(statementDiff.getTransId());
		    if(trans == null){
		        return BizResUtil.fail(BizResCode.PARAMERROR);
		    }
		    Date now = new Date();
		    // 生成结算单当日及之后，不允许再提报
//		    SettleInfo settleInfo = settleInfoDao.selectByMerchantId(trans.getMerchantId());
//		    int daysBetween = DateCalcUtil.getIntervalDays(trans.getFinishTime(), now);
//		    if(daysBetween >= settleInfo.getSettleCircle()){
//		        LOGGER.error("不允许再提报差异, transId: {}, settleCircle: {}", 
//		                trans.getTransId(), settleInfo.getSettleCircle());
//		        return BizResUtil.fail(BizResCode.NOTALLOWSUBMITDIFF);
//		    }
		    
		    statementDiff.setBrandId(trans.getBrandId());
		    statementDiff.setStoreId(trans.getStoreId());
			statementDiff.setDiffStatus(Integer.valueOf(0));
			statementDiff.setReportTime(now);
			statementDiff.setCreateTime(now);
			statementDiffDao.insertStatementDiffSubmit(statementDiff);
			httpResponse = BizResUtil.succ(null);
		} catch (Exception e) {
			LOGGER.error("新增信息失败!", e);
			httpResponse = BizResUtil.fail(BizResCode.SYSTEMERROR);
		}

		return httpResponse;
	}

	/**
	 * 修改对账差异提报
	 */
	@Override
	public void updateStatementDiffSubmit(StatementDiff statementDiff) {
		statementDiff.setDiffStatus(SettlementConsts.DIFF_STATUS_SOLVED);
		statementDiff.setSolveTime(new Date());
		statementDiff.setUpdateTime(new Date());
		statementDiffDao.updateStatementDiffSubmit(statementDiff);

	}

}
