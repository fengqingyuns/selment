package com.hanyun.platform.settle.dao;

import com.hanyun.platform.settle.domain.ProcessInstance;
import java.util.List;

import com.hanyun.platform.settle.vo.ProcessInstanceDetailReq;
import org.springframework.stereotype.Repository;

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
 */
@SuppressWarnings("UnnecessaryInterfaceModifier")
@Repository
public interface ProcessInstanceDao {

    public int deleteByPrimaryKey(Long id);

    public int insertSelective(ProcessInstance record);

    public ProcessInstance selectByPrimaryKey(Long id);

    public List<ProcessInstance> selectSelective(ProcessInstance record);

    public int updateByPrimaryKeySelective(ProcessInstance record);

    public int updateByProcessInstance(ProcessInstance record);

    public ProcessInstance selectByProcessInstance(String businessKey);

    public List<ProcessInstanceDetailReq> selectProcessInstance(ProcessInstanceDetailReq record);

    public int selectProcessInstanceCount(ProcessInstanceDetailReq record);


}