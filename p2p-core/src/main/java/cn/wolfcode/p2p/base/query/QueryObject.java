package cn.wolfcode.p2p.base.query;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页条件
 */
@Setter
@Getter
public class QueryObject {
    private int currentPage = 1;
    private int pageSize = 10;

    //检索索引:
    public int getBeginIndex(){
        return (currentPage - 1)*10;
    }
}
