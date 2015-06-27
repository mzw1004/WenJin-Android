package com.twt.service.wenjin.ui.topic.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.BestAnswer;
import com.twt.service.wenjin.bean.Topic;
import com.twt.service.wenjin.ui.BaseActivity;
import com.twt.service.wenjin.ui.answer.comment.CommentAdapter;
import com.twt.service.wenjin.ui.common.OnItemClickListener;
import com.twt.service.wenjin.ui.common.TextDialogFragment;
import com.twt.service.wenjin.ui.profile.ProfileActivity;
import com.twt.service.wenjin.ui.question.QuestionActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TopicDetailActivity extends BaseActivity implements TopicDetailView, View.OnClickListener, OnItemClickListener {

    private static final String PARAM_TOPIC_ID = "topic_id";
    private static final String PARAM_TOPIC_TITLE = "topic_title";

    @Inject
    TopicDetailPresenter mPresenter;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.topic_detail_recycler_view)
    RecyclerView mRecyclerView;
    @InjectView(R.id.iv_topic_detail_pic)
    ImageView ivTopicPic;
    @InjectView(R.id.tv_topic_detail_title)
    TextView tvTitle;
    @InjectView(R.id.tv_topic_detail_description)
    TextView tvDescription;
    @InjectView(R.id.bt_topic_detail_focus)
    Button btFocus;

    private TopicDetailAdapter mAdapter;
    private int topicId;

    public static void actionStart(Context context, int topicId, String topicTitle) {
        Intent intent = new Intent(context, TopicDetailActivity.class);
        intent.putExtra(PARAM_TOPIC_ID, topicId);
        intent.putExtra(PARAM_TOPIC_TITLE, topicTitle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);
        ButterKnife.inject(this);

        topicId = getIntent().getIntExtra(PARAM_TOPIC_ID, 0);
        toolbar.setTitle(getIntent().getStringExtra(PARAM_TOPIC_TITLE));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TopicDetailAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        tvDescription.setOnClickListener(this);
        btFocus.setOnClickListener(this);
    }

    @Override
    protected List<Object> getModlues() {
        return Arrays.<Object>asList(new TopicDetailModule(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.getTopicDetail(topicId);
    }

    @Override
    public void bindTopicDetail(Topic topic) {
        tvTitle.setText(topic.topic_title);
        tvDescription.setText(topic.topic_description);
        if (!TextUtils.isEmpty(topic.topic_pic)) {
            Picasso.with(this).load(ApiClient.getTopicPicUrl(topic.topic_pic)).into(ivTopicPic);
        }
        if (topic.has_focus == 1) {
            addFocus();
        } else {
            removeFocus();
        }
        btFocus.setVisibility(View.VISIBLE);
    }

    @Override
    public void bindTopicBestAnswer(BestAnswer[] bestAnswers) {
        mAdapter.updateItem(bestAnswers);
    }

    @Override
    public void addFocus() {
        btFocus.setBackgroundResource(R.drawable.button_focused_background);
        btFocus.setText(getString(R.string.action_not_focus));
        btFocus.setTextColor(getResources().getColor(R.color.color_primary_dark));
    }

    @Override
    public void removeFocus() {
        btFocus.setBackgroundResource(R.drawable.button_focus);
        btFocus.setText(getString(R.string.action_focus));
        btFocus.setTextColor(getResources().getColor(android.R.color.white));
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startQuestionActivity(int position) {
        QuestionActivity.actionStart(this, mAdapter.getItem(position).question_info.question_id);
    }

    @Override
    public void startProfileActivity(int position) {
        ProfileActivity.actionStart(this, mAdapter.getItem(position).answer_info.uid);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_topic_detail_description:
                TextDialogFragment.newInstance(tvDescription.getText().toString()).show(this);
                break;
            case R.id.bt_topic_detail_focus:
                mPresenter.actionFocus();
                break;
        }
    }

    @Override
    public void onItemClicked(View view, int position) {
        switch (view.getId()) {
            case R.id.tv_explore_item_title:
                this.startQuestionActivity(position);
                break;
            case R.id.iv_explore_item_avatar:
                this.startProfileActivity(position);
                break;
        }
    }
}
