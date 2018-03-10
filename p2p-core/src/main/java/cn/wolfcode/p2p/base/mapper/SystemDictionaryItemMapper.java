package cn.wolfcode.p2p.base.mapper;

import cn.wolfcode.p2p.base.domain.SystemDictionaryItem;
import cn.wolfcode.p2p.base.query.SytemDictionaryQueryObject;
import cn.wolfcode.p2p.base.service.impl.SystemDictionaryServiceImpl;

import java.util.List;

public interface SystemDictionaryItemMapper {
    int insert(SystemDictionaryItem record);

    SystemDictionaryItem selectByPrimaryKey(Long id);

    List<SystemDictionaryItem> selectAll();

    int updateByPrimaryKey(SystemDictionaryItem record);

    /**
     * 根据根编号查询明细
     * @param sn
     * @return
     */
    List<SystemDictionaryServiceImpl> getItemListBydirSn(String sn);

    /**
     * 高级查询
     * @param qo
     * @return
     */
    int queryForCount(SytemDictionaryQueryObject qo);

    /**
     * 高级查询
     * @param qo
     * @return
     */
    List<SystemDictionaryItem> queryForList(SytemDictionaryQueryObject qo);
}