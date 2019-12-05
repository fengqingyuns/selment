package com.hanyun.platform.settle.dao;

import com.hanyun.platform.settle.domain.SettleEntity;
import java.util.List;

import com.hanyun.platform.settle.vo.CommissionSettleEntityReq;
import com.hanyun.platform.settle.vo.SettleEntityReq;
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
public interface SettleEntityDao {

    public int deleteByPrimaryKey(Long id);

    public int insertSelective(SettleEntity record);

    public SettleEntity selectByPrimaryKey(Long id);

    public List<SettleEntity> selectSelective(SettleEntity record);

    public int updateByPrimaryKeySelective(SettleEntity record);

    public List<SettleEntity> selectCanSettle(SettleEntityReq record);

    public int updateLastSettleTime(SettleEntity record);

    public int deleteBySettle(String entityId);

    public int updateByEntityId(SettleEntity record);

    public List<SettleEntity> selectCanSessionSettle(CommissionSettleEntityReq record);

    public SettleEntity selectBySettleSetting(SettleEntity settleEntity);

    public int updateLastCommissionSettleTime(SettleEntity record);
}