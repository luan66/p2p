package cn.wolfcode.p2p.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonResult {

    private boolean success = true;
    private String msg;
    /**
     * 正常时,调用的构造器;
     */
    public JsonResult(){}
    /**
     * 异常时,抛出的错误;
     * @param msg
     */
    public JsonResult(String msg){
        this.success = false;
        this.msg = msg;
    }

}
