package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.mapper.LoginInfoMapper;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.service.ILoginInfoService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mm on 2017/12/30.
 */
@Service("loginInfoService")
@Transactional
public class LoginInfoServiceImpl implements ILoginInfoService{
    @Autowired
    private LoginInfoMapper loginInfoMapper;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserInfoService userInfoService;
    //从配置文件中获取效验长度:(防止硬编码)
    @Value("${minLength}")
    private int minLength;
    @Value("${maxLength}")
    private int maxLength;


    //效验用户名是否存在:
    public boolean checkUsername(String username) {
        return loginInfoMapper.checkUsername(username) > 0;
    }

    //注冊用戶:
    public void registerUser(String username, String password, String confirmPwd,int userType) {
        //后台效验
        validataRegister(username, password, confirmPwd);
        //01:保存用户:
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUsername(username);
        loginInfo.setUserType(userType);
        //密码加密：
        String encode = MD5.encode(username + password);
        loginInfo.setPassword(encode);
        loginInfoMapper.insert(loginInfo);
        //02:保存默认的账户信息:
        Account account = new Account();
        account.setId(loginInfo.getId());
        accountService.iniAccount(account);
        //03:保存默认的基本信息:
        UserInfo userInfo = new UserInfo();
        userInfo.setId(loginInfo.getId());
        userInfoService.iniUserInfo(userInfo);
    }

    /**
     * 用户登录:
     * @param username
     * @param password
     * @return
     */
    public LoginInfo login(String username, String password) {
        //效验用户名和密码的长度:
        checkUsernameAndPassword(username, password);

        //加密密码:
        password = MD5.encode(username+password);
        LoginInfo loginInfo = loginInfoMapper.login(username,password);
        if (loginInfo == null){
            throw new DisplayableException("用户名或密码错误!");
        }
        return loginInfo;
    }

    //初始化后台管理员
    public void addAdmin() {
        if(!checkUsername("admin")){
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setUsername("admin");
            loginInfo.setPassword(MD5.encode("admin000000"));
            loginInfo.setUserType(LoginInfo.USERTYPE_MGRSITE);
            loginInfoMapper.insert(loginInfo);
        }
    }

    /**
     * 后台效验注册:
     * @param username
     * @param password
     * @param confirmPwd
     */
    private void validataRegister(String username, String password, String confirmPwd) {
        //效验用户名和密码的长度:
        checkUsernameAndPassword(username, password);

        //注册时,效验用户名是否存在:
        if(checkUsername(username)){
            throw new DisplayableException("用户名已存在!");
        }
        //效验确认密码:
        if(confirmPwd == null || confirmPwd == ""){
            throw new DisplayableException("确认密码不能为空!");
        }
        if(!password.equals(confirmPwd)){
            throw new DisplayableException("密码不一致!");
        }
    }

    /**
     *  效验用户名和密码的长度:
     * @param username
     * @param password
     */
    public void checkUsernameAndPassword(String username, String password) {
        //效验用户名:
        if(username == null || username == ""){
            throw new DisplayableException("用户名不能为空!");
        }
        if(!(username.length() >= minLength && username.length() <= maxLength)){
            throw new DisplayableException("用户名长度为"+minLength+"~"+maxLength+"位!");
        }
        //效验密码:
        if(password == null || password == ""){
            throw new DisplayableException("密码不能为空!");
        }
        if(!(password.length() >= minLength && password.length() <= maxLength)){
            throw new DisplayableException("密码长度为"+minLength+"~"+maxLength+"位!");
        }
    }

    /**
     * 测试
     */
    public void test(){
        System.out.println("===============================");
    }
}
