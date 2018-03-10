package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.SystemDictionary;
import cn.wolfcode.p2p.base.domain.SystemDictionaryItem;
import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.base.query.QueryObject;
import cn.wolfcode.p2p.base.query.SytemDictionaryQueryObject;
import cn.wolfcode.p2p.base.service.impl.SystemDictionaryServiceImpl;

import java.util.List;

public interface ISystemDictionaryService {
    /**
     * 根据目录编号查询明细集合
     * @param sn
     * @return
     */
    List<SystemDictionaryServiceImpl> getItemListBydirSn(String sn);

    /**
     * 数据字典目录高级查询
     * @param qo
     * @return
     */
    PageResult query(QueryObject qo);

    /**
     * 字典的添加或修改
     * @param systemDictionary
     */
    void saveOrUpdate(SystemDictionary systemDictionary);

    /**
     * 明细的高级查询
     * @param qo
     * @return
     */
    PageResult queryItem(SytemDictionaryQueryObject qo);

    /**
     * 查询所有的数据字典数据:
     * @return
     */
    List<SystemDictionary> selectAll();

    /**
     * 字典明细的添加或修改
     * @param systemDictionaryItem
     */
    void saveOrUpdateItem(SystemDictionaryItem systemDictionaryItem);
}
