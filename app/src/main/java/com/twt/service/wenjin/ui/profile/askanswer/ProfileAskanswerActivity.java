package com.twt.service.wenjin.ui.profile.askanswer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.MyAnswer;
import com.twt.service.wenjin.bean.MyQuestion;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.BaseActivity;
import com.twt.service.wenjin.ui.answer.detail.AnswerDetailActivity;
import com.twt.service.wenjin.ui.common.OnItemClickListener;
import com.twt.service.wenjin.ui.question.QuestionActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileAskanswerActivity extends BaseActivity implements ProfileAskanswerView,OnItemClickListener {

    private static final String LOG_TAG = ProfileAskanswerActivity.class.getSimpleName();

    @Inject
    ProfileAskanswerPresenter _presenter;

    @Bind(R.id.toolbar)
    Toolbar _toolbar;
    @Bind(R.id.profile_askanswer_recycler_view)
    RecyclerView _recyclerView;

    private final static String ACTION_TYPE = "type";
    private static final String ACTION_TYPE_ASK = "ask";
    private static final String ACTION_TYPE_ANSWER = "answer";
    private static final String USER_ID = "uid";
    private static final String USER_NAME = "uname";
    private static final String USER_AVATAR_URL = "avatarurl";

    /*Activity类型:答过/问过*/
    private String _type;

    private ProfileAskanswerAdapter _adapter;

    private int _uid;
    private String _uname;
    private String _avatarurl;

    public static void anctionStart(Context context,String type,int uid,String userName,String avatarUrl){
        Intent intent = new Intent(context,ProfileAskanswerActivity.class);
        intent.putExtra(ACTION_TYPE,type);
        intent.putExtra(USER_ID,uid);
        intent.putExtra(USER_NAME,userName);
        intent.putExtra(USER_AVATAR_URL,avatarUrl);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_askanswer);
        ButterKnife.bind(this);

        _type = getIntent().getStringExtra(ACTION_TYPE);
        _uid = getIntent().getIntExtra(USER_ID,0);
        _uname = getIntent().getStringExtra(USER_NAME);
        _avatarurl = getIntent().getStringExtra(USER_AVATAR_URL);

        if(_type.compareTo(ACTION_TYPE_ASK) == 0){
            _toolbar.setTitle(ResourceHelper.getString(R.string.action_type_questions));
        }
        if(_type.compareTo(ACTION_TYPE_ANSWER) == 0){
            _toolbar.setTitle(ResourceHelper.getString(R.string.action_type_answers));
        }
        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        _recyclerView.setLayoutManager(linearLayoutManager);
        _adapter = new ProfileAskanswerAdapter(this,_type,_uid,_uname,_avatarurl,this);
        _recyclerView.setAdapter(_adapter);

        _recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastitemposition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastitemposition == linearLayoutManager.getItemCount() - 1 && dy > 0) {
                    _presenter.loadMoreItems(_type,_uid);
                }
            }
        });

        _presenter.loadMoreItems(_type,_uid);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new ProfileAskanswerModule(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_askanswer, menu);
        return true;
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
    public void toastMessage(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refreshListData(Object items) {
        _adapter.updateData((List<Object>)items);
    }

    @Override
    public void addListData(Object items,int totalRows) {
        if(_adapter.getItemCount() < totalRows) {
            _adapter.addData((List<Object>) items);
        }else{
            toastMessage(ResourceHelper.getString(R.string.no_more_information));
        }
    }

    @Override
    public void startQuestionActivity(int position) {
        if(_type.compareTo(ACTION_TYPE_ASK) == 0){
            MyQuestion item = (MyQuestion)_adapter.getItem(position);
            QuestionActivity.actionStart(this, item.id);
        }

        if(_type.compareTo(ACTION_TYPE_ANSWER) == 0){
            MyAnswer item = (MyAnswer)_adapter.getItem(position);
            QuestionActivity.actionStart(this,item.question_id);
        }

    }

    @Override
    public void startAnswerActivity(int position) {
        MyAnswer item = (MyAnswer)_adapter.getItem(position);
        AnswerDetailActivity.actionStart(this, item.answer_id, item.question_title);
    }

    @Override
    public void showFooter() {
        _adapter.setUseFooter(true);
    }

    @Override
    public void hideFooter() {
        _adapter.setUseFooter(false);
    }

    @Override
    public void onItemClicked(View view, int position) {
        _presenter.onItemClicked(view, position);
    }
}
