package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.RealAuth;
import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.base.query.QueryObject;

/**
 * 实名认证
 */
public interface IRealAuthService {
    /**
     * 保存认证信息
     * @param realAuth
     */
    public void save(RealAuth realAuth, LoginInfo loginInfo);

    /**
     * 查询一条数据
     * @param id
     * @return
     */
    RealAuth selectByPrimaryKey(Long id);

    /**
     * 实名认证后台的高级查询
     * @param qo
     * @return
     */
    PageResult query(QueryObject qo);

    /**
     * 后台审核实名认证
     * @param realAuth
     */
    public void audit(RealAuth realAuth,LoginInfo loginInfo);
}
