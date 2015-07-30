package com.twt.service.wenjin.ui.article.comment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.ArticleComment;
import com.twt.service.wenjin.support.FormatHelper;
import com.twt.service.wenjin.ui.BaseActivity;
import com.twt.service.wenjin.ui.common.OnItemClickListener;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by RexSun on 15/7/17.
 */
public class CommentActivlty extends BaseActivity implements CommentView, OnItemClickListener {

    private static final String PARAM_ARTICLE_ID = "article_id";
    private static final String PARAM_COMMENT_COUNT = "comment_count";
    @Inject
    CommentPresenter mPresenter;
    @InjectView(R.id.comment_recycler_view)
    RecyclerView commentRecyclerView;
    @InjectView(R.id.pb_comment_loading)
    ProgressBar pbCommentLoading;
    @InjectView(R.id.et_comment_content)
    EditText etCommentContent;
    @InjectView(R.id.iv_comment_publish)
    ImageView ivCommentPublish;
    @InjectView(R.id.ll_comment)
    LinearLayout llComment;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private int articleId;
    private int commentCount;

    private CommentAdapter commentAdapter;

    public static void actionStart(Context context, int articleId) {
        Intent intent = new Intent(context, CommentActivlty.class);
        intent.putExtra(PARAM_ARTICLE_ID, articleId);
        //intent.putExtra(PARAM_COMMENT_COUNT, commentCount);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        articleId = getIntent().getIntExtra(PARAM_ARTICLE_ID, 0);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        commentRecyclerView.setLayoutManager(linearLayoutManager);
        commentAdapter = new CommentAdapter(this, this);
        commentRecyclerView.setAdapter(commentAdapter);
        ivCommentPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.setClickable(false);
                mPresenter.publishComment(articleId, etCommentContent.getText().toString(), v);
                etCommentContent.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

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
    protected void onStart() {
        super.onStart();
        mPresenter.loadComment(articleId);
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new CommentModule(this));
    }

    @Override
    public void onItemClicked(View view, int position) {

        mPresenter.onItemClicked(view, position);
    }

    @Override
    public void bindComment(ArticleComment[] comments) {
        commentAdapter.updateData(comments);
    }

    @Override
    public void addAtUser(int position) {
        String content = FormatHelper.formatCommentAtUser(commentAdapter.getUserName(position));
        etCommentContent.setText(content);
        etCommentContent.setSelection(content.length());
    }

    @Override
    public void clearTextContent() {

        etCommentContent.setText("");
    }

    @Override
    public void showProgressBar() {
        pbCommentLoading.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgressBar() {

        pbCommentLoading.setVisibility(View.GONE);
    }

    @Override
    public void toastMessage(String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
