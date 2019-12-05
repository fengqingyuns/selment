package com.hanyun.platform.settle.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hanyun.platform.settle.consts.SettlementConsts;

/**
 * 设置字典工具类，
 * 
 * @author jack
 *
 */
public class DictionaryTypeUtil {
    /** 支付方式类型  **/
    public static final Map<String, String> payTypeMap = new HashMap<String, String>();
    /** 是否可以线上结算   **/
    public static final Map<Integer, String> settleTypeMap = new HashMap<Integer, String>();
    /** 明细类型  **/
    public static final Map<Integer, String> operateTypeMap = new HashMap<Integer, String>(); 
    /** 差异来源   **/
    public static final Map<Integer, String> diffSrcMap = new HashMap<Integer, String>();
    /** 差异类型  **/
    public static final Map<Integer, String> diffTypeMap = new HashMap<Integer, String>();
    /** 差异状态  **/
    public static final Map<Integer, String> diffStatusMap = new HashMap<Integer, String>();
	/** 支付分类  **/
	public static final Map<String, String> typeCategoryMap = new HashMap<String, String>();
	/** 交易状态  **/
	public static final Map<Integer, String> transactionStatusMap = new HashMap<Integer, String>();
	/** 结算状态  **/
	public static final Map<Integer, String> settleStatusMap = new HashMap<Integer, String>();
	/** 结算周期  **/
	public static final Map<Integer, String> settleCircleMap = new HashMap<Integer, String>();
	/** 佣金结算周期  **/
	public static final Map<Integer, String> commissionSettleCircleMap = new HashMap<Integer, String>();
	/** 佣金结算状态 **/
	public static final Map<Integer, String> commissionsettleStatusMap = new HashMap<Integer, String>();

    static{
        /** 支付方式类型  **/
		payTypeMap.put(SettlementConsts.PAYTYPE_CASH, "现金");
		payTypeMap.put(SettlementConsts.PAYTYPE_BANKCARD_OFFLINE, "POS刷卡");
		payTypeMap.put(SettlementConsts.PAYTYPE_STOREDVALUE, "储值余额");
		payTypeMap.put(SettlementConsts.PAYTYPE_WXPAY_USC, "微信主动扫码");
		payTypeMap.put(SettlementConsts.PAYTYPE_WXPAY_MSC, "微信被动扫码");
		payTypeMap.put(SettlementConsts.PAYTYPE_WXPAY_JS, "微信公众号");
		payTypeMap.put(SettlementConsts.PAYTYPE_ALIPAY_USC, "支付宝主动扫码");
		payTypeMap.put(SettlementConsts.PAYTYPE_ALIPAY_MSC, "支付宝被动扫码");
	    
		/** 是否可以线上结算   **/		 
	    settleTypeMap.put(SettlementConsts.PAYTYPE_SETTLE_TYPE_ON , "是");
	    settleTypeMap.put(SettlementConsts.PAYTYPE_SETTLE_TYPE_OFF, "否");		
	    
	    /** 明细类型  **/    	
		operateTypeMap.put(SettlementConsts.TRANS_OPERATE_TYPE_PAY, "扣款");
		operateTypeMap.put(SettlementConsts.TRANS_OPERATE_TYPE_REF, "退款");    	
	    
	    /** 差异来源   **/
		diffSrcMap.put(SettlementConsts.DIFF_SRC_MERCHANT_OFFER, "商户提报");
		diffSrcMap.put(SettlementConsts.DIFF_SRC_SYS_OFFER, "系统对账");
		diffSrcMap.put(SettlementConsts.DIFF_SRC_OTHER, "其他");
	    
		/** 差异类型  **/
		diffTypeMap.put(SettlementConsts.DIFF_TYPE_AMOUNT_NOT_CONSISTENT, "金额有不匹配");
		diffTypeMap.put(SettlementConsts.DIFF_TYPE_INCONSISTENT_STATE, "状态不一致");
		diffTypeMap.put(SettlementConsts.DIFF_TYPE_OTHER, "其他");
	    
		/** 差异状态  **/
		diffStatusMap.put(SettlementConsts.DIFF_STATUS_NOT_PROCESSED, "未处理");
		diffStatusMap.put(SettlementConsts.DIFF_STATUS_SOLVED, "已解决");

		/** 支付分类  **/
		typeCategoryMap.put(SettlementConsts.PAY_CATEGORY_CASH,"现金");
		typeCategoryMap.put(SettlementConsts.PAY_CATEGORY_WEIXIN,"微信");
		typeCategoryMap.put(SettlementConsts.PAY_CATEGORY_ALIPAY,"支付宝");
		typeCategoryMap.put(SettlementConsts.PAY_CATEGORY_BANKCARD,"银行卡");

		/** 交易状态  **/
		transactionStatusMap.put(SettlementConsts.TRANS_STATUS_INIT,"初始化");
		transactionStatusMap.put(SettlementConsts.TRANS_STATUS_PROCESS,"处理中");
		transactionStatusMap.put(SettlementConsts.TRANS_STATUS_FINISH,"已完成");
		transactionStatusMap.put(SettlementConsts.TRANS_STATUS_CANCLE,"取消");
		transactionStatusMap.put(SettlementConsts.TRANS_STATUS_FAIL,"失败");
		/**
		 * 结算状态
		 */
		settleStatusMap.put(SettlementConsts.NEW_SETTLE_STATUS_NOT_GENES,"未生成");
		settleStatusMap.put(SettlementConsts.NEW_SETTLE_STATUS_NOT_SETTLE,"未结算");
		settleStatusMap.put(SettlementConsts.NEW_SETTLE_STATUS_SETTLED,"已结算");

		/**
		 * 结算周期
		 */
		settleCircleMap.put(1,"T+1");
		settleCircleMap.put(2,"T+2");
		settleCircleMap.put(3,"T+3");
		settleCircleMap.put(4,"T+4");
		settleCircleMap.put(5,"T+5");

		/**
		 * 佣金结算周期
		 */
		commissionSettleCircleMap.put(0,"按天结算");
		commissionSettleCircleMap.put(1,"按月结算");
		commissionSettleCircleMap.put(2,"按季结算");
		commissionSettleCircleMap.put(3,"按年结算");


		/**
		 * 佣金结算状态
		 */
		commissionsettleStatusMap.put(0,"待结算");
		commissionsettleStatusMap.put(1,"部分结算");
		commissionsettleStatusMap.put(2,"已结算");

	}
    
    
    /**
     * 
    * @Title: getPayTypeDes 
    * @Description: 支付方式类型 
    * @param  
    * @return String   
    * @throws
     */
	public static String getPayTypeDes(String payType) {
		if (StringUtils.isNotEmpty(payType)) {
			return payTypeMap.get(payType);
		} else {
			return "";
		}
	}
	
	/**
	 * 
	* @Title: getSettleTypeDes 
	* @Description: 是否可以线上结算
	* @param  
	* @return String
	* @throws
	 */
	public static String getSettleTypeDes(Integer settleType) {
		if (settleType != null) {
		    return settleTypeMap.get(settleType);
		} else {
			return "";
		}
	}
	
	/**
	 * 
	* @Title: getOperateTypeDes 
	* @Description: 明细类型 
	* @param  
	* @return String   
	* @throws
	 */
	public static String getOperateTypeDes(Integer operateType) {
		if (operateType != null) {
			return operateTypeMap.get(operateType);
		} else {
			return "";
		}
	}
	
	/**
	 * 
	* @Title: getDiffSrcDes 
	* @Description: 差异来源 
	* @param  
	* @return String   
	* @throws
	 */
   public static String getDiffSrcDes(Integer diffSrc){
		if (diffSrc != null) {
			return diffSrcMap.get(diffSrc);
		} else {
			return "";
		}	   
	   
   }
   
   /**
    * 
   * @Title: getDiffTypeDes 
   * @Description: 差异类型 
   * @param  
   * @return String   
   * @throws
    */
   public static String getDiffTypeDes(Integer diffType){
		if (diffType != null) {
			return diffTypeMap.get(diffType);
		} else {
			return "";
		}	   
	   
   }
   
   /**
    * 
   * @Title: getDiffStatusDes 
   * @Description: 差异状态 
   * @param  
   * @return String   
   * @throws
    */
   public static String getDiffStatusDes(Integer diffStatus){
		if (diffStatus != null) {
			return diffStatusMap.get(diffStatus);
		} else {
			return "";
		}	   
	   
   }
	/**
	 *
	 * @Title:
	 * @Description: 支付分类
	 * @param
	 * @return String
	 * @throws
	 */
	public static String geTypeCategoryDes(String typeCategory){
		if (!"".equals(typeCategory)) {
			return typeCategoryMap.get(typeCategory);
		} else {
			return "";
		}

	}

	/**
	 *
	 * @Title: TransactionStatusDes
	 * @Description: 交易状态
	 * @param
	 * @return String
	 * @throws
	 */
	public static String getTransactionStatusDes(Integer status){
		if (status != null) {
			return transactionStatusMap.get(status);
		} else {
			return "";
		}

	}


	/**
	 *
	 * @Title: getSettleStatusDes
	 * @Description: 结算状态
	 * @param
	 * @return String
	 * @throws
	 */
	public static String getSettleStatusDes(Integer status){
		if (status != null) {
			return settleStatusMap.get(status);
		} else {
			return "";
		}

	}

	/**
	 *
	 * @Title: getSettleCircleDes
	 * @Description: 结算周期
	 * @param
	 * @return String
	 * @throws
	 */
	public static String getSettleCircleDes(Integer settleCircle){
		if (settleCircle != null) {
			return settleCircleMap.get(settleCircle);
		} else {
			return "";
		}

	}

	/**
	 *
	 * @Title: getCommissionSettleCircleDes
	 * @Description: 佣金结算周期
	 * @param
	 * @return String
	 * @throws
	 */
	public static String getCommissionSettleCircleDes(Integer settleCircle){
		if (settleCircle != null) {
			return commissionSettleCircleMap.get(settleCircle);
		} else {
			return "";
		}

	}

	/**
	 *
	 * @Title: getCommissionSettleStatusDes
	 * @Description: 佣金结算状态
	 * @param
	 * @return String
	 * @throws
	 */
	public static String getCommissionSettleStatusDes(Integer settleStatus){
		if (settleStatus != null) {
			return commissionsettleStatusMap.get(settleStatus);
		} else {
			return "";
		}

	}

}
