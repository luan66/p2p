package cn.wolfcode.p2p.util;

import lombok.Getter;

/**
 * 排序类型
 */
@Getter
public enum  OrderByType {

    ORDER_BY_ASC("ASC"),ORDER_BY_DESC("DESC");

    //排序类型
    private final String orderByType;

    //构造器
    OrderByType(String orderByType) {
        this.orderByType = orderByType;
    }
}
