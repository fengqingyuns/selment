package com.hanyun.platform.settle.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hanyun.ground.util.http.HttpClient;
import com.hanyun.ground.util.http.HttpClientResponse;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.platform.settle.domain.activiti.*;
import com.hanyun.platform.settle.vo.ExecuteAuditReq;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

/**
 * Created by jack on 2017/5/22.
 */
public class ActivitiUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(ActivitiUtil.class);
    private volatile static ActivitiUtil instance = null;
    private static final String url = "http://activiti.api.hanyun.com/service";
    private String userName;
    private String password;
    //流程实例化
    private static final String PROCESS_INSTANCE_URL = url + "/runtime/process-instances";
    //查询任务
    private static final String QUERY_TASKS_RUL = url + "/query/tasks";
    //任务
    private static final String TASK_URL = url + "/runtime/tasks";
    //任务历史
    private static final String HISTORIC_ACTIVITY_INSTANCES_URL = url + "/history/historic-activity-instances";
    //任务变量
    private static final String HISTORIC_VARIABLE_INSTANCES_URL = url + "/query/historic-variable-instances";

    private ActivitiUtil(){}

    public static ActivitiUtil getInstance() {
        if (instance == null) {
            synchronized (ActivitiUtil.class) {
                if (instance == null) {
                    PropertiesUtil propertiesUtil = new PropertiesUtil();
                    Properties properties = null;
                    try {
                        properties = propertiesUtil.getProperties("settlement-api.properties");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    instance = new ActivitiUtil();
                    instance.userName = properties.getProperty("activiti_username");
                    instance.password = properties.getProperty("activiti_password");
                }
            }
        }
        return instance;
    }

    /**
     * 启动流程实例
     * @return
     */
    public static ActivitiProcessInstance startupProcess(String processDefinitionId, String  businessKey){
        JSONObject params = new JSONObject();
        params.put("processDefinitionId", processDefinitionId);
        params.put("businessKey", businessKey);
        String result = HttpClient.post(PROCESS_INSTANCE_URL).json(params).authentication(instance.userName, instance.password).action().result();
        LOGGER.info("请求启动流程实例activiti返回信息 {}",result);
        return JsonUtil.fromJson(result, ActivitiProcessInstance.class);
    }

    /**
     * 获取流程当前任务
     * @param processInstanceId
     * @return
     */
    public static ActivitQueryTask  getProcessCurrentTasks(String processInstanceId){
        JSONObject params = new JSONObject();
        params.put("processInstanceId", processInstanceId);
        String result = HttpClient.post(QUERY_TASKS_RUL).json(params).authentication(instance.userName, instance.password).action().result();
        LOGGER.info("请求获取流程当前任务activiti返回信息 {}",result);
        return JsonUtil.fromJson(result, ActivitQueryTask.class);

    }


    /**
     * 获取流程当前角色
     * @param taskid
     * @return
     */
    public static List<ActivitiIdentity> getProcessCurrentTasksRole(String taskid){
        HttpClient httpClient = HttpClient.get(TASK_URL + "/" + taskid + "/identitylinks");
        String result = httpClient.authentication(instance.userName, instance.password).action().result();
        LOGGER.info("请求获取流程当前角色activiti返回信息 {}",result);

        List<ActivitiIdentity> activitiList = JsonUtil.fromJson(result,new TypeReference<List<ActivitiIdentity>>() {
        });
        return activitiList;
    }

    /**
     * 任务--设置变量
     */
    public static void  setVariables(ExecuteAuditReq executeAuditReq){
        List<TaskVariables> variablesList = new ArrayList<TaskVariables>();
        TaskVariables  resultTaskVariables = new TaskVariables();
        resultTaskVariables.setName("result");
        resultTaskVariables.setType("string");
        resultTaskVariables.setValue(executeAuditReq.getResult());

        TaskVariables  remarkTaskVariables = new TaskVariables();
        remarkTaskVariables.setName("remark");
        remarkTaskVariables.setType("string");
        remarkTaskVariables.setValue(executeAuditReq.getRemark());
        variablesList.add(resultTaskVariables);
        variablesList.add(remarkTaskVariables);
        String result = HttpClient.post(TASK_URL + "/" + executeAuditReq.getTaskId() + "/variables").json(JsonUtil.toJson(variablesList)).authentication(instance.userName, instance.password).action().result();
    }

    /**
     * 任务--声明审核人
     */
    public static void setAuditor(ExecuteAuditReq executeAuditReq){
        JSONObject params = new JSONObject();
        params.put("action","claim");
        params.put("assignee",executeAuditReq.getUserId());
        String result = HttpClient.post(TASK_URL + "/" + executeAuditReq.getTaskId()).json(params).authentication(instance.userName, instance.password).action().result();
    }

    /**
     * 任务--执行任务
     */
    public static void executeTask(ExecuteAuditReq executeAuditReq){
        TaskVariables  passTaskVariables = new TaskVariables();
        passTaskVariables.setName("pass");
        passTaskVariables.setType("string");
        passTaskVariables.setValue(executeAuditReq.getResult());
        List<TaskVariables> variablesList = new ArrayList<TaskVariables>();
        variablesList.add(passTaskVariables);
        ActivitiComplete activitiComplete = new ActivitiComplete();
        activitiComplete.setAction("complete");
        activitiComplete.setVariables(variablesList);
        String result = HttpClient.post(TASK_URL + "/" + executeAuditReq.getTaskId()).json(JsonUtil.toJson(activitiComplete)).authentication(instance.userName, instance.password).action().result();

    }

    /**
     * 任务历史节点
     */
    public static HistoricActivityInstances historicActivityInstances(String processInstanceId){
        int sizeCount = 100;
        HttpClient httpClient = HttpClient.get(HISTORIC_ACTIVITY_INSTANCES_URL+"?"+"processInstanceId="+processInstanceId+"&"+"size="+sizeCount);
        String result = httpClient.authentication(instance.userName, instance.password).action().result();
        LOGGER.info("请求任务历史节点activiti返回信息 {}",result);

        HistoricActivityInstances historicActivityInstances = JsonUtil.fromJson(result,new TypeReference<HistoricActivityInstances>() {
        });
       return  historicActivityInstances;
    }

    /**
     *历史任务变量
     * @return
     */
    public static HistoricVariable historicVariableInstances(String processInstanceId){
        JSONObject json = new JSONObject();
        int sizeCount = 100;
        json.put("processInstanceId",processInstanceId);
        String result = HttpClient.post(HISTORIC_VARIABLE_INSTANCES_URL+"?"+"size="+sizeCount).json(JsonUtil.toJson(json)).authentication(instance.userName, instance.password).action().result();
        LOGGER.info("请求历史任务变量activiti返回信息 {}",result);
        HistoricVariable historicVariable = JsonUtil.fromJson(result,new TypeReference<HistoricVariable>() {
        });
        return  historicVariable;

    }


    /**
     *  流程图 -- 流程未结束
     * @param processInstanceId
     */
    public static String flowChart(String processInstanceId) throws IOException {
        HttpClient httpClient = HttpClient.get(PROCESS_INSTANCE_URL + "/" + processInstanceId + "/diagram");
        HttpClientResponse result = httpClient.authentication(instance.userName, instance.password).action();
        String str = "";
        System.out.println("111"+result.getInputStream());
        try {
            byte[]  imgByte = readInputStream1(result.getInputStream());
            System.out.println(imgByte.length);
             str = Base64.encodeBase64String(imgByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  str;
    }

    public static void main(String[] args) {
        ActivitiUtil.getInstance();
        try {
            ActivitiUtil.flowChart("70608");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static byte[] readInputStream1(InputStream inputStream) throws Exception{
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1){
            outputStream.write(buffer, 0, len);
        }
        outputStream.close();
        inputStream.close();
        return outputStream.toByteArray();
    }


}
