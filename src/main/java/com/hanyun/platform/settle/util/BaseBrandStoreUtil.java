package com.hanyun.platform.settle.util;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;
import com.hanyun.ground.util.http.HttpClient;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.platform.settle.vo.BrandStoreInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Properties;

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
 * BrandStoreUtil
 * 查询品牌信息工具类
 */
public abstract class BaseBrandStoreUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseBrandStoreUtil.class);


    /**
     * 获取该品牌下的所有门店
     * @param brandId 品牌id
     * @return Map
     */
    public static Map<String, BrandStoreInfo> getBrandAllStore(String brandId){
        Map<String, BrandStoreInfo> map = Maps.newHashMapWithExpectedSize(7);
        try {
            if(StringUtils.isEmpty(brandId)){
                LOGGER.error("品牌id不能为空");
                return map;
            }
            Properties properties = PropertiesUtil.getProperties("settlement-api.properties");
            String url = properties.getProperty("queryAllStoreUrl")+"/"+brandId;
            String result = HttpClient.get(url).action().result();
            if(StringUtils.isEmpty(result)){
                LOGGER.error("获取所有门店失败: result为空");
                return map;
            }
            HttpResponse response = JsonUtil.fromJson(result, HttpResponse.class);
            if (response == null || !StringUtils.equals(response.getCode(), HttpResponse.success().getCode()) || response.getData() == null) {
                LOGGER.error("获取所有门店失败:response错误");
                return map;
            }
            List<BrandStoreInfo> storeList = JsonUtil.fromJson(JsonUtil.toJson(response.getData()), new TypeReference<List<BrandStoreInfo>>() {
            });
            storeList.forEach(store ->map.put(store.getStoreId(), store));
            return map;
        } catch (Exception e){
            LOGGER.error("获取所有门店失败", e);
            return map;
        }
    }
}
