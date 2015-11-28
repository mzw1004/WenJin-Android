package com.twt.service.wenjin.support;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;

import com.twt.service.wenjin.ui.innerweb.InnerWebActivity;

/**
 * Created by Green on 15/11/23.
 */
public class TextviewUrlClickableBuilder {

    public static void BuildTextviewUrlClickable(IClickUrlLink iClickUrlLink,TextView textView){
        CharSequence text = textView.getText();
        if(text instanceof Spannable) {
            int end = text.length();
            Spannable sp = (Spannable) textView.getText();
            URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);
            SpannableStringBuilder style = new SpannableStringBuilder(text);
            style.clearSpans();
            for(URLSpan url:urls){
                ClickableURLSpan urlSpan = new ClickableURLSpan(iClickUrlLink,url.getURL());
                style.setSpan(urlSpan, sp.getSpanStart(url),sp.getSpanEnd(url), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            textView.setText(style);
        }
    }

    private static class ClickableURLSpan extends ClickableSpan {

        private String mUrl;
        private IClickUrlLink mIClickUrlLink;

        ClickableURLSpan(IClickUrlLink iClickUrlLink,String url){
            mUrl = url;
            mIClickUrlLink = iClickUrlLink;
        }

        @Override
        public void onClick(View widget) {
            mIClickUrlLink.onClickUrlLink(mUrl);
        }
    }

    public interface IClickUrlLink{
        void onClickUrlLink(String url);
    }
}
