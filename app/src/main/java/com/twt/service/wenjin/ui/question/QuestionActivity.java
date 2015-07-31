package com.twt.service.wenjin.ui.question;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.Answer;
import com.twt.service.wenjin.bean.QuestionInfo;
import com.twt.service.wenjin.bean.QuestionResponse;
import com.twt.service.wenjin.support.FormatHelper;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.support.UmengShareHelper;
import com.twt.service.wenjin.ui.BaseActivity;
import com.twt.service.wenjin.ui.answer.AnswerActivity;
import com.twt.service.wenjin.ui.answer.detail.AnswerDetailActivity;
import com.twt.service.wenjin.ui.common.OnItemClickListener;
import com.twt.service.wenjin.receiver.JPushNotiReceiver;
import com.twt.service.wenjin.ui.main.MainActivity;
import com.twt.service.wenjin.ui.profile.ProfileActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class QuestionActivity extends BaseActivity implements QuestionView, OnItemClickListener {

    private static final String LOG_TAG = QuestionActivity.class.getSimpleName();

    private static final String PARAM_QUESTION_ID = "question_id";


    @Inject
    QuestionPresenter mPresenter;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.question_recycler_view)
    RecyclerView mRecyclerView;
    @InjectView(R.id.pb_question_loading)
    ProgressBar mPbLoading;

    private QuestionAdapter mQuestionAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private int questionId;
    private int mIntentFlag;

//    private UMSocialService umSocialService = UMServiceFactory.getUMSocialService("com.umeng.share");

//    private UMSocialService umSocialService = UMServiceFactory.getUMSocialService("com.umeng.share");

    public static void actionStart(Context context, int questionId) {
        Intent intent = new Intent(context, QuestionActivity.class);
        intent.putExtra(PARAM_QUESTION_ID, questionId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        questionId = getIntent().getIntExtra(PARAM_QUESTION_ID, 0);
        mIntentFlag = getIntent().getIntExtra(JPushNotiReceiver.INTENT_FLAG_NOTIFICATION, 0 );
        LogHelper.v(LOG_TAG, "question id:" + questionId);
        mPresenter.loadingContent(questionId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_answer:
                this.startAnswerActivity();
                break;
            case R.id.action_share:
                UmengShareHelper.init(this);
                UmengShareHelper.setContent(
                        this,
                        mQuestionAdapter.getQuestionInfo().question_content,
                        FormatHelper.formatQuestionLink(mQuestionAdapter.getQuestionInfo().question_id)
                );
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new QuestionModule(this));
    }

    @Override
    public void onItemClicked(View view, int position) {
        switch (view.getId()) {
            case R.id.tag_group_question:
                toastMessage("tag clicked");
                break;
            case R.id.bt_question_focus:
                mPresenter.actionFocus(mQuestionAdapter.getQuestionInfo().question_id);
                break;
            case R.id.iv_question_answer_avatar:
                if (mQuestionAdapter.getAnswer(position).uid == -1){
                    Toast.makeText(this, ResourceHelper.getString(R.string.not_exist), Toast.LENGTH_SHORT).show();
                }else {
                    startProfileActivity(position);
                }
                break;
            case R.id.tv_question_answer_username:
                if(mQuestionAdapter.getAnswer(position).uid == -1){
                    Toast.makeText(this, ResourceHelper.getString(R.string.not_exist), Toast.LENGTH_SHORT).show();
                }else {
                    startProfileActivity(position);
                }
                break;
            case R.id.tv_question_answer_content:
                startAnswerDetailActivty(position);
                break;
        }
    }

    @Override
    public void setAdapter(QuestionResponse questionResponse) {
        mQuestionAdapter = new QuestionAdapter(this, questionResponse, this);
        mRecyclerView.setAdapter(mQuestionAdapter);
    }

    @Override
    public void setFocus(boolean isFocus) {
        mQuestionAdapter.setFocused(isFocus);
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
    public void startAnswerDetailActivty(int position) {
        Answer answer = mQuestionAdapter.getAnswer(position);
        QuestionInfo questionInfo = mQuestionAdapter.getQuestionInfo();
        AnswerDetailActivity.actionStart(this, answer.answer_id, questionInfo.question_content);
    }

    @Override
    public void startAnswerActivity() {
        AnswerActivity.actionStart(this, questionId, mQuestionAdapter.getQuestionInfo().question_content);
    }

    @Override
    public void startProfileActivity(int position) {
        Answer answer = mQuestionAdapter.getAnswer(position);
        ProfileActivity.actionStart(this, answer.uid);
    }

}
