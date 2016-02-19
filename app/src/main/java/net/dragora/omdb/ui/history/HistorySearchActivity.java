package net.dragora.omdb.ui.history;

import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import net.dragora.omdb.MyApplication;
import net.dragora.omdb.R;
import net.dragora.omdb.commons.BaseActivity;
import net.dragora.omdb.data.HistoryStore;
import net.dragora.omdb.models.ResponseSearch;
import net.dragora.omdb.network.NetworkApi;
import net.dragora.omdb.ui.search.SearchMovieActivity;
import net.dragora.omdb.ui.search.SearchMovieActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

@EActivity(R.layout.history_search_activity)
@OptionsMenu(R.menu.menu_history)
public class HistorySearchActivity extends BaseActivity {

    @VisibleForTesting
    @Inject
    public NetworkApi networkApi;
    @Inject
    HistoryStore historyStore;

    @OptionsMenuItem
    MenuItem actionSearch;
    SearchView searchView;
    @ViewById
    Toolbar toolbar;
    @ViewById
    UltimateRecyclerView ultimateRecyclerView;
    @ViewById
    FrameLayout searchDetailContainer;
    @Bean
    HistorySearchRecyclerAdapter historySearchRecyclerAdapter;
    Subscription subscriptionSearch, subscriptionHistory;
    ItemTouchHelper.Callback swipeDismissCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            final int index = viewHolder.getAdapterPosition();
            final ResponseSearch search = historySearchRecyclerAdapter.getItems().get(index);
            historyStore.delete(search.getKeyword());
            Snackbar.make(ultimateRecyclerView, getString(R.string.search_deleted, search.getKeyword()), Snackbar.LENGTH_LONG)
                    .setAction(R.string.undo, v -> historyStore.put(search.getKeyword(), search))
                    .show();
        }
    };
    private ItemTouchHelper itemTouchHelper;
    private String filter;

    public HistorySearchActivity() {
        MyApplication.getInstance().getGraph().inject(this);

    }

    @AfterViews
    protected void setup() {
        setSupportActionBar(toolbar);
        ultimateRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ultimateRecyclerView.setEmptyView(R.layout.history_search_empty);
        ultimateRecyclerView.setAdapter(historySearchRecyclerAdapter);

        itemTouchHelper = new ItemTouchHelper(swipeDismissCallback);
        itemTouchHelper.attachToRecyclerView(ultimateRecyclerView.mRecyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateHistory();
    }

    private void updateHistory() {
        Observable<Map<String, ResponseSearch>> observable = historyStore.getHistory();
        if (subscriptionHistory != null)
            subscriptionHistory.unsubscribe();
        subscriptionHistory =
                observable.map(map -> {
                    ArrayList<ResponseSearch> list = new ArrayList<ResponseSearch>(map.size());
                    for (Map.Entry<String, ResponseSearch> entry :
                            map.entrySet()) {
                        ResponseSearch search = entry.getValue();
                        search.setKeyword(entry.getKey());
                        if (TextUtils.isEmpty(filter) || entry.getKey().contains(filter))
                            list.add(search);
                    }
                    return list;
                })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(list -> {
                            Collections.sort(list, (lhs, rhs) -> lhs.getKeyword().compareToIgnoreCase(rhs.getKeyword()));
                            historySearchRecyclerAdapter.setItems(list);
                        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        searchView = (SearchView) actionSearch.getActionView();
        searchView.setQueryHint(getString(R.string.query_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter = newText;
                updateHistory();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (subscriptionSearch != null)
            subscriptionSearch.unsubscribe();
        if (subscriptionHistory != null)
            subscriptionHistory.unsubscribe();
    }

    public void startSearch(String query) {
        if (isTwoPane()) {
            SearchMovieActivity.addSearchFragment(this, query);
        } else {
            SearchMovieActivity_.intent(this)
                    .query(query)
                    .start();
        }

    }

    public boolean isTwoPane() {
        return searchDetailContainer != null;
    }
}
