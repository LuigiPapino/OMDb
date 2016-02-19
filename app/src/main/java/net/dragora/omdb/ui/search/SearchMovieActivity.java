package net.dragora.omdb.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.FrameLayout;

import net.dragora.omdb.R;
import net.dragora.omdb.commons.BaseActivity;
import net.dragora.omdb.ui.history.HistorySearchActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

/**
 * Created by nietzsche on 19/02/16.
 */
@EActivity(R.layout.search_movie_activity)
public class SearchMovieActivity extends BaseActivity {

    @Extra
    String query;

    @ViewById
    Toolbar detailToolbar;
    @ViewById
    AppBarLayout appBar;
    @ViewById
    FrameLayout searchDetailContainer;

    private boolean shouldAddFragment = false;

    public static void addSearchFragment(BaseActivity activity, String query) {

        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.search_detail_container,
                        SearchMovieFragment_.
                                builder()
                                .query(query)
                                .build()
                )
                .commit();
    }

    @AfterViews
    protected void setup() {
        logD("setup() called with: " + "");
        setSupportActionBar(detailToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(query);
        }
        if (shouldAddFragment) {
            addSearchFragment(this, query);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            shouldAddFragment = true;
        }
    }

    @OptionsItem(android.R.id.home)
    protected void menuBack() {
        navigateUpTo(new Intent(this, HistorySearchActivity_.class));
    }

}
