/**
 * 
 */
package com.hanyun.platform.settle.consts;

import java.util.HashSet;
import java.util.Set;

/**
 * 结算相关常量
 * @author liyinglong@hanyun.com
 * @date 2016年7月26日 上午10:02:28
 */
public abstract class SettlementConsts {
    /** 费率存储使用的乘数(倍数) */
    public static final long FEE_RATE_MULTIPLIER = 1000000L;
    
    /** 支付方式可线上结算标识-是 */
    public static final Integer PAYTYPE_SETTLE_TYPE_ON = 0;
    /** 支付方式可线上结算标识-否 */
    public static final Integer PAYTYPE_SETTLE_TYPE_OFF = 1;
    
    /** 统一平台结算-是 */
    public static final Integer UNIFORM_SETTLE_ON = 0;
    /** 统一平台结算-否 */
    public static final Integer UNIFORM_SETTLE_OFF = 1;
    
    /** 支付流水操作类型－扣款 */
    public static final Integer TRANS_OPERATE_TYPE_PAY = 1;
    /** 支付流水操作类型－退款 */
    public static final Integer TRANS_OPERATE_TYPE_REF = 2;
    
    /** 支付流水状态－已完成 */
    public static final Integer TRANS_STATUS_FINISH = 20;
    /** 支付流水状态－初始化 */
    public static final Integer TRANS_STATUS_INIT = 0;
    /** 支付流水状态－处理中 */
    public static final Integer TRANS_STATUS_PROCESS= 10;
    /** 支付流水状态－取消*/
    public static final Integer TRANS_STATUS_CANCLE = 30;
    /** 支付流水状态－失败*/
    public static final Integer TRANS_STATUS_FAIL= 50;

    /** 结算单状态-非平台统一结算 */
    public static final Integer SETTLE_STATUS_NOT_UNIFORM_SETTLE = -1;
    /** 结算单状态-初始 */
    public static final Integer SETTLE_STATUS_INIT = 0;
    /** 结算单状态-已生成 */
    public static final Integer SETTLE_STATUS_CREATED = 10;
    /** 结算单状态-商户已确认 */
    public static final Integer SETTLE_STATUS_MCHCONFIRM = 20;
    /** 结算单状态-财务已确认 */
    public static final Integer SETTLE_STATUS_FINCONFIRM = 30;
    /** 结算单状态-财务已打款 */
    public static final Integer SETTLE_STATUS_FINPAY = 40;
    
    /** 差异来源-商户提报 */
    public static final Integer DIFF_SRC_MERCHANT_OFFER = 1;
    /** 差异来源-系统对账 */    
    public static final Integer DIFF_SRC_SYS_OFFER = 2;
    /** 差异来源-其他*/
    public static final Integer DIFF_SRC_OTHER = 3;
    
    /** 差异类型-金额有不匹配 */
    public static final Integer DIFF_TYPE_AMOUNT_NOT_CONSISTENT = 1;
    /** 差异类型-状态不一致 */
    public static final Integer DIFF_TYPE_INCONSISTENT_STATE = 2;
    /** 差异类型-其他 */
    public static final Integer DIFF_TYPE_OTHER = 3;

    
    /** 差异状态-未处理 */    
    public static final Integer DIFF_STATUS_NOT_PROCESSED = 0;    
    /** 差异状态-已解决 */    
    public static final Integer DIFF_STATUS_SOLVED = 10;
    
    /** 支付方式－现金 */
    public static final String PAYTYPE_CASH = "CASH";    
    /** 支付方式－离线pos刷卡 */
    public static final String PAYTYPE_BANKCARD_OFFLINE = "BANKCARD_OFFLINE";
    /** 支付方式－储值余额 */
    public static final String PAYTYPE_STOREDVALUE = "STOREDVALUE";
    /** 支付方式－pos刷卡 */
    public static final String PAYTYPE_BANKCARD = "BANKCARD";
    /** 支付方式－微信用户扫码 */
    public static final String PAYTYPE_WXPAY_USC = "WXPAY_USC";
    /** 支付方式－微信商户扫码 */
    public static final String PAYTYPE_WXPAY_MSC = "WXPAY_MSC";
    /** 支付方式－微信公众号H5 */
    public static final String PAYTYPE_WXPAY_JS = "WXPAY_JS";
    /** 支付方式－支付宝用户扫码 */
    public static final String PAYTYPE_ALIPAY_USC = "ALIPAY_USC";
    /** 支付方式－支付宝商户扫码 */
    public static final String PAYTYPE_ALIPAY_MSC = "ALIPAY_MSC";
    
    /** 支付方式分类－现金 */
    public static final String PAY_CATEGORY_CASH = "CASH";
    /** 支付方式分类－微信 */
    public static final String PAY_CATEGORY_WEIXIN = "WEIXIN";
    /** 支付方式分类－支付宝 */
    public static final String PAY_CATEGORY_ALIPAY = "ALIPAY";
    /** 支付方式分类－银行卡*/
    public static final String PAY_CATEGORY_BANKCARD = "BANKCARD";
    /** 支付方式分类－商场支付 */
    public static final String PAY_CATEGORY_SHOPPINGMALL = "SHOPPINGMALL";
    /** 所有支付方式分类集合 */
    public static final Set<String> ALL_PAY_CATEGORY_SET = new HashSet<>();
    static{
        ALL_PAY_CATEGORY_SET.add(PAY_CATEGORY_WEIXIN);
        ALL_PAY_CATEGORY_SET.add(PAY_CATEGORY_ALIPAY);
        ALL_PAY_CATEGORY_SET.add(PAY_CATEGORY_BANKCARD);
        ALL_PAY_CATEGORY_SET.add(PAY_CATEGORY_CASH);
        ALL_PAY_CATEGORY_SET.add(PAY_CATEGORY_SHOPPINGMALL);
    }
    /** 在线支付方式分类集合 */
    public static final Set<String> ONLINE_PAY_CATEGORY_SET = new HashSet<>();
    static{
        ONLINE_PAY_CATEGORY_SET.add(PAY_CATEGORY_WEIXIN);
        ONLINE_PAY_CATEGORY_SET.add(PAY_CATEGORY_ALIPAY);
        ONLINE_PAY_CATEGORY_SET.add(PAY_CATEGORY_BANKCARD);
    }
    
    /** 现金  微信 支付宝  银行卡 四种支付方式*/
    public static final Integer PAY_CATEGORY = 4;

    /** 主体类型 1 品牌   2  门店 */
    public static final Integer ENTITY_TYPE_BRAND = 1;
    public static final Integer ENTITY_TYPE_STORE = 2;

    /** 独立结算：0-是，1-否； */
    public static final Integer SEPARATE_SETTLE_YES = 0;
    public static final Integer SEPARATE_SETTLE_NO = 1;

    /** 资金归集：0-是，1-否 */
    public static final Integer CAPITAL_COLLECT_YES = 0;
    public static final Integer CAPITAL_COLLECT_NO = 1;

    /** 资资金归集方式：1-品牌统一，2-门店独立 */
    public static final Integer CAPITAL_COLLECT_TYPE_BRAND = 1;
    public static final Integer CAPITAL_COLLECT_TYPE_STORE = 2;

    /** 可用状态：0-启用，1-停用 */
    public static final Integer AVAIL_STATUS_START = 0;
    public static final Integer AVAIL_STATUS_STOP = 1;

    /** 结算状态：1-未生成，10-未结算，20-已结算 */
    public static final Integer NEW_SETTLE_STATUS_NOT_GENES = 1;
    public static final Integer NEW_SETTLE_STATUS_NOT_SETTLE = 10;
    public static final Integer NEW_SETTLE_STATUS_SETTLED = 20;

    /** 业务单据类型：1-结算单 */
    public static final Integer BUSINESS_KEY_SETTLE = 1;

    /** 流程状态：0-未启动；1-审核中；2-审核完成 */
    public static final Integer PROCESS_STATUS_NOT_STARTED = 0;
    public static final Integer PROCESS_STATUS_IN_AUDIT = 1;
    public static final Integer PROCESS_STATUS_AUDIT_COMPLETED= 2;

    /**当前任务审批人类型：1-人员；2-角色；3-机构 */
    public static final Integer PROCESS_AUDIT_PERSON = 1;
    public static final Integer PROCESS_AUDIT_ROLE = 2;
    public static final Integer PROCESS_AUDIT_ORGANIZATION = 3;



}
