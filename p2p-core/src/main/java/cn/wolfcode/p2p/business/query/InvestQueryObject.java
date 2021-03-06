package cn.wolfcode.p2p.business.query;

import cn.wolfcode.p2p.base.query.QueryObject;
import lombok.Getter;
import lombok.Setter;

/**
 * 投标页面的查询条件
 */
@Setter
@Getter
public class InvestQueryObject extends QueryObject{
    //查询单个状态:
    private int bidRequestState = -1;
    //查询状态:
    private int[] states;

    //排序条件:
    private String orderBy;
    //排序类型:
    private String orderByType;

}
