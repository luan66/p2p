package cn.wolfcode.p2p.business.service.impl;

import cn.wolfcode.p2p.business.domain.PaymentScheduleDetail;
import cn.wolfcode.p2p.business.domain.SystemAccount;
import cn.wolfcode.p2p.business.domain.SystemAccountFlow;
import cn.wolfcode.p2p.business.mapper.SystemAccountFlowMapper;
import cn.wolfcode.p2p.business.service.ISystemAccountFlowService;
import cn.wolfcode.p2p.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 系统账户流水
 */
@Service
public class SystemAccountFlowServiceImpl implements ISystemAccountFlowService {
    @Autowired
    private SystemAccountFlowMapper systemAccountFlowMapper;

    //产生系统账户流水:
    public void createrSystemAccountFlow(SystemAccount systemAccount, BigDecimal managementChargeMoney) {
        SystemAccountFlow systemAccountFlow = new SystemAccountFlow();
        systemAccountFlow.setActionType(Constants.ACCOUNT_ACTIONTYPE_CHARGE);
        systemAccountFlow.setAmount(managementChargeMoney);
        systemAccountFlow.setCreatedDate(new Date());
        systemAccountFlow.setFreezedAmount(systemAccount.getFreezedAmount());
        systemAccountFlow.setNote("平台收取管理费"+managementChargeMoney+"元");
        systemAccountFlow.setUsableAmount(systemAccount.getUsableAmount());
        systemAccountFlowMapper.insert(systemAccountFlow);
    }

    public void createrInterestManagerChargeSystemAccountFlow(SystemAccount systemAccount, BigDecimal interestManagerCharge) {
        SystemAccountFlow systemAccountFlow = new SystemAccountFlow();
        systemAccountFlow.setActionType(Constants.ACCOUNT_ACTIONTYPE_CHARGE);
        systemAccountFlow.setAmount(interestManagerCharge);
        systemAccountFlow.setCreatedDate(new Date());
        systemAccountFlow.setFreezedAmount(systemAccount.getFreezedAmount());
        systemAccountFlow.setNote("平台收取利息管理费"+interestManagerCharge+"元");
        systemAccountFlow.setUsableAmount(systemAccount.getUsableAmount());
        systemAccountFlowMapper.insert(systemAccountFlow);
    }

}
