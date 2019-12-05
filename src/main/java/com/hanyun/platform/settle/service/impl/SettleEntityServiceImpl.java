package com.hanyun.platform.settle.service.impl;

import com.hanyun.ground.util.date.DateCalcUtil;
import com.hanyun.ground.util.date.DateFormatUtil;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.platform.settle.consts.CommissionConsts;
import com.hanyun.platform.settle.dao.SettleEntityBankAccDao;
import com.hanyun.platform.settle.dao.SettleEntityDao;
import com.hanyun.platform.settle.dao.SettleSettingDao;
import com.hanyun.platform.settle.domain.SettleEntity;
import com.hanyun.platform.settle.domain.SettleEntityBankAcc;
import com.hanyun.platform.settle.domain.SettleSetting;
import com.hanyun.platform.settle.service.SettleEntityService;
import com.hanyun.platform.settle.vo.SettleSettingReq;
import com.hanyun.platform.settle.vo.base.PageResData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by jack on 2017/4/6.
 */
@Service
public class SettleEntityServiceImpl implements SettleEntityService {
    private static Logger LOGGER = LoggerFactory.getLogger(SettleEntityServiceImpl.class);
    @Resource
    private SettleSettingDao settleSettingDao;
    @Resource
    private SettleEntityDao settleEntityDao;
    @Resource
    private SettleEntityBankAccDao settleEntityBankAccDao;


    @Override
    public PageResData getSettleSetting(SettleSettingReq settleSettingReq) {
        PageResData pageResData = new PageResData();

        LOGGER.info("请求参数为：{}", JsonUtil.toJson(settleSettingReq));
        List<SettleSetting> settleSettingList= settleSettingDao.selectBySettleSettingList(settleSettingReq);
        int count = settleSettingDao.selectBySettleSettingCount(settleSettingReq);
        pageResData.setDataList(settleSettingList);
        pageResData.setTotalCount(count);

        return pageResData;
    }

    @Override
    public SettleEntity selectSettleSetting(SettleSetting settleSetting){
        LOGGER.info("请求参数为：{}", JsonUtil.toJson(settleSetting));
        SettleEntity settleEntity = new SettleEntity();
        settleEntity.setBrandId(settleSetting.getBrandId());
        settleEntity.setEntityType(CommissionConsts.ENTITY_TYPE_BRAND);
        return settleEntityDao.selectBySettleSetting(settleEntity);
    }

    @Override
    @Transactional
    public String addSettleSetting(SettleSetting settleSetting) {
        LOGGER.info("请求参数为：{}", JsonUtil.toJson(settleSetting));

        try {
            SettleEntity settleEntity = new SettleEntity();
            settleEntity.setCreateTime(new Date());
            settleEntity.setUpdateTime(new Date());
            settleEntity.setEntityId(settleSetting.getEntityId());
            settleEntity.setEntityName(settleSetting.getEntityName());
            settleEntity.setEntityType(settleSetting.getEntityType());
            settleEntity.setBrandId(settleSetting.getBrandId());
            settleEntity.setStoreId(settleSetting.getStoreId());
            settleEntity.setSeparateSettle(settleSetting.getSeparateSettle());
            settleEntity.setCapitalCollect(settleSetting.getCapitalCollect());
            settleEntity.setCapitalCollectType(settleSetting.getCapitalCollectType());
            settleEntity.setSettleCircle(settleSetting.getSettleCircle());
            settleEntity.setLastSettleTime(DateCalcUtil.truncateMillis(DateCalcUtil.getDayEnd(new Date())));
            settleEntity.setAvailStatus(settleSetting.getAvailStatus());
            settleEntity.setCommissionSettlementSwitch(settleSetting.getCommissionSettlementSwitch());
            settleEntity.setOnlineCommission(settleSetting.getOnlineCommission() );
            Date commissionLastTime = DateCalcUtil.getDayEnd(DateFormatUtil.parseDate(settleSetting.getCommissionLastSettleTime()));
            settleEntity.setCommissionLastSettleTime(DateCalcUtil.truncateMillis(commissionLastTime));
            settleEntity.setTurnoverPoints(settleSetting.getTurnoverPoints());
            settleEntity.setCommissionSettlementCircle(settleSetting.getCommissionSettlementCircle());
            int countEntity = settleEntityDao.insertSelective(settleEntity);
            LOGGER.info("新增结算基本信息{}条数据", countEntity);

            SettleEntityBankAcc settleEntityBankAcc = new SettleEntityBankAcc();
            settleEntityBankAcc.setCreateTime(new Date());
            settleEntityBankAcc.setUpdateTime(new Date());
            settleEntityBankAcc.setEntityId(settleSetting.getEntityId());
            settleEntityBankAcc.setEntityType(settleSetting.getEntityType());
            settleEntityBankAcc.setBrandId(settleSetting.getBrandId());
            settleEntityBankAcc.setStoreId(settleSetting.getStoreId());
            settleEntityBankAcc.setBrandId(settleSetting.getBrandId());
            settleEntityBankAcc.setBankName(settleSetting.getBankName());
            settleEntityBankAcc.setBankAccount(settleSetting.getBankAccount());
            settleEntityBankAcc.setBankAccountName(settleSetting.getBankAccountName());
            settleEntityBankAcc.setBankUnionCode(settleSetting.getBankUnionCode());
            settleEntityBankAcc.setDepositBank(settleSetting.getDepositBank());
            settleEntityBankAcc.setBankAccountType(settleSetting.getBankAccountType());
            int countAcc = settleEntityBankAccDao.insertSelective(settleEntityBankAcc);
            LOGGER.info("新增结算账户信息{}条数据", countAcc);
        }catch (Exception e){
            LOGGER.info("新增结算基础设置信息失败！！！");
            return "FAIL";
        }
        return "SUCCESS";
    }

    @Override
    public String deleteSettleSetting(SettleSettingReq settleSettingReq) {
        try {
            int entiyDel = settleEntityDao.deleteBySettle(settleSettingReq.getEntityId());
            LOGGER.info("删除结算基本信息{}条数据", entiyDel);

            int BankAccDel = settleEntityBankAccDao.deleteBySettle(settleSettingReq.getEntityId());
            LOGGER.info("删除结算账户信息{}条数据", BankAccDel);
        }catch (Exception e){
            LOGGER.info("删除结算结算基本信息失败！！！");
            return "FAIL";
        }
        return "SUCCESS";
    }

    @Override
    public String updateSettleSetting(SettleSetting settleSetting) {
        LOGGER.info("修改结算单基础设置数据", JsonUtil.toJson(settleSetting));
        try {
            SettleEntity settleEntity = new SettleEntity();
            settleEntity.setUpdateTime(new Date());
            settleEntity.setEntityId(settleSetting.getEntityId());
            settleEntity.setEntityName(settleSetting.getEntityName());
            settleEntity.setEntityType(settleSetting.getEntityType());
            settleEntity.setBrandId(settleSetting.getBrandId());
            settleEntity.setStoreId(settleSetting.getStoreId());
            settleEntity.setSeparateSettle(settleSetting.getSeparateSettle());
            settleEntity.setCapitalCollect(settleSetting.getCapitalCollect());
            settleEntity.setCapitalCollectType(settleSetting.getCapitalCollectType());
            settleEntity.setSettleCircle(settleSetting.getSettleCircle());
            settleEntity.setLastSettleTime(settleSetting.getLastSettleTime());
            settleEntity.setAvailStatus(settleSetting.getAvailStatus());
            settleEntity.setCommissionSettlementSwitch(settleSetting.getCommissionSettlementSwitch());
            settleEntity.setOnlineCommission(settleSetting.getOnlineCommission() );
            Date commissionLastTime = DateCalcUtil.getDayEnd(DateFormatUtil.parseDate(settleSetting.getCommissionLastSettleTime()));
            settleEntity.setCommissionLastSettleTime(DateCalcUtil.truncateMillis(commissionLastTime));
            settleEntity.setTurnoverPoints(settleSetting.getTurnoverPoints());
            settleEntity.setCommissionSettlementCircle(settleSetting.getCommissionSettlementCircle());
            int countEntity = settleEntityDao.updateByEntityId(settleEntity);
            LOGGER.info("更新结算基础设置实体信息{}数据",countEntity);

            if (CommissionConsts.ENTITY_TYPE_BRAND.equals(settleSetting.getEntityType())){
                SettleEntity entity = new SettleEntity();
                entity.setBrandId(settleSetting.getBrandId());
                entity.setEntityType(CommissionConsts.ENTITY_TYPE_STORE);
                List<SettleEntity> list = settleEntityDao.selectSelective(entity);
                for (SettleEntity s:list) {
                    Date storeCommissionLastTime = DateCalcUtil.getDayEnd(DateFormatUtil.parseDate(settleSetting.getCommissionLastSettleTime()));
                    s.setCommissionLastSettleTime(DateCalcUtil.truncateMillis(storeCommissionLastTime));
                    s.setCommissionSettlementSwitch(settleSetting.getCommissionSettlementSwitch());
                    s.setCommissionSettlementCircle(settleSetting.getCommissionSettlementCircle());
                    s.setUpdateTime(new Date());
                    if (CommissionConsts.SEPARATE_SETTLE_NO.equals(s.getSeparateSettle())){
                        s.setTurnoverPoints(settleSetting.getTurnoverPoints());
                        s.setOnlineCommission(settleSetting.getOnlineCommission());
                    }
                    settleEntityDao.updateByEntityId(s);
                }
            }

            SettleEntityBankAcc settleEntityBankAcc = new SettleEntityBankAcc();
            settleEntityBankAcc.setUpdateTime(new Date());
            settleEntityBankAcc.setEntityId(settleSetting.getEntityId());
            settleEntityBankAcc.setEntityType(settleSetting.getEntityType());
            settleEntityBankAcc.setBrandId(settleSetting.getBrandId());
            settleEntityBankAcc.setStoreId(settleSetting.getStoreId());
            settleEntityBankAcc.setBrandId(settleSetting.getBrandId());
            settleEntityBankAcc.setBankName(settleSetting.getBankName());
            settleEntityBankAcc.setBankAccount(settleSetting.getBankAccount());
            settleEntityBankAcc.setBankAccountName(settleSetting.getBankAccountName());
            settleEntityBankAcc.setBankUnionCode(settleSetting.getBankUnionCode());
            settleEntityBankAcc.setDepositBank(settleSetting.getDepositBank());
            settleEntityBankAcc.setBankAccountType(settleSetting.getBankAccountType());
            int updateBankAccCount  = settleEntityBankAccDao.updateByEntityId(settleEntityBankAcc);
            LOGGER.info("更新结算基础设银行信息{}数据",updateBankAccCount);

        }catch (Exception e){
            LOGGER.info("更新结算结算基本信息失败！！！");
            return "FAIL";
        }
        return "SUCCESS";
    }
}
