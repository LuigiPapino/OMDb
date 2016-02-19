package net.dragora.omdb.ui.search;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.view.ViewGroup;

import net.dragora.omdb.commons.UltimateRecyclerViewAdapterBase;
import net.dragora.omdb.commons.ViewWrapper;
import net.dragora.omdb.models.ResponseSearch;
import net.dragora.omdb.models.Search;
import net.dragora.omdb.ui.history.HistorySearchItemView;
import net.dragora.omdb.ui.history.HistorySearchItemView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nietzsche on 19/02/16.
 */
@EBean
public class SearchMovieRecyclerAdapter extends UltimateRecyclerViewAdapterBase<Search, SearchMovieItemView> {
    @RootContext
    Context context;

    @UiThread
    public void setItems(@Nullable List<Search> items) {
        if (items != null)
            this.items = items;
        else
            this.items = new ArrayList<>(0);
        notifyDataSetChanged();
    }

    @Override
    protected SearchMovieItemView onCreateItemView(ViewGroup parent, int viewType) {
        return SearchMovieItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<SearchMovieItemView> holder, int position) {
        SearchMovieItemView view = holder.getView();
        Search item = items.get(position);
        view.bind(item);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
