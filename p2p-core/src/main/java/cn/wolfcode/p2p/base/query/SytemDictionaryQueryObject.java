package cn.wolfcode.p2p.base.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * 数据字典高级查询
 */
@Setter
@Getter
public class SytemDictionaryQueryObject extends QueryObject{
    private String keyword;
    private Long parentId = -1L;      //父类id

    //处理keyword字符串
    public String getKeyword() {
        return StringUtils.hasLength(keyword)?keyword.trim(): null ;
    }
}
