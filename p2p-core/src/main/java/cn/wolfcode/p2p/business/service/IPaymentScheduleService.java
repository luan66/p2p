package cn.wolfcode.p2p.business.service;

import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.base.query.QueryObject;
import cn.wolfcode.p2p.business.domain.BidRequest;
import cn.wolfcode.p2p.business.domain.PaymentSchedule;

/**
 * 还款计划
 */
public interface IPaymentScheduleService {

    public void createrPaymentSchedule(BidRequest br);

    PageResult query(QueryObject qo);

    PaymentSchedule selectByPrimaryKey(Long id);

    /**
     * 还款计划
     */
    void updateByPaymentSchedule(PaymentSchedule ps);

    /**
     * 修改还款账单
     * @param ps
     */
    void update(PaymentSchedule ps);
}
