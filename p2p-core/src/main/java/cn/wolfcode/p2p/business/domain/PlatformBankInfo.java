package cn.wolfcode.p2p.business.domain;

import cn.wolfcode.p2p.base.domain.BaseDomain;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 银行账户:
 */
@Setter
@Getter
public class PlatformBankInfo extends BaseDomain{
    private String bankName;        //银行名称
    private String accountName;     //账户名称;
    private String accountNumber;   //账号;
    private String bankForkName;      //银行账号;

    //回显数据的json:
    public String getJsonString(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("bankName",bankName);
        map.put("accountName",accountName);
        map.put("accountNumber",accountNumber);
        map.put("bankForkName",bankForkName);
        return JSON.toJSONString(map);
    }
}
