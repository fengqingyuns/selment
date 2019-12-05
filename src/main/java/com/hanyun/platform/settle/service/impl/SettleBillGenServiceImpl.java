package com.hanyun.platform.settle.service.impl;

import com.hanyun.ground.util.date.DateCalcUtil;
import com.hanyun.ground.util.date.DateFormatUtil;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.platform.settle.consts.CommissionConsts;
import com.hanyun.platform.settle.consts.SettlementConsts;
import com.hanyun.platform.settle.dao.*;
import com.hanyun.platform.settle.domain.*;
import com.hanyun.platform.settle.service.CommissionBillService;
import com.hanyun.platform.settle.service.SettleBillGenService;
import com.hanyun.platform.settle.util.BusinessIdUtils;
import com.hanyun.platform.settle.vo.SettleEntityReq;
import com.hanyun.platform.settle.vo.settlebill.*;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by jack on 2017/4/6.
 */
@Service
public class SettleBillGenServiceImpl implements SettleBillGenService {
    private static Logger LOGGER = LoggerFactory.getLogger(SettleBillGenServiceImpl.class);

    @Resource
    private SettleEntityDao settleEntityDao;
    @Resource
    private SettleBillDao settleBillDao;
    @Resource
    private SettleBillPaychnDao settleBillPaychnDao;
    @Resource
    private SettleBillDetailDao settleBillDetailDao;
    @Resource
    private CommissionBillService commissionBillService;
    @Resource
    private CommissionBillDao commissionBillDao;
    
    /**
     * 预生成结算单
     * @param date 交易日期
     * @param brandId 品牌编号
     * @return
     */
    @Override
    public SettleBillGenResultVo preGenSettleBill(Date date, String brandId) {
        SettleBillGenResultVo result = new SettleBillGenResultVo(false);     
        if (date == null) {
            result.setMessage("date is null");
            return result;
        }
        if(StringUtils.isBlank(brandId)){
            brandId = null;
        }
        String datastr = DateFormatUtil.formatDateNoSep(date);
        result.setDate(datastr);
        result.setBrandId(brandId);
        
        LOGGER.info("预生成结算单开始，日期: {}，指定品牌: {}", datastr, brandId);
        
        SettleEntityReq SettleEntityReq = new SettleEntityReq();
        SettleEntityReq.setEntityType(SettlementConsts.ENTITY_TYPE_BRAND);//主体类型：品牌
        SettleEntityReq.setAvailStatus(SettlementConsts.AVAIL_STATUS_START);//状态：开启
        SettleEntityReq.setSeparateSettle(SettlementConsts.SEPARATE_SETTLE_YES);//独立结算状态：是
        SettleEntityReq.setBrandId(brandId);// 品牌编号
        List<SettleEntity> settleEntityBrandList = settleEntityDao.selectCanSettle(SettleEntityReq);
        
        if (settleEntityBrandList == null || settleEntityBrandList.isEmpty()) {
            result.setMessage("settleEntityBrandList is empty, brandId: " + brandId);
            return result;
        }
        result.setTotalCount(settleEntityBrandList.size());
        
        for (SettleEntity settleEntity : settleEntityBrandList) {
            try {
                List<SettleEntity> settleEntityStoreList = getStoreSettleEntityByBrandId(settleEntity);//获取门店列表
                if (settleEntityStoreList != null) {
                    for (SettleEntity settleStoreEntity : settleEntityStoreList) {
                        preGenForSettle(date, settleStoreEntity);
                    }
                }
                preGenForSettle(date, settleEntity);
                result.setSuccCount(result.getSuccCount() + 1);
                
            } catch (Exception e) {
                LOGGER.error("preGenSettleBill error, brandId: {}", settleEntity.getBrandId());
                LOGGER.error("preGenSettleBill error" ,e);
                result.getFailBrandIdList().add(settleEntity.getBrandId());
            }
        }
        LOGGER.info("预生成结算单结束，日期: {}，指定品牌: {}", datastr, brandId);
        
        if(result.getTotalCount() == result.getSuccCount()){
            result.setSuccess(true);
        }
        
        return result;
    }

    /**
     * 预生成结算单
     * @param tradeDate 交易日期
     * @param settleEntity
     */
    private void preGenForSettle(Date tradeDate, SettleEntity settleEntity) {
        LOGGER.info("preGenForSettle, date: {}, info: {}", tradeDate, JsonUtil.toJson(settleEntity));
        Integer settleCircle = settleEntity.getSettleCircle();//获取结算周期
        
        Date now = new Date();
        
        SettleBill settleBill = new SettleBill();
        settleBill.setCreateTime(now);//创建日期
        settleBill.setUpdateTime(now);
        settleBill.setBillId(BusinessIdUtils.genSettleBillId(settleEntity.getEntityId(),tradeDate));//设置结算单号
        settleBill.setEntityId(settleEntity.getEntityId());//主体编号
        settleBill.setEntityType(settleEntity.getEntityType());//主体类型
        settleBill.setBrandId(settleEntity.getBrandId());//所属品牌编号
        settleBill.setStoreId(settleEntity.getStoreId());//所属门店编号
        settleBill.setSeparateSettle(settleEntity.getSeparateSettle());//独立结算
        settleBill.setCapitalCollect(settleEntity.getCapitalCollect());//资金归集
        settleBill.setCapitalCollectType(settleEntity.getCapitalCollectType());//资金归集方式
        settleBill.setSettleCircle(settleEntity.getSettleCircle());//结算周期
        settleBill.setTradeDateStart(tradeDate);//交易日期开始
        settleBill.setTradeDateEnd(tradeDate);//交易日期结束
        settleBill.setSettleDate(DateCalcUtil.addDays(tradeDate, settleCircle));//结算日期
        settleBill.setSettleStatus(SettlementConsts.NEW_SETTLE_STATUS_NOT_GENES);//结算状态 1-未生成，10-未结算，20-已结算
        
        SettleBill settleBillPara = new SettleBill();
        settleBillPara.setBrandId(settleBill.getBrandId());
        settleBillPara.setStoreId(settleBill.getStoreId());
        settleBillPara.setEntityType(settleBill.getEntityType());
        settleBillPara.setTradeDateStart(settleBill.getTradeDateStart());
        SettleBill settleBillQry = settleBillDao.getSettleBillByParameter(settleBillPara);//判断是否已经初始化记录
        if(settleBillQry == null){
            settleBillDao.insertSelective(settleBill);
        }
    }

    /**
     * 通过品牌id获取门店结算基础信息
     * @param settleEntity
     * @return
     */
    private List<SettleEntity> getStoreSettleEntityByBrandId(SettleEntity settleEntity) {
        LOGGER.info("getStoreSettleEntityByBrandId brandId:{}", settleEntity.getBrandId());
        
        SettleEntityReq SettleEntityReq = new SettleEntityReq();
        SettleEntityReq.setBrandId(settleEntity.getBrandId());//品牌Id
        SettleEntityReq.setEntityType(SettlementConsts.ENTITY_TYPE_STORE);//主体类型 门店
        //SettleEntityReq.setCapitalCollect(SettlementConsts.CAPITAL_COLLECT_NO);//资金归集 针对于门店由品牌统一归集
        SettleEntityReq.setSeparateSettle(SettlementConsts.SEPARATE_SETTLE_YES);//独立结算状态：是
        SettleEntityReq.setAvailStatus(SettlementConsts.AVAIL_STATUS_START);//状态启用
        List<SettleEntity> settleEntityStoreList = settleEntityDao.selectCanSettle(SettleEntityReq);//获取门店列表
        
        return settleEntityStoreList;
    }

    /**
     * 生成结算单
     * @param date 交易日期
     * @param brandId 品牌编号
     * @return
     */
    @Override
    public SettleBillGenResultVo genSettleBill(Date date, String brandId){
        SettleBillGenResultVo result = new SettleBillGenResultVo(false);     
        if (date == null) {
            result.setMessage("date is null");
            return result;
        }
        if(StringUtils.isBlank(brandId)){
            brandId = null;
        }
        String datastr = DateFormatUtil.formatDateNoSep(date);
        result.setDate(datastr);
        result.setBrandId(brandId);
        
        LOGGER.info("生成结算单开始，日期: {}，指定品牌: {}", datastr, brandId);
        
        //1.获取所有可以结算品牌列表
        SettleEntityReq SettleEntityReq = new SettleEntityReq();
        SettleEntityReq.setEntityType(SettlementConsts.ENTITY_TYPE_BRAND);//主体类型：品牌
        SettleEntityReq.setAvailStatus(SettlementConsts.AVAIL_STATUS_START);//状态：开启
        SettleEntityReq.setSeparateSettle(SettlementConsts.SEPARATE_SETTLE_YES);//独立结算状态：是
        SettleEntityReq.setBrandId(brandId);// 品牌编号
        List<SettleEntity> settleEntityBrandList = settleEntityDao.selectCanSettle(SettleEntityReq);
        
        if (settleEntityBrandList == null || settleEntityBrandList.isEmpty()) {
            result.setMessage("settleEntityBrandList is empty, brandId: " + brandId);
            return result;
        }
        result.setTotalCount(settleEntityBrandList.size());
        
        for (SettleEntity settleEntity : settleEntityBrandList) {//获取品牌列表
            try {
                List<SettleEntity> settleEntityStoreList = getStoreSettleEntityByBrandId(settleEntity);//获取门店列表
                if (settleEntityStoreList != null) {
                    for (SettleEntity settleStoreEntity : settleEntityStoreList) {
                        genForStore(date, settleStoreEntity);//针对门店生成结算单
                    }
                }
                genForBrand(date, settleEntity);//生成品牌对账单
                result.setSuccCount(result.getSuccCount() + 1);
                
            } catch (Exception e) {
                LOGGER.error("genSettleBill error, brandId: {}", settleEntity.getBrandId());
                LOGGER.error("genSettleBill error" ,e);
                result.getFailBrandIdList().add(settleEntity.getBrandId());
            }
        }
        LOGGER.info("生成结算单结束，日期: {}，指定品牌: {}", datastr, brandId);
        
        if(result.getTotalCount() == result.getSuccCount()){
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 品牌生成结算单
     * @param tradeDate
     * @param settleEntity
     */
    private void genForBrand(Date tradeDate, SettleEntity settleEntity) {
        LOGGER.info("genForBrand, date: {}, info: {}", tradeDate, JsonUtil.toJson(settleEntity));
        Date lastSettleDate = settleEntity.getLastSettleTime();//获取最后结算日期
        long turnoverPoints = settleEntity.getTurnoverPoints() == null ? 0:settleEntity.getTurnoverPoints();
        SettleBill settleBillPara = new SettleBill();
        settleBillPara.setBrandId(settleEntity.getBrandId());
        settleBillPara.setEntityType(settleEntity.getEntityType());
        settleBillPara.setTradeDateStart(tradeDate);
        LOGGER.info("genForBrand 查询参数信息: {}", JsonUtil.toJson(settleBillPara));
        SettleBill settleBill = settleBillDao.getSettleBillByParameter(settleBillPara);//通过参数查询已经预创建的结算单信息
        
        if (settleBill == null) {
            return ;
        }
        
        doCalcSettleBill(settleBill, tradeDate);
        settleBill.setSettleStatus(SettlementConsts.NEW_SETTLE_STATUS_NOT_SETTLE);//结算状态 1-未生成，10-未结算，20-已结算
        settleBill.setAuditStatus(SettlementConsts.PROCESS_STATUS_NOT_STARTED);
        
        Long allStoreDivideAmt = settleBillDao.getBrandDivideAmt(settleBill);//获取所有的门店分账和
        allStoreDivideAmt = (allStoreDivideAmt == null) ? 0 : allStoreDivideAmt;
        settleBill.setEntityDivideAmt(allStoreDivideAmt);

        //流水提点
        settleBill.setTurnoverPoints(turnoverPoints);
        //获取独立门店流水总和
        SettleBill brandSettleBill = new SettleBill();
        brandSettleBill.setBrandId(settleBill.getBrandId());
        brandSettleBill.setEntityType(SettlementConsts.ENTITY_TYPE_STORE);
        brandSettleBill.setSeparateSettle(settleBill.getSeparateSettle());
        brandSettleBill.setTradeDateStart(tradeDate);
        Long storeFlowAnt = settleBillDao.getStoreFlowAmtSum(brandSettleBill) == null ? 0L : settleBillDao.getStoreFlowAmtSum(brandSettleBill);
        //品牌当日佣金
        long todayCommission = (settleBill.getTotalFlowAmt() - storeFlowAnt.longValue()) * turnoverPoints / 10000;
        settleBill.setTodayCommission(todayCommission);
        settleBill.setBrandFlowAmt(settleBill.getTotalFlowAmt() - storeFlowAnt.longValue());

        //佣金扣除金额
        long commissionDeductionPrice = settleBill.getCommissionDeduction() == null ? 0 : settleBill.getCommissionDeduction().longValue();
        LOGGER.info("品牌佣金扣除金额："+ commissionDeductionPrice);
        //获取独立门店扣除佣金总和
        Long sumCommissionDeducted = settleBillDao.getSumCommissionDeducted(brandSettleBill) == null ? 0L : settleBillDao.getSumCommissionDeducted(brandSettleBill);
        //结算金额 = 汉云实收 + 异常订单合计 -汉云分成 - 门店分账
        settleBill.setSettleAmt(
                settleBill.getPlatformActualRecAmt() + settleBill.getDiffTotalAmt() 
                - settleBill.getPlatformShareAmt() - settleBill.getEntityDivideAmt() - (commissionDeductionPrice + sumCommissionDeducted));

//        settleBill.setCommissionDeduction(0L);
        LOGGER.info("genForBrand, 品牌更新对象 settleBill: {}", JsonUtil.toJson(settleBill));
        settleBillDao.updateSettleBySettleId(settleBill);

        // 更新最新已结算的交易日期
        if(tradeDate.after(lastSettleDate)){
            SettleEntity updateSettleEntity = new SettleEntity();
            updateSettleEntity.setBrandId(settleEntity.getBrandId());
            updateSettleEntity.setStoreId(settleEntity.getStoreId());
            updateSettleEntity.setLastSettleTime(DateCalcUtil.truncateMillis(DateCalcUtil.getDayEnd(tradeDate)));
            settleEntityDao.updateLastSettleTime(updateSettleEntity);
        }
    }


    /**
     * 生成品牌下门店结算单
     * @param tradeDate
     * @param settleEntity
     */
    private void genForStore(Date tradeDate, SettleEntity settleEntity) {
        LOGGER.info("genForStore, date: {}, info: {}", tradeDate, JsonUtil.toJson(settleEntity));
        Date lastSettleDate = settleEntity.getLastSettleTime();//获取最后结算日期
        long turnoverPoints = settleEntity.getTurnoverPoints() == null ? 0:settleEntity.getTurnoverPoints();
        SettleBill settleBillPara = new SettleBill();
        settleBillPara.setBrandId(settleEntity.getBrandId());
        settleBillPara.setStoreId(settleEntity.getStoreId());
        settleBillPara.setTradeDateStart(tradeDate);
        settleBillPara.setEntityType(settleEntity.getEntityType());
        SettleBill settleBill = settleBillDao.getSettleBillByParameter(settleBillPara);//通过参数查询已经预创建的结算单信息
        
        if(settleBill == null){
            return;
        }

        doCalcSettleBill(settleBill, tradeDate);
        settleBill.setSettleStatus(SettlementConsts.NEW_SETTLE_STATUS_NOT_SETTLE);//结算状态 1-未生成，10-未结算，20-已结算
        settleBill.setAuditStatus(SettlementConsts.PROCESS_STATUS_NOT_STARTED);

        //流水提点
        settleBill.setTurnoverPoints(turnoverPoints);
        //门店当日佣金
        long todayCommission = (settleBill.getTotalFlowAmt().longValue() * turnoverPoints)/10000;
        settleBill.setTodayCommission(todayCommission);
        //佣金扣除金额
        long commissionDeduction = settleBill.getCommissionDeduction() == null ? 0 : settleBill.getCommissionDeduction().longValue();
        if (SettlementConsts.CAPITAL_COLLECT_TYPE_STORE.equals(settleBill.getCapitalCollectType())) {//资金归集方式：1-品牌统一，2-门店独立
            // 资金归集方式为门店独立
            // 分账金额（0或负值) = 0 - 品牌分成
            settleBill.setEntityDivideAmt(0 - settleBill.getBrandShareAmt() - commissionDeduction);
        } else {
            //资金归集方式为品牌统一
            //分账金额 = 汉云实收 + 异常订单合计
            settleBill.setEntityDivideAmt(settleBill.getPlatformActualRecAmt() + settleBill.getDiffTotalAmt() - commissionDeduction);
        }
        //结算金额 = 汉云实收 + 异常订单合计 -汉云分成 - 品牌分成
        settleBill.setSettleAmt(
                settleBill.getPlatformActualRecAmt() + settleBill.getDiffTotalAmt()
                        - settleBill.getPlatformShareAmt() - settleBill.getBrandShareAmt() - commissionDeduction);

        LOGGER.info("genForStore, 门店更新对象 settleBill: {}", JsonUtil.toJson(settleBill));
        settleBillDao.updateSettleBySettleId(settleBill);
        
        // 更新最新已结算的交易日期
        if(tradeDate.after(lastSettleDate)){
            SettleEntity updateSettleEntity = new SettleEntity();
            updateSettleEntity.setBrandId(settleEntity.getBrandId());
            updateSettleEntity.setStoreId(settleEntity.getStoreId());
            updateSettleEntity.setLastSettleTime(DateCalcUtil.truncateMillis(DateCalcUtil.getDayEnd(tradeDate)));
            settleEntityDao.updateLastSettleTime(updateSettleEntity);
        }
        
    }

    /**
     * 计算结算单
     * @param settleBill
     * @param circleBegin
     * @param circleEnd
     */
    private void doCalcSettleBill(SettleBill settleBill, Date tradeDate) {
        Date circleBegin = DateCalcUtil.truncateMillis(DateCalcUtil.getDayBegin(tradeDate));
        Date circleEnd = DateCalcUtil.truncateMillis(DateCalcUtil.getDayEnd(tradeDate));
        // 交易明细金额
        SettleBillDetailParam normalParam = new SettleBillDetailParam();
        normalParam.setBrandId(settleBill.getBrandId());
        normalParam.setStoreId(settleBill.getStoreId());
        normalParam.setStartTime(circleBegin);
        normalParam.setEndTime(circleEnd);
        List<SettleBillDetail> detailsList = settleBillDetailDao.selectForGenSettlement(normalParam);//获取所有的结算明细
        if (detailsList == null) {
            detailsList = new ArrayList<>();
        }
        doCalcSettleData(detailsList, settleBill);

        // 已解决差异明细金额
        SettleBillDetailParam diffSolveParam = new SettleBillDetailParam();
        diffSolveParam.setBrandId(settleBill.getBrandId());
        diffSolveParam.setStoreId(settleBill.getStoreId());
        diffSolveParam.setStartTime(circleBegin);
        diffSolveParam.setEndTime(circleEnd);
        List<SettleBillDetail> diffSolvedDetailsList = settleBillDetailDao.selectSolvedDiffSettlement(diffSolveParam);//获取已解决异常明细
        if (diffSolvedDetailsList != null && diffSolvedDetailsList.size() > 0) {
            doCalcSolvedSettleData(diffSolvedDetailsList, settleBill);
        } else {
            settleBill.setDiffResolveActualAmt(0L);
        }
        // 新增差异明细金额
        SettleBillDetailParam diffUnSolvedParam = new SettleBillDetailParam();
        diffUnSolvedParam.setBrandId(settleBill.getBrandId());
        diffUnSolvedParam.setStoreId(settleBill.getStoreId());
        diffUnSolvedParam.setStartTime(circleBegin);
        diffUnSolvedParam.setEndTime(circleEnd);
        List<SettleBillDetail> diffUnSolvedDetailsList = settleBillDetailDao.selectUnSolvedDiffSettlement(diffSolveParam);//新增异常明细
        if (diffUnSolvedDetailsList != null && diffUnSolvedDetailsList.size() > 0) {
            doCalcUnSolvedSettleData(diffUnSolvedDetailsList, settleBill);
        } else {
            settleBill.setDiffTotalAmt(settleBill.getDiffResolveActualAmt());
        }
    }

    /**
     * 计算所有结算明细
     *
     * @param detailsList
     * @param settleBill
     * @return
     */
    private void doCalcSettleData(List<SettleBillDetail> detailsList, SettleBill settleBill) {
        Map<String, PayCategorySstatis> payCateStatisMap = new HashMap<>();
        for(String category : SettlementConsts.ALL_PAY_CATEGORY_SET){
            payCateStatisMap.put(category, new PayCategorySstatis());
        }

        long payamt = 0;//扣款金额
        long refamt = 0;//退款金额
        long onlinepayamt = 0;//线上扣款金额
        long onlinerefamt = 0;//线上退款金额

        long onlinepayfeetotal = 0;//线上扣款手续费
        long onlinereffeetotal = 0;//线上退款手续费

        for (SettleBillDetail settleBillDetail : detailsList) {
            long amt = settleBillDetail.getAmount();//获取扣款金额
            long mchFee = settleBillDetail.getMchFee();//获取商户手续费金额
            if (SettlementConsts.TRANS_OPERATE_TYPE_PAY.equals(settleBillDetail.getOperateType())) {//扣款
                payamt += amt;//获取扣款流水
                if(SettlementConsts.PAYTYPE_SETTLE_TYPE_ON.equals(settleBillDetail.getSettleType())){
                    onlinepayamt += amt;
                    onlinepayfeetotal += mchFee;
                }
                
                PayCategorySstatis tmpPayCategorySstatis = payCateStatisMap.get(settleBillDetail.getTypeCategory());
                if(tmpPayCategorySstatis == null){
                    tmpPayCategorySstatis = new PayCategorySstatis();
                    payCateStatisMap.put(settleBillDetail.getTypeCategory(), tmpPayCategorySstatis);
                }
                
                tmpPayCategorySstatis.setTotal(tmpPayCategorySstatis.getTotal() + amt);
                tmpPayCategorySstatis.setFeeTotal(tmpPayCategorySstatis.getFeeTotal() + mchFee);

            } else {//退款
                refamt += amt;//获取退款流水
                if(SettlementConsts.PAYTYPE_SETTLE_TYPE_ON.equals(settleBillDetail.getSettleType())){
                    onlinerefamt += amt;
                    onlinereffeetotal += mchFee;
                }
                
                PayCategorySstatis tmpPayCategorySstatis = payCateStatisMap.get(settleBillDetail.getTypeCategory());
                if(tmpPayCategorySstatis == null){
                    tmpPayCategorySstatis = new PayCategorySstatis();
                    payCateStatisMap.put(settleBillDetail.getTypeCategory(), tmpPayCategorySstatis);
                }

                tmpPayCategorySstatis.setRefTotal(tmpPayCategorySstatis.getRefTotal() + amt);
                tmpPayCategorySstatis.setRefFeeTotal(tmpPayCategorySstatis.getRefFeeTotal() + mchFee);
                
            }
        }
        //添加 结算单支付渠道明细
        insertSettleBillPaychn(settleBill, payCateStatisMap);

        //填充 settle_bill数据
        //总流水= 扣款金额-退款金额
        settleBill.setTotalFlowAmt(payamt - refamt);
        //在线总流水 = 线上扣款金额 - 线上退款金额
        settleBill.setOnlineFlowAmt(onlinepayamt - onlinerefamt);
        //第三方支付手续费 = 线上付款手续费 - 线上退款手续费
        settleBill.setPayFee(onlinepayfeetotal - onlinereffeetotal);
        //主体应收= 线上流水-线上手续费
        settleBill.setEntityShaltRecAmt(settleBill.getOnlineFlowAmt()- settleBill.getPayFee());
        settleBill.setEntityActualRecAmt(settleBill.getEntityShaltRecAmt());//主体实收
        settleBill.setBankCollectAmt(settleBill.getEntityShaltRecAmt());//银行归集金额
        settleBill.setPlatformActualRecAmt(settleBill.getEntityShaltRecAmt());//平台实收金额

        settleBill.setPlatformShareAmt(0L);//平台分成金额
        settleBill.setPlatformShareRate(0L);//平台分成比例，x10000
        settleBill.setBrandShareAmt(0L);//品牌分成金额
        settleBill.setBrandShareRate(0L);//品牌分成比例，x10000
    }

    /**
     * 添加/更新结算单支付渠道明细
     *
     * @param settleBill
     * @param payCateStatisMap
     */
    private void insertSettleBillPaychn(SettleBill settleBill, Map<String, PayCategorySstatis> payCateStatisMap) {
        
        for(String payTypeCategory : payCateStatisMap.keySet()){
            PayCategorySstatis payCategorySstatis = payCateStatisMap.get(payTypeCategory);
            
            SettleBillPaychn settleBillPaychn = new SettleBillPaychn();
            settleBillPaychn.setCreateTime(new Date());
            settleBillPaychn.setUpdateTime(new Date());
            settleBillPaychn.setPayTypeCategory(payTypeCategory);
            settleBillPaychn.setBillId(settleBill.getBillId());
            settleBillPaychn.setEntityId(settleBill.getEntityId());
            settleBillPaychn.setBrandId(settleBill.getBrandId());
            settleBillPaychn.setStoreId(settleBill.getStoreId());
            settleBillPaychn.setTotalFlowAmt(payCategorySstatis.getTotal() - payCategorySstatis.getRefTotal());
            
            if (SettlementConsts.ONLINE_PAY_CATEGORY_SET.contains(payTypeCategory)) {
                // 线上支付方式
                // 扣款手续费-退款手续费
                settleBillPaychn.setPayFee( payCategorySstatis.getFeeTotal() - payCategorySstatis.getRefFeeTotal() ); 
                settleBillPaychn.setEntityShaltRecAmt(settleBillPaychn.getTotalFlowAmt() - settleBillPaychn.getPayFee());
                settleBillPaychn.setEntityActualRecAmt(settleBillPaychn.getEntityShaltRecAmt());
                settleBillPaychn.setBankCollectAmt(settleBillPaychn.getEntityShaltRecAmt());
                settleBillPaychn.setPlatformActualRecAmt(settleBillPaychn.getEntityShaltRecAmt());
            } else {
                // 线下支付方式
                settleBillPaychn.setPayFee(0L);
                settleBillPaychn.setEntityShaltRecAmt(0L);
                settleBillPaychn.setEntityActualRecAmt(0L);
                settleBillPaychn.setBankCollectAmt(0L);
                settleBillPaychn.setPlatformActualRecAmt(0L);
            }
            SettleBillPaychn dbexist = settleBillPaychnDao.selectByPrimaryKey(settleBillPaychn);
            if(dbexist == null){
                settleBillPaychnDao.insertSelective(settleBillPaychn);
            }else{
                settleBillPaychnDao.updateByPrimaryKeySelective(settleBillPaychn);
            }
        }
        
    }

    /**
     * 计算解决异常结算明细
     *
     * @param diffSolvedDetailsList
     * @param settleBill
     * @return
     */
    private void doCalcSolvedSettleData(List<SettleBillDetail> diffSolvedDetailsList, SettleBill settleBill) {
        int diffSolveCount = 0;//差异解决数量
        long diffSolveAmt = 0;//差异解决金额

        long payamt = 0;//扣款金额
        long refamt = 0;//退款金额

        long payfeetotal = 0;//扣款手续费
        long reffeetotal = 0;//退款手续费

        for (SettleBillDetail settleBillDetail : diffSolvedDetailsList) {
            long amt = settleBillDetail.getAmount();//获取扣款金额
            long mchFee = settleBillDetail.getMchFee();//获取商户手续费金额
            if (SettlementConsts.TRANS_OPERATE_TYPE_PAY.equals(settleBillDetail.getOperateType())) {//扣款
                payamt += amt;
                payfeetotal += mchFee;
            } else {//退款
                refamt += amt;
                reffeetotal += mchFee;
            }
        }
        diffSolveCount = diffSolvedDetailsList.size();//解决数量
        diffSolveAmt = payamt - refamt; //差异解决金额 = 支付金额 - 退费金额

        settleBill.setDiffResolveCnt(diffSolveCount);
        settleBill.setDiffResolveAmt(diffSolveAmt);
        settleBill.setDiffResolvePayFee(payfeetotal - reffeetotal );//差异解决第三方支付手续费 = 扣款手续费 - 退款手续费
        settleBill.setDiffResolveActualAmt(diffSolveAmt - settleBill.getDiffResolvePayFee());//差异解决实际金额 = 差异解决金额-(扣款手续费 - 退款手续费)

    }

    /**
     * 计算新增异常结算明细
     *
     * @param diffUnSolvedDetailsList
     * @param settleBill
     * @return
     */
    private void doCalcUnSolvedSettleData(List<SettleBillDetail> diffUnSolvedDetailsList, SettleBill settleBill) {
        int diffSubmitCount = 0;//差异新增数量
        long diffSubmitAmt = 0;//差异新增金额

        long payamt = 0;//扣款金额
        long refamt = 0;//退款金额

        long payfeetotal = 0;//扣款手续费
        long reffeetotal = 0;//退款手续费

        for (SettleBillDetail settleBillDetail : diffUnSolvedDetailsList) {
            long amt = settleBillDetail.getAmount();//获取扣款金额
            long mchFee = settleBillDetail.getMchFee();//获取商户手续费金额
            if (SettlementConsts.TRANS_OPERATE_TYPE_PAY.equals(settleBillDetail.getOperateType())) {//扣款
                payamt += amt;
                payfeetotal += mchFee;
            } else {//退款
                refamt += amt;
                reffeetotal += mchFee;
            }
        }
        diffSubmitCount = diffUnSolvedDetailsList.size();
        diffSubmitAmt = payamt - refamt; //差异新增金额 = 支付金额 - 退费金额

        settleBill.setDiffSubmitCnt(diffSubmitCount);//差异新增数量
        settleBill.setDiffSubmitAmt(diffSubmitAmt);//差异新增金额
        settleBill.setDiffSubmitPayFee(payfeetotal- reffeetotal);//差异新增第三方支付手续费
        settleBill.setDiffSubmitActualAmt(diffSubmitAmt - settleBill.getDiffSubmitPayFee());//差异新增实际金额 = 新增退费手续费-扣款手续费
        //差异订单合计金额 = 解决差异- 新增差异
        settleBill.setDiffTotalAmt(settleBill.getDiffResolveActualAmt() - settleBill.getDiffSubmitActualAmt());
    }

    /**
     * 创建佣金结算单
     * @param settleEntity
     * @param settleBill
     * @param tradeDate
     */
    private void createCommissionBill(SettleEntity settleEntity,SettleBill settleBill,Date tradeDate){
        //判断是否佣金结算
        if (CommissionConsts.COMMISSION_SETTLEMENT_SWITCH_YES.equals(settleEntity.getCommissionSettlementSwitch())){
            //判断是否日结
            if (CommissionConsts.COMMISSION_BILL_CIRCLE_DAY.equals(settleEntity.getCommissionSettlementCircle())){
                Date beforeDate = DateCalcUtil.getPreDay(tradeDate);
                //判断是否有需要生成的日佣金结算单
                if(!settleEntity.getLastSettleTime().equals(beforeDate)){
                    //根据时间段查询出需要生成佣金结算单的结算单
                    SettleBill settleBillBefore = new SettleBill();
                    settleBillBefore.setBrandId(settleEntity.getBrandId());
                    settleBillBefore.setEntityType(settleEntity.getEntityType());
                    settleBillBefore.setTradeDateStart(settleEntity.getLastSettleTime());
                    settleBillBefore.setTradeDateEnd(beforeDate);
                    List<SettleBill> settleBillList = settleBillDao.getNoSettlement(settleBillBefore);
                    for (SettleBill s : settleBillList) {
                        commissionBillService.createDayCommissionStatement(settleEntity,s,tradeDate);
                    }
                }
                //生成当日佣金结算单
                commissionBillService.createDayCommissionStatement(settleEntity,settleBill,tradeDate);
            }
        }
    }

}