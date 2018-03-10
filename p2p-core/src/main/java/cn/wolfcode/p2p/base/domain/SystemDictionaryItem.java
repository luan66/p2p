package cn.wolfcode.p2p.base.domain;

/**
 * 数据字典明细
 */

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class SystemDictionaryItem extends BaseDomain{
    private String title;   //数据字典明细名字;
    private Long parentId;  //数据字典id;
    private int sequence;   //数据字典明细的排序;

    //封装一个json字符串:用于修改时使用
    public String getJson(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",getId());
        map.put("title",title);
        map.put("parentId",parentId);
        map.put("sequence",sequence);
        return JSON.toJSONString(map);
    }
}
