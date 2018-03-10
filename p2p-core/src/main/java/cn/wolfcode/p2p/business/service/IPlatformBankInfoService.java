package cn.wolfcode.p2p.business.service;

import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.business.domain.PlatformBankInfo;
import cn.wolfcode.p2p.business.query.PlatformBankInfoQueryObject;

import java.util.List;

/**
 * Created by mm on 2018/1/9.
 */
public interface IPlatformBankInfoService {
    PageResult query(PlatformBankInfoQueryObject qo);

    void saveOrUpdate(PlatformBankInfo platformBankInfo);

    /**
     * 查询公司所有的账户:
     * @return
     */
    List<PlatformBankInfo> selectAll();

}
