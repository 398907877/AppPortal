package com.huake.saas.baidu.entity;


import java.io.Serializable;



/**消息实体
 * @author wujiajun
 */
public class Message implements Serializable{
    private Long id;//消息id
    private String crDate;//发送时间
    private String upDate;//更新时间 2014-04-30 14:14:05
    private String status;//消息状态： 0：无效 1：有效未读，2：已读，
    private String title;//标题
    private String message;//内容

    //com:普通类型，s_reply:服务端回复反馈，c_back:客户端反馈信息
    // feeback_reply：到意见反馈模块 coupon：到我的优惠券列表 ship（发货）
    // mention（自提）：订单表
    private String category;//消息类型
    private Long memberId;//会员id
    private Long mark;//门店Id（暂无用）
    private Long userId;//官方用户（暂存放关联实体的id）
    private String genre;//=新华都" 发送方

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCrDate() {
        return crDate;
    }

    public void setCrDate(String crDate) {
        this.crDate = crDate;
    }

    public String getUpDate() {
        return upDate;
    }

    public void setUpDate(String upDate) {
        this.upDate = upDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMark() {
        return mark;
    }

    public void setMark(Long mark) {
        this.mark = mark;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }


}
