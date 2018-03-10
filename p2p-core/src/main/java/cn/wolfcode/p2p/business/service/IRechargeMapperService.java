package cn.wolfcode.p2p.business.service;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.business.domain.Recharge;
import cn.wolfcode.p2p.business.query.RechargeQueryObject;

/**
 * 线下充值
 */
public interface IRechargeMapperService {
    /**
     * 添加充值申请
     * @param recharge
     */
    public void insert(Recharge recharge, LoginInfo loginInfo);

    /**
     * 查询线下充值记录
     * @param qo
     * @return
     */
    PageResult query(RechargeQueryObject qo);
    /**
     * 审核线上充值:
     * @param state
     * @param remark
     */
    public void audit(Long id, int state, String remark, LoginInfo loginInfo);
}
