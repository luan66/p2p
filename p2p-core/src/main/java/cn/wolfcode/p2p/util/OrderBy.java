package cn.wolfcode.p2p.util;

import lombok.Getter;

/**
 * 排序条件
 */
@Getter
public enum OrderBy {

    //根据类型排序
    ORDERBY("br.bidRequestState");

    //排序条件
    private final String column;
    //构造器
    OrderBy(String column) {
        this.column = column;
    }
}
