package net.dragora.omdb.ui.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.dragora.omdb.R;
import net.dragora.omdb.models.Search;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by nietzsche on 19/02/16.
 */
@EViewGroup(R.layout.search_movie_item_view)
public class SearchMovieItemView extends RelativeLayout {
    @ViewById
    ImageView image;
    @ViewById
    TextView title;
    private Search search;

    public SearchMovieItemView(Context context) {
        super(context);
    }

    public SearchMovieItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchMovieItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SearchMovieItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void bind(@NonNull Search search) {
        this.search = search;
        Glide.with(getContext())
                .load(search.getPoster())
                .fallback(R.drawable.ic_photo_blue_200_48dp)
                .placeholder(R.drawable.ic_photo_blue_200_48dp)
                .crossFade()
                .into(image);
        title.setText(search.getTitle());
    }
}
