package com.twt.service.wenjin.ui.answer.detail;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import android.util.Log;
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
import com.twt.service.wenjin.support.UmengShareHelper;
import com.twt.service.wenjin.ui.BaseActivity;
import com.twt.service.wenjin.ui.answer.comment.CommentActivity;
import com.twt.service.wenjin.ui.common.PicassoImageGetter;
import com.twt.service.wenjin.ui.main.MainActivity;
import com.twt.service.wenjin.ui.profile.ProfileActivity;
import com.twt.service.wenjin.ui.question.QuestionActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnswerDetailActivity extends BaseActivity implements AnswerDetailView, View.OnClickListener,ObservableScrollViewCallbacks {

    private static final String LOG_TAG = AnswerDetailActivity.class.getSimpleName();

    private static final String PARAM_ANSWER_ID = "answer_id";
    private static final String PARAM_QUESTION = "question";


    private static final int VOTE_STATE_UPVOTE = 1;
    private static final int VOTE_STATE_DOWNVOTE = -1;
    private static final int VOTE_STATE_NONE = 0;


    @Inject
    AnswerDetailPresenter mPresenter;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_answer_title)
    TextView tvAnswerTitle;
    @Bind(R.id.pb_answer_loading)
    ProgressBar mPbLoading;
    @Bind(R.id.iv_answer_avatar)
    ImageView ivAvatar;
    @Bind(R.id.iv_answer_agree)
    ImageView ivAgree;
    @Bind(R.id.tv_answer_agree_number)
    TextView tvAgreeNumber;
    @Bind(R.id.tv_answer_username)
    TextView tvUsername;
    @Bind(R.id.tv_answer_signature)
    TextView tvSignature;
    @Bind(R.id.tv_answer_content)
    TextView tvContent;
    @Bind(R.id.tv_answer_add_time)
    TextView tvAddTime;
    @Bind(R.id.obscroll)
    ObservableScrollView scrollView;
    @Bind(R.id.answer_detail_head)
    View answer_detail_head;
    @Bind(R.id.fl_bottom_actions)
    View fy_bottom_actions;
    @Bind(R.id.v_container_answer_agree)
    View vContainerAnswerAgree;

    @Bind(R.id.iv_bottom_action_thank)
    ImageView ivBottomActionThank;
    @Bind(R.id.iv_bottom_action_upvote)
    ImageView ivBottomActionUpvote;
    @Bind(R.id.iv_bottom_action_downvote)
    ImageView ivBottomActionDownvote;
    @Bind(R.id.rl_container_bottom_action_comment)
    View rlContainerBottomActionComment;
    @Bind(R.id.iv_bottom_action_comment)
    ImageView ivBottomActionComment;
    @Bind(R.id.tv_bottom_action_comment_count)
    TextView tvBottomActionCommentCount;


    private int answerId;
    private Answer answer;
    private int uid;
    private int questionId;

    private int mIntentNotiFlag;

    private boolean isThank = false;

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
        ButterKnife.bind(this);
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
        ivBottomActionUpvote.setOnClickListener(this);
        rlContainerBottomActionComment.setOnClickListener(this);
        ivBottomActionDownvote.setOnClickListener(this);
        ivBottomActionThank.setOnClickListener(this);
        vContainerAnswerAgree.setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PARAM_ANSWER_ID, answerId);
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new AnswerDetailModule(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_answer_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_share:
                UmengShareHelper.init(this);
                UmengShareHelper.setContent(
                        this,
                        getIntent().getStringExtra(PARAM_QUESTION),
                        FormatHelper.formatQuestionLink(answer.question_id)
                );

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_answer_avatar:
                if (this.answer.uid == -1){
                    Toast.makeText(this, getResources().getString(R.string.not_exist),Toast.LENGTH_SHORT).show();
                }else {
                    startProfileActivity();
                }
                break;
            case R.id.tv_answer_username:
                if (this.answer.uid == -1){
                    Toast.makeText(this, getResources().getString(R.string.not_exist),Toast.LENGTH_SHORT).show();
                }else {
                    startProfileActivity();
                }
                break;
            case R.id.rl_container_bottom_action_comment:
                if (!tvBottomActionCommentCount.getText().toString().equals("…")) {
                    startCommentActivity();
                }
                break;
            case R.id.v_container_answer_agree:
                mPresenter.actionVote(answerId, 1);
                break;
            case R.id.iv_bottom_action_thank:
                mPresenter.actionThank(answerId);
                break;
            case R.id.iv_bottom_action_upvote:
                mPresenter.actionVote(answerId, 1);
                break;
            case R.id.iv_bottom_action_downvote:
                mPresenter.actionDownVote(answerId, -1);
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
        this.answer = answer;
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
            ivBottomActionUpvote.setImageResource(R.drawable.ic_action_agreed);
            ivBottomActionDownvote.setImageResource(R.drawable.ic_action_disagree);
        }else if(answer.vote_value == -1){
            ivBottomActionDownvote.setImageResource(R.drawable.ic_action_disagreed);
            ivAgree.setImageResource(R.drawable.ic_action_agree);
            ivBottomActionUpvote.setImageResource(R.drawable.ic_action_agree);
        } else {
            ivAgree.setImageResource(R.drawable.ic_action_agree);
            ivBottomActionUpvote.setImageResource(R.drawable.ic_action_agree);
            ivBottomActionDownvote.setImageResource(R.drawable.ic_action_disagree);
        }
        if(answer.thank_value == 1){
            ivBottomActionThank.setImageResource(R.drawable.ic_action_favorited);
        }else {
            ivBottomActionThank.setImageResource(R.drawable.ic_action_favorite);
        }

        tvAgreeNumber.setText("" + answer.agree_count);
        LogHelper.d(LOG_TAG, "answer detail: " + answer.answer_content);
        tvContent.setText(Html.fromHtml(answer.answer_content, new PicassoImageGetter(this, tvContent), null));
        tvContent.setMovementMethod(LinkMovementMethod.getInstance());
        tvAddTime.setText(FormatHelper.formatAddDate(answer.add_time));
        tvBottomActionCommentCount.setText("" + answer.comment_count);
        fy_bottom_actions.setVisibility(View.VISIBLE);
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
    public void setAgree(int voteState, int agreeCount) {
        if (voteState == VOTE_STATE_UPVOTE) {
            ivAgree.setImageResource(R.drawable.ic_action_agreed);
            ivBottomActionUpvote.setImageResource(R.drawable.ic_action_agreed);
            ivBottomActionDownvote.setImageResource(R.drawable.ic_action_disagree);
        } else {
            ivAgree.setImageResource(R.drawable.ic_action_agree);
            ivBottomActionUpvote.setImageResource(R.drawable.ic_action_agree);
        }
        tvAgreeNumber.setText("" + agreeCount);
    }

    @Override
    public void setDisAgree(int voteState) {
        if(voteState == VOTE_STATE_DOWNVOTE){
            ivBottomActionDownvote.setImageResource(R.drawable.ic_action_disagreed);

            ivAgree.setImageResource(R.drawable.ic_action_agree);
            ivBottomActionUpvote.setImageResource(R.drawable.ic_action_agree);
        }else{
            ivBottomActionDownvote.setImageResource(R.drawable.ic_action_disagree);
        }
    }

    @Override
    public void setAgreeCount(int agreeCount) {
        tvAgreeNumber.setText("" + agreeCount);
    }

    @Override
    public void setThank(boolean isThank) {
        if(isThank){
            ivBottomActionThank.setImageResource(R.drawable.ic_action_favorited);
        }else {
            ivBottomActionThank.setImageResource(R.drawable.ic_action_favorite);
        }
    }


    @Override
    public void startProfileActivity() {
        ProfileActivity.actionStart(this, answer.uid);
    }

    @Override
    public void startCommentActivity() {
        CommentActivity.actionStart(this, answerId, Integer.parseInt(tvBottomActionCommentCount.getText().toString()));
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
        return fy_bottom_actions.getVisibility() == View.VISIBLE;
    }

    private boolean toobarIsHidden(){
        return fy_bottom_actions.getVisibility() == View.GONE;
    }

    private void showToolbar(){
        fy_bottom_actions.setTranslationY(fy_bottom_actions.getHeight());
        fy_bottom_actions.setVisibility(View.VISIBLE);
        fy_bottom_actions.setAlpha(0.0f);
        fy_bottom_actions.animate()
                .translationY(0)
                .alpha(1.0f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        fy_bottom_actions.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void hideToolbar(){
        fy_bottom_actions.animate()
                .translationY(fy_bottom_actions.getHeight())
                .alpha(0.0f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        fy_bottom_actions.setVisibility(View.GONE);
                    }
                });

    }

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
