package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.base.query.QueryObject;

import java.util.Date;

/**
 * 登录记录:
 */
public interface IIplogService {
    /**
     * 添加登录记录：
     * @param username
     * @param remoteAddr
     * @param loginTime
     * @param state
     * @param usertypeWebsite
     */
    void insert(String username, String remoteAddr, Date loginTime, int state, int usertypeWebsite);

    /**
     * 高级查询:
     * @param qo
     * @return
     */
    PageResult query(QueryObject qo);
}
