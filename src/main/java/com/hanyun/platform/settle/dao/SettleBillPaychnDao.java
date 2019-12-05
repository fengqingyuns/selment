package com.hanyun.platform.settle.dao;

import com.hanyun.platform.settle.domain.SettleBillPaychn;
import java.util.List;

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
public interface SettleBillPaychnDao {

    public int deleteByPrimaryKey(Long id);

    public int insertSelective(SettleBillPaychn record);

    public SettleBillPaychn selectByPrimaryKey(SettleBillPaychn record);

    public List<SettleBillPaychn> selectSelective(SettleBillPaychn record);

    public int updateByPrimaryKeySelective(SettleBillPaychn record);

    public List<SettleBillPaychn> getSettlePayChnList(SettleBillPaychn settleBillPaychn);

    public SettleBillPaychn sumSettleBillPayChn(SettleBillPaychn settleBillPaychn);

    public List<SettleBillPaychn> getSummarySettleBillPayChn(SettleBillListReq settleBillListReq);
}