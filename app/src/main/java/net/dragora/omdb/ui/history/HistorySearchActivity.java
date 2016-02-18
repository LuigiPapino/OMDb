package net.dragora.omdb.ui.history;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.dragora.omdb.MyApplication;
import net.dragora.omdb.R;
import net.dragora.omdb.commons.BaseActivity;
import net.dragora.omdb.network.NetworkApi;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.internal.util.SubscriptionList;
import rx.schedulers.Schedulers;

@EActivity(R.layout.activity_history_search)
public class HistorySearchActivity extends BaseActivity {

    @Inject
    NetworkApi networkApi;

    public HistorySearchActivity() {
        MyApplication.getInstance().getGraph().inject(this);

    }

    SubscriptionList subscriptionList = new SubscriptionList();
    @AfterViews
    protected void setup() {

        subscriptionList.add(networkApi.getAndFetchSearch("giant")
                .subscribe(search -> {
                    logD(search.toString());
                })
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        subscriptionList.unsubscribe();
    }
}
