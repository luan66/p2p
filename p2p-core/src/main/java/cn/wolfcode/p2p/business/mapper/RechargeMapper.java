package cn.wolfcode.p2p.business.mapper;

import cn.wolfcode.p2p.business.domain.Recharge;
import cn.wolfcode.p2p.business.query.RechargeQueryObject;

import java.util.List;

public interface RechargeMapper {
    int insert(Recharge record);

    Recharge selectByPrimaryKey(Long id);

    /**
     * 高級查詢
     * @param qo
     * @return
     */
    int queryForCount(RechargeQueryObject qo);

    /**
     * 高级查询
     * @param qo
     * @return
     */
    List<Recharge> queryForList(RechargeQueryObject qo);

    /**
     * 修改信息
     * @param recharge
     * @return
     */
    int updateByPrimaryKey(Recharge recharge);

    //List<Recharge> selectAll();
}