package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.Iplog;
import cn.wolfcode.p2p.base.mapper.IplogMapper;
import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.base.query.QueryObject;
import cn.wolfcode.p2p.base.service.IIplogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 登录记录
 */
@Service
public class IplogServiceImpl implements IIplogService {
    @Autowired
    private IplogMapper iplogMapper;

    //添加登录记录
    public void insert(String username, String ip,Date loginTime, int state, int userType) {
        Iplog iplog = new Iplog();
        iplog.setUsername(username);
        iplog.setIp(ip);
        iplog.setLoginTime(loginTime);
        iplog.setState(state);
        iplog.setUserType(userType);
        iplogMapper.insert(iplog);
    }

    //高级查询
    public PageResult query(QueryObject qo) {
        int totalCount = iplogMapper.queryForCount(qo);
        if (totalCount == 0){
            return new PageResult(qo.getPageSize());
        }
        List<Iplog> list = iplogMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(),qo.getPageSize(),totalCount,list);
    }

}
