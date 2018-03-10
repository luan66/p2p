package cn.wolfcode.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 客户登录信息
 */
@Setter
@Getter
public class LoginInfo extends BaseDomain{
    //状态正常:
    public static final int STATE_NORMAL = 1;
    //状态异常:
    public static final int STATE_LOCK = 0;

    //用户:
    public static final int USERTYPE_WEBSITE = 0;
    //员工
    public static final int USERTYPE_MGRSITE = 1;

    private Long id;
    private String username;
    private String password;
    private int state = STATE_NORMAL;
    //标记是用户还是员工
    private int userType = USERTYPE_WEBSITE;
}
