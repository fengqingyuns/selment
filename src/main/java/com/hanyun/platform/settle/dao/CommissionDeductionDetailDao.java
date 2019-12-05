package com.hanyun.platform.settle.dao;

import com.hanyun.platform.settle.domain.CommissionDeductionDetail;
import java.util.List;

import org.apache.ibatis.annotations.Param;
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
public interface CommissionDeductionDetailDao {

    public int deleteByPrimaryKey(Long id);

    public int insertSelective(CommissionDeductionDetail record);

    public CommissionDeductionDetail selectByPrimaryKey(Long id);

    public CommissionDeductionDetail selectByParameter(CommissionDeductionDetail record);

    public List<CommissionDeductionDetail> selectSelective(CommissionDeductionDetail record);

    public int updateByPrimaryKeySelective(CommissionDeductionDetail record);

    public List<CommissionDeductionDetail> selectBycommissionBillId(@Param("commissionBillId") String commissionBillId);

    public int updateCommissionAmountByCommissionBillId(CommissionDeductionDetail record);

    public int updateCommissionDeductionAmountByCommissionBillId(CommissionDeductionDetail record);

    public List<CommissionDeductionDetail> selectBySettleBill(CommissionDeductionDetail record);

    public Long sumDeduction(CommissionDeductionDetail record);
}