package com.hanyun.platform.settle.service.impl;

import com.hanyun.ground.util.date.DateCalcUtil;
import com.hanyun.ground.util.date.DateFormatUtil;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.platform.settle.consts.BizResCode;
import com.hanyun.platform.settle.consts.CommissionConsts;
import com.hanyun.platform.settle.consts.SettlementConsts;
import com.hanyun.platform.settle.dao.CommissionBillDao;
import com.hanyun.platform.settle.dao.CommissionDeductionDetailDao;
import com.hanyun.platform.settle.dao.SettleBillDao;
import com.hanyun.platform.settle.dao.SettleEntityDao;
import com.hanyun.platform.settle.domain.CommissionBill;
import com.hanyun.platform.settle.domain.CommissionDeductionDetail;
import com.hanyun.platform.settle.domain.SettleBill;
import com.hanyun.platform.settle.domain.SettleEntity;
import com.hanyun.platform.settle.service.CommissionBillService;
import com.hanyun.platform.settle.util.BizResUtil;
import com.hanyun.platform.settle.util.BusinessIdUtils;
import com.hanyun.platform.settle.util.DictionaryTypeUtil;
import com.hanyun.platform.settle.util.ExcelUtil;
import com.hanyun.platform.settle.domain.*;
import com.hanyun.platform.settle.util.*;
import com.hanyun.platform.settle.vo.BrandStoreInfo;
import com.hanyun.platform.settle.vo.CommissionSettleEntityReq;
import com.hanyun.platform.settle.vo.CommissionStatisticsVo;
import com.hanyun.platform.settle.vo.base.PageResData;
import com.hanyun.platform.settle.vo.commissionbill.*;
import com.hanyun.platform.settle.vo.settlebill.CommissionBillGenResultVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * @author wangjie@hanyun.com
 * @Date 2018/2/27 11:32
 */
@Service
public class CommissionBillServiceImpl implements CommissionBillService {
    private static Logger LOGGER = LoggerFactory.getLogger(CommissionBillServiceImpl.class);

    @Resource
    private CommissionBillDao commissionBillDao;
    @Resource
    private CommissionDeductionDetailDao commissionDeductionDetailDao;
    @Resource
    private SettleBillDao settleBillDao;
    @Resource
    private SettleEntityDao settleEntityDao;

    private static String[][] headersCommissionBill = { { "佣金结算单号", "25" }, { "品牌名称", "22" }, { "门店名称", "22" }, { "交易日期", "27" },
            { "结算周期", "17" }, { "佣金金额（元）", "18" }, { "结算状态", "17" }, { "已结算金额", "17" }, { "未结算金额", "17" }};

    private static String[] fieldValuesCommissionBill = { "commissionBillId", "brandName", "storeName", "tradeDate", "commissionBillCircle", "commissionAmount",
            "commissionBillStatus", "settledAmount","unSettleAmount"};

    private static String[][] headersCommissionStatistics = { { "品牌名称", "22" }, { "门店名称", "22" }, { "交易日期", "27" },
            { "当日流水（元）", "18" }, { "流水提点(%)", "17" }, { "佣金金额（元）", "18" }};

    private static String[] fieldValuesCommissionStatistics = { "brandName", "storeName", "tradeDate", "crrentDayTurnover",
            "turnoverPoints","commissionAmount"};


    @Override
    public PageResData query(CommissionBillReq commissionBillReq) {
        if(StringUtils.isNotBlank(commissionBillReq.getTradeDateStart())) {
            commissionBillReq.setBeginTime(DateCalcUtil.getDayBegin(DateFormatUtil.parseDate(commissionBillReq.getTradeDateStart())));
        }
        if(StringUtils.isNotBlank(commissionBillReq.getTradeDateEnd())) {
            commissionBillReq.setEndTime(DateCalcUtil.getDayEnd(DateFormatUtil.parseDate(commissionBillReq.getTradeDateEnd())));
        }

        PageResData pg = new PageResData();
        LOGGER.info("请求参数 {}", JsonUtil.toJson(commissionBillReq));
        List<CommissionBill> list = commissionBillDao.query(commissionBillReq);
        Integer count  = commissionBillDao.queryCount(commissionBillReq);
        pg.setDataList(list);
        pg.setTotalCount(count);
        return pg;
    }

    @Override
    public HttpResponse<OpenTransferRes> openTransfer(String[] commissionBillIds) {
        HttpResponse<OpenTransferRes> httpResOpenTransfer = null;
        LOGGER.info("请求参数 {}", JsonUtil.toJson(commissionBillIds));
        OpenTransferRes opres = new OpenTransferRes();
        List<CommissionBill> list = commissionBillDao.openTransfer(commissionBillIds);
        String brandName = "";
        String storeName = "";
        Long commissionAmount =0L;
        Long settledAmount =0L;
        Map<String,String> brandMap = new HashMap<>();
        Map<String,String> storeMap = new HashMap<>();

        for (CommissionBill commissionBill:list) {
            brandName = commissionBill.getBrandName();
            if(null!=commissionBill.getStoreName()){
                storeName = commissionBill.getStoreName();
            }
            if(CommissionConsts.COMMISSION_BILL_STATUS_SETTLED.equals(commissionBill.getCommissionBillStatus())){
                LOGGER.error("已结算的不能再进行结算");
                return HttpResponse.failure(BizResCode.SETTLEDNOTAGESETTLE);
            }
            commissionAmount += commissionBill.getCommissionAmount();
            settledAmount += commissionBill.getSettledAmount();
            brandMap.put(commissionBill.getBrandId(),  commissionBill.getBrandId());
            storeMap.put(commissionBill.getStoreId(),  commissionBill.getStoreId());
        }
        if(brandMap.size() != 1){
            LOGGER.error("选择不是同一个品牌");
            return HttpResponse.failure(BizResCode.SELECTEDNOTSAMEBRAND);
        }
        if(storeMap.size() != 1){
            LOGGER.error("选择不是同一个门店");
            return HttpResponse.failure(BizResCode.SELECTEDNOTSAMESTORE);
        }

        opres.setBrandName(brandName);
        opres.setStoreName(storeName);
        opres.setCommissionBillList(list);
        opres.setAmount(commissionAmount - settledAmount);
        httpResOpenTransfer = BizResUtil.succ(opres);
        return httpResOpenTransfer;
    }

    @Override
    public HttpResponse saveTransfer(SaveTransferReq req) {
        //转账总金额
        Long totalAmount = req.getAmount();
        List<CommissionBill> commisList = commissionBillDao.openTransfer(req.getCommissionBillIds());
        for (CommissionBill commissionBill:commisList) {
            //未结算佣金
           Long unsettleAmount = commissionBill.getCommissionAmount() - commissionBill.getSettledAmount();
           if(totalAmount >= unsettleAmount){//当总金额大于未结算金额
               totalAmount -= unsettleAmount;//扣减未结算金额
               //更新佣金结算单
               commissionBill.setUpdateTime(new Date());
               commissionBill.setSettledAmount(commissionBill.getSettledAmount() + unsettleAmount);//已结算金额+未结算金额
               commissionBill.setCommissionBillStatus(CommissionConsts.COMMISSION_BILL_STATUS_SETTLED);//已结算
               commissionBillDao.updateByPrimaryKeySelective(commissionBill);
               saveCommissionDeduction(commissionBill,unsettleAmount,req.getRemark());

           } else if(totalAmount == 0){//没有金额不再进行循环扣减
                break;
           } else{ //当总金额小于未结算金额
               //更新佣金结算单
               commissionBill.setUpdateTime(new Date());
               commissionBill.setSettledAmount(commissionBill.getSettledAmount() + totalAmount);//已结算金额+转账剩余总金额
               commissionBill.setCommissionBillStatus(CommissionConsts.COMMISSION_BILL_STATUS_PARTIALLY_SETTLED);//部分结算
               commissionBillDao.updateByPrimaryKeySelective(commissionBill);
               saveCommissionDeduction(commissionBill,totalAmount,req.getRemark());
               totalAmount -= totalAmount;//扣减本次结算金额
           }
        }

        return HttpResponse.success();
    }

    /**
     *
     * @param commissionBill
     * @param amount
     * @param remark
     */
    private void saveCommissionDeduction(CommissionBill commissionBill, Long amount, String remark) {
        CommissionDeductionDetail cdd = new CommissionDeductionDetail();
        cdd.setCreateTime(new Date());
        cdd.setUpdateTime(new Date());
        cdd.setCommissionDeductionId(BusinessIdUtils.genCommissionDeductionId());
        cdd.setCommissionBillId(commissionBill.getCommissionBillId());
        cdd.setBillId("");
        cdd.setBrandId(commissionBill.getBrandId());
        cdd.setBrandName(commissionBill.getBrandName());
        cdd.setStoreId(commissionBill.getStoreId());
        cdd.setStoreName(commissionBill.getStoreName());
        cdd.setTradeDateStart(commissionBill.getTradeDateStart());
        cdd.setTradeDateEnd(commissionBill.getTradeDateEnd());
        cdd.setCommissionSettleType(CommissionConsts.COMMISSION_SETTLE_TYPE_NO);//线下结算
        cdd.setCommissionAmount(commissionBill.getCommissionAmount());
        cdd.setDeductionAmount(amount);
        cdd.setSettledAmount(commissionBill.getSettledAmount());
        cdd.setRemark(remark);
        commissionDeductionDetailDao.insertSelective(cdd);
    }

    @Override
    public HttpResponse<CommissionBillDetailRes> detail(String commissionBillId) {
       HttpResponse<CommissionBillDetailRes> httpResponse = null;
       CommissionBillDetailRes commissionBillDetailRes = new CommissionBillDetailRes();
       CommissionBill commissionBill = commissionBillDao.selectByPrimaryKey(commissionBillId);
       if(null == commissionBill){
           LOGGER.error("没有找到佣金结算单");
            return HttpResponse.failure(BizResCode.COMMISSIONBILLNOTFOUND);
       }
        commissionBillDetailRes.setCommissionBill(commissionBill);
        //获取结算记录
        List<CommissionDeductionDetail> cDDList = commissionDeductionDetailDao.selectBycommissionBillId(commissionBillId);
        commissionBillDetailRes.setCommissionDeductionDetailList(cDDList);
        httpResponse = BizResUtil.succ(commissionBillDetailRes);
        return httpResponse;
    }

    /**
     *  佣金明细
     * @param commissionDetailReq
     * @return
     */
    @Override
    public PageResData getCommissionDetail(CommissionDetailReq commissionDetailReq) {
        PageResData pg = new PageResData();
        HttpResponse<List<CommissionDetail>> httpResponse = null;
        CommissionBill commissionBill = commissionBillDao.selectByPrimaryKey(commissionDetailReq.getCommissionBillId());
        if(null == commissionBill){
            LOGGER.error("没有找到佣金结算单");
           // return HttpResponse.failure(BizResCode.COMMISSIONBILLNOTFOUND);
        }
        QueryCommissionPara  queryCommissionPara = new QueryCommissionPara();
        queryCommissionPara.setBrandId(commissionBill.getBrandId());
        queryCommissionPara.setStoreId(commissionBill.getStoreId());
        queryCommissionPara.setEntityType(commissionBill.getEntityType());
        queryCommissionPara.setBeginTime(commissionBill.getTradeDateStart());
        queryCommissionPara.setEndTime(commissionBill.getTradeDateEnd());
        queryCommissionPara.setPageNo(commissionDetailReq.getPageNo());
        queryCommissionPara.setPageSize(commissionDetailReq.getPageSize());
        LOGGER.info("查询结算单信息{}",JsonUtil.toJson(queryCommissionPara));
        List<SettleBill> list = settleBillDao.getCommissionInfo(queryCommissionPara);
        Integer count = settleBillDao.getCommissionInfoCount(queryCommissionPara);
        List<CommissionDetail> commissionDetailList = TransferCommissionDetail(list);
        pg.setDataList(commissionDetailList);
        pg.setTotalCount(count);
        return pg;
    }

    private List<CommissionDetail> TransferCommissionDetail(List<SettleBill> list) {
        List<CommissionDetail> commissionDetailList = new ArrayList<>();
        for(SettleBill settleBill:list){


            CommissionDetail commissionDetail = new CommissionDetail();
            commissionDetail.setTredeDate(settleBill.getTradeDateStart());
            if(settleBill.getEntityType().equals(SettlementConsts.ENTITY_TYPE_BRAND)){
                //主体类型为品牌 获取类品牌流水字段为总流水
                commissionDetail.setCrrentDayTurnover(settleBill.getBrandFlowAmt());
            }else{
                //主体类型为门店  获取总流水为
                commissionDetail.setCrrentDayTurnover(settleBill.getTotalFlowAmt());
            }
            commissionDetail.setTurnoverPoints(settleBill.getTurnoverPoints());
            commissionDetail.setCommissionAmount(settleBill.getTodayCommission());
            commissionDetail.setBrandName(BaseBrandInfoUtil.getBrandAll().get(settleBill.getBrandId()).getBrandName());
            if (settleBill.getEntityType().equals(SettlementConsts.ENTITY_TYPE_BRAND)) {//实体类型为品牌，门店则为总部
                commissionDetail.setStoreName("总部");
            }else{
                commissionDetail.setStoreName(BaseBrandStoreUtil.getBrandAllStore(settleBill.getBrandId()).get(settleBill.getStoreId()).getStoreName());
            }
            commissionDetailList.add(commissionDetail);
        }
        return commissionDetailList;
    }

    @Override
    public void expCommissionBillList(HttpServletResponse response, HttpServletRequest request, CommissionBillReq req) throws IOException {
        if(StringUtils.isNotBlank(req.getTradeDateStart())) {
            req.setBeginTime(DateCalcUtil.getDayBegin(DateFormatUtil.parseDate(req.getTradeDateStart())));
        }
        if(StringUtils.isNotBlank(req.getTradeDateEnd())) {
            req.setEndTime(DateCalcUtil.getDayEnd(DateFormatUtil.parseDate(req.getTradeDateEnd())));
        }

        req.setPageNo(1);
        req.setPageSize(Integer.MAX_VALUE);
        List<CommissionBill> commissionBillList = commissionBillDao.query(req);
        List<CommissionBillExcel> commissionBillListExcel = commissionBillTransformation(commissionBillList);

        String expFileName = "佣金结算单";
        OutputStream out = null;
        response.reset();// 清空输出流
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String((expFileName + ".xls").getBytes(), "iso-8859-1"));
        out = response.getOutputStream();// 取得输出流
        try {
            new ExcelUtil<CommissionBillExcel>("佣金结算单", headersCommissionBill, fieldValuesCommissionBill, commissionBillListExcel, out)
                    .ExportExcel();
        } catch (Exception e) {
            LOGGER.error("生成文件失败!", e);
        } finally {
            out.close();
        }
    }

    @Override
    public HttpResponse createDayCommissionStatement(SettleEntity settleEntity, SettleBill settleBill, Date tradeDate) {
        long storeSumFlowAmt = 0;
        //佣金金额
        Long commissionAmount;
        if (settleBill.getEntityType().equals(CommissionConsts.ENTITY_TYPE_STORE)){
            //门店佣金金额=门店总流水*提点
            commissionAmount = settleBill.getTotalFlowAmt().longValue() * settleEntity.getTurnoverPoints()/10000;
        }else {
            SettleBill settleBillParameter = new SettleBill();
            //品牌Id
            settleBillParameter.setBrandId(settleEntity.getBrandId());
            //主体类型 门店
            settleBillParameter.setEntityType(SettlementConsts.ENTITY_TYPE_STORE);
            //独立结算状态：是
            settleBillParameter.setSeparateSettle(SettlementConsts.SEPARATE_SETTLE_YES);
            settleBillParameter.setTradeDateStart(tradeDate);
            //获取门店列表
            List<SettleBill> settleEntityStoreList = settleBillDao.selectSelective(settleBillParameter);
            for (SettleBill item : settleEntityStoreList) {
                storeSumFlowAmt += item.getTotalFlowAmt();
            }
            //品牌佣金金额=（品牌总流水-独立结算门店总流水之和）*提点
            commissionAmount = (settleBill.getTotalFlowAmt().longValue() - storeSumFlowAmt) * settleEntity.getTurnoverPoints()/10000;
        }
        //生成佣金结算单
        CommissionBill commissionBill = new CommissionBill();
        BrandAllInfo brandAllInfo = BaseBrandInfoUtil.getBrandAll().get(settleEntity.getBrandId());
        BrandStoreInfo brandStoreInfo = BaseBrandStoreUtil.getBrandAllStore(settleEntity.getBrandId()).get(settleEntity.getStoreId());
        commissionBill.setCommissionBillId(BusinessIdUtils.genCommissionBillId(settleEntity.getEntityId(),tradeDate));
        commissionBill.setEntityId(settleEntity.getEntityId());
        commissionBill.setEntityType(settleEntity.getEntityType());
        commissionBill.setEntityName(settleEntity.getEntityName());
        commissionBill.setBrandId(settleEntity.getBrandId());
        commissionBill.setBrandName(brandAllInfo == null ? null : brandAllInfo.getBrandName());
        commissionBill.setStoreId(settleEntity.getStoreId());
        commissionBill.setStoreName(brandStoreInfo == null ? null : brandStoreInfo.getStoreName());
        commissionBill.setTradeDateStart(settleBill.getTradeDateStart());
        commissionBill.setTradeDateEnd(settleBill.getTradeDateEnd());
        commissionBill.setCommissionBillCircle(settleEntity.getCommissionSettlementCircle());
        commissionBill.setCommissionBillStatus(CommissionConsts.COMMISSION_BILL_STATUS_UNSETTLEMENT);
        commissionBill.setCommissionAmount(commissionAmount);
        commissionBill.setSettledAmount(0L);
        commissionBill.setOnlineCommission(settleEntity.getOnlineCommission());
        commissionBill.setCreateTime(new Date());
        commissionBill.setUpdateTime(new Date());
        commissionBillDao.insertSelective(commissionBill);
        settleEntity.setCommissionLastSettleTime(DateCalcUtil.getDayEnd(tradeDate));
        settleEntity.setUpdateTime(new Date());
        settleEntityDao.updateByEntityId(settleEntity);
        return HttpResponse.success();
    }

    private List<CommissionBillExcel> commissionBillTransformation(List<CommissionBill> commissionBillList) {
        List<CommissionBillExcel> list = new ArrayList<>();
        for(CommissionBill commissionBill:commissionBillList){
            CommissionBillExcel cbe = new CommissionBillExcel();
            cbe.setCommissionBillId(commissionBill.getCommissionBillId());
            cbe.setBrandName(commissionBill.getBrandName());
            //如果是实体类型品牌则显示总部
            if (commissionBill.getEntityType().equals(SettlementConsts.ENTITY_TYPE_BRAND)) {
                cbe.setStoreName("总部");
            }else{
                cbe.setStoreName(commissionBill.getStoreName());
            }
            cbe.setTradeDate(DateFormatUtil.formatDate(commissionBill.getTradeDateStart())+ "至" +
                    DateFormatUtil.formatDate(commissionBill.getTradeDateEnd()));
            cbe.setCommissionBillCircle(DictionaryTypeUtil.getCommissionSettleCircleDes(commissionBill.getCommissionBillCircle()));
            cbe.setCommissionBillStatus(DictionaryTypeUtil.getCommissionSettleStatusDes(commissionBill.getCommissionBillStatus()));
            cbe.setCommissionAmount(commissionBill.getCommissionAmount()/100.0);
            cbe.setSettledAmount(commissionBill.getSettledAmount()/100.0);
            cbe.setUnSettleAmount((commissionBill.getCommissionAmount() - commissionBill.getSettledAmount())/100.0);
            list.add(cbe);
        }
        return list;
    }

    @Override
    public CommissionStatisticsVo commissionStatistics(QueryCommissionPara queryCommissionPara) {
        CommissionStatisticsVo csv = new CommissionStatisticsVo();

        if(StringUtils.isNotBlank(queryCommissionPara.getTradeDateStart())) {
            queryCommissionPara.setBeginTime(DateCalcUtil.getDayBegin(DateFormatUtil.parseDate(queryCommissionPara.getTradeDateStart())));
        }
        if(StringUtils.isNotBlank(queryCommissionPara.getTradeDateEnd())) {
            queryCommissionPara.setEndTime(DateCalcUtil.getDayEnd(DateFormatUtil.parseDate(queryCommissionPara.getTradeDateEnd())));
        }

        List<SettleBill> list = settleBillDao.getCommissionInfo(queryCommissionPara);
        List<CommissionDetail> commissionDetailList = TransferCommissionDetail(list);
        Integer count = settleBillDao.getCommissionInfoCount(queryCommissionPara);
        Long commissionSum = settleBillDao.getCommissionSum(queryCommissionPara);
        csv.setDataList(commissionDetailList);
        csv.setTotalCount(count);
        csv.setSumAmount(commissionSum);
        return csv;
    }

    @Override
    public void expCommissionStatisticsList(HttpServletResponse response, HttpServletRequest request, QueryCommissionPara req) throws IOException {
        if(StringUtils.isNotBlank(req.getTradeDateStart())) {
            req.setBeginTime(DateCalcUtil.getDayBegin(DateFormatUtil.parseDate(req.getTradeDateStart())));
        }
        if(StringUtils.isNotBlank(req.getTradeDateEnd())) {
            req.setEndTime(DateCalcUtil.getDayEnd(DateFormatUtil.parseDate(req.getTradeDateEnd())));
        }
        req.setPageNo(1);
        req.setPageSize(Integer.MAX_VALUE);
        List<SettleBill> list = settleBillDao.getCommissionInfo(req);
        List<CommissionDetail> commissionDetailList = TransferCommissionDetail(list);
        List<CommissionStatisticsExcel> listExcel = commissionStatisticsTransfer(commissionDetailList);

        String expFileName = "佣金统计";
        OutputStream out = null;
        response.reset();// 清空输出流
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String((expFileName + ".xls").getBytes(), "iso-8859-1"));
        out = response.getOutputStream();// 取得输出流
        try {
            new ExcelUtil<CommissionStatisticsExcel>("佣金统计", headersCommissionStatistics, fieldValuesCommissionStatistics, listExcel, out)
                    .ExportExcel();
        } catch (Exception e) {
            LOGGER.error("生成文件失败!", e);
        } finally {
            out.close();
        }

    }

    private List<CommissionStatisticsExcel> commissionStatisticsTransfer(List<CommissionDetail> commissionDetailList) {
        List<CommissionStatisticsExcel> list  = new ArrayList<>();
        for(CommissionDetail cd:commissionDetailList){
            CommissionStatisticsExcel cse = new CommissionStatisticsExcel();
            cse.setBrandName(cd.getBrandName());
            cse.setStoreName(cd.getStoreName());
            cse.setTradeDate(DateFormatUtil.formatDate(cd.getTredeDate()));
            cse.setCrrentDayTurnover(cd.getCrrentDayTurnover()/100.0);
            cse.setTurnoverPoints(null == cd.getTurnoverPoints()?0.0:cd.getTurnoverPoints()/100.0);
            cse.setCommissionAmount(null == cd.getCommissionAmount()?0.0:cd.getCommissionAmount()/100.0);
            list.add(cse);
        }
        return list;
    }


    /**
     * 生成佣金结算单
     *
     * @param date    交易日期
     * @param brandId 品牌编号
     * @return
     */
    @Override
    public CommissionBillGenResultVo genCommissionBill(Date date, String brandId) {
        CommissionBillGenResultVo result = new CommissionBillGenResultVo(false);
        if(date == null){
            result.setMessage("date is null");
            return result;
        }
        if(StringUtils.isBlank(brandId)){
            brandId = null;
        }
        String datestr = DateFormatUtil.formatDateNoSep(date);
        result.setDate(datestr);
        result.setBrandId(brandId);
        LOGGER.info("生成佣金结算单开始，日期: {}，指定品牌: {}", datestr, brandId);
        //1.获取所有可以佣金结算的品牌列表

        CommissionSettleEntityReq commissionSettleEntityReq = new CommissionSettleEntityReq();
        commissionSettleEntityReq.setEntityType(SettlementConsts.ENTITY_TYPE_BRAND);//主体类型：品牌
        commissionSettleEntityReq.setAvailStatus(SettlementConsts.AVAIL_STATUS_START);//状态：开启
        commissionSettleEntityReq.setSeparateSettle(SettlementConsts.SEPARATE_SETTLE_YES);//独立结算状态：是
        commissionSettleEntityReq.setCommissionSettlementSwitch(CommissionConsts.COMMISSION_SETTLEMENT_SWITCH_YES);//生成佣金结算单：是
        commissionSettleEntityReq.setBrandId(brandId);
        List<SettleEntity>  settleEntityBrandList= settleEntityDao.selectCanSessionSettle(commissionSettleEntityReq);
        if (settleEntityBrandList == null || settleEntityBrandList.isEmpty()) {
            result.setMessage("settleEntityBrandList is empty, brandId: " + brandId);
            return result;
        }
        result.setTotalCount(settleEntityBrandList.size());

        for (SettleEntity settleEntity : settleEntityBrandList) {//获取品牌列表
            Boolean checkFlag =  checkCommissionSettleCondition(settleEntity,date);
                if(checkFlag){
                    try {
                        List<SettleEntity> settleEntityStoreList = getStoreSettleEntityByBrandId(settleEntity);//获取门店列表
                        if (settleEntityStoreList != null) {
                            for (SettleEntity settleStoreEntity : settleEntityStoreList) {
                                genCommission(date, settleStoreEntity);//针对门店生成佣金结算
                            }
                        }
                        genCommission(date, settleEntity);//生成品牌对账单
                        result.setSuccCount(result.getSuccCount() + 1);

                    } catch (Exception e) {
                        LOGGER.error("genSettleBill error, brandId: {}", settleEntity.getBrandId());
                        LOGGER.error("genSettleBill error" ,e);
                        result.getFailBrandIdList().add(settleEntity.getBrandId());
                    }
                }
            }
        LOGGER.info("生成结算单结束，日期: {}，指定品牌: {}", datestr, brandId);
        if(result.getTotalCount() == result.getSuccCount()){
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 指定日期生成佣金结算单
     *
     * @param date
     * @param brandId
     * @return
     */
    @Override
    public CommissionBillGenResultVo designationCommissionInfo(Date date, String brandId) {
        CommissionBillGenResultVo result = new CommissionBillGenResultVo(false);
        if(date == null){
            result.setMessage("date is null");
            return result;
        }
        String datestr = DateFormatUtil.formatDateNoSep(date);
        result.setDate(datestr);
        result.setBrandId(brandId);
        LOGGER.info("生成指定佣金结算单开始，指定日期: {}，指定品牌: {}", datestr, brandId);
        //1. 通过条件查询佣金结算单
        QueryCommissionPara qcp = new QueryCommissionPara();
        qcp.setBrandId(brandId);
        qcp.setBeginTime(date);
        qcp.setEndTime(date);
        List<CommissionBill> commissionBillList = commissionBillDao.queryCommissionSettle(qcp);
        if(commissionBillList == null){
            result.setMessage("未查询到已生成的佣金结算单");
            result.setSuccCount(0);
            result.setSuccess(false);
            return result;
        }
        for(CommissionBill cb:commissionBillList){
            //2. 根据佣金结算单信息查询商户结算单信息并更新佣金结算
            QuerySettleBillPara querySettleBillPara = new QuerySettleBillPara();
            querySettleBillPara.setBrandId(cb.getBrandId());
            querySettleBillPara.setStoreId(cb.getStoreId());
            querySettleBillPara.setEntityType(cb.getEntityType());
            querySettleBillPara.setTradeDateStart(cb.getTradeDateStart());
            querySettleBillPara.setTradeDateEnd(cb.getTradeDateEnd());
            Long commissionAmount = settleBillDao.getSumCosssionAmount(querySettleBillPara);

            LOGGER.info("商户结算单佣金总和  {}", commissionAmount);
            LOGGER.info("佣金结算单状态 {}", cb.getCommissionBillStatus());
            LOGGER.info("原来佣金结算单金额 {}", cb.getCommissionAmount());

            SettleBill  settleBillPara = new SettleBill();
            settleBillPara.setBrandId(cb.getBrandId());
            settleBillPara.setStoreId(cb.getStoreId());
            settleBillPara.setEntityType(cb.getEntityType());
            settleBillPara.setTradeDateStart(cb.getTradeDateStart());
            Long commissionDeduction =settleBillDao.getSumCommissionDeducted(settleBillPara);
            LOGGER.info("原商户结算单已扣除金额总和{}", commissionDeduction);


            if(CommissionConsts.COMMISSION_BILL_STATUS_SETTLED.equals(cb.getCommissionBillStatus())){//如果原来佣金结算单是已结算的
                if(commissionAmount > cb.getCommissionAmount()){ //如果现在金额大于原来的佣金金额则修改状态为部分结算
                    cb.setCommissionBillStatus(CommissionConsts.COMMISSION_BILL_STATUS_PARTIALLY_SETTLED);
                }else if(commissionAmount < cb.getCommissionAmount()){// ---- 如果现在的佣金金额小于原来的佣金金额则需要退回处理，相当于多扣佣金
                    HandlingMoreDeductions(commissionAmount,cb);
                }
            }else if (CommissionConsts.COMMISSION_BILL_STATUS_UNSETTLEMENT.equals(cb.getCommissionBillStatus())) {// 如果原来佣金结算单是未结算的
                if(commissionAmount > cb.getCommissionAmount()){ //如果现在金额大于原来的佣金金额则状态，还是未结算金额
                    cb.setCommissionBillStatus(CommissionConsts.COMMISSION_BILL_STATUS_UNSETTLEMENT);
                }

            }else if (CommissionConsts.COMMISSION_BILL_STATUS_PARTIALLY_SETTLED.equals(cb.getCommissionBillStatus())){// 如果原来佣金结算状态是部分结算的
                if(commissionAmount > cb.getCommissionAmount()){ //如果现在金额大于原来的佣金金额则状态不变还是部分结算
                    cb.setCommissionBillStatus(CommissionConsts.COMMISSION_BILL_STATUS_PARTIALLY_SETTLED);
                }else if(commissionAmount < cb.getCommissionAmount()){//---- 如果现在的佣金金额小于原来的佣金金额则需要退回处理，相当于多扣佣金
                    HandlingMoreDeductions(commissionAmount,cb);
                }
            }

            cb.setCommissionAmount(commissionAmount);
            cb.setUpdateTime(new Date());
            commissionBillDao.updateByPrimaryKeySelective(cb);
            CommissionDeductionDetail cdd = new CommissionDeductionDetail();
            cdd.setCommissionBillId(cb.getCommissionBillId());//佣金结算单
            cdd.setCommissionAmount(commissionAmount);//佣金
            cdd.setUpdateTime(new Date());//更新记录
            commissionDeductionDetailDao.updateCommissionAmountByCommissionBillId(cdd);
        }
        result.setMessage("已更新佣金结算单");
        result.setSuccess(true);
        return result;
    }

    /**
     * 处理多扣除佣金问题
     * @param commissionAmount
     * @param cb
     *
     */
    private void HandlingMoreDeductions(Long commissionAmount, CommissionBill cb) {
        LOGGER.info("最新佣金金额{}",commissionAmount);
        LOGGER.info("佣金结算单：{}",JsonUtil.toJson(cb));
        if(cb !=null){
            //现在佣金大于已结算的佣金
            if(commissionAmount.longValue() > cb.getSettledAmount().longValue()){
                cb.setUpdateTime(new Date());
                cb.setCommissionBillStatus(CommissionConsts.COMMISSION_BILL_STATUS_PARTIALLY_SETTLED);//部分结算
            //现在佣金小于已结算的佣金
            }else if(commissionAmount.longValue() < cb.getSettledAmount().longValue()){
                //现在佣金-已结算用金额，就是需要返还的金额
                Long moreDeductionAmount = cb.getSettledAmount() - commissionAmount;
                List<CommissionDeductionDetail> cddList = commissionDeductionDetailDao.selectBycommissionBillId(cb.getCommissionBillId());
                for(CommissionDeductionDetail cdd:cddList){
                    //如果本扣除明细已经扣除的金额大于等于多扣除的金额，则直接扣除
                    if(cdd.getDeductionAmount().longValue() >= moreDeductionAmount.longValue()){

                        if(StringUtils.isBlank(cdd.getBillId())){
                            continue;
                        }else{
                            cdd.setDeductionAmount(cdd.getDeductionAmount() - moreDeductionAmount);
                            cdd.setUpdateTime(new Date());
                            cdd.setSettledAmount(cdd.getSettledAmount()-moreDeductionAmount);
                            //更新扣除佣金结算金额
                            commissionDeductionDetailDao.updateCommissionDeductionAmountByCommissionBillId(cdd);
                            LOGGER.info("更新扣除明细结算金额:{}",JsonUtil.toJson(cdd));


                            //更新商户结算单
                            String billId = cdd.getBillId();//商户结算单
                            SettleBill settleBillPara = new SettleBill();
                            settleBillPara.setBillId(billId);
                            SettleBill sb = settleBillDao.getSingleSettleBill(settleBillPara);
                            LOGGER.info("获取商户结算单{}",JsonUtil.toJson(sb));
                            sb.setCommissionDeduction(sb.getCommissionDeduction() - moreDeductionAmount);
                            sb.setSettleAmt(sb.getSettleAmt()+moreDeductionAmount);
                            //需要给回退的金额加到分账主体中
                            sb.setEntityDivideAmt(sb.getEntityDivideAmt()+moreDeductionAmount);
                            settleBillDao.updateSettleBySettleId(sb);
                            LOGGER.info("需要给回退的金额加到分账主体中{}",JsonUtil.toJson(sb));
                            //更新品牌分账金额
                            updateBrandEntityDivideAmt(sb);

                        }
                        break;
                    }else{//如果本扣除明细已经扣除的金额小于多扣除的金额，则需要分多次进行扣除

                        if(StringUtils.isBlank(cdd.getBillId())){
                            continue;
                        }else{
                            cdd.setDeductionAmount(cdd.getDeductionAmount()-cdd.getDeductionAmount());
                            cdd.setSettledAmount(cdd.getSettledAmount()-cdd.getDeductionAmount());//已结算金额
                            cdd.setUpdateTime(new Date());
                            //更新扣除佣金结算金额
                            commissionDeductionDetailDao.updateCommissionDeductionAmountByCommissionBillId(cdd);
                            LOGGER.info("更新扣除明细结算金额:{}",JsonUtil.toJson(cdd));

                            //更新商户结算单
                            String billId = cdd.getBillId();//商户结算单
                            SettleBill settleBillPara = new SettleBill();
                            settleBillPara.setBillId(billId);
                            SettleBill sb = settleBillDao.getSingleSettleBill(settleBillPara);
                            LOGGER.info("获取商户结算单{}",JsonUtil.toJson(sb));
                            sb.setCommissionDeduction(0L);
                            //需要给回退的金额加到分账主体中
                            sb.setEntityDivideAmt(sb.getEntityDivideAmt()+cdd.getDeductionAmount());
                            settleBillDao.updateSettleBySettleId(sb);
                            LOGGER.info("需要给回退的金额加到分账主体中{}",JsonUtil.toJson(sb));

                            //更新品牌分账金额
                            updateBrandEntityDivideAmt(sb);
                        }

                        //每次需要用多扣除金额减去已扣除金额
                        moreDeductionAmount -= cdd.getDeductionAmount();
                        //如果多扣除金额等于零的时候结束循环
                        if(moreDeductionAmount == 0){
                            break;
                        }
                    }
                }
                cb.setUpdateTime(new Date());
                cb.setSettledAmount(cb.getSettledAmount()-moreDeductionAmount);//已结算金额需要减去多扣除的佣金
                LOGGER.info("已结算佣金：{}  最新的佣金{}",cb.getSettledAmount(),commissionAmount);

                if(cb.getSettledAmount().longValue() == commissionAmount.longValue()){//如果已经结算的金额等于最新的佣金金额，则为已结算
                    cb.setCommissionBillStatus(CommissionConsts.COMMISSION_BILL_STATUS_SETTLED);//已结算
                }

                if(cb.getSettledAmount().longValue() < commissionAmount.longValue()){//如果已经结算的金额小于最新的佣金金额，则为部分结算
                    cb.setCommissionBillStatus(CommissionConsts.COMMISSION_BILL_STATUS_PARTIALLY_SETTLED);//部分结算
                }
                //现在佣金等于已结算的佣金
            }else if(commissionAmount.longValue()==cb.getSettledAmount().longValue()){//如果已经结算的金额等于最新的佣金金额，则为已结算
                cb.setUpdateTime(new Date());
                cb.setCommissionBillStatus(CommissionConsts.COMMISSION_BILL_STATUS_SETTLED);//已结算
            }
                commissionBillDao.updateByPrimaryKeySelective(cb);//更新佣金结算单 更新时间 已结算佣金  佣金结算状态
            }
    }

    /**
     * 更新品牌分账金额
     * @param sb
     */
    private void updateBrandEntityDivideAmt(SettleBill sb) {
        SettleBill settleBillPara = new SettleBill();
        settleBillPara.setBrandId(sb.getBrandId());
        settleBillPara.setSettleDate(sb.getSettleDate());
        settleBillDao.getBrandDivideAmt(settleBillPara);
        //计算门店分账总额
        Long sumDivideAmt = settleBillDao.getBrandDivideAmt(settleBillPara);
        LOGGER.info("计算门店分账总额{}",sumDivideAmt);

        //获取品牌结算单
        SettleBill settleBillBrand = new SettleBill();
        settleBillBrand.setBrandId(sb.getBrandId());
        settleBillBrand.setEntityType(SettlementConsts.ENTITY_TYPE_BRAND);
        settleBillBrand.setTradeDateStart(sb.getTradeDateStart());
        LOGGER.info("获取品牌结算单信息",JsonUtil.toJson(settleBillBrand));
        SettleBill  settleBill = settleBillDao.getSettleBillByParameter(settleBillBrand);
        settleBill.setEntityDivideAmt(sumDivideAmt);
        //更新分账金额
        settleBillDao.updateSettleBySettleId(settleBill);
        LOGGER.info("更新分账金额",JsonUtil.toJson(settleBill));
    }

    @Override
    public List<CommissionDeductionDetail> getCommissionDeductionDetail(CommissionDeductionDetail commissionDeductionDetail) {
        return commissionDeductionDetailDao.selectSelective(commissionDeductionDetail);
    }

    /**
     * 判断是否存在佣金结算单
     * @param brandId
     * @param date
     * @return
     */
    @Override
    public int judeCommissionSettleId( Date date, String brandId) {
        //查询是否生成佣金结算单
        QueryCommissionPara qcp = new QueryCommissionPara();
        qcp.setBrandId(brandId);
        qcp.setBeginTime(date);
        qcp.setEndTime(date);
        List<CommissionBill> commissionBillList = commissionBillDao.queryCommissionSettle(qcp);
        return commissionBillList.size();
    }

    /**
     * 佣金结算单
     * @param date
     * @param settleEntity
     */
    private void genCommission(Date date, SettleEntity settleEntity) {
        List<Date[]> listDates = new ArrayList<>();
        LOGGER.info("genForStore, date: {}, info: {}", date, JsonUtil.toJson(settleEntity));
        Date commissionLastSettleTime = settleEntity.getCommissionLastSettleTime();
        Date startDate = DateCalcUtil.getDayBegin(DateCalcUtil.getNextDay(commissionLastSettleTime));//获取交易的开始时间
        Date endDate = DateCalcUtil.getDayEnd(date);
        LOGGER.info("交易开始日期 {}, 交易结束日期 {}", startDate, endDate);
        //按日 结算佣金
        if(CommissionConsts.COMMISSION_BILL_CIRCLE_DAY.equals(settleEntity.getCommissionSettlementCircle())){
            listDates = DateCalcUtil.splitInDay(startDate, endDate);
        }

        //按月结算佣金
        if(CommissionConsts.COMMISSION_BILL_CIRCLE_MONTH.equals(settleEntity.getCommissionSettlementCircle())){
            listDates = DateCalcUtil.splitInMonth(startDate, endDate);
        }

        //按季度结算佣金
        if(CommissionConsts.COMMISSION_BILL_CIRCLE_SEASON.equals(settleEntity.getCommissionSettlementCircle())){
            listDates = DateUtils.splitInSeason(startDate, endDate);
        }

        //按年度结算佣金
        if(CommissionConsts.COMMISSION_BILL_CIRCLE_YEAR.equals(settleEntity.getCommissionSettlementCircle())){
           Date[] years = new Date[]{startDate,endDate};
            listDates.add(years);
        }

        for (Date[] dates:listDates) {
            //1. 根据品牌结算主体信息来查询佣金信息
            QuerySettleBillPara querySettleBillPara = new QuerySettleBillPara();
            querySettleBillPara.setBrandId(settleEntity.getBrandId());
            querySettleBillPara.setStoreId(settleEntity.getStoreId());
            querySettleBillPara.setEntityType(settleEntity.getEntityType());
            querySettleBillPara.setSeparateSettle(settleEntity.getSeparateSettle());
            querySettleBillPara.setTradeDateStart(dates[0]);
            querySettleBillPara.setTradeDateEnd(dates[1]);
            Long cosssionAmount = settleBillDao.getSumCosssionAmount(querySettleBillPara);
            LOGGER.info("佣金总金额",cosssionAmount);
            //2.生成佣金结算单
            insertCommissionBill(settleEntity,cosssionAmount,dates[0],dates[1]);
            //3.更新结算主体表佣金最后结算日期
            //3.1如果当前日期大于最后佣金结算时间
            if(dates[1].after(settleEntity.getCommissionLastSettleTime())){
                settleEntity.setCommissionLastSettleTime(dates[1]);
            }
            settleEntity.setUpdateTime(new Date());
            settleEntityDao.updateLastCommissionSettleTime(settleEntity);
        }

    }

    /**
     * 插入佣金结算
     * @param settleStoreEntity
     * @param cosssionAmount
     */
    private void insertCommissionBill(SettleEntity settleStoreEntity, Long cosssionAmount, Date startDate, Date endDate) {
        Map<String, BrandAllInfo>  brandMap= BaseBrandInfoUtil.getBrandAll();
        Map<String, BrandStoreInfo> storeMap =  BaseBrandStoreUtil.getBrandAllStore(settleStoreEntity.getBrandId());
        CommissionBill commissionBill = new CommissionBill();
        commissionBill.setCommissionBillId(BusinessIdUtils.genCommissionBillId(settleStoreEntity.getEntityId(),endDate));
        commissionBill.setEntityId(settleStoreEntity.getEntityId());
        commissionBill.setEntityType(settleStoreEntity.getEntityType());
        commissionBill.setEntityName(settleStoreEntity.getEntityName());
        commissionBill.setBrandId(settleStoreEntity.getBrandId());
        commissionBill.setBrandName(brandMap.get(settleStoreEntity.getBrandId()).getBrandName());
        commissionBill.setStoreId(settleStoreEntity.getStoreId());
        commissionBill.setStoreName(settleStoreEntity.getStoreId() != null?storeMap.get(settleStoreEntity.getStoreId()).getStoreName():"");
        commissionBill.setTradeDateStart(startDate);
        commissionBill.setTradeDateEnd(endDate);
        commissionBill.setCommissionBillCircle(settleStoreEntity.getCommissionSettlementCircle());
        commissionBill.setCommissionBillStatus(CommissionConsts.COMMISSION_BILL_STATUS_UNSETTLEMENT);
        commissionBill.setCommissionAmount(cosssionAmount);
        commissionBill.setCreateTime(new Date());
        commissionBill.setUpdateTime(new Date());
        commissionBill.setOnlineCommission(settleStoreEntity.getOnlineCommission());
        commissionBillDao.insertSelective(commissionBill);
    }

    /**
     * 通过条件判断是否生成佣金结算单号
     * @param settleEntity
     * @param date 交易日期
     * @return
     */
    private Boolean checkCommissionSettleCondition(SettleEntity settleEntity, Date date) {
        Boolean checkFlag = false;

        Date date1 = DateCalcUtil.getNextDay(date);
        String actDate = DateFormatUtil.formatDateNoSep(date1);//实际日期 20180401

        if(CommissionConsts.COMMISSION_BILL_CIRCLE_DAY.equals(settleEntity.getCommissionSettlementCircle())){//结算周期日
            checkFlag = true;
        }

        if(CommissionConsts.COMMISSION_BILL_CIRCLE_MONTH.equals(settleEntity.getCommissionSettlementCircle())){//结算周期月
            //1 月 .下个月的第一天则生成上个月佣金结算单
            Date preDate = DateCalcUtil.getMonthBegin(date1);//yyyy-mm-01
            String preDateStr = DateFormatUtil.formatDateNoSep(preDate);
            LOGGER.info("预计日期：{}, 实际日期：{}", preDateStr, actDate);

            if(actDate.equals(preDateStr)){
                checkFlag = true;
            }
        }


        if(CommissionConsts.COMMISSION_BILL_CIRCLE_SEASON.equals(settleEntity.getCommissionSettlementCircle())){//结算周期季度
            //2 季度. 每个季度的第一天则生成佣金结算单号

            Date preDate = DateUtils.getFirstDateOfSeason(date1);//预计20180401
            String preDateStr = DateFormatUtil.formatDateNoSep(preDate);
            LOGGER.info("预计日期：{}, 实际日期：{}", preDateStr, actDate);

            if(actDate.equals(preDateStr)){
                checkFlag = true;
            }
        }

        if(CommissionConsts.COMMISSION_BILL_CIRCLE_YEAR.equals(settleEntity.getCommissionSettlementCircle())){//结算周期季度
            //3 年度. 如果最后结算日期 下一年 第一天等于date 下一天 则，认为生成上一年佣金结算单

            Date date2 =  DateFormatUtil.parseDateNoSep(DateCalcUtil.getYear(date1)+"0101");
            String preDate = DateFormatUtil.formatDateNoSep(date2);//预计日期

            LOGGER.info("预计日期：{}  实际日期：{}",preDate,actDate);
            if(actDate.equals(preDate)){
                checkFlag = true;
            }
        }
        return checkFlag;
    }


    /**
     * 通过品牌id获取门店结算基础信息
     * @param settleEntity
     * @return
     */
    private List<SettleEntity> getStoreSettleEntityByBrandId(SettleEntity settleEntity) {
        LOGGER.info("getStoreSettleEntityByBrandId brandId:{}", settleEntity.getBrandId());

        CommissionSettleEntityReq commissionSettleEntityReq = new CommissionSettleEntityReq();
        commissionSettleEntityReq.setBrandId(settleEntity.getBrandId());//品牌id
        commissionSettleEntityReq.setEntityType(SettlementConsts.ENTITY_TYPE_STORE);//主体类型：门店
        commissionSettleEntityReq.setAvailStatus(SettlementConsts.AVAIL_STATUS_START);//状态：开启
        commissionSettleEntityReq.setSeparateSettle(SettlementConsts.SEPARATE_SETTLE_YES);//独立结算状态：是
        commissionSettleEntityReq.setCommissionSettlementSwitch(CommissionConsts.COMMISSION_SETTLEMENT_SWITCH_YES);//生成佣金结算单：是
        List<SettleEntity> settleEntityStoreList = settleEntityDao.selectCanSessionSettle(commissionSettleEntityReq);//获取门店列表
        return settleEntityStoreList;
    }


}
