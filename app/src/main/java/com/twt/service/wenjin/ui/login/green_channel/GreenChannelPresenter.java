package com.twt.service.wenjin.ui.login.green_channel;

import com.twt.service.wenjin.api.ApiClient;

/**
 * Created by RexSun on 15/7/18.
 */
public class GreenChannelPresenter {

    private static final String LOG_TAG = GreenChannelPresenter.class.getSimpleName();
    private GreenChannelView greenChannelView;

    public GreenChannelPresenter(GreenChannelView greenChannelView) {
        this.greenChannelView = greenChannelView;
    }

    public void loadGreenChannel() {
        greenChannelView.showWebView(ApiClient.GREEN_CHANNEL_URL);
    }

}
