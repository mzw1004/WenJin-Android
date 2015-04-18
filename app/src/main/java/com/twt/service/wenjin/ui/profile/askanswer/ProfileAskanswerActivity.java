package com.twt.service.wenjin.ui.profile.askanswer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
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
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.BaseActivity;
import com.twt.service.wenjin.ui.answer.AnswerActivity;
import com.twt.service.wenjin.ui.answer.detail.AnswerDetailDetailActivity;
import com.twt.service.wenjin.ui.common.OnItemClickListener;
import com.twt.service.wenjin.ui.question.QuestionActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProfileAskanswerActivity extends BaseActivity implements ProfileAskanswerView,OnItemClickListener {

    private static final String LOG_TAG = ProfileAskanswerActivity.class.getSimpleName();

    @Inject
    ProfileAskanswerPresenter _presenter;

    @InjectView(R.id.toolbar)
    Toolbar _toolbar;
    @InjectView(R.id.profile_askanswer_recycler_view)
    RecyclerView _recyclerView;

    private final static String ACTION_TYPE = "type";
    private static final String ACTION_TYPE_ASK = "ask";
    private static final String ACTION_TYPE_ANSWER = "answer";

    /*Activity类型:答过/问过*/
    private String _type;

    private ProfileAskanswerAdapter _adapter;


    public static void anctionStart(Context context,String type){
        Intent intent = new Intent(context,ProfileAskanswerActivity.class);
        intent.putExtra(ACTION_TYPE,type);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_askanswer);
        ButterKnife.inject(this);

        _type = getIntent().getStringExtra(ACTION_TYPE);
        if(_type.compareTo(ACTION_TYPE_ASK) == 0){
            _toolbar.setTitle(ResourceHelper.getString(R.string.action_type_questions));
        }
        if(_type.compareTo(ACTION_TYPE_ANSWER) == 0){
            _toolbar.setTitle(ResourceHelper.getString(R.string.action_type_answers));
        }
        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        _recyclerView.setLayoutManager(new LinearLayoutManager(this));
        _adapter = new ProfileAskanswerAdapter(this,_type,this);
        _recyclerView.setAdapter(_adapter);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        _recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastitemposition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastitemposition == linearLayoutManager.getItemCount() - 1 && dy > 0) {

                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        _presenter.refreshItems(_type);
    }

    @Override
    protected List<Object> getModlues() {
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
    public void addListData(Object items) {
        _adapter.addData((List<Object>)items);
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
        AnswerDetailDetailActivity.actionStart(this,item.answer_id,item.question_title);
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
