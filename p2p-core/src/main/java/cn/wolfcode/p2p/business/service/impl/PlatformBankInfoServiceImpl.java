package cn.wolfcode.p2p.business.service.impl;

import cn.wolfcode.p2p.base.mapper.UserInfoMapper;
import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.business.domain.PlatformBankInfo;
import cn.wolfcode.p2p.business.mapper.PlatformBankInfoMapper;
import cn.wolfcode.p2p.business.mapper.RechargeMapper;
import cn.wolfcode.p2p.business.query.PlatformBankInfoQueryObject;
import cn.wolfcode.p2p.business.service.IPlatformBankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mm on 2018/1/9.
 */
@Service
public class PlatformBankInfoServiceImpl implements IPlatformBankInfoService {
    @Autowired
    private PlatformBankInfoMapper platformBankInfoMapper;
    @Autowired
    private RechargeMapper rechargeMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public PageResult query(PlatformBankInfoQueryObject qo) {
        int totalCount = platformBankInfoMapper.queryForCount(qo);
        if (totalCount == 0){
            return new PageResult(qo.getPageSize());
        }
        List<PlatformBankInfo> list = platformBankInfoMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),totalCount,list);
    }

    public void saveOrUpdate(PlatformBankInfo platformBankInfo) {
        if(platformBankInfo.getId()==null){
            platformBankInfoMapper.insert(platformBankInfo);
        }else{
            platformBankInfoMapper.updateByPrimaryKey(platformBankInfo);
        }
    }

    //查询公司所有账户
    public List<PlatformBankInfo> selectAll() {
        return platformBankInfoMapper.selectAll();
    }

}
