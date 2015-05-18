package com.twt.service.wenjin.ui.draft;

import com.activeandroid.query.Select;
import com.twt.service.wenjin.bean.AnswerDraft;

import java.util.List;

/**
 * Created by M on 2015/5/17.
 */
public class DraftPresenterImpl implements DraftPresenter {

    private DraftView mView;

    public DraftPresenterImpl(DraftView view) {
        this.mView = view;
    }

    @Override
    public void getDrafts() {
        List<AnswerDraft> drafts = new Select().from(AnswerDraft.class).execute();
        if (drafts.size() > 0) {
            mView.hideHintText();
            mView.showRecyclerView(drafts);
        }
    }
}
