package cn.wolfcode.p2p.business.mapper;

import cn.wolfcode.p2p.base.query.QueryObject;
import cn.wolfcode.p2p.business.domain.PaymentSchedule;

import java.util.List;

public interface PaymentScheduleMapper {
    int insert(PaymentSchedule record);

    PaymentSchedule selectByPrimaryKey(Long id);

    List<PaymentSchedule> selectAll();

    int updateByPrimaryKey(PaymentSchedule record);

    int queryForCount(QueryObject qo);

    List<PaymentSchedule> queryForList(QueryObject qo);

}