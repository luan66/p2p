package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.SystemDictionary;
import cn.wolfcode.p2p.base.domain.SystemDictionaryItem;
import cn.wolfcode.p2p.base.mapper.SystemDictionaryItemMapper;
import cn.wolfcode.p2p.base.mapper.SystemDictionaryMapper;
import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.base.query.QueryObject;
import cn.wolfcode.p2p.base.query.SytemDictionaryQueryObject;
import cn.wolfcode.p2p.base.service.ISystemDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemDictionaryServiceImpl implements ISystemDictionaryService {
    @Autowired
    private SystemDictionaryMapper systemDictionaryMapper;
    @Autowired
    private SystemDictionaryItemMapper systemDictionaryItemMapper;

    //根据根目录编号查询明细
    public List<SystemDictionaryServiceImpl> getItemListBydirSn(String sn) {
        return systemDictionaryItemMapper.getItemListBydirSn(sn);
    }

    //字典目录高级查询:
    public PageResult query(QueryObject qo) {
        int totalCount = systemDictionaryMapper.queryForCount(qo);
        if (totalCount == 0){
            return new PageResult(qo.getPageSize());
        }
        List<SystemDictionary> list = systemDictionaryMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),totalCount,list);
    }

    //字典的添加或修改
    public void saveOrUpdate(SystemDictionary systemDictionary) {
        if(systemDictionary.getId() == null){
            systemDictionaryMapper.insert(systemDictionary);
        }else{
            systemDictionaryMapper.updateByPrimaryKey(systemDictionary);
        }
    }

    //明细的高级查询
    public PageResult queryItem(SytemDictionaryQueryObject qo) {
        int totalCount = systemDictionaryItemMapper.queryForCount(qo);
        if (totalCount == 0){
            return new PageResult(qo.getPageSize());
        }
        List<SystemDictionaryItem> list = systemDictionaryItemMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),totalCount,list);
    }

    //查询所有的数据字典
    public List<SystemDictionary> selectAll() {
        return systemDictionaryMapper.selectAll();
    }

    //字典明细的添加或修改
    public void saveOrUpdateItem(SystemDictionaryItem systemDictionaryItem) {
        if(systemDictionaryItem.getId() == null){
            systemDictionaryItemMapper.insert(systemDictionaryItem);
        }else{
            systemDictionaryItemMapper.updateByPrimaryKey(systemDictionaryItem);
        }
    }
}
