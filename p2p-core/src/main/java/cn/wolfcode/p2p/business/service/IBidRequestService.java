package cn.wolfcode.p2p.business.service;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.page.PageResult;
import cn.wolfcode.p2p.base.query.QueryObject;
import cn.wolfcode.p2p.business.domain.BidRequest;

import java.math.BigDecimal;
import java.util.List;

/**
 * 借款申请
 */
public interface IBidRequestService {
    /**
     * 申请借款
     * @param bidRequest
     */
    void borrowApply(BidRequest bidRequest);

    /**
     * 发标审核高级查询
     * @param qo
     * @return
     */
    PageResult query(QueryObject qo);

    /**
     * 审核发标
     * @param id
     * @param state
     * @param remark
     */
    void audit(Long id, int state, String remark,LoginInfo loginInfo);

    /**
     * 投标查询
     * @param qo
     * @return
     */
    List<BidRequest> queryForList(QueryObject qo);

    /**
     * 查询一条借款
     * @param id
     * @return
     */
    BidRequest selectByPrimaryKey(Long id);

    /**
     * 投标
     * @param bidRequestId
     * @param amount
     */
    public void bid(Long bidRequestId, BigDecimal amount,LoginInfo loginInfo);

    /**
     * 满标一审
     * @param id
     * @param state
     * @param remark
     * @param loginInfo
     */
    void audit1(Long id, int state, String remark, LoginInfo loginInfo);

    /**
     * 满标二审
     * @param id
     * @param state
     * @param remark
     * @param loginInfo
     */
    void audit2(Long id, int state, String remark, LoginInfo loginInfo);

    /**
     * 还款
     * @param id
     * @param loginInfo
     */
    void returnMoney(Long id,LoginInfo loginInfo);
}
