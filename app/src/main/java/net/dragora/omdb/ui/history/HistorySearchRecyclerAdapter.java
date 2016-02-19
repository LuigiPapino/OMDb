package net.dragora.omdb.ui.history;

import android.content.Context;
import android.support.annotation.UiThread;
import android.view.ViewGroup;

import net.dragora.omdb.commons.UltimateRecyclerViewAdapterBase;
import net.dragora.omdb.commons.ViewWrapper;
import net.dragora.omdb.models.ResponseSearch;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

/**
 * Created by nietzsche on 19/02/16.
 */
@EBean
public class HistorySearchRecyclerAdapter extends UltimateRecyclerViewAdapterBase<ResponseSearch, HistorySearchItemView> {
    @RootContext
    Context context;

    @UiThread
    public void setItems(List<ResponseSearch> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    protected HistorySearchItemView onCreateItemView(ViewGroup parent, int viewType) {
        return HistorySearchItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<HistorySearchItemView> holder, int position) {
        HistorySearchItemView view = holder.getView();
        ResponseSearch item = items.get(position);
        view.bind(item);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
