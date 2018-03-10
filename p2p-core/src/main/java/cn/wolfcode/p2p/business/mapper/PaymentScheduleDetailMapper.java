package cn.wolfcode.p2p.business.mapper;

import cn.wolfcode.p2p.business.domain.PaymentScheduleDetail;
import java.util.List;

public interface PaymentScheduleDetailMapper {
    int insert(PaymentScheduleDetail record);

    PaymentScheduleDetail selectByPrimaryKey(Long id);


    List<PaymentScheduleDetail> selectAll();

    int updateByPrimaryKey(PaymentScheduleDetail record);

    List<PaymentScheduleDetail> listDetailByPsId(Long id);
}