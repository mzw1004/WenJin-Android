package com.twt.service.wenjin.ui.answer.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.squareup.picasso.Picasso;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.Answer;
import com.twt.service.wenjin.receiver.JPushNotiReceiver;
import com.twt.service.wenjin.support.FormatHelper;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.BaseActivity;
import com.twt.service.wenjin.ui.answer.comment.CommentActivity;
import com.twt.service.wenjin.ui.common.PicassoImageGetter;
import com.twt.service.wenjin.ui.main.MainActivity;
import com.twt.service.wenjin.ui.profile.ProfileActivity;
import com.twt.service.wenjin.ui.question.QuestionActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AnswerDetailActivity extends BaseActivity implements AnswerDetailView, View.OnClickListener,ObservableScrollViewCallbacks {

    private static final String LOG_TAG = AnswerDetailActivity.class.getSimpleName();

    private static final String PARAM_ANSWER_ID = "answer_id";
    private static final String PARAM_QUESTION = "question";

    @Inject
    AnswerDetailPresenter mPresenter;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tv_answer_title)
    TextView tvAnswerTitle;
    @InjectView(R.id.pb_answer_loading)
    ProgressBar mPbLoading;
    @InjectView(R.id.iv_answer_avatar)
    ImageView ivAvatar;
    @InjectView(R.id.iv_answer_agree)
    ImageView ivAgree;
    @InjectView(R.id.tv_answer_agree_number)
    TextView tvAgreeNumber;
    @InjectView(R.id.tv_answer_username)
    TextView tvUsername;
    @InjectView(R.id.tv_answer_signature)
    TextView tvSignature;
    @InjectView(R.id.tv_answer_content)
    TextView tvContent;
    @InjectView(R.id.tv_answer_add_time)
    TextView tvAddTime;
    @InjectView(R.id.obscroll)
    ObservableScrollView scrollView;
    @InjectView(R.id.answer_detail_head)
    View answer_detail_head;



    private TextView tvCommentCount;

    private int answerId;
    private int uid;
    private int questionId;

    private int mIntentNotiFlag;

    public static void actionStart(Context context, int answerId, String question) {
        Intent intent = new Intent(context, AnswerDetailActivity.class);
        intent.putExtra(PARAM_ANSWER_ID, answerId);
        intent.putExtra(PARAM_QUESTION, question);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_detail);
        ButterKnife.inject(this);
        scrollView.setScrollViewCallbacks(this);

        if (savedInstanceState != null) {
            answerId = savedInstanceState.getInt(PARAM_ANSWER_ID);
        } else {
            answerId = getIntent().getIntExtra(PARAM_ANSWER_ID, 0);
        }
        LogHelper.v(LOG_TAG, "answer id: " + answerId);

        String question = getIntent().getStringExtra(PARAM_QUESTION);

        mIntentNotiFlag = getIntent().getIntExtra(JPushNotiReceiver.INTENT_FLAG_NOTIFICATION,0);

        toolbar.setTitle(R.string.title_activity_answer_detail);
        if(mIntentNotiFlag == 0){
            tvAnswerTitle.setText(question);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPresenter.loadAnswer(answerId);

        ivAgree.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
        tvUsername.setOnClickListener(this);
        tvAnswerTitle.setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PARAM_ANSWER_ID, answerId);
    }

    @Override
    protected List<Object> getModlues() {
        return Arrays.<Object>asList(new AnswerDetailModule(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_answer_detail, menu);
        View menuComment = menu.findItem(R.id.action_comment).getActionView();
        menuComment.setOnClickListener(this);
        tvCommentCount = (TextView) menuComment.findViewById(R.id.tv_action_comment_number);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_answer_agree:
                mPresenter.actionVote(answerId, 1);
                break;
            case R.id.iv_answer_avatar:
                startProfileActivity();
                break;
            case R.id.tv_answer_username:
                startProfileActivity();
                break;
            case R.id.action_comment:
                if (!tvCommentCount.getText().toString().equals("…")) {
                    startCommentActivity();
                }
                break;
            case R.id.tv_answer_title:
                if(questionId > 0) {
                    startQuestionActivity();
                }
                finish();
                break;
        }
    }

    @Override
    public void showProgressBar() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mPbLoading.setVisibility(View.GONE);
    }

    @Override
    public void bindAnswerData(Answer answer) {
        uid = answer.uid;
        questionId = answer.question_id;

        if(mIntentNotiFlag == JPushNotiReceiver.INTENT_FLAG_NOTIFICATION_VALUE){
            mPresenter.loadTitle(answer.question_id);
        }

        if (!TextUtils.isEmpty(answer.avatar_file)) {
            Picasso.with(this).load(ApiClient.getAvatarUrl(answer.avatar_file)).into(ivAvatar);
        }
        tvUsername.setText(answer.nick_name);
        tvSignature.setText(answer.signature);
        ivAgree.setVisibility(View.VISIBLE);
        if (answer.vote_value == 1) {
            ivAgree.setImageResource(R.drawable.ic_action_agreed);
        } else {
            ivAgree.setImageResource(R.drawable.ic_action_agree);
        }
        tvAgreeNumber.setText("" + answer.agree_count);
        tvContent.setText(Html.fromHtml(answer.answer_content, new PicassoImageGetter(this, tvContent), null));
        tvContent.setMovementMethod(LinkMovementMethod.getInstance());
        tvAddTime.setText(FormatHelper.formatAddDate(answer.add_time));
        tvCommentCount.setText("" + answer.comment_count);

    }

    @Override
    public void bindTitle(String title) {
        tvAnswerTitle.setText(title);
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setAgree(boolean isAgree, int agreeCount) {
        if (isAgree) {
            ivAgree.setImageResource(R.drawable.ic_action_agreed);
        } else {
            ivAgree.setImageResource(R.drawable.ic_action_agree);
        }
        tvAgreeNumber.setText("" + agreeCount);
    }

    @Override
    public void startProfileActivity() {
        ProfileActivity.actionStart(this, uid);
    }

    @Override
    public void startCommentActivity() {
        CommentActivity.actionStart(this, answerId, Integer.parseInt(tvCommentCount.getText().toString()));
    }

    @Override
    public void startQuestionActivity() {
        QuestionActivity.actionStart(this, questionId);
    }

    @Override
    public void onScrollChanged(int i, boolean b, boolean b1) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        if(scrollState == ScrollState.UP){
            if(toolbarIsShown()){
                hideToolbar();
            }
        }else if(scrollState == scrollState.DOWN){
            if(toobarIsHidden()){
                showToolbar();
            }
        }
    }

    private boolean toolbarIsShown(){
        return ViewHelper.getTranslationY(toolbar) == 0;
    }

    private boolean toobarIsHidden(){
        return ViewHelper.getTranslationY(toolbar) == -toolbar.getHeight();
    }

    private void showToolbar(){ moveToolbar(0);}

    private void hideToolbar(){moveToolbar(-toolbar.getHeight());}

    private void moveToolbar(final float toTranslationY){
        if (ViewHelper.getTranslationY(toolbar) == toTranslationY) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(ViewHelper.getTranslationY(toolbar), toTranslationY).setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float translationY = (float) animation.getAnimatedValue();
                ViewHelper.setTranslationY(toolbar, translationY);
                ViewHelper.setTranslationY( scrollView, translationY);
                ViewHelper.setTranslationY(answer_detail_head, translationY);
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) scrollView.getLayoutParams();
                lp.height = (int) -translationY + getScreenHight() - lp.topMargin;
                scrollView.requestLayout();
            }
        });
        animator.start();
    }

    private int getScreenHight(){
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        return display.heightPixels;
    }




}
