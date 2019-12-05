/**
 * 
 */
package com.hanyun.platform.settle.consts;

import com.hanyun.ground.util.protocol.MessageId;
import com.hanyun.ground.util.protocol.Project;

/**
 * 业务结果码
 * @author liyinglong@hanyun.com
 * @date 2016年8月10日 下午6:33:15
 */
public interface BizResCode {
    MessageId SYSTEMERROR = MessageId.create(Project.SETTLEMENT_API, 1, "系统错误");
    MessageId PARAMERROR = MessageId.create(Project.SETTLEMENT_API, 2, "参数错误");
    
    MessageId FILENOTFOUNDEXCEPTION = MessageId.create(Project.SETTLEMENT_API, 101, "文件找不到异常");
    MessageId GENERATEFILEERROR = MessageId.create(Project.SETTLEMENT_API, 102, "生成文件失败");
    
    MessageId NOTALLOWSUBMITDIFF = MessageId.create(Project.SETTLEMENT_API, 200, "不允许再提报差异");

    MessageId STARTPROCESSFAIL = MessageId.create(Project.SETTLEMENT_API, 300, "启动流程实例失败");

    MessageId INSERTPROCESSINSTANCEFAIL = MessageId.create(Project.SETTLEMENT_API, 301, "插入流程实例表失败");

    MessageId GETPROCESSCURRENTTASKSFAIL = MessageId.create(Project.SETTLEMENT_API, 302, "获取流程当前任务失败");

    MessageId GETPROCESSCURRENTTASKSCONTENTFAIL = MessageId.create(Project.SETTLEMENT_API, 303, "获取流程当前任务内容失败");

    MessageId GETPROCESSFAIL = MessageId.create(Project.SETTLEMENT_API, 304, "获取流程实例失败");

    MessageId STARTPROCESSEXCEPTION = MessageId.create(Project.SETTLEMENT_API, 305, "启动流程异常");

    MessageId SELECTPROCESSAUDITEXCEPTION = MessageId.create(Project.SETTLEMENT_API, 306, "查询待审核列表失败");

    MessageId EXECUTETASKEXCEPTION = MessageId.create(Project.SETTLEMENT_API, 307, "执行任务异常");

    MessageId EXECUTETASKFAIL = MessageId.create(Project.SETTLEMENT_API, 308, "执行任务失败");

    MessageId QUERYAUDITTASKIDEXCEPTION = MessageId.create(Project.SETTLEMENT_API, 309, "查询审批任务列表异常");

    MessageId UPDATEAUDITSTATUSEXCEPTION = MessageId.create(Project.SETTLEMENT_API, 310, "更新结算单审批状态失败");

    MessageId BATCHAUDITEXCEPTION = MessageId.create(Project.SETTLEMENT_API, 311, "批量审批请求参数不能为空");

    MessageId PARANOTNULL = MessageId.create(Project.SETTLEMENT_API, 500, "请求参数不能为空");

    MessageId SELECTEDNOTSAMEBRAND = MessageId.create(Project.SETTLEMENT_API, 501, "选择品牌不是同一个品牌");

    MessageId SELECTEDNOTSAMESTORE = MessageId.create(Project.SETTLEMENT_API, 502, "选择不是同一个门店");

    MessageId SETTLEDNOTAGESETTLE = MessageId.create(Project.SETTLEMENT_API, 503, "已结算的不能再进行结算");

    MessageId COMMISSIONBILLNOTFOUND = MessageId.create(Project.SETTLEMENT_API, 504, "没有找到佣金结算单");

}
