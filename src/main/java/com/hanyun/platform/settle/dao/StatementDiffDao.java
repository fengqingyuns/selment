package com.hanyun.platform.settle.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanyun.platform.settle.domain.StatementDiff;

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
public interface StatementDiffDao {

    /**
     * 根据主键ID查询单个对账差异明细
     * @param statementDiff
     * @return
     */
    public StatementDiff selectStatmentDiffDetailByTransId(StatementDiff statementDiff);
    /**
     * 新增对账差异提报
     * @param statementDiff
     * @return
     */
    public int insertStatementDiffSubmit(StatementDiff statementDiff);
    /**
     * 修改对账差异提交
     * @param statementDiff
     * @return
     */
    public int updateStatementDiffSubmit(StatementDiff statementDiff);
    
}