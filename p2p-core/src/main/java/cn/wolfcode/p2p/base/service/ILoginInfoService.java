package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.LoginInfo;

/**
 * Created by mm on 2017/12/30.
 */
public interface ILoginInfoService {
    /**
     * 注册时,验证用户名是否存在:
     * @param username
     * @return
     */
    boolean checkUsername(String username);

    /**
     * 注册用户：
     * @param username
     * @param password
     */
    void registerUser(String username, String password,String confirmPwd,int userType);

    /**
     * 用户登录:
     * @param username
     * @param password
     * @return
     */
    LoginInfo login(String username, String password);

    /**
     * 初始化后台管理员:
     */
    void addAdmin();

    public void test();
}
