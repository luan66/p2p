package cn.wolfcode.p2p.base.mapper;

import cn.wolfcode.p2p.base.domain.UserInfo;

public interface UserInfoMapper {
    int insert(UserInfo record);

    //List<UserInfo> selectAll();

    int updateByPrimaryKey(UserInfo record);

    /**
     * 通过id查询对象:
     * @param userId
     * @return
     */
    UserInfo getUserInfoById(Long userId);
}