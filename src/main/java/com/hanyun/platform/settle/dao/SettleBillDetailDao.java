package com.hanyun.platform.settle.dao;

import com.hanyun.platform.settle.domain.PayTransaction;
import com.hanyun.platform.settle.domain.SettleBillDetail;
import com.hanyun.platform.settle.vo.PayTypeReq;
import com.hanyun.platform.settle.vo.PayTypeVo;
import com.hanyun.platform.settle.vo.settlebill.SettleBillDetailParam;
import com.hanyun.platform.settle.vo.settlebill.SettleBillDetailReq;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jack on 2017/4/7.
 */
@SuppressWarnings("UnnecessaryInterfaceModifier")
@Repository
public interface SettleBillDetailDao {
    public List<SettleBillDetail> selectForGenSettlement(SettleBillDetailParam settleBillDetailParam);

    public List<SettleBillDetail> selectSolvedDiffSettlement(SettleBillDetailParam settleBillDetailParam);

    public List<SettleBillDetail> selectUnSolvedDiffSettlement(SettleBillDetailParam settleBillDetailParam);

    public List<SettleBillDetail> getSettleDiffDetailList(SettleBillDetailReq settleBillDetailReq);

    public List<SettleBillDetail> getSettleDetailList(SettleBillDetailReq settleBillDetailReq);

    public Integer getSettleDetailListCount(SettleBillDetailReq settleBillDetailReq);

    public List<SettleBillDetail> getAddSettleDiffDetailList(SettleBillDetailReq settleBillDetailReq);

    public List<SettleBillDetail> getSettleRefundDetailList(SettleBillDetailReq settleBillDetailReq);

    PayTypeVo getDiffStatistics(PayTypeReq payTypeReq);

    PayTypeVo getPayTransStatistics(PayTypeReq payTypeReq);

}
