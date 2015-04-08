package com.twt.service.wenjin.api;

import android.net.Uri;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.twt.service.wenjin.WenJinApp;

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

    private static final AsyncHttpClient sClient = new AsyncHttpClient();
    private static final int DEFAULT_TIMEOUT = 20000;

    private static final String BASE_URL = "http://2014shequ.twtstudio.com/";
    private static final String LOGIN_URL = "?/api/account/login_process/";
    private static final String HOME_URL = "?/api/home/";
    private static final String TOPIC_URL = "?/api/topic/square/";
    private static final String QUESTION_URL = "?/api/question/question/";
    private static final String FOCUS_QUESTION_URL = "?/question/ajax/focus/";
    private static final String ANSWER_DETAIL_URL = "?/api/question/answer_detail/";
    private static final String ANSWER_VOTE_URL = "?/question/ajax/answer_vote/";
    private static final String PUBLISH_QUESTION_URL = "?/api/publish/publish_question/";
    private static final String ANSWER_URL = "?/api/publish/save_answer/";
    private static final String USER_INFO_URL = "?/api/account/get_userinfo/";
    private static final String COMMENT_URL = "api/answer_comment.php";
    private static final String PUBLISH_COMMENT_URL = "?/question/ajax/save_answer_comment/";

    private boolean isLogin;

    static {
        sClient.setTimeout(DEFAULT_TIMEOUT);
    }

    public static AsyncHttpClient getInstance() {
        return sClient;
    }

    public static void userLogin(String username, String password, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("user_name", username);
        params.put("password", password);

        sClient.post(BASE_URL + LOGIN_URL, params, handler);
    }

    public static void userLogout() {
        WenJinApp.getCookieStore().clear();
    }

    public static void publishQuestion(String title, String content, String attachKey, String topics, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("question_content", title);
        params.put("question_detail", content);
        params.put("attach_access_key", attachKey);
        params.put("topics", topics);

        sClient.post(BASE_URL + PUBLISH_QUESTION_URL, params, handler);
    }

    public static void getHome(int perPage, int page, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("per_page", perPage);
        params.put("page", page);

        sClient.get(BASE_URL + HOME_URL, params, handler);
    }

    public static void getTopics(String type, int page, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", type);
        params.put("page", page);

        sClient.get(BASE_URL + TOPIC_URL, params, handler);
    }

    public static void getQuestion(int questionId, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", questionId);

        sClient.get(BASE_URL + QUESTION_URL, params, handler);
    }

    public static void focusQuestion(int questionId, JsonHttpResponseHandler handler) {
        RequestParams params =  new RequestParams();
        params.put("question_id", questionId);

        sClient.get(BASE_URL + FOCUS_QUESTION_URL, params, handler);
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

        sClient.post(BASE_URL+ ANSWER_VOTE_URL, params, new JsonHttpResponseHandler());
    }

    public static void answer(int questionId, String content, String attachKey, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("question_id", questionId);
        params.put("answer_content", content);
        params.put("attach_access_key", attachKey);

        sClient.post(BASE_URL + ANSWER_URL, params, handler);
    }

    public static void getUserInfo(int uid, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);

        sClient.get(BASE_URL + USER_INFO_URL, params, handler);
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

}
