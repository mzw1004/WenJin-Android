package com.twt.service.wenjin.ui.answer.comment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.Comment;
import com.twt.service.wenjin.support.FormatHelper;
import com.twt.service.wenjin.ui.BaseActivity;
import com.twt.service.wenjin.ui.common.OnItemClickListener;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CommentActivity extends BaseActivity implements CommentView, OnItemClickListener {

    private static final String PARAM_ANSWER_ID = "answer_id";
    private static final String PARAM_COMMENT_COUNT = "comment_count";

    @Inject
    CommentPresenter mPresenter;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.comment_recycler_view)
    RecyclerView mRecyclerView;
    @InjectView(R.id.et_comment_content)
    EditText etContent;
    @InjectView(R.id.iv_comment_publish)
    ImageView ivPublish;
    @InjectView(R.id.pb_comment_loading)
    ProgressBar pbLoading;

    private int answerId;
    private int commentCount;

    private CommentAdapter mAdapter;

    public static void actionStart(Context context, int answerId, int commentCount) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra(PARAM_ANSWER_ID, answerId);
        intent.putExtra(PARAM_COMMENT_COUNT, commentCount);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.inject(this);

        answerId = getIntent().getIntExtra(PARAM_ANSWER_ID, 0);
        commentCount = getIntent().getIntExtra(PARAM_COMMENT_COUNT, 0);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new CommentAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        ivPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.setClickable(false);
                mPresenter.publishComment(answerId, etContent.getText().toString(), v);
                etContent.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (commentCount > 0) {
            mPresenter.loadComments(answerId);
        }
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new CommentModule(this));
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
    public void bindComment(Comment[] comments) {
        mAdapter.updateData(comments);
    }

    @Override
    public void addAtUser(int position) {
        String content = FormatHelper.formatCommentAtUser(mAdapter.getUsername(position));
        etContent.setText(content);
        etContent.setSelection(content.length());
    }

    @Override
    public void clearTextContent() {
        etContent.setText("");
    }

    @Override
    public void showProgressBar() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClicked(View view, int position) {
        mPresenter.onItemClicked(view, position);
    }
}
