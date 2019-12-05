package com.hanyun.platform.settle.dao;

import com.hanyun.platform.settle.domain.SettleBill;
import java.util.List;

import com.hanyun.platform.settle.vo.commissionbill.QueryCommissionPara;
import com.hanyun.platform.settle.vo.commissionbill.QuerySettleBillPara;
import com.hanyun.platform.settle.vo.settlebill.SettleBillListReq;
import com.hanyun.platform.settle.vo.settlebill.SettleBillReq;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑, 永无BUG!
 * 　　　　┃　　　┃Code is far away from bug with the animal protecting
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * </pre>
 */
@SuppressWarnings("UnnecessaryInterfaceModifier")
@Repository
public interface SettleBillDao {

    public int deleteByPrimaryKey(Long id);

    public int insertSelective(SettleBill record);

    public SettleBill selectByPrimaryKey(Long id);

    public List<SettleBill> selectSelective(SettleBill record);

    public int updateByPrimaryKeySelective(SettleBill record);

    public List<SettleBill> getSettleBillList(SettleBillListReq settleBillListReq);

    public Integer getSettleBillListCount(SettleBillListReq settleBillListReq);

    public SettleBill getSingleSettleBill(SettleBill param);

    public Long getBrandDivideAmt(SettleBill settleBill);

    public List<SettleBill> getSettleBillDetailList(SettleBillReq settleBillReq);

    public SettleBill sumSettleBill(SettleBillReq settleBillReq);

    public int updateSettleBySettleId(SettleBill record);

    public SettleBill getSettleBillByParameter(SettleBill record);

    public int updateSettleBill(SettleBill settleBill);

    public SettleBill getSummarySettleBill(SettleBillListReq settleBillListReq);

    public List<SettleBill> getCommissionInfo(QueryCommissionPara queryCommissionPara);

    Integer getCommissionInfoCount(QueryCommissionPara queryCommissionPara);

    Long getCommissionSum(QueryCommissionPara queryCommissionPara);

    Long getStoreFlowAmtSum(SettleBill record);

    Long getSumCosssionAmount(QuerySettleBillPara querySettleBillPara);

    Long getSumCommissionDeducted(SettleBill record);

    List<SettleBill> getNoSettlement(SettleBill record);

    Long getPlatformActualRecAmt(SettleBill record);

}