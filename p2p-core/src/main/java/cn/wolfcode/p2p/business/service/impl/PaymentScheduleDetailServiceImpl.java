package cn.wolfcode.p2p.business.service.impl;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.business.domain.PaymentScheduleDetail;
import cn.wolfcode.p2p.business.mapper.PaymentScheduleDetailMapper;
import cn.wolfcode.p2p.business.service.IPaymentScheduleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 收款计划
 */
@Service
public class PaymentScheduleDetailServiceImpl implements IPaymentScheduleDetailService {
    @Autowired
    private PaymentScheduleDetailMapper paymentScheduleDetailMapper;

    //添加收款计划:
    public void insert(PaymentScheduleDetail psd) {
        paymentScheduleDetailMapper.insert(psd);
    }

    @Override
    public void update(PaymentScheduleDetail detail) {
        int num = paymentScheduleDetailMapper.updateByPrimaryKey(detail);
        if(num == 0){
            throw new DisplayableException("修改收款单出错[乐观锁异常]");
        }
    }
}
