package com.hanyun.platform.settle.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hanyun.ground.util.date.DateCalcUtil;
import com.hanyun.ground.util.date.DateFormatUtil;
import com.hanyun.ground.util.http.HttpClient;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.platform.settle.consts.SettlementConsts;
import com.hanyun.platform.settle.dao.SettleBillDao;
import com.hanyun.platform.settle.dao.SettleBillDetailDao;
import com.hanyun.platform.settle.domain.*;
import com.hanyun.platform.settle.service.SettleBillService;
import com.hanyun.platform.settle.util.*;
import com.hanyun.platform.settle.vo.*;
import com.hanyun.platform.settle.vo.base.PageResData;
import com.hanyun.platform.settle.dao.SettleBillPaychnDao;
import com.hanyun.platform.settle.dao.SettleEntityDao;
import com.hanyun.platform.settle.dao.*;
import com.hanyun.platform.settle.vo.settlebill.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import static java.lang.System.out;


/**
 * Created by jack on 2017/4/6.
 */
@Service
public class SettleBillServiceImpl implements SettleBillService {
    private static Logger LOGGER = LoggerFactory.getLogger(SettleBillServiceImpl.class);

    @Resource
    private SettleEntityDao settleEntityDao;
    @Resource
    private SettleBillDao settleBillDao;
    @Resource
    private SettleBillPaychnDao settleBillPaychnDao;
    @Resource
    private SettleBillDetailDao settleBillDetailDao;
    @Resource
    private SettleEntityBankAccDao settleEntityBankAccDao;
    @Resource
    private CommissionBillDao commissionBillDao;
    
    String[][] headerstemp = {{" ","30"}};
    String[][] headers1 = {{"数据统计信息","30"}};      
    String[][] headers2 = {{"品牌","30"},{" ","20"},{"门店","20"},{"","20"},{"","20"},{"微信","12"},{"支付宝","12"},{"银行卡","12"},{"现金","12"},{"总流水","12"},
    		{"线上流水","12"},{"手续费","12"},{"实收金额","12"},{"异常订单数量","12"},{"异常金额","12"},{"结算金额","12"}};
    String[][] headers3 = {{"数据明细信息","30"}};
    private static String[][] headers = { { "结算单号", "30" }, { "品牌", "20" }, { "门店", "20" }, { "交易日期", "25" },
            { "结算周期", "12" }, { "微信", "12" }, { "支付宝", "12" }, { "银行卡", "12" }, { "现金", "12" }, { "总流水", "12" },
            { "线上流水", "12" },{ "手续费", "12" },{ "实收金额", "12" },{ "新增异常订单量", "12" },{ "异常金额", "12" },{ "结算金额", "12" }
            ,{ "状态", "12" }};
    private static String[] fieldValues2 = {"brandId","storeId", "storeId","weixin","weixin","weixin","alipay", "bankcard", "cash", "totalFlowAmt",
    		"onlineFlowAmt", "payFee", "entityActualRecAmt", "diffSubmitCnt",
            "diffTotalAmt", "settleAmt"};
    private static String[] fieldValues = { "billId", "brandId", "storeId", "tradeDateStart", "settleCircle", "weixin",
            "alipay", "bankcard", "cash", "totalFlowAmt", "onlineFlowAmt", "payFee", "entityActualRecAmt", "diffSubmitCnt",
            "diffTotalAmt", "settleAmt", "settleStatus"};

    /**
     * @Author : wangximin
     * @Date : 2017/4/6 18:38
     * @Describe : 查询结算单列表
     */
    public PageResData getSettleBillList(SettleBillListReq settleBillListReq) {
        if(StringUtils.isNotBlank(settleBillListReq.getBeginDate())) {
            settleBillListReq.setBeginTime(DateCalcUtil.getDayBegin(DateFormatUtil.parseDate(settleBillListReq.getBeginDate())));
        }
        if(StringUtils.isNotBlank(settleBillListReq.getEndDate())) {
            settleBillListReq.setEndTime(DateCalcUtil.getDayEnd(DateFormatUtil.parseDate(settleBillListReq.getEndDate())));
        }
        List<SettleBill> settleBillList = settleBillDao.getSettleBillList(settleBillListReq);
        List<SettleBillExt>  list = ConvertList(settleBillList);//增加各个支付通道明细
        Integer count = settleBillDao.getSettleBillListCount(settleBillListReq);
        PageResData pageResData = new PageResData();
        pageResData.setDataList(list);
        pageResData.setTotalCount(count);

        return pageResData;
    }

    /**
     * @Author : wangximin
     * @Date : 2017/4/7 14:29
     * @Describe : 获取单个结算单的详情
     */
    public SettleBill getSingleSettleBill(SettleBill param) {
        return settleBillDao.getSingleSettleBill(param);
    }

    /**
     * @Author : wangximin
     * @Date : 2017/4/7 14:36
     * @Describe : 结算单常规明细
     */
    public PageResData getSettleDetailList(SettleBillDetailReq settleBillDetailReq) {

        SettleBill param = new SettleBill();
        param.setBillId(settleBillDetailReq.getBillId());
        SettleBill settleBill = settleBillDao.getSingleSettleBill(param);
        settleBillDetailReq.setBrandId(settleBill.getBrandId());
        settleBillDetailReq.setTradeDateStart(DateCalcUtil.getDayBegin(settleBill.getTradeDateStart()));
        settleBillDetailReq.setTradeDateEnd(DateCalcUtil.getDayEnd(settleBill.getTradeDateStart()));
        settleBillDetailReq.setStoreId(settleBill.getStoreId());
        List<SettleBillDetail> settleDetailsList = settleBillDetailDao.getSettleDetailList(settleBillDetailReq);
        Integer count  = settleBillDetailDao.getSettleDetailListCount(settleBillDetailReq);
        PageResData pageResData = new PageResData();
        pageResData.setTotalCount(count);
        pageResData.setDataList(settleDetailsList);
        return  pageResData;
    }



    /**
     * @Author : wangximin
     * @Date : 2017/4/7 14:32
     * @Describe : 结算明细中对账差异列表
     */
    public PageResData getSettleDiffDetailList(SettleBillDetailReq settleBillDetailReq) {

        SettleBill param = new SettleBill();
        param.setBillId(settleBillDetailReq.getBillId());
        SettleBill settleBill = settleBillDao.getSingleSettleBill(param);
        settleBillDetailReq = JsonUtil.fromJson(JsonUtil.toJson(settleBill),settleBillDetailReq.getClass());
        settleBillDetailReq.setTradeDateStart(DateCalcUtil.getDayBegin(settleBill.getTradeDateStart()));
        settleBillDetailReq.setTradeDateEnd(DateCalcUtil.getDayEnd(settleBill.getTradeDateStart()));
        settleBillDetailReq.setStoreId(settleBill.getStoreId());
        List<SettleBillDetail> settleDiffDetailsList = settleBillDetailDao.getSettleDiffDetailList(settleBillDetailReq);
        PageResData pageResData = new PageResData();
        pageResData.setDataList(settleDiffDetailsList);

        return pageResData;
    }

    /**
     * @Author : wangximin
     * @Date : 2017/4/7 15:30
     * @Describe : 品牌结算单(根据不同类型查询数据)
     */
    public SettleBillRes getSettleBill(SettleBillReq settleBillReq) {

        SettleBillRes response = new SettleBillRes();

        //基本信息
        SettleBill settleParam = new SettleBill(settleBillReq.getBillId(),settleBillReq.getBrandId(),settleBillReq.getStoreId());
        SettleBill settleBill = settleBillDao.getSingleSettleBill(settleParam);
        response.setSettleBill(settleBill);

        //结算信息
        SettleEntityBankAcc param = new SettleEntityBankAcc(settleBill.getEntityId(),settleBill.getBrandId(),settleBill.getStoreId());
        SettleEntityBankAcc settleEntityBankAcc = settleEntityBankAccDao.getSingleEntityBankAcc(param);
        response.setSettleEntityBankAcc(settleEntityBankAcc);

        //品牌的结算单
        if(StringUtils.isBlank(settleBillReq.getStoreId())){
            //门店分账(无分页)
            settleBillReq.setBeginTime(DateCalcUtil.getDayBegin(settleBill.getTradeDateStart()));
            settleBillReq.setEndTime(DateCalcUtil.getDayEnd(settleBill.getTradeDateStart()));
            settleBillReq.setEntityType(SettlementConsts.ENTITY_TYPE_STORE);
            List<SettleBill> settleBillsList = settleBillDao.getSettleBillDetailList(settleBillReq);
            SettleBill sumSettleBill1 = settleBillDao.sumSettleBill(settleBillReq);
            if(sumSettleBill1!=null){
                settleBillsList.add(sumSettleBill1);
                response.setSettleBillList(settleBillsList);
            }
            //结算账单
            SettleBillPaychn settleBillPaychn = new SettleBillPaychn(settleBillReq.getBillId(),settleBillReq.getBrandId());
            List<SettleBillPaychn> settleBillPaychnsList = settleBillPaychnDao.getSettlePayChnList(settleBillPaychn);
            SettleBillPaychn sumSettle = settleBillPaychnDao.sumSettleBillPayChn(settleBillPaychn);
            if(sumSettle!=null){
                sumSettle.setPayTypeCategory("SUM");
                settleBillPaychnsList.add(sumSettle);
            }
            response.setSettleBillPaychnsList(settleBillPaychnsList);

        }else{
            //结算账单
            SettleBillPaychn settleBillPaychn = new SettleBillPaychn();
            settleBillPaychn.setBillId(settleBillReq.getBillId());
            settleBillPaychn.setBrandId(settleBillReq.getBrandId());
            settleBillPaychn.setStoreId(settleBillReq.getStoreId());

            List<SettleBillPaychn> settleBillPaychnsList = settleBillPaychnDao.getSettlePayChnList(settleBillPaychn);
            SettleBillPaychn sumSettle = settleBillPaychnDao.sumSettleBillPayChn(settleBillPaychn);
            if(sumSettle!=null){
                sumSettle.setPayTypeCategory("SUM");
                settleBillPaychnsList.add(sumSettle);
            }
            response.setSettleBillPaychnsList(settleBillPaychnsList);
        }

        return response;
    }

    /**
     * @Author : wangximin
     * @Date : 2017/4/7 14:32
     * @Describe : 结算明细中新增对账差异列表
     */
    public PageResData getAddSettleDiffDetailList(SettleBillDetailReq settleBillDetailReq) {

        SettleBill param = new SettleBill();
        param.setBillId(settleBillDetailReq.getBillId());
        SettleBill settleBill = settleBillDao.getSingleSettleBill(param);
        settleBillDetailReq = JsonUtil.fromJson(JsonUtil.toJson(settleBill),settleBillDetailReq.getClass());
        settleBillDetailReq.setTradeDateStart(DateCalcUtil.getDayBegin(settleBill.getTradeDateStart()));
        settleBillDetailReq.setTradeDateEnd(DateCalcUtil.getDayEnd(settleBill.getTradeDateStart()));
        settleBillDetailReq.setStoreId(settleBill.getStoreId());
        List<SettleBillDetail> settleDiffDetailsList = settleBillDetailDao.getAddSettleDiffDetailList(settleBillDetailReq);
        PageResData pageResData = new PageResData();
        pageResData.setDataList(settleDiffDetailsList);

        return pageResData;
    }

    @Override
    public HttpResponse updateSettleStatus(SettleBillReq settleBillReq) {

        SettleBill settleBill = JsonUtil.fromJson(JsonUtil.toJson(settleBillReq),SettleBill.class);
        int count = settleBillDao.updateSettleBill(settleBill);
        LOGGER.debug("更新受影响行数{}",count);
        return HttpResponse.success();
    }

    @Override
    public List<SettleBillPaychn> getSettlePayChnList(SettleBillPaychn settleBillPaychn) {
        List<SettleBillPaychn> settleBillPaychnsList = settleBillPaychnDao.getSettlePayChnList(settleBillPaychn);
        return settleBillPaychnsList;
    }

    /**
     *
     * @param response  导出结算列表
     * @param request
     * @param settleBillListReq
     */
    @Override
    public void expSettleBillList(HttpServletResponse response, HttpServletRequest request, SettleBillListReq settleBillListReq) throws IOException {
        if(StringUtils.isNotBlank(settleBillListReq.getBeginDate())) {
            settleBillListReq.setBeginTime(DateCalcUtil.getDayBegin(DateFormatUtil.parseDate(settleBillListReq.getBeginDate())));
        }
        if(StringUtils.isNotBlank(settleBillListReq.getEndDate())) {
            settleBillListReq.setEndTime(DateCalcUtil.getDayEnd(DateFormatUtil.parseDate(settleBillListReq.getEndDate())));
        }

        settleBillListReq.setPageNo(1);
        settleBillListReq.setPageSize(Integer.MAX_VALUE);
        List<SettleBill> settleBillList = settleBillDao.getSettleBillList(settleBillListReq);
        List<SettleBillExt>  list = ConvertList(settleBillList);//增加各个支付通道明细
        List<SettleBillExcel> settleBillListExcel = classTransformation(list);

        String expFileName = "结算单列表";
        OutputStream out = null;
        response.reset();// 清空输出流
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String((expFileName + ".xls").getBytes(), "iso-8859-1"));
        out = response.getOutputStream();// 取得输出流
        try {
            new ExcelUtil<SettleBillExcel>("结算单列表", headers, fieldValues, settleBillListExcel, out)
                    .ExportExcel();
        } catch (Exception e) {
            LOGGER.error("生成文件失败!", e);
        } finally {
            out.close();
        }
    }

    /**
     * 查询结算单和结算明细汇总
     * @param settleBillListReq
     * @return
     */
    @Override
    public SettleBillExt getSummarySettleBill(SettleBillListReq settleBillListReq) {
        SettleBillExt settleBillExt = new  SettleBillExt();
        if(StringUtils.isNotBlank(settleBillListReq.getBeginDate())) {
            settleBillListReq.setBeginTime(DateCalcUtil.getDayBegin(DateFormatUtil.parseDate(settleBillListReq.getBeginDate())));
        }

        if(StringUtils.isNotBlank(settleBillListReq.getEndDate())) {
            settleBillListReq.setEndTime(DateCalcUtil.getDayEnd(DateFormatUtil.parseDate(settleBillListReq.getEndDate())));
        }

        
        //获取结算单汇总信息
        SettleBill settleBill =  settleBillDao.getSummarySettleBill(settleBillListReq);
        if (settleBill != null) {
            settleBillExt.setBrandId(settleBill.getBrandId());
            settleBillExt.setStoreId(settleBill.getStoreId());
            settleBillExt.setTotalFlowAmt(settleBill.getTotalFlowAmt());
            settleBillExt.setOnlineFlowAmt(settleBill.getOnlineFlowAmt());
            settleBillExt.setPayFee(settleBill.getPayFee());
            settleBillExt.setPlatformActualRecAmt(settleBill.getPlatformActualRecAmt());
            settleBillExt.setDiffSubmitCnt(settleBill.getDiffSubmitCnt());
            settleBillExt.setDiffTotalAmt(settleBill.getDiffTotalAmt());
            settleBillExt.setSettleAmt(settleBill.getSettleAmt());
        }
        //获取各渠道汇总信息
        SettleBillListReq settleBillPayChnParam =  new SettleBillListReq();
        settleBillPayChnParam.setBrandId(settleBillListReq.getBrandId());
        settleBillPayChnParam.setStoreId(settleBillListReq.getStoreId());
        settleBillPayChnParam.setBeginTime(settleBillListReq.getBeginTime());
        settleBillPayChnParam.setEndTime(settleBillListReq.getEndTime());


        List<SettleBillPaychn> settleBillPaychnList = settleBillPaychnDao.getSummarySettleBillPayChn(settleBillPayChnParam);
        if (settleBillPaychnList != null && settleBillPaychnList.size()>0) {
            for (SettleBillPaychn settleBillPaychn:settleBillPaychnList){
                 if(SettlementConsts.PAY_CATEGORY_WEIXIN.equals(settleBillPaychn.getPayTypeCategory())){
                     settleBillExt.setWEIXIN(settleBillPaychn.getTotalFlowAmt());
                 }

                if(SettlementConsts.PAY_CATEGORY_ALIPAY.equals(settleBillPaychn.getPayTypeCategory())){
                    settleBillExt.setALIPAY(settleBillPaychn.getTotalFlowAmt());
                }

                if(SettlementConsts.PAY_CATEGORY_BANKCARD.equals(settleBillPaychn.getPayTypeCategory())){
                    settleBillExt.setBANKCARD(settleBillPaychn.getTotalFlowAmt());
                }
                if(SettlementConsts.PAY_CATEGORY_CASH.equals(settleBillPaychn.getPayTypeCategory())){
                    settleBillExt.setCASH(settleBillPaychn.getTotalFlowAmt());
                }
            }
        }
        return settleBillExt;
    }

    /**
     * 退款明细
     * @param settleBillDetailReq
     * @return
     */
    @Override
    public PageResData getSettleRefundDetailList(SettleBillDetailReq settleBillDetailReq) {
        SettleBill param = new SettleBill();
        param.setBillId(settleBillDetailReq.getBillId());
        SettleBill settleBill = settleBillDao.getSingleSettleBill(param);
        settleBillDetailReq.setBrandId(settleBill.getBrandId());
        settleBillDetailReq.setTradeDateStart(DateCalcUtil.getDayBegin(settleBill.getTradeDateStart()));
        settleBillDetailReq.setTradeDateEnd(DateCalcUtil.getDayEnd(settleBill.getTradeDateStart()));
        settleBillDetailReq.setStoreId(settleBill.getStoreId());
        List<SettleBillDetail> settleDetailsList = settleBillDetailDao.getSettleRefundDetailList(settleBillDetailReq);
        PageResData pageResData = new PageResData();
        pageResData.setDataList(settleDetailsList);
        return pageResData;
    }
    /**
     * 封装品牌结算总Excel数据
     * @param settleBillExt
     * @return
     */
    private List<SettleBillExcelAll> classTransformationAll(SettleBillExt settleBillExt){
    
    	  Set<String>  brandSet = new HashSet<String>();//品牌列表
          Set<StoreInfo>  storeSet = new HashSet<StoreInfo>();;//门店列表
          Map<String,String> brandMap = new HashMap<String,String>();
          Map<String,String> storeMap = new HashMap<String,String>();

          PropertiesUtil propertiesUtil = new PropertiesUtil();
          Properties properties = null;
          try {
              properties = propertiesUtil.getProperties("settlement-api.properties");
          } catch (Exception e) {
              e.printStackTrace();
          }
          String brandNameArraysQueryUrl = properties.getProperty("brandNameArraysQueryUrl");
          String brandAndStoreArraysQueryUrl = properties.getProperty("brandAndStoreArraysQueryUrl");


          	if(settleBillExt !=null){
                  if(settleBillExt.getBrandId() !=null && settleBillExt.getStoreId() !=null){
                      StoreInfo storeInfo = new StoreInfo();
                      storeInfo.setBrandId(settleBillExt.getBrandId());
                      storeInfo.setStoreId(settleBillExt.getStoreId());
                      storeSet.add(storeInfo);
                  }

                  if(settleBillExt.getBrandId() !=null && settleBillExt.getStoreId() == null){
                      brandSet.add(settleBillExt.getBrandId());
                  }
          	}
          if ( brandSet.size() >0 ){
              String[] brandArray = new String[brandSet.size()];
              String[] brandInfosArray = (String[])brandSet.toArray(brandArray);
              StringBuilder brandNameQueryUrlBuilder = new StringBuilder();
              brandNameQueryUrlBuilder.append(brandNameArraysQueryUrl);
              String brandInfo = HttpClient.post(brandNameQueryUrlBuilder.toString()).json(JsonUtil.toJson(brandInfosArray)).action().result();

              HttpResponse httpResponse = JsonUtil.fromJson(brandInfo, HttpResponse.class);
              BrandInfo[] brandInfos = JsonUtil.fromJson(JsonUtil.toJson(httpResponse.getData()),new TypeReference<BrandInfo[]>() {
              });
              for(BrandInfo brandInfo1:brandInfos){
                  brandMap.put(brandInfo1.getBrandId(),brandInfo1.getBrandName());
              }

          }

          if( storeSet.size() > 0){
              StoreInfo[] storeArray = new StoreInfo[storeSet.size()];
              StoreInfo[] storeInfosArray = (StoreInfo[])storeSet.toArray(storeArray);

              StringBuilder brandAndStoreQueryUrlBuilder = new StringBuilder();
              brandAndStoreQueryUrlBuilder.append(brandAndStoreArraysQueryUrl);
              String brandAndStoreInfo = HttpClient.post(brandAndStoreQueryUrlBuilder.toString()).json(JsonUtil.toJson(storeInfosArray)).action().result();
              HttpResponse httpResponse = JsonUtil.fromJson(brandAndStoreInfo, HttpResponse.class);
              BrandInfo[] storeInfos = JsonUtil.fromJson(JsonUtil.toJson(httpResponse.getData()), new TypeReference<BrandInfo[]>(){
              });
              for(BrandInfo brandInfo2:storeInfos){
                  brandMap.put(brandInfo2.getBrandId(),brandInfo2.getBrandName());
                  storeMap.put(brandInfo2.getStoreId(),brandInfo2.getStoreName());
              }

          }
    	
    	List<SettleBillExcelAll> settleBillExcelAllList = new ArrayList<>();
    	SettleBillExcelAll settleBillExcel = new SettleBillExcelAll();
    	if(settleBillExt.getBrandId() !=null && settleBillExt.getStoreId() !=null){
            settleBillExcel.setBrandId(brandMap.get(settleBillExt.getBrandId()));
            settleBillExcel.setStoreId(storeMap.get(settleBillExt.getStoreId()));
        }
        if(settleBillExt.getBrandId() !=null && null == settleBillExt.getStoreId()){
            settleBillExcel.setBrandId(brandMap.get(settleBillExt.getBrandId()));
        }
        if (settleBillExt.getWEIXIN() == null ) {
            settleBillExcel.setWeixin(0.0);
        }else{
            settleBillExcel.setWeixin(settleBillExt.getWEIXIN()/100.0);
        }

        if (settleBillExt.getALIPAY() == null) {
            settleBillExcel.setAlipay(0.0);
        }else{
            settleBillExcel.setAlipay(settleBillExt.getALIPAY()/100.0);
        }

        if (settleBillExt.getBANKCARD() == null) {
            settleBillExcel.setBankcard(0.0);
        }else{
            settleBillExcel.setBankcard(settleBillExt.getBANKCARD()/100.0);
        }

        if (settleBillExt.getCASH() == null) {
            settleBillExcel.setCash(0.0);
        }else{
            settleBillExcel.setCash(settleBillExt.getCASH()/100.0);
        }
        if(settleBillExt.getTotalFlowAmt() == null){
        	settleBillExcel.setTotalFlowAmt(0.0);
        }else{
            settleBillExcel.setTotalFlowAmt(settleBillExt.getTotalFlowAmt()/100.0);
        }
        if(settleBillExt.getOnlineFlowAmt() == null){
        	settleBillExcel.setOnlineFlowAmt(0.0);
        }else{
        	settleBillExcel.setOnlineFlowAmt(settleBillExt.getOnlineFlowAmt()/100.0);
        }
        if(settleBillExt.getPayFee() == null){
        	settleBillExcel.setPayFee(0.0);
        }else{
        	settleBillExcel.setPayFee(settleBillExt.getPayFee()/100.0);
        }
        if(settleBillExt.getEntityActualRecAmt() == null){
        	settleBillExcel.setEntityActualRecAmt(0.0);
        }else{
        	settleBillExcel.setEntityActualRecAmt(settleBillExt.getEntityActualRecAmt()/100.0);
        }
        if(settleBillExt.getDiffSubmitCnt() == null){
        	settleBillExcel.setDiffSubmitCnt(0);
        }else{
        	settleBillExcel.setDiffSubmitCnt(settleBillExt.getDiffSubmitCnt());
        }
      
        if(settleBillExt.getDiffTotalAmt() == null){
        	settleBillExcel.setDiffTotalAmt(0.0);
        }else{
        	settleBillExcel.setDiffTotalAmt(settleBillExt.getDiffTotalAmt()/100.0);
        }
        if(settleBillExt.getSettleAmt() == null){
        	settleBillExcel.setSettleAmt(0.0);
        }else{
        	settleBillExcel.setSettleAmt(settleBillExt.getSettleAmt()/100.0);
        }
      
        settleBillExcelAllList.add(settleBillExcel);
    	return settleBillExcelAllList;
    }

    private List<SettleBillExcel> classTransformation(List<SettleBillExt> settleBillExtList) {
        Set<String>  brandSet = new HashSet<String>();//品牌列表
        Set<StoreInfo>  storeSet = new HashSet<StoreInfo>();;//门店列表
        Map<String,String> brandMap = new HashMap<String,String>();
        Map<String,String> storeMap = new HashMap<String,String>();

        PropertiesUtil propertiesUtil = new PropertiesUtil();
        Properties properties = null;
        try {
            properties = propertiesUtil.getProperties("settlement-api.properties");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String brandNameArraysQueryUrl = properties.getProperty("brandNameArraysQueryUrl");
        String brandAndStoreArraysQueryUrl = properties.getProperty("brandAndStoreArraysQueryUrl");


        if(settleBillExtList.size() > 0 && settleBillExtList !=null){
            for (SettleBillExt settleBillExt:settleBillExtList){
                if(settleBillExt.getBrandId() !=null && settleBillExt.getStoreId() !=null){
                    StoreInfo storeInfo = new StoreInfo();
                    storeInfo.setBrandId(settleBillExt.getBrandId());
                    storeInfo.setStoreId(settleBillExt.getStoreId());
                    storeSet.add(storeInfo);
                }

                if(settleBillExt.getBrandId() !=null && settleBillExt.getStoreId() == null){
                    brandSet.add(settleBillExt.getBrandId());
                }

            }
        }
        if ( brandSet.size() >0 ){
            String[] brandArray = new String[brandSet.size()];
            String[] brandInfosArray = (String[])brandSet.toArray(brandArray);
            StringBuilder brandNameQueryUrlBuilder = new StringBuilder();
            brandNameQueryUrlBuilder.append(brandNameArraysQueryUrl);
            String brandInfo = HttpClient.post(brandNameQueryUrlBuilder.toString()).json(JsonUtil.toJson(brandInfosArray)).action().result();

            HttpResponse httpResponse = JsonUtil.fromJson(brandInfo, HttpResponse.class);
            BrandInfo[] brandInfos = JsonUtil.fromJson(JsonUtil.toJson(httpResponse.getData()),new TypeReference<BrandInfo[]>() {
            });
            for(BrandInfo brandInfo1:brandInfos){
                brandMap.put(brandInfo1.getBrandId(),brandInfo1.getBrandName());
            }

        }

        if( storeSet.size() > 0){
            StoreInfo[] storeArray = new StoreInfo[storeSet.size()];
            StoreInfo[] storeInfosArray = (StoreInfo[])storeSet.toArray(storeArray);

            StringBuilder brandAndStoreQueryUrlBuilder = new StringBuilder();
            brandAndStoreQueryUrlBuilder.append(brandAndStoreArraysQueryUrl);
            String brandAndStoreInfo = HttpClient.post(brandAndStoreQueryUrlBuilder.toString()).json(JsonUtil.toJson(storeInfosArray)).action().result();
            HttpResponse httpResponse = JsonUtil.fromJson(brandAndStoreInfo, HttpResponse.class);
            BrandInfo[] storeInfos = JsonUtil.fromJson(JsonUtil.toJson(httpResponse.getData()), new TypeReference<BrandInfo[]>(){
            });
            for(BrandInfo brandInfo2:storeInfos){
                brandMap.put(brandInfo2.getBrandId(),brandInfo2.getBrandName());
                storeMap.put(brandInfo2.getStoreId(),brandInfo2.getStoreName());
            }

        }


        List<SettleBillExcel> excleList = new ArrayList<SettleBillExcel>();
        if(settleBillExtList.size() > 0 && settleBillExtList !=null){
            for (SettleBillExt settleBillExt:settleBillExtList){
                SettleBillExcel settleBillExcel = new SettleBillExcel();
                if(settleBillExt.getBrandId() !=null && settleBillExt.getStoreId() !=null){
                    settleBillExcel.setBrandId(brandMap.get(settleBillExt.getBrandId()));
                    settleBillExcel.setStoreId(storeMap.get(settleBillExt.getStoreId()));
                }
                if(settleBillExt.getBrandId() !=null && settleBillExt.getStoreId() == null){
                    settleBillExcel.setBrandId(brandMap.get(settleBillExt.getBrandId()));
                }


                settleBillExcel.setBillId(settleBillExt.getBillId());
                settleBillExcel.setTradeDateStart(DateFormatUtil.formatDate(settleBillExt.getTradeDateStart()));
                settleBillExcel.setSettleCircle(DictionaryTypeUtil.getSettleCircleDes(settleBillExt.getSettleCircle()));
                if (settleBillExt.getWEIXIN() == null ) {
                    settleBillExcel.setWeixin(0.0);
                }else{
                    settleBillExcel.setWeixin(settleBillExt.getWEIXIN()/100.0);
                }

                if (settleBillExt.getALIPAY() == null) {
                    settleBillExcel.setAlipay(0.0);
                }else{
                    settleBillExcel.setAlipay(settleBillExt.getALIPAY()/100.0);
                }

                if (settleBillExt.getBANKCARD() == null) {
                    settleBillExcel.setBankcard(0.0);
                }else{
                    settleBillExcel.setBankcard(settleBillExt.getBANKCARD()/100.0);
                }

                if (settleBillExt.getCASH() == null) {
                    settleBillExcel.setCash(0.0);
                }else{
                    settleBillExcel.setCash(settleBillExt.getCASH()/100.0);
                }
                settleBillExcel.setTotalFlowAmt(settleBillExt.getTotalFlowAmt()/100.0);
                settleBillExcel.setOnlineFlowAmt(settleBillExt.getOnlineFlowAmt()/100.0);
                settleBillExcel.setPayFee(settleBillExt.getPayFee()/100.0);
                settleBillExcel.setEntityActualRecAmt(settleBillExt.getEntityActualRecAmt()/100.0);
                settleBillExcel.setDiffSubmitCnt(settleBillExt.getDiffSubmitCnt());
                settleBillExcel.setDiffTotalAmt(settleBillExt.getDiffTotalAmt()/100.0);
                settleBillExcel.setSettleAmt(settleBillExt.getSettleAmt()/100.0);
                settleBillExcel.setSettleStatus(DictionaryTypeUtil.getSettleStatusDes(settleBillExt.getSettleStatus()));
                excleList.add(settleBillExcel);
            }
        }
        return excleList;
    }



    /**
     * 装换数据
     * @param settleBillList
     * @return
     */
    public List<SettleBillExt> ConvertList(List<SettleBill> settleBillList){
        List<SettleBillExt> list = new ArrayList<SettleBillExt>();
        for (SettleBill settleBill:settleBillList) {
            SettleBillExt settleBillExt = new SettleBillExt();
            SettleBillPaychn settleBillPaychnPara = new SettleBillPaychn();
            settleBillPaychnPara.setBillId(settleBill.getBillId());//结算单号
            List<SettleBillPaychn> settleBillPaychnsList = settleBillPaychnDao.getSettlePayChnList(settleBillPaychnPara);
            if(settleBillPaychnsList != null && settleBillPaychnsList.size()>0){
                for (SettleBillPaychn settleBillPaychn:settleBillPaychnsList) {
                    if (SettlementConsts.PAY_CATEGORY_WEIXIN.equals(settleBillPaychn.getPayTypeCategory())){//微信
                        settleBillExt.setWEIXIN(settleBillPaychn.getTotalFlowAmt());
                    }

                    if (SettlementConsts.PAY_CATEGORY_ALIPAY.equals(settleBillPaychn.getPayTypeCategory())) {//支付宝
                        settleBillExt.setALIPAY(settleBillPaychn.getTotalFlowAmt());
                    }

                    if (SettlementConsts.PAY_CATEGORY_BANKCARD.equals(settleBillPaychn.getPayTypeCategory())) {//银行卡
                        settleBillExt.setBANKCARD(settleBillPaychn.getTotalFlowAmt());
                    }

                    if (SettlementConsts.PAY_CATEGORY_CASH.equals(settleBillPaychn.getPayTypeCategory())) {//现金
                        settleBillExt.setCASH(settleBillPaychn.getTotalFlowAmt());
                    }
                }
            }

            settleBillExt.setCreateTime(settleBill.getCreateTime());
            settleBillExt.setUpdateTime(settleBill.getUpdateTime());
            settleBillExt.setBillId(settleBill.getBillId());
            settleBillExt.setEntityId(settleBill.getEntityId());
            settleBillExt.setEntityType(settleBill.getEntityType());
            settleBillExt.setBrandId(settleBill.getBrandId());
            settleBillExt.setStoreId(settleBill.getStoreId());
            settleBillExt.setSeparateSettle(settleBill.getSeparateSettle());
            settleBillExt.setCapitalCollect(settleBill.getCapitalCollect());
            settleBillExt.setCapitalCollectType(settleBill.getCapitalCollectType());
            settleBillExt.setSettleCircle(settleBill.getSettleCircle());
            settleBillExt.setTradeDateStart(settleBill.getTradeDateStart());
            settleBillExt.setTradeDateEnd(settleBill.getTradeDateEnd());
            settleBillExt.setSettleDate(settleBill.getSettleDate());
            settleBillExt.setSettleStatus(settleBill.getSettleStatus());
            settleBillExt.setAuditStatus(settleBill.getAuditStatus());
            settleBillExt.setTotalFlowAmt(settleBill.getTotalFlowAmt());
            settleBillExt.setOnlineFlowAmt(settleBill.getOnlineFlowAmt());
            settleBillExt.setPayFee(settleBill.getPayFee());
            settleBillExt.setEntityShaltRecAmt(settleBill.getEntityShaltRecAmt());
            settleBillExt.setEntityActualRecAmt(settleBill.getEntityActualRecAmt());
            settleBillExt.setBankCollectAmt(settleBill.getBankCollectAmt());
            settleBillExt.setPlatformActualRecAmt(settleBill.getPlatformActualRecAmt());
            settleBillExt.setPlatformShareRate(settleBill.getPlatformShareRate());
            settleBillExt.setPlatformShareAmt(settleBill.getPlatformShareAmt());
            settleBillExt.setBrandShareRate(settleBill.getBrandShareRate());
            settleBillExt.setBrandShareAmt(settleBill.getBrandShareAmt());
            settleBillExt.setEntityDivideAmt(settleBill.getEntityDivideAmt());
            settleBillExt.setDiffResolveCnt(settleBill.getDiffResolveCnt());
            settleBillExt.setDiffResolveAmt(settleBill.getDiffResolveAmt());
            settleBillExt.setDiffResolvePayFee(settleBill.getDiffResolvePayFee());
            settleBillExt.setDiffResolveActualAmt(settleBill.getDiffResolveActualAmt());
            settleBillExt.setDiffSubmitCnt(settleBill.getDiffSubmitCnt());
            settleBillExt.setDiffSubmitAmt(settleBill.getDiffSubmitAmt());
            settleBillExt.setDiffSubmitPayFee(settleBill.getDiffSubmitPayFee());
            settleBillExt.setDiffSubmitActualAmt(settleBill.getDiffSubmitActualAmt());
            settleBillExt.setDiffTotalAmt(settleBill.getDiffTotalAmt());
            settleBillExt.setSettleAmt(settleBill.getSettleAmt());
            list.add(settleBillExt);
        }
       return list;
    }

    @Override
    public void getOperationsSettleData(HttpServletResponse response, HttpServletRequest request, SettleBillListReq settleBillListReq) throws IOException {

        //------make data start-----
        OperationsSettle operationsSettle = new OperationsSettle();
        if(StringUtils.isNotBlank(settleBillListReq.getBeginDate())) {
            settleBillListReq.setBeginTime(DateCalcUtil.getDayBegin(DateFormatUtil.parseDate(settleBillListReq.getBeginDate())));
        }
        if(StringUtils.isNotBlank(settleBillListReq.getEndDate())) {
            settleBillListReq.setEndTime(DateCalcUtil.getDayEnd(DateFormatUtil.parseDate(settleBillListReq.getEndDate())));
        }
        //数据导出只针对门店，目前品牌没有非独立的门店
        settleBillListReq.setEntityType(SettlementConsts.ENTITY_TYPE_STORE);
        settleBillListReq.setPageNo(1);
        settleBillListReq.setPageSize(Integer.MAX_VALUE);
        List<SettleBill> settleBillList = settleBillDao.getSettleBillList(settleBillListReq);
        //结算收入
        List<SettleIncome> settleIncomeList = getSettleIncomeList(settleBillList);
        //联动对账
        List<UmpayBill> umpayBillList = getUmpayBillList(settleBillList);
        operationsSettle.setSettleIncomeList(settleIncomeList);
        operationsSettle.setUmpayBillList(umpayBillList);
        //------make data end-----
        //生成Excel
        generateOperationData(response, request,operationsSettle);
    }

    //生成Excel数据并导出
    private void generateOperationData(HttpServletResponse response, HttpServletRequest request, OperationsSettle operationsSettle) throws IOException {
        String expFileName = "运营数据";
        OutputStream out = null;
        response.reset();// 清空输出流
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String((expFileName + ".xls").getBytes(), "iso-8859-1"));
        out = response.getOutputStream();// 取得输出流


        //-------------------生成多页签的Excel开始--------------------
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet settleIncomeSheet = workbook.createSheet("结算收入（表1）");
        HSSFSheet umpayStatementSheet = workbook.createSheet("联动对账表（表2）");
        HSSFRow settleRow1 = settleIncomeSheet.createRow(0);//结算收入 表头

        HSSFCell cell=settleRow1.createCell(0);
        cell.setCellValue("基础信息");
        CellRangeAddress region=new CellRangeAddress(0, 0, 0, 4);
        settleIncomeSheet.addMergedRegion(region);

        HSSFCell cell1=settleRow1.createCell(5);
        cell1.setCellValue("微信收入");
        CellRangeAddress region1 = new CellRangeAddress(0, 0, 5, 7);
        settleIncomeSheet.addMergedRegion(region1);

        HSSFCell cell2=settleRow1.createCell(8);
        cell2.setCellValue("微信退款");
        CellRangeAddress region2 = new CellRangeAddress(0, 0, 8, 10);
        settleIncomeSheet.addMergedRegion(region2);

        HSSFCell cell3=settleRow1.createCell(11);
        cell3.setCellValue("现金收入");
        CellRangeAddress region3 = new CellRangeAddress(0, 0, 11, 12);
        settleIncomeSheet.addMergedRegion(region3);

        HSSFCell cell4=settleRow1.createCell(13);
        cell4.setCellValue("现金退款");
        CellRangeAddress region4 = new CellRangeAddress(0, 0, 13, 14);
        settleIncomeSheet.addMergedRegion(region4);

        HSSFCell cell5=settleRow1.createCell(15);
        cell5.setCellValue("银行卡收入");
        CellRangeAddress region5 = new CellRangeAddress(0, 0, 15, 17);
        settleIncomeSheet.addMergedRegion(region5);

        HSSFCell cell6=settleRow1.createCell(18);
        cell6.setCellValue("银行卡退款");
        CellRangeAddress region6 = new CellRangeAddress(0, 0, 18, 20);
        settleIncomeSheet.addMergedRegion(region6);

        HSSFCell cell7=settleRow1.createCell(21);
        cell7.setCellValue("支付宝收入");
        CellRangeAddress region7 = new CellRangeAddress(0, 0, 21, 23);
        settleIncomeSheet.addMergedRegion(region7);

        HSSFCell cell8=settleRow1.createCell(24);
        cell8.setCellValue("支付宝退款");
        CellRangeAddress region8 = new CellRangeAddress(0, 0, 24, 26);
        settleIncomeSheet.addMergedRegion(region8);

        HSSFCell cell9=settleRow1.createCell(27);
        cell9.setCellValue("商场收入");
        CellRangeAddress region9 = new CellRangeAddress(0, 0, 27, 28);
        settleIncomeSheet.addMergedRegion(region9);

        HSSFCell cell10 =settleRow1.createCell(29);
        cell10.setCellValue("商场退货");
        CellRangeAddress region10 = new CellRangeAddress(0, 0, 29, 30);
        settleIncomeSheet.addMergedRegion(region10);

        HSSFCell cell11 =settleRow1.createCell(31);
        cell11.setCellValue("汇总信息");
        CellRangeAddress region11 = new CellRangeAddress(0, 0, 31, 44);
        settleIncomeSheet.addMergedRegion(region11);

        HSSFRow settleRow2 = settleIncomeSheet.createRow(1);
        settleRow2.createCell(0).setCellValue("日期");
        settleRow2.createCell(1).setCellValue("分类");
        settleRow2.createCell(2).setCellValue("品牌");
        settleRow2.createCell(3).setCellValue("门店");
        settleRow2.createCell(4).setCellValue("省");
        settleRow2.createCell(5).setCellValue("微信");
        settleRow2.createCell(6).setCellValue("微信笔数");
        settleRow2.createCell(7).setCellValue("微信手续费");
        settleRow2.createCell(8).setCellValue("微信");
        settleRow2.createCell(9).setCellValue("退款笔数");
        settleRow2.createCell(10).setCellValue("退款手续费");
        settleRow2.createCell(11).setCellValue("现金");
        settleRow2.createCell(12).setCellValue("现金笔数");
        settleRow2.createCell(13).setCellValue("现金");
        settleRow2.createCell(14).setCellValue("退款笔数");
        settleRow2.createCell(15).setCellValue("银行卡");
        settleRow2.createCell(16).setCellValue("银行卡笔数");
        settleRow2.createCell(17).setCellValue("银行卡手续费");
        settleRow2.createCell(18).setCellValue("银行卡");
        settleRow2.createCell(19).setCellValue("退款笔数");
        settleRow2.createCell(20).setCellValue("退款手续费");
        settleRow2.createCell(21).setCellValue("支付宝");
        settleRow2.createCell(22).setCellValue("支付宝笔数");
        settleRow2.createCell(23).setCellValue("支付宝手续费");
        settleRow2.createCell(24).setCellValue("支付宝");
        settleRow2.createCell(25).setCellValue("支付宝笔数");
        settleRow2.createCell(26).setCellValue("退款手续费");
        settleRow2.createCell(27).setCellValue("商场");
        settleRow2.createCell(28).setCellValue("商场笔数");
        settleRow2.createCell(29).setCellValue("商场收入");
        settleRow2.createCell(30).setCellValue("退款笔数");
        settleRow2.createCell(31).setCellValue("订单总数");
        settleRow2.createCell(32).setCellValue("总流水");
        settleRow2.createCell(33).setCellValue("线上总流水");
        settleRow2.createCell(34).setCellValue("手续费");
        settleRow2.createCell(35).setCellValue("退款总金额");
        settleRow2.createCell(36).setCellValue("汉云实收");
        settleRow2.createCell(37).setCellValue("当日产生异常订单数");
        settleRow2.createCell(38).setCellValue("异常订单合计金额");
        settleRow2.createCell(39).setCellValue("佣金扣除前金额");
        settleRow2.createCell(40).setCellValue("流水提点");
        settleRow2.createCell(41).setCellValue("提点金额");
        settleRow2.createCell(42).setCellValue("扣除佣金");
        settleRow2.createCell(43).setCellValue("实际结算金额");
        settleRow2.createCell(44).setCellValue("线下金额");
        List<SettleIncome> settleIncomeList1 = operationsSettle.getSettleIncomeList();
        int settleRowIndex = 2;
        for(SettleIncome si:settleIncomeList1){
            HSSFRow settleRow3 = settleIncomeSheet.createRow(settleRowIndex);
            settleRow3.createCell(0).setCellValue(si.getDateStr());
            settleRow3.createCell(1).setCellValue(si.getIndustryName());
            settleRow3.createCell(2).setCellValue(si.getBrandName());
            settleRow3.createCell(3).setCellValue(si.getStoreName());
            settleRow3.createCell(4).setCellValue(si.getProvice());
            settleRow3.createCell(5).setCellValue(si.getWxAmount());
            settleRow3.createCell(6).setCellValue(si.getWxCount());
            settleRow3.createCell(7).setCellValue(si.getWxFee());
            settleRow3.createCell(8).setCellValue(si.getWxRefund());
            settleRow3.createCell(9).setCellValue(si.getWxRefundCount());
            settleRow3.createCell(10).setCellValue(si.getWxRefundFee());
            settleRow3.createCell(11).setCellValue(si.getCashAmount());
            settleRow3.createCell(12).setCellValue(si.getCashCount());
            settleRow3.createCell(13).setCellValue(si.getCashRefund());
            settleRow3.createCell(14).setCellValue(si.getCashRefundCount());
            settleRow3.createCell(15).setCellValue(si.getBankCarAmount());
            settleRow3.createCell(16).setCellValue(si.getBankCarCount());
            settleRow3.createCell(17).setCellValue(si.getBankCarFee());
            settleRow3.createCell(18).setCellValue(si.getBankCarRefund());
            settleRow3.createCell(19).setCellValue(si.getBankCarRefundCount());
            settleRow3.createCell(20).setCellValue(si.getBankCarRefundFee());
            settleRow3.createCell(21).setCellValue(si.getAlipay());
            settleRow3.createCell(22).setCellValue(si.getAlipayCount());
            settleRow3.createCell(23).setCellValue(si.getAlipayFee());
            settleRow3.createCell(24).setCellValue(si.getAlipayRefund());
            settleRow3.createCell(25).setCellValue(si.getAlipayRefundCount());
            settleRow3.createCell(26).setCellValue(si.getAlipayRefundFee());
            settleRow3.createCell(27).setCellValue(si.getShoppingMall());
            settleRow3.createCell(28).setCellValue(si.getShoppingMallCount());
            settleRow3.createCell(29).setCellValue(si.getShoppingMallRefund());
            settleRow3.createCell(30).setCellValue(si.getShoppingMallRefundCount());
            settleRow3.createCell(31).setCellValue(si.getOrderCount());
            settleRow3.createCell(32).setCellValue(si.getTotalFlow());
            settleRow3.createCell(33).setCellValue(si.getOnlineTotalFlow());
            settleRow3.createCell(34).setCellValue(si.getFee());
            settleRow3.createCell(35).setCellValue(si.getRefundAmount());
            settleRow3.createCell(36).setCellValue(si.getHanyunRealAmount());
            settleRow3.createCell(37).setCellValue(si.getTodayAbnormalCount());
            settleRow3.createCell(38).setCellValue(si.getAbnormalTotalAmount());
            settleRow3.createCell(39).setCellValue(si.getCommissionDeductionBeforeAmount());
            settleRow3.createCell(40).setCellValue(si.getTurnoverPoints());
            settleRow3.createCell(41).setCellValue(si.getTodayCommission());
            settleRow3.createCell(42).setCellValue(si.getCommissionDeduction());
            settleRow3.createCell(43).setCellValue(si.getSettleAmt());
            settleRow3.createCell(44).setCellValue(si.getOfflineFlowAmt());
            settleRowIndex++;
        }

        HSSFRow umpayRow = umpayStatementSheet.createRow(0);//联动优势  表头
        umpayRow.createCell(0).setCellValue("日期");
        umpayRow.createCell(1).setCellValue("品牌");
        umpayRow.createCell(2).setCellValue("门店");
        umpayRow.createCell(3).setCellValue("省份");
        umpayRow.createCell(4).setCellValue("已解决差异总金额");
        umpayRow.createCell(5).setCellValue("已解决差异手续费");
        umpayRow.createCell(6).setCellValue("银行卡总金额");
        umpayRow.createCell(7).setCellValue("银行卡手续费");
        umpayRow.createCell(8).setCellValue("总金额");
        List<UmpayBill> umpayBillList1 = operationsSettle.getUmpayBillList();
        int umpayRowIndex = 1;
        for(UmpayBill ub:umpayBillList1){
            HSSFRow umpayRow1 = umpayStatementSheet.createRow(umpayRowIndex);
            umpayRow1.createCell(0).setCellValue(ub.getDateStr());
            umpayRow1.createCell(1).setCellValue(ub.getBrandName());
            umpayRow1.createCell(2).setCellValue(ub.getStoreName());
            umpayRow1.createCell(3).setCellValue(ub.getProvice());
            umpayRow1.createCell(4).setCellValue(ub.getDiffTotalAmount());
            umpayRow1.createCell(5).setCellValue(ub.getDiffFeeAmount());
            umpayRow1.createCell(6).setCellValue(ub.getBankCardAmount());
            umpayRow1.createCell(7).setCellValue(ub.getBankCardFee());
            umpayRow1.createCell(8).setCellValue(ub.getTotalAmount());
            umpayRowIndex++;
        }
        workbook.write(out);


    }

    /**
     * 结算收入
     * @param settleBillList
     * @return
     */
    private List<SettleIncome> getSettleIncomeList(List<SettleBill> settleBillList) {
        List<SettleIncome> list = new ArrayList<>();
        for(SettleBill sb:settleBillList){
            SettleIncome si = new SettleIncome();
            //日期
            si.setDateStr(DateFormatUtil.formatDate(sb.getTradeDateStart()));
            //分类
            si.setIndustryName(BrandInfoUtil.getBrandIndustry(sb.getBrandId()).get(sb.getBrandId()).getIndustryName());
            si.setBrandId(sb.getBrandId());
            //品牌
            si.setBrandName(BaseBrandInfoUtil.getBrandAll().get(sb.getBrandId()).getBrandName());
            si.setStoreId(sb.getStoreId());
            //门店
            si.setStoreName(BaseBrandStoreUtil.getBrandAllStore(sb.getBrandId()).get(sb.getStoreId()).getStoreName());
            //省
            si.setProvice(BaseBrandStoreUtil.getBrandAllStore(sb.getBrandId()).get(sb.getStoreId()).getProvince());

            //微信收入
            PayTypeVo  wxIncome = payTypeStatistics(sb, SettlementConsts.PAY_CATEGORY_WEIXIN, SettlementConsts.TRANS_OPERATE_TYPE_PAY);
            si.setWxAmount(wxIncome.getPayTypeTotalAmount() == null?0.0:wxIncome.getPayTypeTotalAmount()/100.0);
            si.setWxCount(wxIncome.getPayTypeCount() == null?0:wxIncome.getPayTypeCount());
            si.setWxFee(wxIncome.getPayTypeFee() == null?0.0:wxIncome.getPayTypeFee()/100.0);

            //微信退款
            PayTypeVo  wxRefun = payTypeStatistics(sb, SettlementConsts.PAY_CATEGORY_WEIXIN,SettlementConsts.TRANS_OPERATE_TYPE_REF);
            si.setWxRefund(wxRefun.getPayTypeTotalAmount()== null?0.0:wxRefun.getPayTypeTotalAmount()/100.0);
            si.setWxRefundCount(wxRefun.getPayTypeCount() == null?0:wxRefun.getPayTypeCount());
            si.setWxRefundFee(wxRefun.getPayTypeFee() == null?0.0:wxRefun.getPayTypeFee()/100.0);


            //现金收入
            PayTypeVo  cashIncome = payTypeStatistics(sb, SettlementConsts.PAY_CATEGORY_CASH,SettlementConsts.TRANS_OPERATE_TYPE_PAY);
            si.setCashAmount(cashIncome.getPayTypeTotalAmount() == null?0.0:cashIncome.getPayTypeTotalAmount()/100.0);
            si.setCashCount(cashIncome.getPayTypeCount() == null?0:cashIncome.getPayTypeCount());
            //现金退款
            PayTypeVo  cashRefun = payTypeStatistics(sb, SettlementConsts.PAY_CATEGORY_CASH,SettlementConsts.TRANS_OPERATE_TYPE_REF);
            si.setCashRefund(cashRefun.getPayTypeTotalAmount() == null?0.0:cashRefun.getPayTypeTotalAmount()/100.0);
            si.setCashRefundCount(cashRefun.getPayTypeCount() == null?0:cashRefun.getPayTypeCount());

            //银行卡收入
            PayTypeVo  bankCarIncome = payTypeStatistics(sb, SettlementConsts.PAY_CATEGORY_BANKCARD,SettlementConsts.TRANS_OPERATE_TYPE_PAY);
            si.setBankCarAmount(bankCarIncome.getPayTypeTotalAmount() == null?0.0:bankCarIncome.getPayTypeTotalAmount()/100.0);
            si.setBankCarCount(bankCarIncome.getPayTypeCount());
            si.setBankCarFee(bankCarIncome.getPayTypeFee() == null?0.0:bankCarIncome.getPayTypeFee()/100.0);

            //银行卡退款
            PayTypeVo  bankCarRefun = payTypeStatistics(sb, SettlementConsts.PAY_CATEGORY_BANKCARD,SettlementConsts.TRANS_OPERATE_TYPE_REF);
            si.setBankCarRefund(bankCarRefun.getPayTypeTotalAmount() == null?0.0:bankCarRefun.getPayTypeTotalAmount()/100.0);
            si.setBankCarRefundCount(bankCarRefun.getPayTypeCount() == null?0:bankCarRefun.getPayTypeCount());
            si.setBankCarRefundFee(bankCarRefun.getPayTypeFee() == null?0.0:bankCarRefun.getPayTypeFee()/100.0);


            //支付宝收入
            PayTypeVo  alipayIncome = payTypeStatistics(sb, SettlementConsts.PAY_CATEGORY_ALIPAY,SettlementConsts.TRANS_OPERATE_TYPE_PAY);
            si.setAlipay(alipayIncome.getPayTypeTotalAmount() == null?0.0:alipayIncome.getPayTypeTotalAmount()/100.0);
            si.setAlipayCount(alipayIncome.getPayTypeCount() == null?0:bankCarRefun.getPayTypeCount());
            si.setAlipayFee(alipayIncome.getPayTypeFee() == null?0.0:alipayIncome.getPayTypeFee()/100.0);

            //支付宝退款
            PayTypeVo  alipayRefun = payTypeStatistics(sb, SettlementConsts.PAY_CATEGORY_ALIPAY,SettlementConsts.TRANS_OPERATE_TYPE_REF);
            si.setAlipayRefund(alipayRefun.getPayTypeTotalAmount() == null?0.0:alipayRefun.getPayTypeTotalAmount()/100.0);
            si.setAlipayRefundCount(alipayRefun.getPayTypeCount() == null?0:alipayRefun.getPayTypeCount());
            si.setAlipayRefundFee(alipayRefun.getPayTypeFee() == null?0.0:alipayRefun.getPayTypeFee()/100.0);

            //商场收入
            PayTypeVo  shoppingMallIncome = payTypeStatistics(sb, SettlementConsts.PAY_CATEGORY_SHOPPINGMALL,SettlementConsts.TRANS_OPERATE_TYPE_PAY);
           si.setShoppingMall(shoppingMallIncome.getPayTypeTotalAmount() == null?0.0:shoppingMallIncome.getPayTypeTotalAmount()/100.0);
           si.setShoppingMallCount(shoppingMallIncome.getPayTypeCount() == null?0:shoppingMallIncome.getPayTypeCount());

            //商场退款
            PayTypeVo  shoppingMallRefun = payTypeStatistics(sb, SettlementConsts.PAY_CATEGORY_SHOPPINGMALL,SettlementConsts.TRANS_OPERATE_TYPE_REF);
            si.setShoppingMallRefund(shoppingMallRefun.getPayTypeTotalAmount() == null?0.0:shoppingMallRefun.getPayTypeTotalAmount()/100.0);
            si.setShoppingMallRefundCount(shoppingMallRefun.getPayTypeCount() == null?0:shoppingMallRefun.getPayTypeCount());


            PayTypeVo  payTypeVoCount = payTypeStatistics(sb, null,null);
            //订单总数
            si.setOrderCount(payTypeVoCount.getPayTypeCount());
            //总流水
            si.setTotalFlow(sb.getTotalFlowAmt()/100.0);
            //线上总流水
            si.setOnlineTotalFlow(sb.getOnlineFlowAmt() == null?0.0:sb.getOnlineFlowAmt()/100.0);
            //手续费
            si.setFee(sb.getPayFee()/100.0);
            //退款总金额
            PayTypeVo  payTypeVoRefundAmount = payTypeStatistics(sb, null,SettlementConsts.TRANS_OPERATE_TYPE_REF);
            si.setRefundAmount(payTypeVoRefundAmount.getPayTypeTotalAmount() == null?0.0:payTypeVoRefundAmount.getPayTypeTotalAmount()/100.0);
            //汉云实收
            si.setHanyunRealAmount(sb.getEntityShaltRecAmt()/100.0);
            //当日产生异常订单数
            si.setTodayAbnormalCount(sb.getDiffSubmitCnt().longValue());
            //异常合计
            si.setAbnormalTotalAmount(sb.getDiffTotalAmt()/100.0);
            //佣金扣除前金额 = 汉云实收+异常订单合计
            si.setCommissionDeductionBeforeAmount((sb.getPlatformActualRecAmt()+sb.getDiffTotalAmt())/100.0);
            //流水提点
            si.setTurnoverPoints(sb.getTurnoverPoints() == null?0.0:sb.getTurnoverPoints()/100.0);
            //提点金额
            si.setTodayCommission(sb.getTodayCommission() == null?0.0:sb.getTodayCommission()/100.0);
            //扣除佣金
            si.setCommissionDeduction(sb.getCommissionDeduction() == null?0.0:sb.getCommissionDeduction()/100.0);
            //实际结算金额
            si.setSettleAmt(sb.getSettleAmt()/100.0);
            //线下金额  因为总流水和线上流水单位已经是元，所以不需要再除100
            si.setOfflineFlowAmt(si.getTotalFlow()-si.getOnlineTotalFlow());
            list.add(si);
        }

        return list;
    }


    //获取支付方式统计信息
    private PayTypeVo payTypeStatistics(SettleBill sb,String typeCategory,Integer operateType){
        PayTypeVo payTypeVo = new PayTypeVo();
        PayTypeReq payTypeReq = new PayTypeReq();
        payTypeReq.setBrandId(sb.getBrandId());
        payTypeReq.setStoreId(sb.getStoreId());
        payTypeReq.setOperateType(operateType);
        payTypeReq.setTypeCategory(typeCategory);
        payTypeReq.setTradeDateStart(DateCalcUtil.getDayBegin(sb.getTradeDateStart()));
        payTypeReq.setTradeDateEnd(DateCalcUtil.getDayEnd(sb.getTradeDateEnd()));
        payTypeVo = settleBillDetailDao.getPayTransStatistics(payTypeReq);
        return payTypeVo;
    }


    /**
     * 联动对账
     * @param settleBillList
     * @return
     */
    private List<UmpayBill> getUmpayBillList(List<SettleBill> settleBillList) {
        List<UmpayBill> list = new ArrayList<>();
        for(SettleBill sb:settleBillList){
            UmpayBill ub = new UmpayBill();
            //日期
            ub.setDateStr(DateFormatUtil.formatDate(sb.getTradeDateStart()));
            //品牌
            ub.setBrandName(BaseBrandInfoUtil.getBrandAll().get(sb.getBrandId()).getBrandName());
            //门店
            ub.setStoreName(BaseBrandStoreUtil.getBrandAllStore(sb.getBrandId()).get(sb.getStoreId()).getStoreName());
            //省份
            ub.setProvice(BaseBrandStoreUtil.getBrandAllStore(sb.getBrandId()).get(sb.getStoreId()).getProvince());
            PayTypeReq p = new PayTypeReq();
            p.setBrandId(sb.getBrandId());
            p.setStoreId(sb.getStoreId());
            p.setTypeCategory(SettlementConsts.PAY_CATEGORY_BANKCARD);
            p.setDiffStatus(SettlementConsts.DIFF_STATUS_SOLVED);//状态为已解决
            p.setTradeDateStart(DateCalcUtil.getDayBegin(sb.getTradeDateStart()));
            p.setTradeDateEnd(DateCalcUtil.getDayEnd(sb.getTradeDateEnd()));
            PayTypeVo payTypeVo = settleBillDetailDao.getDiffStatistics(p);

            //已解决差异总金额
            ub.setDiffTotalAmount(null == payTypeVo.getPayTypeTotalAmount()?0.0:payTypeVo.getPayTypeTotalAmount()/100.0);
            //已解决差异手续费
            ub.setDiffFeeAmount(null == payTypeVo.getPayTypeFee()?0.0:payTypeVo.getPayTypeFee()/100.0);

            SettleBillPaychn sp = new SettleBillPaychn();
            sp.setBrandId(sb.getBrandId());
            sp.setStoreId(sb.getStoreId());
            sp.setBillId(sb.getBillId());
            sp.setPayTypeCategory(SettlementConsts.PAYTYPE_BANKCARD);
            SettleBillPaychn  settleBillPaychn = settleBillPaychnDao.sumSettleBillPayChn(sp);
            //银行卡总金额 银行卡手续费
            if(settleBillPaychn == null){
                ub.setBankCardAmount(0.0);
                ub.setBankCardFee(0.0);
            }else{
                ub.setBankCardAmount(null == settleBillPaychn.getTotalFlowAmt()?0.0:settleBillPaychn.getTotalFlowAmt()/100.0);
                ub.setBankCardFee(null == settleBillPaychn.getPayFee()?0.0:settleBillPaychn.getPayFee()/100.0);
            }
            ub.setTotalAmount(ub.getDiffTotalAmount()+ub.getBankCardAmount());//总金额=差异总金额+银行卡总金额
            list.add(ub);
        }
        return list;
    }

	/**
	 * 导出整体结算单
	 */
	@Override
	public void getSettleDataLists(HttpServletResponse response, HttpServletRequest request,
			SettleBillListReq settleBillListReq) throws IOException {
		
        if(null == settleBillListReq.getStoreId() || StringUtils.isBlank(settleBillListReq.getStoreId()) || StringUtils.isBlank(settleBillListReq.getBeginDate()) || StringUtils.isBlank(settleBillListReq.getEndDate())){
        	expSettleBillList(response,request,settleBillListReq);
        }else{
        	if(StringUtils.isNotBlank(settleBillListReq.getBeginDate())) {
                settleBillListReq.setBeginTime(DateCalcUtil.getDayBegin(DateFormatUtil.parseDate(settleBillListReq.getBeginDate())));
            }
            if(StringUtils.isNotBlank(settleBillListReq.getEndDate())) {
                settleBillListReq.setEndTime(DateCalcUtil.getDayEnd(DateFormatUtil.parseDate(settleBillListReq.getEndDate())));
            }
            	settleBillListReq.setPageNo(1);
            	settleBillListReq.setPageSize(Integer.MAX_VALUE);
        	   List<SettleBill> settleBillList = settleBillDao.getSettleBillList(settleBillListReq);
               List<SettleBillExt>  list = ConvertList(settleBillList);//增加各个支付通道明细
               
               //明细汇总
               SettleBillExt settleBillExt  =  getSummarySettleBill(settleBillListReq);
               List<SettleBillExcelAll> settleBillListExcel2 = new ArrayList<>();
               settleBillListExcel2 = classTransformationAll(settleBillExt);
               
               //获取门店名称
               String storeName = settleBillListExcel2.get(0).getBrandId() == null ? "":settleBillListExcel2.get(0).getStoreId();
              
               StringBuffer payTime = new StringBuffer("交易日期 : ");
               StringBuffer brandName = new StringBuffer("门店名称 : ");
               String beginTime = settleBillListReq.getBeginDate();
               String endTime = settleBillListReq.getEndDate();
               payTime.append(beginTime);
               payTime.append(" 至 ");
               payTime.append(endTime);
               brandName.append(storeName);
               
               String[][] headers0 = {{ payTime.toString(), "30" },{"","15"},{"","15"}, {brandName.toString(), "20" }};
               List<String[]> fieldValuesList2 = new ArrayList();
               fieldValuesList2.add(fieldValues2);
              
               
               List<String[]> fieldValuesList4 = new ArrayList();
               fieldValuesList4.add(fieldValues);
               List<SettleBillExcel> settleBillListExcel4 = classTransformation(list);
             //  String[] fieldValues = {};
               String expFileName = "结算单";
               List<String[][]> listHeadersList0 = new ArrayList<>();
               listHeadersList0.add(headers0);
               List<String[][]> listHeadersListtemp = new ArrayList<>();
               listHeadersListtemp.add(headerstemp);
               List<String[][]> listHeadersList1 = new ArrayList<>();
               listHeadersList1.add(headers1);
               //总信息
               List<String[][]> listHeadersList2 = new ArrayList<>();
               listHeadersList2.add(headers2);
              
               List<String[][]> listHeadersList3 = new ArrayList<>();
               listHeadersList3.add(headers3);
               //结算单数据
               List<String[][]> listHeadersList4 = new ArrayList<>();
               listHeadersList4.add(headers);
               List<Collection<SettleBillExcelAll>> list2 = new ArrayList<>();
               list2.add(settleBillListExcel2);
               List<Collection<SettleBillExcel>> list4 = new ArrayList<>();
               list4.add(settleBillListExcel4);
               OutputStream out = null;
               response.reset();// 清空输出流
               response.setContentType("application/vnd.ms-excel;charset=utf-8");
               response.setHeader("Content-Disposition",
                       "attachment;filename=" + new String((expFileName + ".xls").getBytes(), "iso-8859-1"));
               out = response.getOutputStream();// 取得输出流
               List<Map<String,List>> listMap = new ArrayList<>();
              //第一层
               Map map0 = new HashMap<>();
               map0.put("headers",listHeadersList0);
               Map maptemp0 = new HashMap<>();
               maptemp0.put("headers",listHeadersListtemp);
               Map map1 = new HashMap<>();
               map1.put("headers",listHeadersList1);
               Map map2 = new HashMap<>();
               map2.put("headers",listHeadersList2);
               map2.put("fieldValues",fieldValuesList2);
               map2.put("datasets", list2);
               Map maptemp1 = new HashMap<>();
               maptemp1.put("headers",listHeadersListtemp);
               Map map3 = new HashMap<>();
               map3.put("headers",listHeadersList3);
               Map map4 = new HashMap<>();
               map4.put("headers",listHeadersList4);
               map4.put("fieldValues", fieldValuesList4);
               map4.put("datasets", list4);
               listMap.add(map0);
               listMap.add(maptemp0);
               listMap.add(map1);
               listMap.add(map2);
               listMap.add(maptemp1);
               listMap.add(map3);
               listMap.add(map4);
               try {
                   new ExcelUtil<SettleBillExcel>("结算单",listMap,out).ExportExcelMore();
                           
               } catch (Exception e) {
                   LOGGER.error("生成文件失败!", e);
               } finally {
                   out.close();
               }
        }
     
	}
	
}
