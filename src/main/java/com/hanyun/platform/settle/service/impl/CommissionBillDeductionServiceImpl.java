package com.hanyun.platform.settle.service.impl;

import com.hanyun.ground.util.date.DateFormatUtil;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.platform.settle.consts.CommissionConsts;
import com.hanyun.platform.settle.consts.SettlementConsts;
import com.hanyun.platform.settle.dao.CommissionBillDao;
import com.hanyun.platform.settle.dao.CommissionDeductionDetailDao;
import com.hanyun.platform.settle.dao.SettleBillDao;
import com.hanyun.platform.settle.dao.SettleEntityDao;
import com.hanyun.platform.settle.domain.*;
import com.hanyun.platform.settle.service.CommissionBillDeductionService;
import com.hanyun.platform.settle.service.CommissionBillService;
import com.hanyun.platform.settle.util.BaseBrandInfoUtil;
import com.hanyun.platform.settle.util.BaseBrandStoreUtil;
import com.hanyun.platform.settle.util.BusinessIdUtils;
import com.hanyun.platform.settle.vo.BrandStoreInfo;
import com.hanyun.platform.settle.vo.SettleEntityReq;
import com.hanyun.platform.settle.vo.settlebill.CommissionDeductionResultVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2018/3/29.
 */
@Service
public class CommissionBillDeductionServiceImpl implements CommissionBillDeductionService {
    private static Logger LOGGER = LoggerFactory.getLogger(CommissionBillDeductionServiceImpl.class);

    @Resource
    private SettleEntityDao settleEntityDao;
    @Resource
    private SettleBillDao settleBillDao;
    @Resource
    private CommissionBillService commissionBillService;
    @Resource
    private CommissionBillDao commissionBillDao;
    @Resource
    private CommissionDeductionDetailDao commissionDeductionDetailDao;

    @Override
    public CommissionDeductionResultVo commissionDeducted(Date date, String brandId) {
        CommissionDeductionResultVo result = new CommissionDeductionResultVo(false);
        if(date == null){
            result.setMessage("date is null");
            return result;
        }
        if(StringUtils.isBlank(brandId)){
            brandId = null;
        }
        String dateStr = DateFormatUtil.formatDateNoSep(date);
        result.setDate(dateStr);
        result.setBrandId(brandId);
        LOGGER.info("佣金扣除开始，日期: {}，指定品牌: {}", dateStr, brandId);

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

        for (SettleEntity settleEntity : settleEntityBrandList) {
            try{
                //获取品牌列表
                List<SettleEntity> settleEntityStoreList = getStoreSettleEntityByBrandId(settleEntity);
                if (settleEntityStoreList != null) {
                    /**门店**/
                    for (SettleEntity settleStoreEntity : settleEntityStoreList) {
                        SettleBill settleBill = getSettleBill(date,settleStoreEntity);
                        //佣金扣除并生产扣除明细
                        long commissionDeductionPrice = commissionDeducted(settleStoreEntity, settleBill);

                        if (SettlementConsts.CAPITAL_COLLECT_TYPE_STORE.equals(settleBill.getCapitalCollectType())) {//资金归集方式：1-品牌统一，2-门店独立
                            // 资金归集方式为门店独立
                            // 分账金额（0或负值) = 0 - 品牌分成
                            settleBill.setEntityDivideAmt(0 - settleBill.getBrandShareAmt() - commissionDeductionPrice);
                        } else {
                            //资金归集方式为品牌统一
                            //分账金额 = 汉云实收 + 异常订单合计
                            settleBill.setEntityDivideAmt(settleBill.getPlatformActualRecAmt() + settleBill.getDiffTotalAmt() - commissionDeductionPrice);
                        }

                        settleBill.setCommissionDeduction(commissionDeductionPrice);

                        //结算金额 = 汉云实收 + 异常订单合计 -汉云分成 - 品牌分成
                        settleBill.setSettleAmt(
                                settleBill.getPlatformActualRecAmt() + settleBill.getDiffTotalAmt()
                                        - settleBill.getPlatformShareAmt() - settleBill.getBrandShareAmt() - commissionDeductionPrice);

                        settleBillDao.updateSettleBySettleId(settleBill);
                    }
                }
                /**品牌**/
                SettleBill settleBill = getSettleBill(date,settleEntity);

                Long allStoreDivideAmt = settleBillDao.getBrandDivideAmt(settleBill);//获取所有的门店分账和
                allStoreDivideAmt = (allStoreDivideAmt == null) ? 0 : allStoreDivideAmt;
                settleBill.setEntityDivideAmt(allStoreDivideAmt);

                //佣金扣除并生产扣除明细
                long commissionDeductionPrice = commissionDeducted(settleEntity, settleBill);
                //获取独立门店扣除佣金总和
                SettleBill storeSettleBill = new SettleBill();
                storeSettleBill.setBrandId(settleBill.getBrandId());
                storeSettleBill.setEntityType(CommissionConsts.ENTITY_TYPE_STORE);
                storeSettleBill.setSeparateSettle(CommissionConsts.COMMISSION_BILL_CIRCLE_DAY);
                storeSettleBill.setTradeDateStart(settleBill.getTradeDateStart());
                Long sumCommissionDeducted = settleBillDao.getSumCommissionDeducted(storeSettleBill) == null ? 0L : settleBillDao.getSumCommissionDeducted(storeSettleBill);
                LOGGER.info("品牌佣金扣除金额："+ commissionDeductionPrice+"门店扣除佣金总额"+sumCommissionDeducted);

                CommissionDeductionDetail commissionDeductionDetail= new CommissionDeductionDetail();
                commissionDeductionDetail.setBillId(settleBill.getBillId());
                long oldCommissionDeduction = commissionDeductionDetailDao.sumDeduction(commissionDeductionDetail);
                settleBill.setCommissionDeduction(oldCommissionDeduction + commissionDeductionPrice);
                //结算金额 = 汉云实收 + 异常订单合计 -汉云分成 - 门店分账
                settleBill.setSettleAmt(
                        settleBill.getPlatformActualRecAmt() + settleBill.getDiffTotalAmt()
                                - settleBill.getPlatformShareAmt() - settleBill.getEntityDivideAmt() - commissionDeductionPrice - oldCommissionDeduction - sumCommissionDeducted.longValue());
                LOGGER.info("品牌结算金额："+ settleBill.getSettleAmt());

                settleBillDao.updateSettleBySettleId(settleBill);
                result.setSuccCount(result.getSuccCount() + 1);
            }catch (Exception e){
                LOGGER.error("commissionDeducted error, brandId: {}", settleEntity.getBrandId());
                LOGGER.error("commissionDeducted error" ,e);
                result.getFailBrandIdList().add(settleEntity.getBrandId());
            }
        }

        return result;
    }

    @Override
    public long commissionDeducted(SettleEntity settleEntity, SettleBill settleBill) {
        //扣除结算佣金总金额
        long settlementPriceSum = 0;
        if (CommissionConsts.ONLINE_COMMISSION_YES.equals(settleEntity.getOnlineCommission())){
            //实际线上流水
            long grossProfit;
            //获取未结算和部分结算的结算单
            CommissionBill commissionBill = new CommissionBill();
            commissionBill.setBrandId(settleEntity.getBrandId());
            commissionBill.setStoreId(settleEntity.getStoreId());
            commissionBill.setEntityType(settleBill.getEntityType());
            commissionBill.setOnlineCommission(CommissionConsts.ONLINE_COMMISSION_YES);
            List<CommissionBill> commissionBills = commissionBillDao.noSettlement(commissionBill);
            //计算实际流水
            if (CommissionConsts.ENTITY_TYPE_BRAND.equals(settleBill.getEntityType())){
                //获取独立门店平台实收总和
                SettleBill brandSettleBill = new SettleBill();
                brandSettleBill.setBrandId(settleBill.getBrandId());
                brandSettleBill.setEntityType(SettlementConsts.ENTITY_TYPE_STORE);
                brandSettleBill.setSeparateSettle(settleBill.getSeparateSettle());
                brandSettleBill.setTradeDateStart(settleBill.getTradeDateStart());
                long storeParAmtSum = settleBillDao.getPlatformActualRecAmt(brandSettleBill);
                //实际线上流水
                grossProfit = settleBill.getPlatformActualRecAmt() - storeParAmtSum + settleBill.getDiffTotalAmt()
                        - settleBill.getPlatformShareAmt() - settleBill.getBrandShareAmt();
                LOGGER.info("获取独立门店平台实收总和：" + storeParAmtSum + "品牌实际线上流水" + grossProfit);
            }else {
                grossProfit = settleBill.getPlatformActualRecAmt() + settleBill.getDiffTotalAmt()
                        - settleBill.getPlatformShareAmt() - settleBill.getBrandShareAmt();
            }
            CommissionDeductionDetail deductionDetail = new CommissionDeductionDetail();
            deductionDetail.setBillId(settleBill.getBillId());
            deductionDetail.setBrandId(settleBill.getBrandId());
            deductionDetail.setStoreId(settleBill.getStoreId());
            List<CommissionDeductionDetail> commissionDeductionDetailList = commissionDeductionDetailDao.selectBySettleBill(deductionDetail);
            if (commissionDeductionDetailList != null && commissionDeductionDetailList.size() > 0){
                //更新扣除逻辑逻辑
                for (CommissionDeductionDetail detail:commissionDeductionDetailList) {
                    settlementPriceSum += detail.getDeductionAmount().longValue();
                }
                LOGGER.info("扣除明细扣除总金额：" + settlementPriceSum);
                if(grossProfit < settlementPriceSum){
                    //退还金额
                    long refundAmount = settlementPriceSum - grossProfit;
                    settlementPriceSum -= refundAmount;
                    LOGGER.info("退还后结算总金额：" + settlementPriceSum);
                    for (CommissionDeductionDetail c:commissionDeductionDetailList) {
                        if (refundAmount == 0){
                            break;
                        }
                        //获取佣金结算单
                        CommissionBill commission = commissionBillDao.selectByPrimaryKey(c.getCommissionBillId());
                        if (grossProfit > 0){
                            if (grossProfit < c.getDeductionAmount()){
                                long r = c.getDeductionAmount() - grossProfit;
                                c.setDeductionAmount(grossProfit);
                                c.setSettledAmount(c.getSettledAmount().longValue() - r);
                                grossProfit -= grossProfit;
                            }else {
                                grossProfit -= c.getDeductionAmount().longValue();
                            }
                        }else {
                            long settleAmount = c.getSettledAmount();
                            long deductionAmount = c.getDeductionAmount();
                            c.setDeductionAmount(0L);
                            c.setSettledAmount(settleAmount - deductionAmount);
                        }
                        c.setUpdateTime(new Date());
                        commissionDeductionDetailDao.updateByPrimaryKeySelective(c);
                        LOGGER.info("逆向佣金扣除明细数据"+JsonUtil.toJson(c));
                        commission.setSettledAmount(c.getSettledAmount());
                        if (c.getSettledAmount().longValue() == 0){
                            commission.setCommissionBillStatus(CommissionConsts.COMMISSION_BILL_STATUS_UNSETTLEMENT);
                        }else{
                            if (c.getSettledAmount().equals(commission.getCommissionAmount())){
                                commission.setCommissionBillStatus(CommissionConsts.COMMISSION_BILL_STATUS_SETTLED);
                            }
                            if (c.getSettledAmount().longValue() < commission.getCommissionAmount()){
                                commission.setCommissionBillStatus(CommissionConsts.COMMISSION_BILL_STATUS_PARTIALLY_SETTLED);
                            }
                        }
                        commission.setUpdateTime(new Date());
                        LOGGER.info("逆向佣金结算单数据"+JsonUtil.toJson(commission));
                        commissionBillDao.updateByPrimaryKeySelective(commission);
                    }
                }else {
                    return settlementPriceSum;
                }
            }else {
                //扣除逻辑
                for (CommissionBill c : commissionBills) {
                    BrandAllInfo brandAllInfo = BaseBrandInfoUtil.getBrandAll().get(settleEntity.getBrandId());
                    BrandStoreInfo brandStoreInfo = BaseBrandStoreUtil.getBrandAllStore(settleEntity.getBrandId()).get(settleEntity.getStoreId());
                    //未结算佣金金额
                    long noSettlementPrice = c.getCommissionAmount().longValue() - c.getSettledAmount().longValue();
                    LOGGER.info("佣金扣除明细的未结算金额为：" + noSettlementPrice);
                    //生成佣金明细
                    CommissionDeductionDetail commissionDeductionDetail = new CommissionDeductionDetail();
                    commissionDeductionDetail.setCommissionDeductionId(BusinessIdUtils.genCommissionDeductionId());
                    commissionDeductionDetail.setCommissionBillId(c.getCommissionBillId());
                    commissionDeductionDetail.setBillId(settleBill.getBillId());
                    commissionDeductionDetail.setBrandId(settleBill.getBrandId());
                    commissionDeductionDetail.setStoreId(settleBill.getStoreId());
                    commissionDeductionDetail.setBrandName(brandAllInfo == null ? null : brandAllInfo.getBrandName());
                    commissionDeductionDetail.setStoreName(brandStoreInfo == null ? null : brandStoreInfo.getStoreName());
                    commissionDeductionDetail.setTradeDateEnd(c.getTradeDateEnd());
                    commissionDeductionDetail.setTradeDateStart(c.getTradeDateStart());
                    commissionDeductionDetail.setCommissionSettleType(settleEntity.getOnlineCommission());
                    commissionDeductionDetail.setCommissionAmount(c.getCommissionAmount());
                    commissionDeductionDetail.setCreateTime(new Date());
                    commissionDeductionDetail.setUpdateTime(new Date());
                    c.setUpdateTime(new Date());
                    //流水大于0才扣减否则不能扣减
                    if (grossProfit > 0){
                        //判断当前流水是否可以扣除未结算佣金
                        if (grossProfit >= noSettlementPrice){
                            commissionDeductionDetail.setDeductionAmount(noSettlementPrice);
                            commissionDeductionDetail.setSettledAmount(c.getSettledAmount().longValue()+noSettlementPrice);
                            //扣除佣金修改佣金结算单状态为已结算
                            c.setCommissionBillStatus(CommissionConsts.COMMISSION_BILL_STATUS_SETTLED);
                            c.setSettledAmount(c.getCommissionAmount());

                            settlementPriceSum += noSettlementPrice;
                            grossProfit -= noSettlementPrice;
                        }else{
                            commissionDeductionDetail.setDeductionAmount(grossProfit);
                            commissionDeductionDetail.setSettledAmount(c.getSettledAmount().longValue()+grossProfit);

                            //扣除部分佣金修改佣金结算单状态为部分结算
                            c.setCommissionBillStatus(CommissionConsts.COMMISSION_BILL_STATUS_PARTIALLY_SETTLED);
                            c.setSettledAmount(c.getSettledAmount()+grossProfit);

                            settlementPriceSum += grossProfit;
                            grossProfit = 0;
                        }
                    }else {
                        if (c.getCommissionAmount().longValue() == 0){
                            commissionDeductionDetail.setDeductionAmount(grossProfit);
                            commissionDeductionDetail.setSettledAmount(c.getSettledAmount().longValue()+grossProfit);
                            //扣除佣金修改佣金结算单状态为已结算
                            c.setCommissionBillStatus(CommissionConsts.COMMISSION_BILL_STATUS_SETTLED);
                            c.setSettledAmount(c.getCommissionAmount());
                        }else {
                            commissionDeductionDetail.setDeductionAmount(grossProfit);
                            commissionDeductionDetail.setSettledAmount(c.getSettledAmount().longValue()+grossProfit);
                        }
                    }
                    commissionBillDao.updateByPrimaryKeySelective(c);
                    commissionDeductionDetailDao.insertSelective(commissionDeductionDetail);
                }
            }
        }
        return settlementPriceSum;
    }



    /**
     * 通过品牌id获取门店结算基础信息
     * @param settleEntity
     * @return
     */
    private List<SettleEntity> getStoreSettleEntityByBrandId(SettleEntity settleEntity) {
        LOGGER.info("getStoreSettleEntityByBrandId brandId:{}", settleEntity.getBrandId());
        SettleEntity newSettleEntity = new SettleEntity();
        newSettleEntity.setBrandId(settleEntity.getBrandId());
        newSettleEntity.setEntityType(CommissionConsts.ENTITY_TYPE_STORE);
        newSettleEntity.setAvailStatus(SettlementConsts.AVAIL_STATUS_START);
        newSettleEntity.setSeparateSettle(SettlementConsts.SEPARATE_SETTLE_YES);
        newSettleEntity.setCommissionSettlementSwitch(CommissionConsts.COMMISSION_SETTLEMENT_SWITCH_YES);
        return  settleEntityDao.selectSelective(newSettleEntity);
    }

    private SettleBill getSettleBill(Date tradeDate,SettleEntity settleEntity){
        SettleBill settleBillPara = new SettleBill();
        settleBillPara.setBrandId(settleEntity.getBrandId());
        settleBillPara.setStoreId(settleEntity.getStoreId());
        settleBillPara.setTradeDateStart(tradeDate);
        settleBillPara.setEntityType(settleEntity.getEntityType());
        return settleBillDao.getSettleBillByParameter(settleBillPara);//通过参数查询结算单信息
    }

}
