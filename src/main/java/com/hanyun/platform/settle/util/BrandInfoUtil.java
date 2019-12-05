package com.hanyun.platform.settle.util;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;
import com.hanyun.ground.util.http.HttpClient;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.platform.settle.domain.BrandAllInfo;
import com.hanyun.platform.settle.vo.BrandInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * @author wangjie@hanyun.com
 * @Date 2018/3/28 10:10
 */
public abstract class BrandInfoUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseBrandStoreUtil.class);

    /**
     * 获取品牌信息
     * @param brandId 品牌id
     * @return Map
     */
    public static Map<String, BrandAllInfo> getBrandIndustry(String brandId){
        Map<String, BrandAllInfo> map = Maps.newHashMapWithExpectedSize(7);
        try {
            if(StringUtils.isEmpty(brandId)){
                LOGGER.error("品牌id不能为空");
                return map;
            }
            Properties properties = PropertiesUtil.getProperties("settlement-api.properties");
            String url = properties.getProperty("brandInfQueryUrl")+"?"+"brandId="+brandId;
            String result = HttpClient.get(url).action().result();
            if(StringUtils.isEmpty(result)){
                LOGGER.error("获取品牌信息失败: result为空");
                return map;
            }
            HttpResponse response = JsonUtil.fromJson(result, HttpResponse.class);
            if (response == null || !StringUtils.equals(response.getCode(), HttpResponse.success().getCode()) || response.getData() == null) {
                LOGGER.error("获取品牌信息失败:response错误");
                return map;
            }
            BrandInfoVo brandInfoVo = JsonUtil.fromJson(JsonUtil.toJson(response.getData()), new TypeReference<BrandInfoVo>() {
            });
            List<BrandAllInfo> brandList = brandInfoVo.getList();
            brandList.forEach(brand ->map.put(brand.getBrandId(), brand));
            return map;
        } catch (Exception e){
            LOGGER.error("获取品牌失败", e);
            return map;
        }
    }
}