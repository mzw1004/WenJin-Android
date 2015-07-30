package com.twt.service.wenjin.api;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.twt.service.wenjin.WenJinApp;
import com.twt.service.wenjin.support.DeviceUtils;
import com.twt.service.wenjin.support.PrefUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by M on 2015/3/23.
 */
public class ApiClient {

    /*
    接口总是会返回一个json，包含三个字段
    rsm：成功时包含返回的数据，失败时此字段为null
    errno：1-成功，2-失败
    err：成功时为null，失败时包含错误原因，可原样输出
     */
    public static final String RESP_MSG_KEY = "rsm";
    public static final String RESP_ERROR_CODE_KEY = "errno";
    public static final String RESP_ERROR_MSG_KEY = "err";

    public static final int SUCCESS_CODE = 1;
    public static final int ERROR_CODE = -1;

    private static AsyncHttpClient sClient = new AsyncHttpClient();
    private static final PersistentCookieStore sCookieStore = new PersistentCookieStore(WenJinApp.getContext());
    public static final int DEFAULT_TIMEOUT = 20000;

    private static final String BASE_URL = "http://wenjin.in/";
//    private static final String BASE_URL = "http://wenjin.test.twtstudio.com/";
    private static final String LOGIN_URL = "?/api/account/login_process/";
    public static final  String GREEN_CHANNEL_URL = "http://wenjin.in/account/green/";
    private static final String HOME_URL = "?/api/home/";
    private static final String EXPLORE_URL = "?/api/explore/";
    private static final String TOPIC_URL = "?/api/topic/square/";
    private static final String TOPIC_DETAIL_URL = "api/topic.php";
    private static final String TOPIC_BEST_ANSWER = "?/api/topic/topic_best_answer/";
    private static final String FOCUS_TOPIC_URL = "?/topic/ajax/focus_topic/";
    private static final String QUESTION_URL = "?/api/question/question/";
    private static final String FOCUS_QUESTION_URL = "?/question/ajax/focus/";
    private static final String ANSWER_DETAIL_URL = "?/api/question/answer_detail/";
    private static final String ANSWER_VOTE_URL = "?/question/ajax/answer_vote/";
    private static final String ANSWER_THANK_URL = "?/api/question/answer_vote/";
    private static final String UPLOAD_FILE_URL = "?/api/publish/attach_upload/";
    private static final String PUBLISH_QUESTION_URL = "?/api/publish/publish_question/";
    private static final String ANSWER_URL = "?/api/publish/save_answer/";
    private static final String USER_INFO_URL = "?/api/account/get_userinfo/";
    private static final String FOCUS_USER_URL = "?/follow/ajax/follow_people/";
    private static final String COMMENT_URL = "api/answer_comment.php";
    private static final String PUBLISH_COMMENT_URL = "?/question/ajax/save_answer_comment/";
    private static final String MY_ANSWER_URL = "api/my_answer.php";
    private static final String MY_QUESTION_URL = "api/my_question.php";
    private static final String FEEDBACK_URL = "?/api/ticket/publish/";
    private static final String CHECK_UPDATE_URL = "?/api/update/check/";
    private static final String MY_FOCUS_USER = "api/my_focus_user.php";
    private static final String MY_FANS_USER = "api/my_fans_user.php";
    private static final String PROFILE_EDIT_URL = "api/profile_setting.php";
    private static final String ARTICLE_ARTICLE_URL = "?/api/article/article/";
    private static final String ARTICLE_COMMENT_URL = "?/api/article/comment/";
    private static final String PUBLISH_ARTICLE_COMMENT_URL = "?/api/publish/save_comment/";
    private static final String ARTICLE_VOTE_URL = "?/article/ajax/article_vote/";
    private static final String AVATAR_UPLOAD_URL = "?/api/account/avatar_upload/";

    private static final String NOTIFICATIONS_URL = "?/api/notification/notifications/";
    private static final String NOTIFICATIONS_LIST_URL = "?/api/notification/list/";
    private static final String NOTIFICATIONS_MARKASREAD_URL = "?/api/notification/read_notification/";

    static {
        sClient.setTimeout(DEFAULT_TIMEOUT);
        sClient.setCookieStore(sCookieStore);
        sClient.addHeader("User-Agent", getUserAgent());
    }

    public static AsyncHttpClient getInstance() {
        return sClient;
    }

    public static String getUserAgent() {
        // User-Agent Wenjin/1.0.2 (Adnroid; 4.4.4; ...)
        String isRooted = DeviceUtils.isRooted() ? "rooted" : "unrooted";
        String userAgent = "Wenjin/" + DeviceUtils.getVersionName() + " (" +
                "Android; " +
                DeviceUtils.getSystemVersion() + "; " +
                DeviceUtils.getBrand() + "; " +
                DeviceUtils.getModel() + "; " +
                DeviceUtils.getNetworkType() + "; " +
                isRooted +
                ")";
        return userAgent;
    }

    public static void userLogin(String username, String password, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("user_name", username);
        params.put("password", password);

        sClient.post(BASE_URL + LOGIN_URL, params, handler);
    }

    public static void userLogout() {
        sCookieStore.clear();
        PrefUtils.setLogin(false);
    }

    public static void uploadFile(String type, String attachKey, File file, JsonHttpResponseHandler handler) {
        Uri url = Uri.parse(BASE_URL + UPLOAD_FILE_URL).buildUpon()
                .appendQueryParameter("id", type)
                .appendQueryParameter("attach_access_key", attachKey)
                .build();
        RequestParams params = new RequestParams();
        try {
            params.put("qqfile", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        sClient.post(url.toString(), params, handler);
    }

    public static void publishQuestion(String title, String content, String attachKey, String topics, boolean isAnonymous, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("question_content", title);
        params.put("question_detail", content);
        params.put("attach_access_key", attachKey);
        params.put("topics", topics);
        if (isAnonymous) {
            params.put("anonymous", 1);
        } else {
            params.put("anonymous", 0);
        }

        sClient.post(BASE_URL + PUBLISH_QUESTION_URL, params, handler);
    }

    public static void getHome(int perPage, int page, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("per_page", perPage);
        params.put("page", page);

        sClient.get(BASE_URL + HOME_URL, params, handler);
    }

    public static void getExplore(int perPage, int page, int day, int isRecommend, String sortType, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("per_page", perPage);
        params.put("page", page);
        params.put("day", day);
        params.put("is_recommend", isRecommend);
        params.put("sort_type", sortType);

        sClient.get(BASE_URL + EXPLORE_URL, params, handler);

    }

    public static void getTopics(String type, int page, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", type);
        params.put("page", page);

        sClient.get(BASE_URL + TOPIC_URL, params, handler);
    }

    public static void getTopicDetail(int topicId, int uid, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("topic_id", topicId);

        sClient.get(BASE_URL + TOPIC_DETAIL_URL, params, handler);
    }

    public static void getTopicBestAnswer(int topicId, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", topicId);

        sClient.get(BASE_URL + TOPIC_BEST_ANSWER, params, handler);
    }

    public static void focusTopic(int topicId, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("topic_id", topicId);

        sClient.get(BASE_URL + FOCUS_TOPIC_URL, params, handler);
    }

    public static String getTopicPicUrl(String url) {
        return BASE_URL + "uploads/topic/" + url;
    }

    public static void getQuestion(int questionId, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", questionId);

        sClient.get(BASE_URL + QUESTION_URL, params, handler);
    }

    public static void focusQuestion(int questionId, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("question_id", questionId);

        sClient.get(BASE_URL + FOCUS_QUESTION_URL, params, handler);
    }

    public static void getArticle(int articleId, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", articleId);
        sClient.get(BASE_URL + ARTICLE_ARTICLE_URL, params, handler);
    }

    public static void getArticleComment(int articleId, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", articleId);
        params.put("page", 0);
        sClient.get(BASE_URL + ARTICLE_COMMENT_URL, params, handler);
    }

    public static void publishArticleComment(int articleId, String message, JsonHttpResponseHandler handler) {
        //Uri url = Uri.parse(BASE_URL + PUBLISH_ARTICLE_COMMENT_URL).buildUpon().appendQueryParameter("articleId", String.valueOf(articleId)).build();
        RequestParams params = new RequestParams();
        params.put("article_id", articleId);
        params.put("message", message);
        sClient.post(BASE_URL + PUBLISH_ARTICLE_COMMENT_URL, params, handler);

    }

    public static void getAnswer(int answerId, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", answerId);

        sClient.get(BASE_URL + ANSWER_DETAIL_URL, params, handler);
    }

    public static void voteAnswer(int answerId, int value) {
        RequestParams params = new RequestParams();
        params.put("answer_id", answerId);
        params.put("value", value);
        sClient.post(BASE_URL + ANSWER_VOTE_URL, params, new JsonHttpResponseHandler());
    }

    public static void thankAnswer(int answerId, String type, JsonHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("answer_id", answerId);
        params.put("type", "thanks");

        sClient.post(BASE_URL + ANSWER_THANK_URL, params, new JsonHttpResponseHandler());
    }

    public static void voteArticle(int articleId, int value) {
        RequestParams params = new RequestParams();
        params.put("type", "article");
        params.put("item_id", articleId);
        params.put("rating", value);
        sClient.post(BASE_URL + ARTICLE_VOTE_URL, params, new JsonHttpResponseHandler());

    }

    public static void answer(int questionId, String content, String attachKey, boolean isAnonymous, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("question_id", questionId);
        params.put("answer_content", content);
        params.put("attach_access_key", attachKey);
        if (isAnonymous) {
            params.put("anonymous", 1);
        } else {
            params.put("anonymous", 0);
        }

        sClient.post(BASE_URL + ANSWER_URL, params, handler);
    }

    public static void getUserInfo(int uid, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);

        sClient.get(BASE_URL + USER_INFO_URL, params, handler);
    }

    public static void focusUser(int uid, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);

        sClient.get(BASE_URL + FOCUS_USER_URL, params, handler);
    }

    public static String getAvatarUrl(String url) {
        return BASE_URL + "uploads/avatar/" + url;
    }

    public static void getComments(int answerId, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", answerId);

        sClient.get(BASE_URL + COMMENT_URL, params, handler);
    }

    public static void publishComment(int answerId, String content, JsonHttpResponseHandler handler) {
        Uri url = Uri.parse(BASE_URL + PUBLISH_COMMENT_URL).buildUpon()
                .appendQueryParameter("answer_id", String.valueOf(answerId))
                .build();
        RequestParams params = new RequestParams();
//        params.put("answer_id", answerId);
        params.put("message", content);

        sClient.post(url.toString(), params, handler);
    }

    public static void getMyAnswer(int uid, int page, int perPage, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("page", page);
        params.put("per_page", perPage);

        sClient.get(BASE_URL + MY_ANSWER_URL, params, handler);
    }

    public static void getMyQuestion(int uid, int page, int perPage, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("page", page);
        params.put("per_page", perPage);

        sClient.get(BASE_URL + MY_QUESTION_URL, params, handler);
    }

    public static void publishFeedback(String title, String message, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("title", title);
        params.put("message", message);
        params.put("version", DeviceUtils.getVersionName());
        params.put("system", DeviceUtils.getSystemVersion());
        params.put("source", DeviceUtils.getSource());

        sClient.post(BASE_URL + FEEDBACK_URL, params, handler);
    }

    public static void getMyFocusUser(int uid,int page,int perPage,JsonHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("uid",uid);
        params.put("page",page);
        params.put("per_page",perPage);
        sClient.get(BASE_URL + MY_FOCUS_USER, params, handler);
    }

    public static void checkNewVersion(String version, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("version", version);

        sClient.post(BASE_URL + CHECK_UPDATE_URL, params, handler);
    }

    public static void getMyFansUser(int uid,int page,int perPage,JsonHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("uid",uid);
        params.put("page",page);
        params.put("per_page",perPage);
        sClient.get(BASE_URL + MY_FANS_USER, params, handler);
    }

    public static void editProfile(int uid, String username, String signature, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("nick_name", username);
        if (!TextUtils.isEmpty(signature)) {
            params.put("signature", signature);
        }

        sClient.post(BASE_URL + PROFILE_EDIT_URL, params, handler);
    }

    public static void avatarUpload(int uid,  String user_avatar, JsonHttpResponseHandler handler) throws FileNotFoundException {
        RequestParams params = new RequestParams();
            params.put("user_avatar", new File(user_avatar));
        sClient.post(BASE_URL + AVATAR_UPLOAD_URL, params, handler);
    }

    public static void getNotificationsNumberInfo(long argTimestampNow, JsonHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("time", argTimestampNow);
        sClient.get(BASE_URL + NOTIFICATIONS_URL, params, handler);
    }

    public static void getNotificationsList(int argPageNum, int argIsUnreadFlag, JsonHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("page", argPageNum);
        params.put("flag", argIsUnreadFlag);  //0:未读  1:已读

        sClient.get(BASE_URL + NOTIFICATIONS_LIST_URL, params, handler);
    }

    public static void setNotificationsMarkasread(int argNotificationId, JsonHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("notification_id", argNotificationId);

        sClient.get(BASE_URL + NOTIFICATIONS_MARKASREAD_URL, params, handler);
    }

    public static void setNotificationsMarkAllasread(JsonHttpResponseHandler handler){
        sClient.get(BASE_URL + NOTIFICATIONS_MARKASREAD_URL,handler);
    }
}
