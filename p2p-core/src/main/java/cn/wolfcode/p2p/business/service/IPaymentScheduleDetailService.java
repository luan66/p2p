package cn.wolfcode.p2p.business.service;

import cn.wolfcode.p2p.business.domain.PaymentScheduleDetail;

/**
 * 收款计划
 */
public interface IPaymentScheduleDetailService {

    /**
     * 添加收款计划:
     * @param psd
     */
    void insert(PaymentScheduleDetail psd);

    /**
     * 修改时间
     * @param detail
     */
    void update(PaymentScheduleDetail detail);
}
