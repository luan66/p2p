package cn.wolfcode.p2p.base.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据字典根目录:
 */
@Setter
@Getter
@ToString
public class SystemDictionary extends BaseDomain{
    private String sn;      //数据字典编码
    private String title;   //数据字典名称

    //封装一个json字符串:用于修改时使用
    public String getJson(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",getId());
        map.put("sn",sn);
        map.put("title",title);
        return JSON.toJSONString(map);
    }
}
