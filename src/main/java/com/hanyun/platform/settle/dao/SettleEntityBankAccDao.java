package com.hanyun.platform.settle.dao;

import com.hanyun.platform.settle.domain.SettleEntityBankAcc;
import java.util.List;

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
public interface SettleEntityBankAccDao {

    public int deleteByPrimaryKey(Long id);

    public int insertSelective(SettleEntityBankAcc record);

    public SettleEntityBankAcc selectByPrimaryKey(Long id);

    public List<SettleEntityBankAcc> selectSelective(SettleEntityBankAcc record);

    public int updateByPrimaryKeySelective(SettleEntityBankAcc record);

    public SettleEntityBankAcc getSingleEntityBankAcc(SettleEntityBankAcc param);

    public int deleteBySettle(String entityId);

    public int updateByEntityId(SettleEntityBankAcc record);
}