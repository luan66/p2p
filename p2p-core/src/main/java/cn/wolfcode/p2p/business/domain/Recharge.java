package cn.wolfcode.p2p.business.domain;

import cn.wolfcode.p2p.base.domain.BaseAuditorDomain;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 充值记录:
 */
@Setter
@Getter
public class Recharge extends BaseAuditorDomain{
    //银行账户id:
    private PlatformBankInfo bankInfoId;
    //交易号:
    private String tradeCode;
    //交易时间 (什么时间给平台充的钱);
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tradeTime;
    //充值金额:
    private BigDecimal amount;
    //充值说明:
    private String note;

    //回显数据的json:
    public String getJsonString(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("username",applierId.getUsername());
        map.put("tradeCode",tradeCode);
        map.put("amount",amount);
        map.put("tradeTime",tradeTime.toLocaleString());
        return JSON.toJSONString(map);
    }
}
