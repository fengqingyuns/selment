package com.hanyun.platform.settle.vo.settlebill;

/**
 * <pre>
 *                             _ooOoo_
 *                            o8888888o
 *                            88" . "88
 *                            (| -_- |)
 *                            O\  =  /O
 *                         ____/`---'\____
 *                       .'  \\|     |//  `.
 *                      /  \\|||  :  |||//  \
 *                     /  _||||| -:- |||||-  \
 *                     |   | \\\  -  /// |   |
 *                     | \_|  ''\---/''  |   |
 *                     \  .-\__  `-`  ___/-. /
 *                   ___`. .'  /--.--\  `. . __
 *                ."" '<  `.___\_<|>_/___.'  >'"".
 *               | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *               \  \ `-.   \_ __\ /__ _/   .-` /  /
 *          ======`-.____`-.___\_____/___.-`____.-'======
 *                             `=---='
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *
 *                     佛祖保佑        永无BUG
 *
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * </pre>
 * 
 * @date 2018年7月6日
 * 
 *
 * @author litao@hanyun.com
 */
public class SettleBillExcelAll {
	
	private String brandId;
    private String storeId;
    private Double weixin;
    private Double alipay;
    private Double bankcard;
    private Double cash;
    private Double totalFlowAmt;
    private Double onlineFlowAmt;
    private Double payFee;
    private Double entityActualRecAmt;
    private int diffSubmitCnt;
    private Double diffTotalAmt;
    private Double settleAmt;
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public Double getWeixin() {
		return weixin;
	}
	public void setWeixin(Double weixin) {
		this.weixin = weixin;
	}
	public Double getAlipay() {
		return alipay;
	}
	public void setAlipay(Double alipay) {
		this.alipay = alipay;
	}
	public Double getBankcard() {
		return bankcard;
	}
	public void setBankcard(Double bankcard) {
		this.bankcard = bankcard;
	}
	public Double getCash() {
		return cash;
	}
	public void setCash(Double cash) {
		this.cash = cash;
	}
	public Double getTotalFlowAmt() {
		return totalFlowAmt;
	}
	public void setTotalFlowAmt(Double totalFlowAmt) {
		this.totalFlowAmt = totalFlowAmt;
	}
	public Double getOnlineFlowAmt() {
		return onlineFlowAmt;
	}
	public void setOnlineFlowAmt(Double onlineFlowAmt) {
		this.onlineFlowAmt = onlineFlowAmt;
	}
	public Double getPayFee() {
		return payFee;
	}
	public void setPayFee(Double payFee) {
		this.payFee = payFee;
	}
	public Double getEntityActualRecAmt() {
		return entityActualRecAmt;
	}
	public void setEntityActualRecAmt(Double entityActualRecAmt) {
		this.entityActualRecAmt = entityActualRecAmt;
	}
	public int getDiffSubmitCnt() {
		return diffSubmitCnt;
	}
	public void setDiffSubmitCnt(int diffSubmitCnt) {
		this.diffSubmitCnt = diffSubmitCnt;
	}
	public Double getDiffTotalAmt() {
		return diffTotalAmt;
	}
	public void setDiffTotalAmt(Double diffTotalAmt) {
		this.diffTotalAmt = diffTotalAmt;
	}
	public Double getSettleAmt() {
		return settleAmt;
	}
	public void setSettleAmt(Double settleAmt) {
		this.settleAmt = settleAmt;
	}
    
    
}
