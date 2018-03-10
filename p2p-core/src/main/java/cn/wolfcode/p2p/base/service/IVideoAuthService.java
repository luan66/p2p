package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.base.query.QueryObject;

import java.util.List;
import java.util.Map;

/**
 * 视频认证
 */
public interface IVideoAuthService {
    /**
     * 高级查询
     * @param qo
     * @return
     */
    PageResult query(QueryObject qo);

    /**
     * 自动补全
     * @param username
     * @return
     */
    List<Map<String,Object>> getAuthListByUsername(String username);


    /**
     * 视频审核
     */
    void insert(int state, Long loginInfoValue, String remark,LoginInfo auditor);
}
