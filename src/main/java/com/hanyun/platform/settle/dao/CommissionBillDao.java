package com.hanyun.platform.settle.dao;

import com.hanyun.platform.settle.domain.CommissionBill;
import java.util.List;

import com.hanyun.platform.settle.vo.commissionbill.CommissionBillReq;
import com.hanyun.platform.settle.vo.commissionbill.QueryCommissionPara;
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
public interface CommissionBillDao {

    public int deleteByPrimaryKey(Integer id);

    public int insertSelective(CommissionBill record);

    public CommissionBill selectByPrimaryKey(String commissionBillId);

    public List<CommissionBill> selectSelective(CommissionBill record);

    public int updateByPrimaryKeySelective(CommissionBill record);

    public List<CommissionBill> query(CommissionBillReq commissionBillReq);

    public int queryCount(CommissionBillReq commissionBillReq);

    public List<CommissionBill> openTransfer(@Param("commissionBillIds")String[] commissionBillIds);

    List<CommissionBill> noSettlement(CommissionBill record);

    List<CommissionBill> queryCommissionSettle(QueryCommissionPara record);
}