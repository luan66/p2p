package cn.wolfcode.p2p.business.domain;

import cn.wolfcode.p2p.base.domain.BaseDomain;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.util.Constants;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 借款人相关信息:
 */
@Setter
@Getter
public class BidRequest extends BaseDomain{
    private int version;                    //乐观锁
    private int returnType;                 //还款方式
    private int bidRequestType;             //借款类型
    private int bidRequestState;            //借款的状态
    private BigDecimal bidRequestAmount;    //借款总金额
    private BigDecimal currentRate;         //借款利率
    private BigDecimal minBidAmount;        //最小借款金额
    private int monthes2Return;             //借款期限
    private int bidCount;                   //该借款现在已有多少投标
    private BigDecimal totalRewardAmount;          //总回报金额;
    private BigDecimal currentSum = Constants.ZERO;          //该借款共有多少资金了
    private String title;                   //借款标题
    private String description;             //借款描述
    private String note;                    //分控评审意见
    private Date disableDate;               //招标到期时间
    private int disableDays;                //招标天数
    private LoginInfo createUserId;         //借款人
    private List<Bid> bids;                 //投资记录
    private Date applyTime;                 //申请时间
    private Date publishTime;               //发布时间

    //还需多少:
    public BigDecimal getRemainAmount(){
        return bidRequestAmount.subtract(currentSum);
    }

    //进度:
    public int getPersent(){
        return (currentSum.divide(bidRequestAmount,2, RoundingMode.HALF_UP).multiply(new BigDecimal("100"))).intValue();
    }

    //还款方式:
    public String getReturnTypeDisplay(){
        return bidRequestType == Constants.RETURN_TYPE_MONTH_INTEREST_PRINCIPAL ? "按月分期还款":"按月到期还款";
    }

    //回显数据
    public String getJsonString(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("username",createUserId.getUsername());
        map.put("title",title);
        map.put("bidRequestAmount",bidRequestAmount);
        map.put("currentRate",currentRate);
        map.put("monthes2Return",monthes2Return);
        map.put("returnType",monthes2Return);
        map.put("totalRewardAmount",totalRewardAmount);
        return JSON.toJSONString(map);
    }

    //状态:
    public String getBidRequestStateDisplay(){
        switch (bidRequestState){
            case Constants.BIDREQUEST_STATE_PUBLISH_PENDING: return "待发布";
            case Constants.BIDREQUEST_STATE_BIDDING: return "招标中";
            case Constants.BIDREQUEST_STATE_UNDO: return "已撤销";
            case Constants.BIDREQUEST_STATE_BIDDING_OVERDUE: return "流标";
            case Constants.BIDREQUEST_STATE_APPROVE_PENDING_1: return "满标1审";
            case Constants.BIDREQUEST_STATE_APPROVE_PENDING_2: return "满标2审";
            case Constants.BIDREQUEST_STATE_REJECTED: return "满标审核被拒绝";
            case Constants.BIDREQUEST_STATE_PAYING_BACK: return "还款中";
            case Constants.BIDREQUEST_STATE_COMPLETE_PAY_BACK: return "已还清";
            case Constants.BIDREQUEST_STATE_PAY_BACK_OVERDUE: return "逾期";
            case Constants.BIDREQUEST_STATE_PUBLISH_REFUSE: return "发标审核拒绝状态";
        }
        return "未知";
    };
}
