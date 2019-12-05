package com.hanyun.platform.settle.consts;

/**
 * 佣金相关的常量
 * @author wangjie@hanyun.com
 * @Date 2018/2/27 10:38
 */
public abstract class CommissionConsts {

    /** 结算佣金开关－是 */
    public static final Integer COMMISSION_SETTLEMENT_SWITCH_YES = 0;
    /** 结算佣金开关－否 */
    public static final Integer COMMISSION_SETTLEMENT_SWITCH_NO = 1;

    /** 是否独立结算-是 */
    public static final Integer SEPARATE_SETTLE_YES = 0;
    /** 是否独立结算-否 */
    public static final Integer SEPARATE_SETTLE_NO = 1;

    /** 品牌 */
    public static final Integer ENTITY_TYPE_BRAND = 1;
    /** 门店 */
    public static final Integer ENTITY_TYPE_STORE = 2;

    /** 是否线上抽佣－是 */
    public static final Integer ONLINE_COMMISSION_YES = 0;
    /** 是否线上抽佣－否 */
    public static final Integer ONLINE_COMMISSION_NO = 1;

    /** 佣金结算周期－按天 */
    public static final Integer COMMISSION_BILL_CIRCLE_DAY = 0;
    /** 佣金结算周期－按月 */
    public static final Integer COMMISSION_BILL_CIRCLE_MONTH = 1;
    /** 佣金结算周期－按季度 */
    public static final Integer COMMISSION_BILL_CIRCLE_SEASON = 2;
    /** 佣金结算周期－按年 */
    public static final Integer COMMISSION_BILL_CIRCLE_YEAR = 3;

    /** 佣金结算方式－线上结算 */
    public static final Integer COMMISSION_SETTLE_TYPE_YES = 0;
    /** 佣金结算方式－线下结算 */
    public static final Integer COMMISSION_SETTLE_TYPE_NO = 1;

    /** 佣金结算状态－待结算 */
    public static final Integer COMMISSION_BILL_STATUS_UNSETTLEMENT = 0;
    /** 佣金结算状态－部分结算 */
    public static final Integer COMMISSION_BILL_STATUS_PARTIALLY_SETTLED = 1;
    /** 佣金结算状态－已结算 */
    public static final Integer COMMISSION_BILL_STATUS_SETTLED = 2;
}
