package net.dragora.omdb.ui.search;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import net.dragora.omdb.MyApplication;
import net.dragora.omdb.R;
import net.dragora.omdb.commons.BaseFragment;
import net.dragora.omdb.network.NetworkApi;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.internal.util.SubscriptionList;

/**
 * Created by nietzsche on 19/02/16.
 */
@EFragment(R.layout.search_movie_fragment)
public class SearchMovieFragment extends BaseFragment {

    @FragmentArg
    String query;
    @ViewById
    UltimateRecyclerView ultimateRecyclerView;

    @Bean
    SearchMovieRecyclerAdapter adapter;

    @Inject
    NetworkApi networkApi;
    private SubscriptionList subscriptions = new SubscriptionList();

    public SearchMovieFragment() {
        MyApplication.getInstance().getGraph().inject(this);
    }

    @AfterViews
    protected void setup() {
        ultimateRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ultimateRecyclerView.setEmptyView(R.layout.search_movie_empty);
        ultimateRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        setSearch();
    }

    public void setSearch() {
        setRefreshing(true);
        if (subscriptions != null) {
            subscriptions.unsubscribe();
        }
        subscriptions = new SubscriptionList();

        subscriptions.add(
                networkApi.getSearchNetworkError()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::showError)
        );
        subscriptions.add(
                networkApi.getAndFetchSearch(query)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(search -> {
                            adapter.setItems(search.getSearches());
                            setRefreshing(false);
                        })
        );
    }


    private void showError(String error) {
        new AlertDialog.Builder(getActivity(), R.style.MyDialog)
                .setTitle(R.string.error)
                .setMessage(TextUtils.isEmpty(error) ? getString(R.string.unknown_error) : error)
                .setPositiveButton(R.string.ok, null)
                .show();
        setRefreshing(false);
    }

    @Override
    public void onPause() {
        if (subscriptions != null)
            subscriptions.unsubscribe();
        setRefreshing(false);
        super.onPause();
    }

    private void setRefreshing(final boolean isRefreshing) {
        if (ultimateRecyclerView != null)
            ultimateRecyclerView.post(() -> {
                if (ultimateRecyclerView != null)
                    ultimateRecyclerView.setRefreshing(isRefreshing);
            });
    }
}
