package cn.wolfcode.p2p.base.mapper;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LoginInfoMapper {
    /**
     * 注册用户
     * @param entity
     */
    public void insert(LoginInfo entity);

    /**
     * 注册时,验证用户名是否存在:
     * @param username
     * @return
     */
    int checkUsername(String username);

    /**
     * 用户登录:
     * @param username
     * @param password
     * @return
     */
    LoginInfo login(@Param("username") String username, @Param("password") String password);

    /**
     * 自动补全
     * @param username
     * @return
     */
    List<Map<String,Object>> getAuthListByUsername(String username);
}
