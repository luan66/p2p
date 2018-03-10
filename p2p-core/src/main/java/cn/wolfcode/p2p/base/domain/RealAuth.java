package cn.wolfcode.p2p.base.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 实名认证
 */
@Setter
@Getter
public class RealAuth extends BaseAuditorDomain {
    //男:
    public static final int MAN_SEX = 1;
    //女:
    public static final int WUMAN_SEX = 0;

    private String realName;        //真实姓名
    private int sex = MAN_SEX;      //性别
    private String idNumber;        //身份证号
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bornDate;         //生日
    private String address;        //地址
    private String image1;         //省份证正面照
    private String image2;         //省份证背面照

    //格式化性别:
    public String getSexDisplay(){
        return sex == 1?"男":"女";
    }

    //格式化生日:
    public String getBornDateFormar(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(bornDate);
    }

    //回显数据的json:
    public String getJsonString(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("username",applierId.getUsername());
        map.put("realName",realName);
        map.put("idNumber",idNumber);
        map.put("bornDate",getBornDateFormar());
        map.put("sex",getSexDisplay());
        map.put("address",address);
        map.put("image1",image1);
        map.put("image2",image2);
        return JSON.toJSONString(map);
    }

}
