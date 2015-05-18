package com.twt.service.wenjin.ui.draft;

import com.twt.service.wenjin.bean.AnswerDraft;

import java.util.List;

/**
 * Created by M on 2015/5/17.
 */
public interface DraftView {

    void showHintText();

    void hideHintText();

    void showRecyclerView(List<AnswerDraft> drafts);

}
