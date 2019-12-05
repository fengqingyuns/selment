/**
 * 
 */
package com.hanyun.platform.settle.util;

import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import com.hanyun.ground.util.date.DateFormatUtil;

/**
 * 业务ID工具类
 * @author liyinglong@hanyun.com
 * @date 2016年7月14日 下午3:53:46
 */
public abstract class BusinessIdUtils {
    /** 结算单号前缀 **/
    public static final String SETTLE_ID_PREFIX = "500";
    /** 对账单号前缀 **/
    public static final String STATE_ID_PREFIX = "600";
    /** 结算单据编号前缀 **/
    public static final String SETTLE_BILL_ID_PREFIX = "JS";
    /** 佣金结算单据编号前缀 **/
    public static final String COMMISSION_BILL_ID_PREFIX = "YJ";
    /** 佣金扣除编号前缀 **/
    public static final String COMMISSION_DEDUCTION_ID_PREFIX = "KC";

    /**
     * 生成佣金扣除明细编号<br>
     * 格式：3位业务前缀 + 17位时间串(到毫秒) + 7位随机数
     * @return
     */
    public static String genCommissionDeductionId(){
        return generateBusinessId(COMMISSION_DEDUCTION_ID_PREFIX);
    }

    /**
     * 生成结算单号<br>
     * 格式：3位业务前缀 + 17位时间串(到毫秒) + 7位随机数
     * @return
     */
    public static String genSettleId(){
        return generateBusinessId(SETTLE_ID_PREFIX);
    }
    
    /**
     * 生成对账单号<br>
     * 格式：3位业务前缀 + 17位时间串(到毫秒) + 7位随机数
     * @return
     */
    public static String genStateId(){
        return generateBusinessId(STATE_ID_PREFIX);
    }
    
    /**
     * 生成结算单据编号<br>
     * 格式：2位业务前缀 + 7-12位主体编号 + 8位日期
     * @return
     */
    public static String genSettleBillId(String entityId, Date tradeDate){
        if(StringUtils.isBlank(entityId)){
            return null;
        }
        StringBuilder sb = new StringBuilder(32);
        sb.append(SETTLE_BILL_ID_PREFIX);
        sb.append(DateFormatUtil.formatDateNoSep(tradeDate));
        sb.append(entityId);
        
        return sb.toString();
    }
    
    /**
     * 生成业务ID<br>
     * 格式：3位业务前缀 + 17位时间串(到毫秒) + 7位随机数
     * @param prefix
     * @return
     */
    private static String generateBusinessId(String prefix){
        StringBuilder sb = new StringBuilder(32);
        sb.append(prefix);
        sb.append(DateFormatUtil.formatDateTimeMillsNoSep(new Date()));
        sb.append(RandomStringUtils.randomNumeric(7));
        
        return sb.toString();
    }

    /**
     * 生成佣金结算单据编号<br>
     * 格式：2位业务前缀 + 8位日期 + 7-12位主体编号
     * @return
     */
    public static String genCommissionBillId(String entityId, Date tradeDate){
        if(StringUtils.isBlank(entityId)){
            return null;
        }
        StringBuilder sb = new StringBuilder(32);
        sb.append(COMMISSION_BILL_ID_PREFIX);
        sb.append(DateFormatUtil.formatDateNoSep(tradeDate));
        sb.append(entityId);

        return sb.toString();
    }
}
