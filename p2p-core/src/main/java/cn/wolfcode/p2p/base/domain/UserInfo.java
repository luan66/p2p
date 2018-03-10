package cn.wolfcode.p2p.base.domain;

import cn.wolfcode.p2p.util.BitStatesUtils;
import cn.wolfcode.p2p.util.Constants;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户基本信息:
 */
@Setter
@Getter
public class UserInfo extends BaseDomain{
    private int version;                                //版本号;
    private Long bitState = 0L;                         //用户状态值;
    private String realName;                            //用户真实姓名
    private String idNumber;                            //用户身份证号
    private String phoneNumber;                         //电话号
    private String email;                               //邮箱
    private SystemDictionaryItem incomeGrade;           //收入
    private SystemDictionaryItem Marriage;              //婚姻情况
    private SystemDictionaryItem kidCount;              //子女情况
    private SystemDictionaryItem educationBackground;   //学历
    private SystemDictionaryItem houseCondition;        //住房条件
    private int score;                                  //资料验证完成分数
    private Long realAuthId;                            //正式身份认证对象;

    //判断是否已经绑定手机号:
    public boolean hasBindPhoneNumber(){
        return BitStatesUtils.hasState(getBitState(),BitStatesUtils.OP_BIND_PHONE);
    }

    /**
     * 添加位状态
     * @param value
     */
    public void addState(Long value) {
        this.bitState = BitStatesUtils.addState(getBitState(),value);
    }

    /**
     * 删除位状态
     * @param value
     */
    public void removeState(Long value) {
        this.bitState = BitStatesUtils.removeState(getBitState(),value);
    }

    /**
     * 判断是否已经绑定邮箱：
     * @return
     */
    public boolean hasBindEmail() {
        return BitStatesUtils.hasState(getBitState(),BitStatesUtils.OP_BIND_EMAIL);
    }

    /**
     * 判断是否完成基本信息：
     * @return
     */
    public boolean hasBasicInfo() {
        return BitStatesUtils.hasState(getBitState(),BitStatesUtils.OP_BASIC_INFO);
    }

    /**
     * 判断是否完成身份认证：
     * @return
     */
    public boolean hasRealAuth() {
        return BitStatesUtils.hasState(getBitState(),BitStatesUtils.OP_REAL_AUTH);
    }

    /**
     * 判断是否完成视频认证：
     * @return
     */
    public boolean hasVedioAuth() {
        return BitStatesUtils.hasState(getBitState(),BitStatesUtils.OP_VEDIO_AUTH);
    }

    /**
     * 判断是否已有借款申请：
     * @return
     */
    public boolean hasBidRequestInProcess() {
        return BitStatesUtils.hasState(getBitState(),BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS);
    }


    /**
     * 是否可以借贷：
     * @return
     */
    public boolean canBorrow() {
        return hasBasicInfo() && hasRealAuth() && hasVedioAuth() && (score >= Constants.CORE_VERIFY);
    }


}
