package net.dragora.omdb.ui.history;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.dragora.omdb.R;
import net.dragora.omdb.models.ResponseSearch;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by nietzsche on 19/02/16.
 */
@EViewGroup(R.layout.history_search_item_view)
public class HistorySearchItemView extends LinearLayout {
    @ViewById
    TextView keyword;
    @ViewById
    LinearLayout mainLayout;
    private ResponseSearch search;

    public HistorySearchItemView(Context context) {
        super(context);
    }

    public HistorySearchItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HistorySearchItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public HistorySearchItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void bind(@NonNull ResponseSearch search) {
        this.search = search;
        this.keyword.setText(search.getKeyword());
    }

    @Click
    protected void keyword() {
        if (getContext() instanceof HistorySearchActivity)
            ((HistorySearchActivity) getContext()).startSearch(search.getKeyword());
    }
}
