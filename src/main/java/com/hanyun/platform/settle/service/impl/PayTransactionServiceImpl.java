package com.hanyun.platform.settle.service.impl;

import com.hanyun.ground.util.http.HttpClient;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.platform.settle.dao.PayTransactionDao;
import com.hanyun.platform.settle.domain.BrandInfo;
import com.hanyun.platform.settle.domain.PayTransaction;
import com.hanyun.platform.settle.domain.PayTransactionExcel;
import com.hanyun.platform.settle.service.PayTransactionService;
import com.hanyun.platform.settle.util.DictionaryTypeUtil;
import com.hanyun.platform.settle.util.ExcelUtil;
import com.hanyun.platform.settle.util.PropertiesUtil;
import com.hanyun.platform.settle.vo.PayTransactionReq;
import com.hanyun.platform.settle.vo.base.PageResData;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by jack on 2017/5/4.
 */
@Service
public class PayTransactionServiceImpl implements PayTransactionService {
    private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PayTransactionServiceImpl.class);

    @Resource
    private PayTransactionDao  payTransactionDao;

    private static String[][] headers = { { "交易流水号", "22" }, { "订单号", "15" }, { "支付类型", "10" },
            { "支付金额", "10" },{ "商户手续费", "10" }, { "线上结算", "12" },
            { "支付方式", "10" },{ "状态", "10" },{ "支付完成时间", "25" },{ "创建时间", "25" }};

    private static String[] fieldValues = { "transId", "orderDocumentId", "operateType",
            "amount", "mchFee", "settleType",
            "typeCategory","status","finishTime","createTime"};

    /**
     * 通过查询条件获取数据
     *
     * @param payTransactionReq
     * @return
     */
    @Override
    public PageResData selectPayTransactionList(PayTransactionReq payTransactionReq) {
       if(payTransactionReq.getCreateBeginTime() !=null){
            payTransactionReq.setCreateBeginTime(payTransactionReq.getCreateBeginTime()+ " 00:00:00");
        }
        if(payTransactionReq.getCreateEndTime() !=null){
            payTransactionReq.setCreateEndTime(payTransactionReq.getCreateEndTime()+ " 23:59:59");
        }
        PageResData pageResData = new PageResData();
        List<PayTransaction> payTransactionList = payTransactionDao.selectPayTransactionList(payTransactionReq);
        int payTransactionCount = payTransactionDao.selectPayTransactionCount(payTransactionReq);
        pageResData.setDataList(payTransactionList);
        pageResData.setTotalCount(payTransactionCount);
        return pageResData;
    }

    /**
     * 通过查询条件获取数据并导出数据
     * @param payTransactionReq
     */
    @Override
    public void expPayTransactionList(HttpServletResponse response, HttpServletRequest request, PayTransactionReq payTransactionReq) throws IOException {


        {

            if(payTransactionReq.getCreateBeginTime() !=null){
                payTransactionReq.setCreateBeginTime(payTransactionReq.getCreateBeginTime()+ " 00:00:00");
            }
            if(payTransactionReq.getCreateEndTime() !=null){
                payTransactionReq.setCreateEndTime(payTransactionReq.getCreateEndTime()+ " 23:59:59");
            }

            StringBuffer  expFileName = new StringBuffer();//导出文件名称
            PropertiesUtil propertiesUtil = new PropertiesUtil();
            Properties properties = null;
            try {
                properties = propertiesUtil.getProperties("settlement-api.properties");
            } catch (Exception e) {
                e.printStackTrace();
            }
            StringBuilder brandAndStoreQueryUrl = new StringBuilder(properties.getProperty("brandAndStoreQueryUrl"));
            if (payTransactionReq.getBrandId() != null && payTransactionReq.getStoreId() != null) {
                brandAndStoreQueryUrl.append(payTransactionReq.getBrandId());
                brandAndStoreQueryUrl.append("/");
                brandAndStoreQueryUrl.append(payTransactionReq.getStoreId());

                String brandAndStoreInfo = HttpClient.get(brandAndStoreQueryUrl.toString()).action().result();
                HttpResponse httpResponse = JsonUtil.fromJson(brandAndStoreInfo, HttpResponse.class);
                BrandInfo brandInfo = JsonUtil.fromJson(JsonUtil.toJson(httpResponse.getData()), BrandInfo.class);
                expFileName.append(brandInfo.getBrandName());//从接口中获取品牌名称
                expFileName.append("_");
                expFileName.append(brandInfo.getStoreName());//从接口中获取门店名称
                expFileName.append("_");
                expFileName.append(payTransactionReq.getCreateBeginTime().substring(0, 10));//获取查询交易开始日期
                expFileName.append("_");
                expFileName.append(payTransactionReq.getCreateEndTime().substring(0, 10));//获取查询交易结束日期
                LOGGER.info(expFileName.toString());
            }

            List<PayTransaction>  listData = payTransactionDao.expSelectPayTransactionList(payTransactionReq);
            List<PayTransactionExcel> payTransactionListExcel = classTransformation(listData);

            OutputStream out = null;
            response.reset();// 清空输出流
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String((expFileName + ".xls").getBytes(), "iso-8859-1"));
            out = response.getOutputStream();// 取得输出流
            try {
                new ExcelUtil<PayTransactionExcel>("交易明细记录", headers, fieldValues, payTransactionListExcel, out)
                        .ExportExcel();
            } catch (Exception e) {
                LOGGER.error("生成文件失败!", e);
            } finally {
                out.close();
            }
        }


    }

    /**
     * 转化信息
     * @param listData
     * @return
     */
    private List<PayTransactionExcel> classTransformation(List<PayTransaction> listData) {
        List<PayTransactionExcel> excleList = new ArrayList<PayTransactionExcel>();
        if (listData.size() > 0 && listData != null) {
            for (PayTransaction payTransaction : listData) {
                PayTransactionExcel payTransactionExcel = new PayTransactionExcel();
                double amount = payTransaction.getAmount() / 100.0;
                double mchFee = payTransaction.getMchFee() / 100.0;
                String transId = payTransaction.getTransId();
                String orderDocumentId = payTransaction.getOrderDocumentId();
                String settleType = DictionaryTypeUtil.getSettleTypeDes(payTransaction.getSettleType()).toString();
                String operateType = DictionaryTypeUtil.getOperateTypeDes(payTransaction.getOperateType()).toString();
                String typeCategory = DictionaryTypeUtil.geTypeCategoryDes(payTransaction.getTypeCategory());
                String status = DictionaryTypeUtil.getTransactionStatusDes(payTransaction.getStatus());

                payTransactionExcel.setTransId(transId);
                payTransactionExcel.setOrderDocumentId(orderDocumentId);
                payTransactionExcel.setAmount(amount);
                payTransactionExcel.setMchFee(mchFee);
                payTransactionExcel.setSettleType(settleType);
                payTransactionExcel.setOperateType(operateType);
                payTransactionExcel.setTypeCategory(typeCategory);
                payTransactionExcel.setFinishTime(payTransaction.getFinishTime());
                payTransactionExcel.setStatus(status);
                payTransactionExcel.setCreateTime(payTransaction.getCreateTime());
                excleList.add(payTransactionExcel);
            }
        }

        return excleList;
    }
}
