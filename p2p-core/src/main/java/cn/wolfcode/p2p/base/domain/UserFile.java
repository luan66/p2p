package cn.wolfcode.p2p.base.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 资料文件上传对象
 */
@Setter
@Getter
public class UserFile extends BaseAuditorDomain {
    private String image;                    //文件路径;
    private SystemDictionaryItem fileTypeId;    //文件类型;
    private int score;                        //文件单个获取分数;

    //回显数据的json:
    public String getJsonString(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("username",applierId.getUsername());
        map.put("fileTypeId",fileTypeId.getTitle());
        map.put("image",image);
        return JSON.toJSONString(map);
    }
}
