package com.twt.service.wenjin.ui.notification;

/**
 * Created by Green on 15-6-8.
 */
public class NotificationType {

    public static int  TYPE_PEOPLE_FOCUS = 101;  // 被人关注
    public static int  TYPE_NEW_ANSWER   = 102;  // 关注的问题增加了新回复 q
    public static int  TYPE_COMMENT_AT_ME    = 103;  // 有评论@提到我
    public static int  TYPE_INVITE_QUESTION  = 104;  // 被人邀请回答 q
    public static int  TYPE_ANSWER_COMMENT   = 105;  // 我的回复被评论
    public static int  TYPE_QUESTION_COMMENT = 106;  // 我的问题被评论 q
    public static int  TYPE_ANSWER_AGREE = 107;  // 我的回复收到赞同 a
    public static int  TYPE_ANSWER_THANK = 108;  // 我的回复收到感谢 a
    public static int  TYPE_MOD_QUESTION = 110;  // 我发布的问题被编辑 q
    public static int  TYPE_REMOVE_ANSWER    = 111;  // 我发表的回复被删除

    public static int  TYPE_REDIRECT_QUESTION = 113;  // 我发布的问题被重定向
    public static int  TYPE_QUESTION_THANK = 114;  // 我发布的问题收到感谢 q
    public static int  TYPE_CONTEXT  = 100;  // 纯文本通知

    public static int  TYPE_ANSWER_AT_ME = 115;  // 有回答 @ 提到我 a
    public static int  TYPE_ANSWER_COMMENT_AT_ME = 116;  // 有回答评论 @ 提到我 a

    public static int  TYPE_ARTICLE_NEW_COMMENT  = 117; // 文章有新评论
    public static int  TYPE_ARTICLE_COMMENT_AT_ME    = 118; // 文章评论提到我

    public static int  TYPE_ARTICLE_APPROVED = 131; // 文章通过审核
    public static int  TYPE_ARTICLE_REFUSED = 132; // 文章未通过审核
    public static int  TYPE_QUESTION_APPROVED = 133; // 问题通过审核
    public static int  TYPE_QUESTION_REFUSED = 134; // 问题未通过审核

    public static int  TYPE_TICKET_REPLIED = 141; // 工单被回复
    public static int  TYPE_TICKET_CLOSED = 142; // 工单被关闭
}
