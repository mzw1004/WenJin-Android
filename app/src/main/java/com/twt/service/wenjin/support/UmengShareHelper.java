package com.twt.service.wenjin.support;

import android.app.Activity;
import android.content.Context;

import com.twt.service.wenjin.R;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

/**
 * Created by M on 2015/4/29.
 */
public class UmengShareHelper {

    private static final String WEIXIN_APP_ID = "wx65ec2ecbdebe90bb";

    private static final String WEIXIN_APP_SECRET = "97fecfb6b53a769d0b3e367c15a7463d";

    private static UMSocialService sUmSocialService = UMServiceFactory.getUMSocialService("com.umeng.share");

    public static UMSocialService getUmSocialService() {
        return sUmSocialService;
    }

    public static void init(Activity context) {
        addWeiXin(context);
        addWeiXinCircle(context);
        sUmSocialService.openShare(context, false);
    }

    public static void addWeiXin(Context context) {
        UMWXHandler umwxHandler = new UMWXHandler(context, WEIXIN_APP_ID, WEIXIN_APP_SECRET);
        umwxHandler.addToSocialSDK();
    }

    public static void addWeiXinCircle(Context context) {
        UMWXHandler umCircleHandler = new UMWXHandler(context, WEIXIN_APP_ID, WEIXIN_APP_SECRET);
        umCircleHandler.setToCircle(true);
        umCircleHandler.addToSocialSDK();
    }

    public static void addSina() {
    }

    public static void setContent(Context context, String content, String url) {
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent.setShareContent(content);
        weixinContent.setTitle(ResourceHelper.getString(R.string.app_name));
        weixinContent.setShareImage(new UMImage(context, R.drawable.ic_share_logo));
        weixinContent.setTargetUrl(url);
        sUmSocialService.setShareMedia(weixinContent);
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setShareContent(content);
        circleMedia.setTitle(content);
        circleMedia.setShareImage(new UMImage(context, R.drawable.ic_share_logo));
        circleMedia.setTargetUrl(url);
        sUmSocialService.setShareMedia(circleMedia);

        sUmSocialService.setShareContent(content + url);
    }
}
