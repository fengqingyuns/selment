package com.hanyun.platform.settle.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.platform.settle.domain.CommissionDeductionDetail;
import com.hanyun.platform.settle.domain.SettleBill;
import com.hanyun.platform.settle.domain.SettleEntity;
import com.hanyun.platform.settle.vo.CommissionStatisticsVo;
import com.hanyun.platform.settle.vo.base.PageResData;
import com.hanyun.platform.settle.vo.commissionbill.*;
import com.hanyun.platform.settle.vo.settlebill.CommissionBillGenResultVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author wangjie@hanyun.com
 * @Date 2018/2/27 11:31
 */
public interface CommissionBillService {


    PageResData query(CommissionBillReq commissionBillReq);

    HttpResponse<OpenTransferRes> openTransfer(String[] args);

    HttpResponse saveTransfer(SaveTransferReq req);

    HttpResponse<CommissionBillDetailRes> detail(String commissionBillId);

    PageResData getCommissionDetail(CommissionDetailReq commissionDetailReq);

    void expCommissionBillList(HttpServletResponse response, HttpServletRequest request, CommissionBillReq req) throws IOException;

    /**生成当日佣金结算单**/
    HttpResponse createDayCommissionStatement(SettleEntity settleEntity, SettleBill settleBill, Date tradeDate);

    CommissionStatisticsVo commissionStatistics(QueryCommissionPara queryCommissionPara);

    void expCommissionStatisticsList(HttpServletResponse response, HttpServletRequest request, QueryCommissionPara req) throws IOException;

    /**
     * 生成佣金结算单
     * @param date 交易日期
     * @param brandId 品牌编号
     * @return
     */
    CommissionBillGenResultVo genCommissionBill(Date date, String brandId);

    /**
     *  指定日期生成佣金结算单
     * @param date
     * @param brandId
     * @return
     */
    CommissionBillGenResultVo designationCommissionInfo(Date date, String brandId);

    /**
     * 查询佣金扣除明细列表
     * @param commissionDeductionDetail
     * @return
     */
    List<CommissionDeductionDetail> getCommissionDeductionDetail(CommissionDeductionDetail commissionDeductionDetail);

    /**
     *   判断是否存在佣金结算单
     * @param brandId
     * @param date
     * @return
     */
    int judeCommissionSettleId(Date date, String brandId);

}
