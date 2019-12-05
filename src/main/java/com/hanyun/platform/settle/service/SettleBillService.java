package com.hanyun.platform.settle.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.platform.settle.domain.SettleBill;
import com.hanyun.platform.settle.domain.SettleBillPaychn;
import com.hanyun.platform.settle.vo.OperationsSettle;
import com.hanyun.platform.settle.vo.settlebill.*;
import com.hanyun.platform.settle.vo.base.PageResData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by jack on 2017/4/6.
 */
public interface SettleBillService {

	public void getSettleDataLists(HttpServletResponse response, HttpServletRequest request, SettleBillListReq settleBillListReq) throws IOException;
	
    public PageResData getSettleBillList(SettleBillListReq settleBillListReq);

    public PageResData getSettleDiffDetailList(SettleBillDetailReq settleBillDetailReq);

    public SettleBill getSingleSettleBill(SettleBill param);

    public PageResData getSettleDetailList(SettleBillDetailReq settleBillDetailReq);

    public SettleBillRes getSettleBill(SettleBillReq settleBillReq);

    public PageResData getAddSettleDiffDetailList(SettleBillDetailReq settleBillDetailReq);

    public HttpResponse updateSettleStatus(SettleBillReq settleBillReq);

    public List<SettleBillPaychn> getSettlePayChnList(SettleBillPaychn settleBillPaychn);

    public void expSettleBillList(HttpServletResponse response, HttpServletRequest request, SettleBillListReq pettleBillListReq) throws IOException;

    public SettleBillExt getSummarySettleBill(SettleBillListReq settleBillListReq);

    public PageResData getSettleRefundDetailList(SettleBillDetailReq settleBillDetailReq);

    void getOperationsSettleData(HttpServletResponse response, HttpServletRequest request,SettleBillListReq pettleBillListReq) throws IOException;
}
