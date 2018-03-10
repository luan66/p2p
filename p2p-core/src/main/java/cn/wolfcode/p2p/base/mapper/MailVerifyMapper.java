package cn.wolfcode.p2p.base.mapper;

import cn.wolfcode.p2p.base.domain.MailVerify;

public interface MailVerifyMapper {
    int insert(MailVerify record);

    /**
     * 根据uuid查询对象
     * @param uuid
     * @return
     */
    MailVerify getMailVerifyByUuid(String uuid);
}