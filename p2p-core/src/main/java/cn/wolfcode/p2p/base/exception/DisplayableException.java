package cn.wolfcode.p2p.base.exception;

/**
 * 包装那些可以显示给用户看的异常信息:比如 用户名为空
 */
public class DisplayableException extends  RuntimeException {
    public DisplayableException(String message){
        super(message);
    }
}
