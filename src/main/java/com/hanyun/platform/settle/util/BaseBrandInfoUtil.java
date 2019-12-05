package com.hanyun.platform.settle.util;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;
import com.hanyun.ground.util.http.HttpClient;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.platform.settle.domain.BrandAllInfo;
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
 * 查询所有品牌信息
 */
public abstract class BaseBrandInfoUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseBrandInfoUtil.class);


    /**
     * 获取所有品牌
     * @return Map
     */
    public static Map<String, BrandAllInfo> getBrandAll(){
        Map<String, BrandAllInfo> map = Maps.newHashMapWithExpectedSize(7);
        try {
            Properties properties = PropertiesUtil.getProperties("settlement-api.properties");
            String url = properties.getProperty("queryAllBrandUrl");
            String result = HttpClient.get(url).action().result();
            if(StringUtils.isEmpty(result)){
                LOGGER.error("获取所有品牌失败: result为空");
                return map;
            }
            HttpResponse response = JsonUtil.fromJson(result, HttpResponse.class);
            if (response == null || !StringUtils.equals(response.getCode(), HttpResponse.success().getCode()) || response.getData() == null) {
                LOGGER.error("获取所有品牌失败:response错误");
                return map;
            }
            List<BrandAllInfo> brandList = JsonUtil.fromJson(JsonUtil.toJson(response.getData()), new TypeReference<List<BrandAllInfo>>() {
            });
            brandList.forEach(brand ->map.put(brand.getBrandId(), brand));
            return map;
        } catch (Exception e){
            LOGGER.error("获取品牌失败", e);
            return map;
        }
    }

}
