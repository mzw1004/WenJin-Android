package com.twt.service.wenjin.ui.article;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.Article;
import com.twt.service.wenjin.support.BusProvider;
import com.twt.service.wenjin.support.FormatHelper;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.support.UmengShareHelper;
import com.twt.service.wenjin.ui.BaseActivity;
import com.twt.service.wenjin.ui.article.comment.CommentActivlty;
import com.twt.service.wenjin.ui.common.PicassoImageGetter;
import com.twt.service.wenjin.ui.profile.ProfileActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by RexSun on 15/7/16.
 */
public class ArticleActivity extends BaseActivity implements ArticleView, View.OnClickListener {

    private static final String LOG_TAG = ArticleActivity.class.getSimpleName();

    private static final String PARAM_ARTICLE_ID = "article_id";
    private static final String PARAM_ARTICLE = "article";

    private static final int VOTE_STATE_UPVOTE = 1;
    private static final int VOTE_STATE_DOWNVOTE = -1;
    private static final int VOTE_STATE_NONE = 0;
    @Inject
    ArticlePresenter mPresenter;
    @InjectView(R.id.tv_article_title)
    TextView tvArticleTitle;
    @InjectView(R.id.iv_article_avatar)
    CircleImageView ivArticleAvatar;
    @InjectView(R.id.tv_article_username)
    TextView tvArticleUsername;
    @InjectView(R.id.tv_article_content)
    TextView tvArticleContent;
    @InjectView(R.id.iv_article_agree)
    ImageView ivArticleAgree;
    @InjectView(R.id.tv_article_agree)
    TextView tvArticleAgree;
    @InjectView(R.id.iv_article_disagree)
    ImageView ivArticleDisagree;
    @InjectView(R.id.divider)
    View divider;
    @InjectView(R.id.iv_article_comment)
    ImageView ivArticleComment;
    @InjectView(R.id.tv_article_comment)
    TextView tvArticleComment;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tv_article_agree_count)
    TextView tvArticleAgreeCount;
    @InjectView(R.id.pb_article_loading)
    ProgressBar pbArticleLoading;
    @InjectView(R.id.ll_article_comment)
    LinearLayout llArticleComment;

    private int articleId;
    private Article article;

    public static void actionStart(Context context, int articleId, String article) {
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtra(PARAM_ARTICLE_ID, articleId);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("文章");
        articleId = getIntent().getIntExtra(PARAM_ARTICLE_ID, 0);
        LogHelper.i(LOG_TAG, "article id: " + articleId);
        mPresenter.loadArticle(articleId);
        ivArticleAgree.setOnClickListener(this);
        ivArticleDisagree.setOnClickListener(this);
        llArticleComment.setOnClickListener(this);
        ivArticleAvatar.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        BusProvider.getBusInstance().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        BusProvider.getBusInstance().unregister(this);
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new ArticleModule(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article, menu);
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
                        article.article_info.title,
                        FormatHelper.formatArticleLink(article.article_info.id)
                );

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_article_agree:
                mPresenter.actionVote(articleId, 1);
                break;
            case R.id.iv_article_disagree:
                mPresenter.actionDownVote(articleId, -1);
                break;
            case R.id.ll_article_comment:
                startCommentActivity();
                break;
            case R.id.iv_article_avatar:
                startProfileActivity();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PARAM_ARTICLE_ID, articleId);
    }

    @Override
    public void showProgressBar() {
        pbArticleLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        pbArticleLoading.setVisibility(View.GONE);

    }

    @Override
    public void bindArticleData(Article article) {
        this.article = article;
        if (!TextUtils.isEmpty(article.article_info.avatar_file)) {
            Picasso.with(this).load(ApiClient.getAvatarUrl(article.article_info.avatar_file)).into(ivArticleAvatar);
        }
        ivArticleAgree.setVisibility(View.VISIBLE);
        ivArticleDisagree.setVisibility(View.VISIBLE);
        tvArticleUsername.setText(article.article_info.nick_name);
        if (article.article_info.vote_value == 1) {
            ivArticleAgree.setImageResource(R.drawable.ic_action_agreed);
            tvArticleAgreeCount.setTextColor(getResources().getColor(R.color.color_did));
            tvArticleAgree.setTextColor(getResources().getColor(R.color.color_did));
        } else if (article.article_info.vote_value == -1) {
            ivArticleDisagree.setImageResource(R.drawable.ic_action_disagreed);
        }
        tvArticleTitle.setText(article.article_info.title);
        tvArticleContent.setText(Html.fromHtml(article.article_info.message, new PicassoImageGetter(this, tvArticleContent), null));
        tvArticleAgreeCount.setText(String.valueOf(article.article_info.votes));
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setAgree(int voteState, int agreeCount) {

        if (voteState == VOTE_STATE_UPVOTE) {
            ivArticleAgree.setImageResource(R.drawable.ic_action_agreed);
            tvArticleAgreeCount.setTextColor(getResources().getColor(R.color.color_did));
            tvArticleAgree.setTextColor(getResources().getColor(R.color.color_did));
            ivArticleDisagree.setImageResource(R.drawable.ic_action_disagree);
        } else {
            ivArticleAgree.setImageResource(R.drawable.ic_action_agree);
            tvArticleAgreeCount.setTextColor(getResources().getColor(R.color.color_text_secondary));
            tvArticleAgree.setTextColor(getResources().getColor(R.color.color_text_secondary));
        }
        tvArticleAgreeCount.setText(String.valueOf(agreeCount));
    }

    @Override
    public void setDisagree(int voteState) {
        if (voteState == VOTE_STATE_DOWNVOTE) {
            ivArticleDisagree.setImageResource(R.drawable.ic_action_disagreed);
            tvArticleAgreeCount.setTextColor(getResources().getColor(R.color.color_text_secondary));
            tvArticleAgree.setTextColor(getResources().getColor(R.color.color_text_secondary));
            ivArticleAgree.setImageResource(R.drawable.ic_action_agree);
        } else {
            ivArticleDisagree.setImageResource(R.drawable.ic_action_disagree);
        }
    }

    @Override
    public void startProfileActivity() {
        ProfileActivity.actionStart(this, article.article_info.uid);

    }

    @Override
    public void startCommentActivity() {
        CommentActivlty.actionStart(this, articleId);
    }

    @Override
    public void setAgreeCount(int agreeCount) {
        tvArticleAgreeCount.setText(String.valueOf(agreeCount));
    }


}
