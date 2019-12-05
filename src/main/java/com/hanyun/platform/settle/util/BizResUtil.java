/**
 * 
 */
package com.hanyun.platform.settle.util;

import com.hanyun.ground.util.protocol.MessageId;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.platform.settle.vo.base.PageResData;

/**
 * 业务结果工具类
 * @author liyinglong@hanyun.com
 * @date 2016年8月10日 下午6:47:56
 */
public abstract class BizResUtil {
    
    /**
     * 成功结果
     * @param data
     * @return
     */
    public static <T> HttpResponse<T> succ(T data){
        HttpResponse<T> res = new HttpResponse<T>(data);
        
        return res;
    }
    
    /**
     * 分页列表成功结果
     * @param data
     * @return
     */
    public static HttpResponse<PageResData> succPageList(Integer totalCount, Object dataList){
        
        PageResData data = new PageResData(totalCount, dataList);
        
        HttpResponse<PageResData> res = new HttpResponse<PageResData>(data);
        
        return res;
    }
    
    /**
     * 失败结果
     * @param code
     * @return
     */
    public static <T> HttpResponse<T> fail(MessageId code){
        HttpResponse<T> res = new HttpResponse<T>(code);
        
        return res;
    }
}
